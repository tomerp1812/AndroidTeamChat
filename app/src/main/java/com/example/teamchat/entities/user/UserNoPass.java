package com.example.teamchat.entities.user;

public class UserNoPass {

    private String username;
    private String displayName;
    private String profilePic;

    public UserNoPass(String username, String displayName, String profilePic) {
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
