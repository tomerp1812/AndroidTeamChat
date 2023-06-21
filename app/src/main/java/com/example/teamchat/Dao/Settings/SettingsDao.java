package com.example.teamchat.Dao.Settings;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teamchat.entities.SettingsEntity;

import java.util.List;



@Dao
public interface SettingsDao {
    @Insert
    void insert(SettingsEntity se);

    @Update
    void update(SettingsEntity se);

    @Query("SELECT * FROM SettingsEntity")
    List<SettingsEntity> index();
}
