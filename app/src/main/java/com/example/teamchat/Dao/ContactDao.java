package com.example.teamchat.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teamchat.entities.contacts.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    //get all the contact from the DB
    @Query("SELECT * FROM Contact")
    List<Contact> index();

    //get specific contact from the DB
    @Query("SELECT * FROM Contact WHERE id = :id")
    Contact get(int id);

    @Query("SELECT id FROM USERNOPASS WHERE username= :username")
    int getId(String username);

    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... contacts);
}

