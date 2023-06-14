package com.example.teamchat.entities;

public class ContactNoMsg {

    private int id;
    private UserNoPass userNoPass;

    public ContactNoMsg(int id, UserNoPass userNoPass) {
        this.id = id;
        this.userNoPass = userNoPass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserNoPass getUserNoPass() {
        return userNoPass;
    }

    public void setUserNoPass(UserNoPass userNoPass) {
        this.userNoPass = userNoPass;
    }
}
