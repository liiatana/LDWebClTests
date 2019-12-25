package ru.lanit.ld.wc.model;

public class reportResponse {

    public int reportId;
    public String message;
    public boolean isDPOOperation;

    public reportResponse(int instructionId, String message, boolean isDPOOperation) {
        this.reportId = instructionId;
        this.message = message;
        this.isDPOOperation=isDPOOperation;
    }

    public reportResponse() {
    }

    public String getMessage() {
        return message;
    }

    public int getReportId() {
        return reportId;
    }

    public boolean isDPOOperation() {
        return isDPOOperation;
    }

    @Override
    public String toString() {
        return "reportResponse{" +
                "reportId=" + reportId +
                ", message='" + message + '\'' +
                ", isDPOOperation=" + isDPOOperation +
                '}';
    }
}
