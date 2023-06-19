package com.example.teamchat.entities.messages;

import com.example.teamchat.entities.user.UserNoPass;

public class ContactNoMsg {

    private int id;
    private UserNoPass user;

    public ContactNoMsg(int id, UserNoPass user) {
        this.id = id;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserNoPass getUserNoPass() {
        return user;
    }

    public void setUserNoPass(UserNoPass user) {
        this.user = user;
    }
}
