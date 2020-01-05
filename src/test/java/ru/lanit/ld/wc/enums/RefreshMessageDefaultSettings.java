package ru.lanit.ld.wc.enums;

public enum RefreshMessageDefaultSettings {
    YES (0, "Да"),
    NO (1, "Нет"),
    ONLYNEWUSERS (2,"Только для новых пользователей");

    private final int id;   // id корневой папки
    private final String name;   // имя корневой папки

    RefreshMessageDefaultSettings(int id, String name) {
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
