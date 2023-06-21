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
    @PrimaryKey
    private int id;
    private UserNoPass user;
    private LastMsg lastMessage;

    public Contact(int id, UserNoPass user, LastMsg lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
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

    public LastMsg getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMsg lastMessage) {
        this.lastMessage = lastMessage;
    }
}
