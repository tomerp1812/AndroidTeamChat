package com.example.teamchat.entities.messages;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.teamchat.entities.converters.UserNoPassConverter;
import com.example.teamchat.entities.user.UserNoPass;
@Entity
@TypeConverters({UserNoPassConverter.class})

public class Message {

    @PrimaryKey

    private int id;
    private String created;
    private UserNoPass sender;
    private String content;

    private String receiver;

    public Message(int id, String created, UserNoPass sender, String content) {
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public UserNoPass getSender() {
        return sender;
    }

    public void setSender(UserNoPass sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
