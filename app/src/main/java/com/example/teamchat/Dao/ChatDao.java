package com.example.teamchat.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.teamchat.entities.Message;

/////////userId!!!!!!!
@Dao
public interface ChatDao {
//    @Query("SELECT * FROM Message WHERE userId = :userId")
//    List<Message> getMessagesForUser(String userId);

    @Insert
    void insert(Message... messages);
}

