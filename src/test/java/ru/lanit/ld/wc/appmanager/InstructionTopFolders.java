package ru.lanit.ld.wc.appmanager;

public enum InstructionTopFolders {
    INCOMING (1999, "Входящая"),
    PROJECTS (2102, "Проекты"),
    OUTCOMING (2101,"Исходящая"),
    ONCONTROL (2103, "На контроле"),
    EXECUTIONCONTROL (2104,"Контроль исполнения");

    private final int id;   // id корневой папки
    private final String name;   // имя корневой папки

    InstructionTopFolders(int id,String name) {
        this.id = id;
        this.name=name;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }


}
