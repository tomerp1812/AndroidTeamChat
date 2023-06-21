package com.example.teamchat.Dao.Chat;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.teamchat.entities.messages.Message;

import java.util.List;

/////////userId!!!!!!!
@Dao
public interface ChatDao {
    @Query("SELECT * FROM Message")
    List<Message> index();

    @Insert
    void insert(Message messages);

    @Query("SELECT * FROM Message WHERE id = :id")
    Message getMessageById(int id);

    @Delete
    void delete(Message message);

}

