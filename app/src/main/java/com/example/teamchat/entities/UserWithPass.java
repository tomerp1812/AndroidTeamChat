package com.example.teamchat.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserWithPass {
    @PrimaryKey(autoGenerate = true)
    private String username;
    private String password;

    String displayName;
    private int profilePic;


    public UserWithPass(String username, String password, String displayName, int profilePic) {
        setUsername(username);
        setPassword(password);
        setDisplayName(displayName);
        setProfilePic(profilePic);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }
}
