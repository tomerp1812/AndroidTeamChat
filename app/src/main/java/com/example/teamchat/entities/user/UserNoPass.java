package com.example.teamchat.entities.user;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserNoPass {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String displayName;
    private int profilePic;

    public UserNoPass(String username, String displayName, int profilePic) {
        setUsername(username);
        setDisplayName(displayName);
        setProfilePic(profilePic);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }
}
