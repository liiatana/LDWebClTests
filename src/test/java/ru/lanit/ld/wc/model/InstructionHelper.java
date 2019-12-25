package ru.lanit.ld.wc.model;

import ru.lanit.ld.wc.appmanager.ApplicationManager;

public class InstructionHelper {

    private ApplicationManager app;

    public InstructionHelper(ApplicationManager manager) {
        this.app = manager;
    }

    public Instruction OutcommingMessage(Instruction instruction) {


        if (instruction.getReceiverID()==null)
            instruction.withReceiverID(new int[]{app.userList.anyUsers(1, null).users.get(0).getId()});

        if (instruction.getInstructionType() == null) {
            instruction.withInstructionType(app.focusedUser.getUserTypes().getAnyType());
        }
        return instruction;
    }
}