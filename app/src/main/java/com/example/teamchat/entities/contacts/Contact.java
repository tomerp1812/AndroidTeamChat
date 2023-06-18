package com.example.teamchat.entities.contacts;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.teamchat.entities.messages.LastMsg;
import com.example.teamchat.entities.converters.LastMsgConverter;
import com.example.teamchat.entities.converters.UserNoPassConverter;
import com.example.teamchat.entities.user.UserNoPass;

@Entity
@TypeConverters({UserNoPassConverter.class, LastMsgConverter.class})
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
