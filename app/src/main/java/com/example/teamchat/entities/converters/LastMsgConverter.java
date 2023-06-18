package com.example.teamchat.entities.converters;

import androidx.room.TypeConverter;

import com.example.teamchat.entities.messages.LastMsg;
import com.google.gson.Gson;

public class LastMsgConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static LastMsg fromString(String value) {
        // Convert the String value from the database back to the UserNoPass object
        return gson.fromJson(value, LastMsg.class);
    }

    @TypeConverter
    public static String toString(LastMsg lastMsg) {
        // Convert the UserNoPass object to a String value that can be stored in the database
        return gson.toJson(lastMsg);
    }
}
