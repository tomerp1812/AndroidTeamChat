package com.example.teamchat.Dao.Chat;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teamchat.entities.messages.Message;

@Database(entities = {Message.class}, version = 13)
public abstract class ChatDB extends RoomDatabase {
    private static final String DATABASE_NAME = "chat_db";
    private static ChatDB instance;

    public static synchronized ChatDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ChatDB.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ChatDao chatDao();
}
