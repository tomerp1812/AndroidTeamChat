package com.example.teamchat.entities.converters;

import androidx.room.TypeConverter;

import com.example.teamchat.entities.user.UserNoPass;
import com.google.gson.Gson;

public class UserNoPassConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static UserNoPass fromString(String value) {
        // Convert the String value from the database back to the UserNoPass object
        return gson.fromJson(value, UserNoPass.class);
    }

    @TypeConverter
    public static String toString(UserNoPass user) {
        // Convert the UserNoPass object to a String value that can be stored in the database
        return gson.toJson(user);
    }
}

