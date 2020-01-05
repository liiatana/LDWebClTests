package ru.lanit.ld.wc.enums;

public enum SendTypes {
    PARALLEL(0, "Параллельная","view_week"),
    CHAIN(1, "Последовательная","reorder"),
    BOTH (2,"Параллельная+Последовательная",""),
    NONE (3, "NONE","");

    private final int id;   // id типа рассылки
    private final String name;   // имя корневой папки
    private final String icon;   // имя корневой папки

    SendTypes(int id, String name,String icon) {
        this.id = id;
        this.name=name;
        this.icon=icon;
    }

    public int getId(){
        return id;
    }
    public String Id(){
        return String.valueOf(id);
    }
    public String getName(){
        return name;
    }
    public String getIcon(){
        return icon;
    }


    public SendTypes getTypes(boolean[] isActive) {

        if (isActive[0]==true && isActive[1]==true){
            return SendTypes.BOTH;
        }
        if (isActive[0]==true){
            return SendTypes.PARALLEL;
        }
        if (isActive[1]==true){
            return SendTypes.CHAIN;
        }
        return SendTypes.NONE;
    }

}
