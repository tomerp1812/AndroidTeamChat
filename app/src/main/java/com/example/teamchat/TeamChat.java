package com.example.teamchat;

import android.app.Application;
import android.content.Context;

public class TeamChat extends Application {
    public static Context context;
    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }
}
