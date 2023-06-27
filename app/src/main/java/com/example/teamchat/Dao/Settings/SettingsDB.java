package com.example.teamchat.Dao.Settings;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teamchat.entities.SettingsEntity;

import java.io.File;

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

    public static void deleteDatabase(Context context) {
        // Step 1: Close any open instances of the database (if applicable)
        if (instance != null && instance.isOpen()) {
            instance.close();
        }

        // Step 2: Delete the database file
        File databaseFile = context.getDatabasePath(DATABASE_NAME);

        if (databaseFile.exists()) {
            if (databaseFile.delete()) {
                // Database file deleted successfully
                instance = null; // Reset the instance
            } else {
                // Failed to delete the database file
            }
        } else {
            // Database file does not exist
        }
    }
}
