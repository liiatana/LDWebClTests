package ru.lanit.ld.wc.model;

import java.time.LocalDateTime;

public class instResponse {

    public int instructionId;
    public String message;


    public instResponse(int instructionId, String message) {

        this.instructionId = instructionId;
        this.message = message;
    }

    public instResponse() {
    }

    public int getInstructionId() {
        return instructionId;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Response: {" +
                "instructionId=" + instructionId +
                ", message='" + message + '\'' +
                '}';
    }
}
