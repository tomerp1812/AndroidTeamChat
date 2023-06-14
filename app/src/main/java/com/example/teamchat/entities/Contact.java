package com.example.teamchat.entities;

import java.util.Date;

public class Contact {
    private String username;
    private String lastMessage;
    private String profilePic;
    private Date date;

    public Contact(String username, String lastMessage, String profilePic, Date date) {
        this.username = username;
        this.lastMessage = lastMessage;
        this.profilePic = profilePic;
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public Date getDate() {
        return date;
    }
}
