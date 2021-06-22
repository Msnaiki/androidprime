package com.example.myapplication;

public class ParticipentModel {
    private String ID;
    private String name;
    private String email;

    public ParticipentModel(String ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    public ParticipentModel(){

    }
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
