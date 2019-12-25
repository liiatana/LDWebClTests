package ru.lanit.ld.wc.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

public class Instruction {


    private int[] receiverID;//": 40808836,
    private String text;//"text": "Прошу согласовать документ.",
    private String subject;       // "subject": "На согласование",
    private String comment;      //  "comment": null,
    private int documentId;      // "fileIds": null,
    private int execAuditorID;//    "execAuditorID": null,
    private int initiatorID;//      "initiatorID": 1000,
    private int reportReceiverID;  //     "reportReceiverID":1000,
    private LocalDateTime startDate;//       "startDate": "2018-11-22T18:50:39.000Z",
    private LocalDateTime executionDate;//      "executionDate": null,
    private int execInterval;//      "execInterval": null
    private int execIntervalType;//      "execInterval": null
    private int sendType;// 1/0 = последовательная/параллельная
    //private String reportflag;
    private boolean control;
    private boolean withExecutive;
    private boolean reportToExecutive;
    private int[] fileIds;      // "fileIds": null,

    private int instructionTypeId;
    private int operationTypeId;


    //private boolean hasPlugin; //
    //public String signServiceUrl;

    private int InstructionId;
    private InstructionType instructionType;

    private LocalDateTime creationDate;
    private int state;
    private String stateName;


    private instructionPermissions permissions;
    private String result;
    private instructionFolder folder;

    private Report report;



    public Instruction(JsonElement parsed, int InstructionNum) { // для создания объектов Instruction при чтении списка
        this.InstructionId =
                parsed.getAsJsonObject().getAsJsonArray("items").get(InstructionNum).getAsJsonObject().get("instruction").getAsJsonObject().get("id").getAsInt();
    }

