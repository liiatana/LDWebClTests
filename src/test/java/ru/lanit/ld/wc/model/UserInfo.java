package ru.lanit.ld.wc.model;

import ru.lanit.ld.wc.appmanager.RestApiHelper;

import java.util.Objects;

public class UserInfo {

    private int id;
    private String Login;
    private String UserName;
    private String Password;
    private String Auth;
    private boolean isAdmin;
    private RestApiHelper UserApi;

    private InstructionTypes userTypes;


    public UserInfo() {
    }

    public UserInfo(String login, String password) {
        Login = login;
        Password = password;
    }

    public String getAuth() {
        return Auth;
    }

    public UserInfo withAuth(String auth) {
        this.Auth = auth;
        return this;
    }

    public int getId() {
        return id;
    }

    public UserInfo withId(int id) {
        this.id = id;
        return this;
    }
    public UserInfo withLogin(String Login) {
        this.Login = Login;
        return this;
    }

    public String getLogin() {
        return Login;
    }

    public String getUserName() {
        return UserName;
    }

    public UserInfo withUserName(String userName) {
        this.UserName = userName;
        return this;
    }

    public String getPassword() {
        return Password;
    }

    public UserInfo withPassword(String password) {
        this.Password = password;
        return this;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public UserInfo withisAdmin(boolean admin) {
        this.isAdmin = admin;
        return this;
    }

    public RestApiHelper getUserApi() {
        return UserApi;
    }


    public String getLastName() {

        return this.getUserName().split(" ")[0].toUpperCase();
    }

    public UserInfo withUserApi(RestApiHelper userApi) {
        this.UserApi = userApi;
        return this;
    }

    public InstructionTypes getUserTypes() {
        return userTypes;
    }

    public UserInfo withUserTypes(InstructionTypes userTypes) {
        this.userTypes = userTypes;
        return this;
    }







    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return id == userInfo.id &&
                Objects.equals(UserName, userInfo.UserName) &&
                Objects.equals(Password, userInfo.Password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, UserName, Password);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "Login='" + Login + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
