package com.example.teamchat.Dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.teamchat.entities.Message;

@Database(entities = {Message.class}, version = 1)
public abstract class ChatDB extends RoomDatabase {
    public abstract ChatDao chatDao();
}
