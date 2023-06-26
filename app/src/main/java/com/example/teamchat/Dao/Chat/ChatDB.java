package com.example.teamchat.Dao.Chat;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teamchat.entities.messages.Message;

import java.io.File;

@Database(entities = {Message.class}, version = 14)
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
