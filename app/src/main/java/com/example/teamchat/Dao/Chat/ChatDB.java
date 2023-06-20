package com.example.teamchat.Dao.Chat;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.teamchat.entities.messages.Message;

@Database(entities = {Message.class}, version = 1)
public abstract class ChatDB extends RoomDatabase {
    private static final String DATABASE_NAME = "contact_db";

    public abstract ChatDao chatDao();
}
