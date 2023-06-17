package com.example.teamchat.Dao.Chat;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.teamchat.Dao.Chat.ChatDao;
import com.example.teamchat.entities.messages.Message;

@Database(entities = {Message.class}, version = 1)
public abstract class ChatDB extends RoomDatabase {
    public abstract ChatDao chatDao();
}
