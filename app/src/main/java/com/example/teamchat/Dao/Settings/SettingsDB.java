package com.example.teamchat.Dao.Settings;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teamchat.entities.SettingsEntity;

@Database(entities = {SettingsEntity.class}, version = 7)
public abstract class SettingsDB extends RoomDatabase {

    private static final String DATABASE_NAME = "ServerUrl_db";
    private static SettingsDB instance;

    public static synchronized SettingsDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SettingsDB.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract SettingsDao settingsDao();

}
