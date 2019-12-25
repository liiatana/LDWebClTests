package ru.lanit.ld.wc.appmanager;

import com.codeborne.selenide.Selenide;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import ru.lanit.ld.wc.model.*;

import java.util.ArrayList;

public class RestApiHelper {

    private String cookies;
    private String apiPath;

    public RestApiHelper(UserInfo user, ApplicationManager applicationManager) {

        apiPath = applicationManager.properties.getProperty("web.apiUrl");

        Response response = RestAssured
                .given().header("Authorization", user.getAuth())
                .get(String.format("%sauth/basic", apiPath));
        cookies = String.format("landocs.claims=%s; landocs_sessionid=%s", response.getCookie("landocs.claims"), response.getCookie("landocs_sessionid"));

    }

    public JsonElement me() {

        String json = RestAssured
                .given().header("Cookie", cookies)
                .get(String.format("%sme", apiPath))
                .asString();

        return new JsonParser().parse(json);


    }

    public InstructionTypes instructionTypesInfo() { //public ArrayList<InstructionType> instructionTypesInfo() {
        String json = RestAssured
                .given().header("Cookie", cookies)
                .get(String.format("%sinstruction/typesinfo", apiPath))
                .asString();


        JsonElement parsed = new JsonParser().parse(json);

        ArrayList<InstructionType> iTypes = new ArrayList<>();

        for (int i = 0; i <= parsed.getAsJsonArray().size() - 1; i++) {
            JsonElement types1 = parsed.getAsJsonArray().get(i).getAsJsonObject().get("instructionType");
            InstructionType itype = new Gson().fromJson(types1, InstructionType.class);

            JsonElement r2 = parsed.getAsJsonArray().get(i).getAsJsonObject().get("receiverTypes").getAsJsonArray();
            int[] rt = new Gson().fromJson(r2, new TypeToken<int[]>() {
            }.getType());

            itype.receiverTypes = rt;
            iTypes.add(itype);
        }

        InstructionTypes types = new InstructionTypes(iTypes);

        return types; //       return iTypes;
    }


    public instResponse createInstruction(Instruction src, boolean send) { //сообщение

        String operation = send ? "send" : "";
        String res = RestAssured
                .given().header("Cookie", cookies)
                .contentType("application/json")
                //.cookie(cookies)
                .body(src.toJson(true).toString())
                .when()
                .post(String.format("%sinstruction/%s", apiPath, operation))
                .asString();

        JsonElement parsed = new JsonParser().parse(res);
        JsonObject asJsonObject = parsed.getAsJsonObject().getAsJsonObject("success");

        return new Gson().fromJson(asJsonObject, new TypeToken<instResponse>() {
        }.getType());

    }

    public reportResponse createReport(Report src, boolean send) {// проект отчета

        String operation = send ? "createInstruction" : "project";

        String res = RestAssured
                .given().header("Cookie", cookies)
                .contentType("application/json")
                //.cookie(cookies)
                .body(src.toJson().toString())
                .when()
                .post(String.format("%sinstruction/%s/report/%s", apiPath, src.getInstructionId(), operation))
                .asString();

        JsonElement parsed = new JsonParser().parse(res);
        JsonObject asJsonObject = parsed.getAsJsonObject().getAsJsonObject("success");

        return new Gson().fromJson(asJsonObject, new TypeToken<reportResponse>() {
        }.getType());
    }

    public void makeHomeAsLastUrl() {

        JsonObject lastVisitedUrl = new JsonObject();
        lastVisitedUrl.addProperty("lastVisitedUrl", "/instructions/1999");

        /*Response response = */
        RestAssured
                .given().header("Cookie", cookies)
                .contentType("application/json")
                .body(lastVisitedUrl.toString())
                .when()
                .put(String.format("%sme/lasturl", apiPath));

    }

    public FolderList getFolderList(int folder) {

        String data ;//= "{\"top\": \"50\",\"skip\":0,\"searchText\":null,\"members\":null,\"filterId\":null,\"filterValues\":null } ";

        data=String.format("{\"top\": 100,\"skip\":0,\"searchText\":null,\"members\":null,\"filterId\":null,\"filterValues\":null } ");

        String res = RestAssured
                .given().header("Cookie", cookies)
                .contentType("application/json")
                .body(data)
                .when()
                .post(String.format("%sinstructions/folder/%s/instructions", apiPath, folder))
                //instructions/folder/2107/instructions
                .asString();

        JsonElement parsed = new JsonParser().parse(res);
        FolderList folderList = new FolderList(parsed);

        return folderList;

    }


    public void setReaded(boolean b, int id) {
        JsonObject lastVisitedUrl = new JsonObject();
        lastVisitedUrl.addProperty("IsReaded", b);

        //Response response =
        RestAssured
                .given().header("Cookie", cookies)
                .contentType("application/json")
                .body(lastVisitedUrl.toString())
                .when()
                .put(String.format("%sinstruction/%s/operations/readed", apiPath, id));

        //response.getStatusCode();
    }

    public Instruction getInstruction(int instructionId) {

        String json = RestAssured
                .given().header("Cookie", cookies)
                .get(String.format("%sinstruction/%s", apiPath,instructionId))
                .asString();


        JsonElement parsed = new JsonParser().parse(json);
        return new Instruction(parsed);
    }

    public Instruction getLastInstruction(int inFolder) {
        Selenide.sleep(3000);
        Instruction lastInstruction = this.getFolderList(inFolder).items.get(0);
        return this.getInstruction(lastInstruction.getInstructionId());
    }


    public void setViewState(viewState defaultViewState, String section, int folder) {

        JsonObject currentState = new JsonObject();

        /*Response response = */
        RestAssured
                .given().header("Cookie", cookies)
                .contentType("application/json")
                .body(defaultViewState.toJson().toString())
                .when()
                .put(String.format("%sviewstate?key=%s-%s", apiPath,section,folder));

    }

    public void LastUrl(String url) {

        JsonObject lastVisitedUrl = new JsonObject();
        lastVisitedUrl.addProperty("lastVisitedUrl",url );//"/instructions/1999"

        /*Response response = */
        RestAssured
                .given().header("Cookie", cookies)
                .contentType("application/json")
                .body(lastVisitedUrl.toString())
                .when()
                .put(String.format("%sme/lasturl", apiPath));

    }

    public String getBackVersion() {
        Response response  = RestAssured
                //.given().header("Cookie", cookies)
                .get(String.format("%sadmin/apiversion", apiPath));

        return response.getBody().print();

    }
}
