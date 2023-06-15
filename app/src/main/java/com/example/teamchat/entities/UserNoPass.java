package com.example.teamchat.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserNoPass {
    @PrimaryKey(autoGenerate = true)
    private String username;
    private String displayName;
    private int profilePic;

    public UserNoPass(String username, String displayName, int profilePic) {
        setUsername(username);
        setDisplayName(displayName);
        setProfilePic(profilePic);
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
