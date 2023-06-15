package com.example.teamchat.Dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teamchat.entities.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDB extends RoomDatabase {
    public static ContactDB getInstance(Context context) {
        ContactDB contactDB = Room.databaseBuilder(context, ContactDB.class, "my-database")
                .build();
        return contactDB;
    }

    public abstract ContactDao contactDao();
}
