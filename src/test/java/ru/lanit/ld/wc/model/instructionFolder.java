package ru.lanit.ld.wc.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Objects;

public class instructionFolder {

    private int id;//: 1999
    private String name; //: "Входящая"

    /*public instructionFolder() {
        this.id = id;
        this.name = name;
    }*/

    public instructionFolder(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public instructionFolder(JsonObject parsed) {

        //JsonObject jsonFolder = parsed.getAsJsonObject().get("folder").getAsJsonObject();

        //this.id = jsonFolder.get("id").getAsInt();
        //this.name = jsonFolder.get("name").getAsString();

        this.id = parsed.get("id").getAsInt();
        this.name = parsed.get("name").getAsString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        instructionFolder that = (instructionFolder) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "instructionFolder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
