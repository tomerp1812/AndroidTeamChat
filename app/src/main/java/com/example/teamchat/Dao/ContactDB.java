package com.example.teamchat.Dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teamchat.entities.contacts.Contact;

import java.io.File;

@Database(entities = {Contact.class}, version = 14)
public abstract class ContactDB extends RoomDatabase {
    private static final String DATABASE_NAME = "contact_db";
    private static ContactDB instance;

    public static synchronized ContactDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ContactDB.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ContactDao contactDao();
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
