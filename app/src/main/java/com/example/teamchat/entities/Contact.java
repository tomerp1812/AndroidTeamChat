package com.example.teamchat.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private String username;
    private int id;
    private String lastMessage;
    private int profilePic;
    private String date;


    public Contact(String username, String lastMessage, int profilePic, String date) {
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

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public String getDate() {
        return date;
    }
}
