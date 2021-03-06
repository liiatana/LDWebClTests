package ru.lanit.ld.wc.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.appmanager.RestApiHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Users {
    public List<UserInfo> users;
    private Logger logger = LoggerFactory.getLogger(Users.class);


    public Users() {
    }

    public Users(List<UserInfo> users) {
        this.users = users;
    }

    public void load(ApplicationManager app) throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(app.properties.getProperty("data.usersFilePath"))))) {
            String line = reader.readLine();
            String json = "";
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            //gson.fromJson(json, GroupData.class);//писать в кач-ве второго параметра GroupData.class можно,
            // но это будет только один объект, а нам нужен список таких объектов. А написать List нельзя((( не работаеть..
            // поэтому не шибюко понятное действие должно выглядеть так:
            List<UserInfo> usrs = gson.fromJson(json, new TypeToken<List<UserInfo>>() {
            }.getType());

            this.users = usrs;
        }


        for (UserInfo user : this.users) {
            user.withAuth("Basic " + Base64.encodeBase64String(String.format("%s:%s", user.getLogin(), user.getPassword()).getBytes()));

            user.withUserApi(new RestApiHelper(user, app));
            JsonElement parsed = user.getUserApi().me();
            user.withId(parsed.getAsJsonObject().get("effectiveUserId").getAsInt()) // id пользователя
                    .withUserName(parsed.getAsJsonObject().get("name").getAsString()) //ФИО
                    .withisAdmin(parsed.getAsJsonObject().get("isAdmin").getAsBoolean()); //если он админ
            user.withUserTypes(user.getUserApi().instructionTypesInfo());

        }


    }


    public Users anyUsers(int numberOfReceivers, UserInfo excludedUser) {

        List<UserInfo> usersCollection = new ArrayList<UserInfo>();
        usersCollection.addAll(this.users);

        if (excludedUser != null) {
            usersCollection.remove(excludedUser);
        }

        Collections.shuffle(usersCollection);

        return  new Users (usersCollection.subList(0, numberOfReceivers));
    }

    public UserInfo getAnyUser() {

        List<UserInfo> usersCollection = new ArrayList<UserInfo>();
        usersCollection.addAll(this.users);
        Collections.shuffle(usersCollection);

        return usersCollection.stream()
                 .findAny().orElse(null);
    }




    public int[] Ids() {

        int[] ids = new int[this.users.size()];
        for (int i = 0; i < this.users.size(); i++) {
            ids[i] = this.users.get(i).getId();
        }
        return ids;

    }

    public UserInfo getUserById(int Id) {
        return this.users.stream()
                .filter(x -> x.getId()==Id)
                .findAny().orElse(null);
    }

    public UserInfo getUserIdByFIO(String FIOpart) {
        return this.users.stream()
                .filter(x -> x.getUserName().toUpperCase().contains(FIOpart.toUpperCase()))
                .findAny().orElse(null);
    }

    public void stopAllMaintainSession(){
        for (int i = 0; i < this.users.size(); i++) {
            this.users.get(i).getUserApi().session.interrupt();
             logger.info ("Stop maintain session for user with login="+ this.users.get(i).getLogin());
        }
    }

    public UserInfo getAnyAdmin(){
        Collections.shuffle(this.users);
        return this.users.stream()
                .filter(x -> x.isAdmin() == true)
                .findAny().orElse(null);
    }
}