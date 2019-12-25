package ru.lanit.ld.wc.model;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class FolderList {


    public List<Instruction> items ;

    public FolderList(JsonElement jsonList) {
        items = new ArrayList<>();
        for (int i = 0; i <= jsonList.getAsJsonObject().get("totalCount").getAsInt()-1; i++) {
            Instruction inst = new Instruction(jsonList,i);
            items.add(i, inst);
        }
    }

    public int getInstructionNumInFolder(int instructionID) {
        int i=0;
        do
            if(this.items.get(i).getInstructionId() == instructionID){
                return i;
            }
            else i++;
        while (i<=this.items.size()-1)    ;
        return -1;
    }



}
