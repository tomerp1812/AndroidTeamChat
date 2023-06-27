package com.example.teamchat.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SettingsEntity {

    @PrimaryKey(autoGenerate = true)
    int id;

    private String url;

    private String userConnected;
    private boolean isNightMode;
    private String authorizationHeader;

    public SettingsEntity(String url, boolean isNightMode) {
        this.url = url;
        this.isNightMode = isNightMode;
        this.userConnected = null;
        this.authorizationHeader = null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getNightMode() {
        return isNightMode;
    }

    public void setNightMode(boolean nightMode) {
        isNightMode = nightMode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserConnected() {
        return userConnected;
    }

    public void setUserConnected(String userConnected) {
        this.userConnected = userConnected;
    }

    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    public void setAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
    }
}
