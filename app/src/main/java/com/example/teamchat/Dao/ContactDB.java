package com.example.teamchat.Dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teamchat.entities.contacts.Contact;

import java.util.concurrent.CompletableFuture;

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

    //delete all the items from the db
    public static void deleteDatabase(Context context) {
        ContactDB db = ContactDB.getInstance(context);
        ContactDao contactDao = db.contactDao();

        CompletableFuture.supplyAsync(contactDao::index)
                .thenAccept(contactList -> {
                    for (int i = contactList.size() - 1; i >= 0; i--){
                        contactDao.delete(contactList.get(i));
                    }
                });
    }

}
