package com.example.teamchat.entities;

public class Message {
    private String message;
    //private Date time;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
