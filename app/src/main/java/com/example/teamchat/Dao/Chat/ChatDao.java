package com.example.teamchat.Dao.Chat;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.teamchat.entities.messages.MessageReturn;

/////////userId!!!!!!!
@Dao
public interface ChatDao {
//    @Query("SELECT * FROM Message WHERE userId = :userId")
//    List<Message> getMessagesForUser(String userId);

    @Insert
    void insert(MessageReturn... messages);
}