    public Instruction(JsonElement parsed) { // для создания объекта Instruction при олучении ифнормации о сообщении

        JsonObject jsonInstruction = parsed.getAsJsonObject().get("instruction").getAsJsonObject();

        if (!jsonInstruction.get("comment").isJsonNull()) {
            this.comment = jsonInstruction.get("comment").getAsString();
        }

        this.creationDate = LocalDateTime.parse(jsonInstruction.get("createDateTime").getAsString().substring(0, 16).replace("T", ""),
                DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm"));

        this.InstructionId = jsonInstruction.get("id").getAsInt();

        if (!jsonInstruction.get("initiatorID").isJsonNull()) {
            this.initiatorID = jsonInstruction.get("initiatorID").getAsInt();
        }

        if (!jsonInstruction.get("creatorID").isJsonNull()) {
            this.initiatorID = jsonInstruction.get("creatorID").getAsInt();
        }


        this.instructionTypeId = jsonInstruction.get("instructionTypeID").getAsInt();


        this.state = jsonInstruction.get("state").getAsInt();
        this.stateName = jsonInstruction.get("stateName").getAsString();
        this.subject = jsonInstruction.get("subject").getAsString();
        this.text = jsonInstruction.get("text").getAsString();

        this.receiverID= Arrays.stream(jsonInstruction.get("receiverID").getAsString().split(",")).mapToInt(Integer::parseInt).toArray();
        //LocalDateTime secondParseResult = LocalDateTime.parse("September, 24, 2014 17:18:55", DateTimeFormatter.ofPattern("MMMM, dd, yyyy HH:mm:ss"));

        // срок исполнения
        if (!jsonInstruction.get("executionEndDate").isJsonNull()) {
            this.executionDate =LocalDateTime.parse(jsonInstruction.get("executionEndDate").getAsString().substring(0,16).replace("T", ""),
                    DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm"));
            // DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm"));
        }

        this.permissions=new instructionPermissions(parsed);
        if (!jsonInstruction.get("result").isJsonNull()) {
            this.result=jsonInstruction.get("result").getAsString();
            this.report= new Report(jsonInstruction);

        }else  this.result= null;//this.result="";
//instructionFolder iFolder=new instructionFolder(jsonInstruction.get("folder").getAsJsonObject());

        this.folder= new instructionFolder(jsonInstruction.get("folder").getAsJsonObject());




    }

    public Instruction() {

    }

    public String getResult() {
        return result;
    }

    public Instruction withResult(String result) {
        this.result = result;
        return this;
    }

    public instructionFolder getFolder() {
        return folder;
    }

    public Instruction withInstructionType(InstructionType instructionType) {
        this.instructionType = instructionType;
        return this;
    }

    public int getInstructionTypeId() {
        return instructionTypeId;
    }

    public Instruction(InstructionType type) {

        this.instructionType = type;
        this.instructionTypeId = type.getId();
        //this.documentId = 0;
        this.sendType = 0;
        this.control = type.getUseControl();
        this.withExecutive = false;

        this.reportToExecutive = false;

        this.text = type.getName();
        this.subject = String.format("%s", type.getName());
        this.comment = String.format("%s", type.getName());

        this.startDate = LocalDateTime.now();//.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        this.operationTypeId = type.getOperationID();


    }


    public int[] getReceiverID() {
        return receiverID;
    }

    public Instruction withReceiverID(int[] receiverID) {
        this.receiverID = receiverID;
        return this;
    }

    public int getOperationTypeId() {
        return operationTypeId;
    }

    public String getText() {
        return text;
    }

    public Instruction withText(String text) {
        this.text = text;
        return this;
    }

    public Report getReport() {
        return report;
    }

    public boolean isControl() {
        return control;
    }

    public String getSubject() {
        return subject;
    }

    public Instruction withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Instruction withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public int getFileIds() {
        return documentId;
    }

    public Instruction withFileIds(int fileIds) {
        this.documentId = fileIds;
        return this;
    }

    public int getExecAuditorID() {
        return execAuditorID;
    }

    public Instruction withExecAuditorID(int[] execAuditorID) {
        this.control = true;
        this.execAuditorID = execAuditorID[0];
        return this;
    }

    public int getInitiatorID() {
        return initiatorID;
    }

    public Instruction withInitiatorID(int[] initiatorID) {
        this.initiatorID = initiatorID[0];
        /*if (this.reportReceiverID == 0) {
            this.reportReceiverID = this.initiatorID;
        }*/


        return this;
    }


    public int getReportReceiverID() {
        if (reportReceiverID>0){
            return reportReceiverID;}
        else return initiatorID;
    }

    public Instruction withReportReceiverID(int[] reportReceiverID) {


        this.reportReceiverID = reportReceiverID[0];
        return this;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Instruction withStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public Instruction withExecutionDate(LocalDateTime executionDate, int execIntervalType) {
        this.executionDate = executionDate;
        //this.execIntervalType = execIntervalType;
        return this;
    }

    public boolean isWithExecutive() {
        return withExecutive;
    }

    public Instruction setWithExecutive(boolean withExecutive) {
        this.withExecutive = withExecutive;
        return this;
    }

    public boolean isReportToExecutive() {
        return reportToExecutive;
    }

    public Instruction setReportToExecutive(boolean reportToExecutive) {
        this.reportToExecutive = reportToExecutive;
        return this;
    }

    public int getSendType() {
        return sendType;
    }

    public Instruction withSendType(int sendType) {
        this.sendType = sendType;
        return this;
    }


    public int getInstructionId() {
        return InstructionId;
    }

    public Instruction withInstructionId(int instructionId) {
        InstructionId = instructionId;
        return this;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Instruction withCreationDate(LocalDateTime d) {
        creationDate=d;
        return this;
    }

    public int getState() {
        return state;
    }

    public Instruction withState(int state) {
        this.state = state;
        return this;
    }

    public String getStateName() {
        return stateName;
    }

    public Instruction withStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public Instruction withReport(Report report) {
        this.report = report;
        return this;
    }

    public instructionPermissions getPermissions() {
        return permissions;
    }

    //public String getCreationDateAsTextInWeb() {
    //    return creationDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm"));
    //}

    public InstructionType getInstructionType() {
        return instructionType;
    }

    public JsonObject toJson(boolean send) {
        JsonObject mainInstruction = new JsonObject();
        JsonObject jsonInstruction = new JsonObject();

        mainInstruction.addProperty("receiverID", this.receiverID[0]);
        mainInstruction.addProperty("subject", this.subject);
        mainInstruction.addProperty("text", this.text);
//+String.format("_%sZ", this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000")))
        mainInstruction.addProperty("comment", this.comment);

        if (this.fileIds == null) {
            mainInstruction.add("fileIds", null);
        } else {
            mainInstruction.addProperty("fileIds", this.fileIds[0]);
        }

        mainInstruction.addProperty("execAuditorID", this.execAuditorID);
        mainInstruction.addProperty("initiatorID", this.initiatorID);

        if (!this.control) {
            mainInstruction.addProperty("reportReceiverID", 0);
        } else if (this.reportReceiverID != 0) {
            mainInstruction.addProperty("reportReceiverID", this.reportReceiverID);
        } else {
            mainInstruction.addProperty("reportReceiverID", this.initiatorID);
        }


        if (this.startDate == null) {
            mainInstruction.add("startDate", null);
        } else {
            mainInstruction.addProperty("startDate", String.format("%sZ", this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));
        }


        if (this.executionDate == null) {
            mainInstruction.add("executionDate", null);
        } else {
            mainInstruction.addProperty("executionDate", String.format("%sZ", this.executionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));
            mainInstruction.addProperty("execIntervalType", this.execIntervalType);
        }


        mainInstruction.add("execInterval", null);

        if (this.execAuditorID == 0) {
            mainInstruction.add("execAuditorID", null);
        } else {
            mainInstruction.addProperty("execAuditorID", this.execAuditorID);
        }


        jsonInstruction.addProperty("instructionTypeId", this.instructionTypeId);

        if (this.documentId == 0) {
            jsonInstruction.add("documentId", null);
        } else {
            jsonInstruction.addProperty("documentId", this.documentId);
        }


        jsonInstruction.addProperty("sendType", this.sendType);
        jsonInstruction.addProperty("reportToExecutive", this.reportToExecutive);
        jsonInstruction.addProperty("withExecutive", this.withExecutive);
        jsonInstruction.addProperty("control", this.control);

        if (send) {
            jsonInstruction.addProperty("doEdit", false);
            jsonInstruction.add("instructionId", null);
            jsonInstruction.addProperty("operationTypeId", this.operationTypeId);
            jsonInstruction.add("scrInstructionId", null);
            jsonInstruction.add("reportId", null);
        }

        jsonInstruction.add("mainInstruction", mainInstruction);

        if (this.receiverID.length > 1) {

            JsonArray coExecutorInstruction = new JsonArray();

            JsonObject coExecFirst = mainInstruction.deepCopy();
            coExecFirst.remove("receiverID");
            coExecFirst.addProperty("receiverID", this.receiverID[1]);
            coExecutorInstruction.add(coExecFirst);


            JsonObject coExecOthers = mainInstruction.deepCopy();
            //coExecFirst.remove("receiverID");

            if (this.withExecutive && this.reportToExecutive) {
                coExecOthers.remove("reportReceiverID");
                coExecOthers.addProperty("reportReceiverID", this.receiverID[1]);
            }

            for (int i = 2; i < receiverID.length; i++) {
                coExecOthers.remove("receiverID");
                coExecOthers.addProperty("receiverID", this.receiverID[i]);
                coExecutorInstruction.add(coExecOthers);
            }

            jsonInstruction.add("coExecutorInstruction", coExecutorInstruction);
        }

        JsonObject request = new JsonObject();
        request.add("request", jsonInstruction);
        return request;
    }


  /*  public String toString() {
        return "Instruction: {" +
                "Id="+InstructionId+
                ", receiverID=" + Arrays.toString(receiverID) +
                ", text='" + text + '\'' +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", documentId=" + documentId +
                ", execAuditorID=" + execAuditorID +
                ", initiatorID=" + initiatorID +
                ", reportReceiverID=" + reportReceiverID +
                ", creationDate=" + creationDate +
                ", startDate=" + startDate +
                ", executionDate=" + executionDate +
                ", execInterval=" + execInterval +
                ", sendType=" + sendType +
                ", control=" + control +
                ", withExecutive=" + withExecutive +
                ", reportToExecutive=" + reportToExecutive +
                ", fileIds=" + Arrays.toString(fileIds) +
                ", instructionTypeId=" + instructionTypeId +
                ", operationTypeId=" + operationTypeId +
                '}';
    }*/

    @Override
    public String toString() {
        return "Instruction{" +
                "receiverID=" + Arrays.toString(receiverID) +
                ", text='" + text + '\'' +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", documentId=" + documentId +
                ", execAuditorID=" + execAuditorID +
                ", initiatorID=" + initiatorID +
                ", reportReceiverID=" + reportReceiverID +
                ", startDate=" + startDate +
                ", executionDate=" + executionDate +
                ", execInterval=" + execInterval +
                ", execIntervalType=" + execIntervalType +
                ", sendType=" + sendType +
                ", control=" + control +
                ", withExecutive=" + withExecutive +
                ", reportToExecutive=" + reportToExecutive +
                ", fileIds=" + Arrays.toString(fileIds) +
                ", instructionTypeId=" + instructionTypeId +
                ", operationTypeId=" + operationTypeId +
                ", InstructionId=" + InstructionId +
                ", InstructionType=" + instructionType +
                ", creationDate=" + creationDate +
                ", state=" + state +
                ", stateName='" + stateName + '\'' +
                ", permissions=" + permissions +
                ", result='" + result + '\'' +
                ", folder=" + folder +
                ", report=" + report +
                '}';
    }

  /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instruction that = (Instruction) o;
        return documentId == that.documentId &&
                execAuditorID == that.execAuditorID &&
                initiatorID == that.initiatorID &&
                reportReceiverID == that.reportReceiverID &&
                execInterval == that.execInterval &&
                execIntervalType == that.execIntervalType &&
                sendType == that.sendType &&
                control == that.control &&
                withExecutive == that.withExecutive &&
                reportToExecutive == that.reportToExecutive &&
                instructionTypeId == that.instructionTypeId &&
                operationTypeId == that.operationTypeId &&
                InstructionId == that.InstructionId &&
                state == that.state &&
                Arrays.equals(receiverID, that.receiverID) &&
                Objects.equals(text, that.text) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(executionDate, that.executionDate) &&
                Arrays.equals(fileIds, that.fileIds) &&
                Objects.equals(InstructionType, that.InstructionType) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(stateName, that.stateName) &&
                Objects.equals(permissions, that.permissions) &&
                Objects.equals(result, that.result) &&
                Objects.equals(folder, that.folder);
    }

    @Override
    public int hashCode() {
        int result1 = Objects.hash(text, subject, comment, documentId, execAuditorID, initiatorID, reportReceiverID, startDate, executionDate, execInterval, execIntervalType, sendType, control, withExecutive, reportToExecutive, instructionTypeId, operationTypeId, InstructionId, InstructionType, creationDate, state, stateName, permissions, result, folder);
        result1 = 31 * result1 + Arrays.hashCode(receiverID);
        result1 = 31 * result1 + Arrays.hashCode(fileIds);
        return result1;
    }*/


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instruction that = (Instruction) o;
        return documentId == that.documentId &&
                execAuditorID == that.execAuditorID &&
                initiatorID == that.initiatorID &&
                reportReceiverID == that.reportReceiverID &&
                execInterval == that.execInterval &&
                execIntervalType == that.execIntervalType &&
                sendType == that.sendType &&
                control == that.control &&
                withExecutive == that.withExecutive &&
                reportToExecutive == that.reportToExecutive &&
                instructionTypeId == that.instructionTypeId &&
                operationTypeId == that.operationTypeId &&
                InstructionId == that.InstructionId &&
                state == that.state &&
                Arrays.equals(receiverID, that.receiverID) &&
                Objects.equals(text, that.text) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(executionDate, that.executionDate) &&
                Arrays.equals(fileIds, that.fileIds) &&
                Objects.equals(instructionType, that.instructionType) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(stateName, that.stateName) &&
                Objects.equals(permissions, that.permissions) &&
                Objects.equals(result, that.result) &&
                Objects.equals(folder, that.folder) &&
                Objects.equals(report, that.report);
    }

    @Override
    public int hashCode() {
        int result1 = Objects.hash(text, subject, comment, documentId, execAuditorID, initiatorID, reportReceiverID, startDate, executionDate, execInterval, execIntervalType, sendType, control, withExecutive, reportToExecutive, instructionTypeId, operationTypeId, InstructionId, instructionType, creationDate, state, stateName, permissions, result, folder, report);
        result1 = 31 * result1 + Arrays.hashCode(receiverID);
        result1 = 31 * result1 + Arrays.hashCode(fileIds);
        return result1;
    }

    public Instruction getOnlyListViewInformation(boolean isFromReceiverView) {
        Instruction inst = new Instruction();

        if(isFromReceiverView){
             inst.initiatorID=this.initiatorID;
             //inst.reportReceiverID=this.initiatorID;// костыль
        }
        else {
            inst.receiverID[0]= this.receiverID[0];
        }

        inst.instructionType=this.instructionType;
        inst.stateName=this.stateName;
        //inst.startDate=this.startDate;
        inst.creationDate=this.creationDate;
        inst.executionDate=this.executionDate;
        inst.text=this.text;

        return inst;
    }
}
