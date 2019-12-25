package ru.lanit.ld.wc.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class instructionPermissions {

    private  boolean canAcceptReport;
    private  boolean canArchiveInstruction;
    private  boolean canCreateReport;
    private  boolean canDeleteInstruction;
    private  boolean canEditInstruction;
    private  boolean canEditReport;
    private  boolean canMoveInstructionToFolder;
    private  boolean canPerformSign;
    private  boolean canReadInstruction;
    private  boolean canRedirectInstruction;
    private  boolean canRedirectReportAsInstruction;
    private  boolean canRedirectReportAsReport;
    private  boolean canRepeatChain;
    private  boolean canRepeatInstruction;
    private  boolean canReplaceInstruction;
    private  boolean canRevokeChain;
    private  boolean canRevokeInstruction;
    private  boolean canSendReport;
    private  boolean canTakeInstructionInWork;
    private  boolean canUnreadInstruction;




    public instructionPermissions(JsonElement parsed) {

        JsonObject jsonInstruction = parsed.getAsJsonObject().get("permissions").getAsJsonObject();

        this.canAcceptReport = jsonInstruction.get("canAcceptReport").getAsBoolean();
        this.canArchiveInstruction = jsonInstruction.get("canArchiveInstruction").getAsBoolean();
        this.canCreateReport = jsonInstruction.get("canCreateReport").getAsBoolean();
        this.canDeleteInstruction = jsonInstruction.get("canDeleteInstruction").getAsBoolean();
        this.canEditInstruction = jsonInstruction.get("canEditInstruction").getAsBoolean();
        this.canEditReport = jsonInstruction.get("canEditReport").getAsBoolean();
        this.canMoveInstructionToFolder = jsonInstruction.get("canMoveInstructionToFolder").getAsBoolean();
        this.canPerformSign = jsonInstruction.get("canPerformSign").getAsBoolean();
        this.canReadInstruction = jsonInstruction.get("canReadInstruction").getAsBoolean();
        this.canRedirectInstruction = jsonInstruction.get("canRedirectInstruction").getAsBoolean();
        this.canRedirectReportAsInstruction = jsonInstruction.get("canRedirectReportAsInstruction").getAsBoolean();
        this.canRedirectReportAsReport = jsonInstruction.get("canRedirectReportAsReport").getAsBoolean();
        this.canRepeatChain = jsonInstruction.get("canRepeatChain").getAsBoolean();
        this.canRepeatInstruction = jsonInstruction.get("canRepeatInstruction").getAsBoolean();
        this.canReplaceInstruction = jsonInstruction.get("canReplaceInstruction").getAsBoolean();
        this.canRevokeChain = jsonInstruction.get("canRevokeChain").getAsBoolean();
        this.canRevokeInstruction = jsonInstruction.get("canRevokeInstruction").getAsBoolean();
        this.canSendReport = jsonInstruction.get("canSendReport").getAsBoolean();
        this.canTakeInstructionInWork = jsonInstruction.get("canTakeInstructionInWork").getAsBoolean();
        this.canUnreadInstruction = jsonInstruction.get("canUnreadInstruction").getAsBoolean();
    }

    public boolean isCanUnreadInstruction() {
        return canUnreadInstruction;
    }

}
