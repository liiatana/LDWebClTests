package ru.lanit.ld.wc.enums;

public enum EsignModes {
    DISABLED (1, "Не использовать функционал ЭП"),
    ENABLED (2, "Требовать ЭП"),
    ENABLEDIF (3,"Использовать ЭП, если это возможно"),
    DSS (4, "Использовать DSS");


    private final int id;   // id корневой папки
    private final String name;   // имя корневой папки

    EsignModes(int id, String name) {
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
