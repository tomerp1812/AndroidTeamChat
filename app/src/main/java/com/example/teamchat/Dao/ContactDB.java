package com.example.teamchat.Dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.teamchat.entities.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDB extends RoomDatabase {
    public abstract ContactDao contactDao();
}
