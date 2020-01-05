package ru.lanit.ld.wc.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.lanit.ld.wc.enums.RefreshMessageDefaultSettings;
import ru.lanit.ld.wc.enums.SendTypes;

import java.time.format.DateTimeFormatter;

public class MessageDefaultSettings {
    private int initiatorSettings;
    private int initiatorID;

    private int reportReceiverSettings;
    private int reportReceiverID;

    private int execAuditorSettings;
    private int execAuditorID;

    private int beginning;//: 0
    private int ending; //: 0

    private int sendType;

    private boolean withResponsive;
    private boolean reportToExecutive;

    private boolean useControl;

    private int receiverSettings;
    private int [] receiverIDs;

    public MessageDefaultSettings(JsonElement jsonSettings) {

        JsonObject settingsObj = jsonSettings.getAsJsonObject();

        this.initiatorSettings = settingsObj.get("initiatorSettings").getAsInt();
        if(settingsObj.has("initiatorID")) { //если такой параметр вернулся
            if (!settingsObj.get("initiatorID").isJsonNull()) {
                this.initiatorID = settingsObj.get("initiatorID").getAsInt();
            }
        }

        this.reportReceiverSettings = settingsObj.get("reportReceiverSettings").getAsInt();
        if(settingsObj.has("reportReceiverID")) { //если такой параметр вернулся
            if (!settingsObj.get("reportReceiverID").isJsonNull()) {
                this.reportReceiverID = settingsObj.get("reportReceiverID").getAsInt();
            }
        }

        this.execAuditorSettings = settingsObj.get("execAuditorSettings").getAsInt();
        if(settingsObj.has("execAuditorID")) { //если такой параметр вернулся
            if (!settingsObj.get("execAuditorID").isJsonNull()) {
                this.execAuditorID = settingsObj.get("execAuditorID").getAsInt();
            }
        }

        this.receiverSettings = settingsObj.get("receiverSettings").getAsInt();
        if(settingsObj.has("receiverIDs")) { //если такой параметр вернулся

            if (!settingsObj.get("receiverIDs").isJsonNull()) {
                int i = 0;
                this.receiverIDs= new int[settingsObj.get("receiverIDs").getAsJsonArray().size()];
                for (JsonElement jsonElement : settingsObj.get("receiverIDs").getAsJsonArray()) {
                    this.receiverIDs[i] = jsonElement.getAsInt();
                    i++;
                }
            }
        } else {
            this.receiverIDs=null;
        }

        this.beginning = settingsObj.get("beginning").getAsInt();
        this.ending = settingsObj.get("ending").getAsInt();

        this.sendType = settingsObj.get("sendType").getAsInt();
        this.withResponsive = settingsObj.get("withResponsive").getAsBoolean();
        this.reportToExecutive = settingsObj.get("reportToExecutive").getAsBoolean();
        this.useControl = settingsObj.get("useControl").getAsBoolean();

    }

    public MessageDefaultSettings withSendType(SendTypes sendType){
        this.sendType=sendType.getId();
        return this;
    }

    public int getSendType() {
        return  sendType;
    }

    public JsonObject toJson(InstructionType type, boolean isForForward){

        //String operation = isForForward ? "forwardMessageDefaultSettings" : "newMessageDefaultSettings";
        JsonObject typeSettings = new JsonObject();

//        typeSettings.addProperty("%s", operation);

        JsonObject newSettings = new JsonObject();

        newSettings.addProperty("initiatorSettings", this.initiatorSettings);
        if (this.initiatorID>0) {
            newSettings.addProperty("initiatorID", this.initiatorID);
        }

        newSettings.addProperty("reportReceiverSettings", this.reportReceiverSettings);
        if (this.reportReceiverID>0) {
            newSettings.addProperty("reportReceiverID", this.reportReceiverID);
        }

        newSettings.addProperty("execAuditorSettings", this.execAuditorSettings);
        if (this.execAuditorID>0) {
            newSettings.addProperty("execAuditorID", this.execAuditorID);
        }

        newSettings.addProperty("beginning", this.beginning);
        newSettings.addProperty("ending", this.ending);
        newSettings.addProperty("withResponsive", this.withResponsive);
        newSettings.addProperty("reportToExecutive", this.reportToExecutive);

        newSettings.addProperty("useControl", this.useControl);
        newSettings.addProperty("sendType", this.sendType);

        newSettings.addProperty("receiverSettings", this.receiverSettings);
        if (this.receiverIDs!=null ) {

            JsonArray receiversArray = new JsonArray();

            for (int i = 0; i < this.receiverIDs.length; i++) {
                receiversArray.add(this.receiverIDs[i]);
            }
            newSettings.add("receiverIDs", receiversArray);
        }

        typeSettings.add(Integer.toString(type.getId()),newSettings);

       // String operation = isForForward ? "forwardMessageDefaultSettings" : "newMessageDefaultSettings";
        JsonObject newSet = new JsonObject();
        newSet.add(isForForward ? "forwardMessageDefaultSettings" : "newMessageDefaultSettings",typeSettings);

        return newSet;
    }

}
