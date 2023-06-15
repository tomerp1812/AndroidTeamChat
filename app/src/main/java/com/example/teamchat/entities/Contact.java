package com.example.teamchat.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private UserNoPass user;
    private LastMsg lastMsg;

    public Contact(int id, UserNoPass user, LastMsg lastMsg) {
        this.id = id;
        this.user = user;
        this.lastMsg = lastMsg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserNoPass getUser() {
        return user;
    }

    public void setUser(UserNoPass user) {
        this.user = user;
    }

    public LastMsg getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(LastMsg lastMsg) {
        this.lastMsg = lastMsg;
    }
}
