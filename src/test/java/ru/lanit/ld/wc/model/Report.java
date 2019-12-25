package ru.lanit.ld.wc.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

public class Report {

    private String comment;
    private int completionTypeId;
    private int documentId;
    private LocalDateTime executionDate;
    private int[] fileIds;
    private int initiatorID;
    private int operationDocumentID;
    private int operationTypeId;
    private int receiverId;
    private int reportId;
    private String subject;
    private String text;


    private int InstructionId;

    public Report( Instruction instruction) {//UserInfo initiator, UserInfo receiver
        this.comment=instruction.getComment();
        this.completionTypeId = 1500; //1500= положительный отчет, 1501- отказ
        this.documentId = 0;
        this.executionDate = LocalDateTime.now();
        //this.fileIds
        //this.initiatorID=initiator.getId();
        //this.operationDocumentID
        this.operationTypeId=instruction.getOperationTypeId();
        //this.receiverId=receiver.getId();
        //this.reportId
        this.subject = "Отчет:" + instruction.getSubject();
        this.text = "";//instruction.getText();
        this.InstructionId=instruction.getInstructionId();
    }

    public Report( Report report) { //копирование объекта
        this.comment=report.getComment();
        this.completionTypeId=report.getCompletionTypeId();
        this.documentId = report.getDocumentId();
        this.executionDate = report.getExecutionDate();
        this.fileIds=report.getFileIds();
        //this.initiatorID=initiator.getId();
        this.operationDocumentID=report.getDocumentId();
        this.operationTypeId=report.getOperationTypeId();
        //this.receiverId=receiver.getId();
        this.reportId=report.getReportId();
        this.subject=report.getSubject();
        this.text=report.getText();
        this.InstructionId=0;

    }

    public Report (JsonObject jsonInstruction){
        this.InstructionId = jsonInstruction.get("id").getAsInt();
        this.initiatorID = jsonInstruction.get("reportCreatorID").getAsInt();
        this.reportId = jsonInstruction.get("reportID").getAsInt();
        if (!jsonInstruction.get("reportReceiverID").isJsonNull()){
            this.receiverId = jsonInstruction.get("reportReceiverID").getAsInt();
        }


        this.subject = jsonInstruction.get("reportSubject").getAsString();
        this.text = jsonInstruction.get("reportText").getAsString();
    }

    public Report() {
    }

    public Report withInitiatorID(int initiatorID) {
        this.initiatorID = initiatorID;
        return this;
    }

    public Report withReceiverId(int receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public int getInstructionId() {
        return InstructionId;
    }

    public Report withInstructionId(int instructionId) {
        InstructionId = instructionId;
        return this;
    }

    public Report withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Report withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Report withText(String text) {
        this.text = text;
        return this;
    }

    public Report withRepodtId(int ID) {
        this.reportId = ID;
        return this;
    }



    public Report withCompletionTypeId(boolean positive) {
        int CompletionTypeId = positive ? 1500 : 1501;
        this.completionTypeId = CompletionTypeId;
        return this;
    }




    @Override
    public String toString() {
        return "Report{" +
                "comment='" + comment + '\'' +
                ", completionTypeId=" + completionTypeId +
                ", documentId=" + documentId +
                ", executionDate=" + executionDate +
                ", fileIds=" + Arrays.toString(fileIds) +
                ", initiatorID=" + initiatorID +
                ", operationDocumentID=" + operationDocumentID +
                ", operationTypeId=" + operationTypeId +
                ", receiverId=" + receiverId +
                ", reportId=" + reportId +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return completionTypeId == report.completionTypeId &&
                documentId == report.documentId &&
                initiatorID == report.initiatorID &&
                operationDocumentID == report.operationDocumentID &&
                operationTypeId == report.operationTypeId &&
                receiverId == report.receiverId &&
                reportId == report.reportId &&
                InstructionId == report.InstructionId &&
                Objects.equals(comment, report.comment) &&
                Objects.equals(executionDate, report.executionDate) &&
                Arrays.equals(fileIds, report.fileIds) &&
                Objects.equals(subject, report.subject) &&
                Objects.equals(text, report.text);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(comment, completionTypeId, documentId, executionDate, initiatorID, operationDocumentID, operationTypeId, receiverId, reportId, subject, text, InstructionId);
        result = 31 * result + Arrays.hashCode(fileIds);
        return result;
    }

    public JsonObject toJson() {

        JsonObject request = new JsonObject();

        request.addProperty("comment", this.comment);
        request.addProperty("completionTypeId", this.completionTypeId);

        if (this.documentId == 0) {
            request.add("documentId", null);
        } else {
            request.addProperty("documentId", this.documentId);
        }

        request.addProperty("executionDate", String.format("%sZ", this.executionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.000"))));

        if (this.fileIds == null) {
            request.add("fileIds", null);
        } else {
            request.addProperty("fileIds", this.fileIds[0]);
        }

        request.addProperty("initiatorID", this.initiatorID);

        if (this.operationDocumentID == 0) {
            request.add("operationDocumentID", null);
        } else {
            request.addProperty("documentId", this.operationDocumentID);
        }

        request.addProperty("operationTypeId", this.operationTypeId);
        request.addProperty("receiverId", this.receiverId);
        request.add("reportId", null);


        request.addProperty("subject", this.subject);
        request.addProperty("text", this.text);

        JsonObject request1 = new JsonObject();
        request1.add("request", request);

        return request1;
    }


    public String getComment() {
        return comment;
    }

    public int getCompletionTypeId() {
        return completionTypeId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public int[] getFileIds() {
        return fileIds;
    }

    public int getInitiatorID() {
        return initiatorID;
    }

    public int getOperationDocumentID() {
        return operationDocumentID;
    }

    public int getOperationTypeId() {
        return operationTypeId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public int getReportId() {
        return reportId;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}
