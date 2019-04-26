package com.example.applligent.nagoriengineering.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.applligent.nagoriengineering.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertAll(List<User> register);


    @Insert
    void insert(User user);


    @Query("SELECT * FROM user")
    List<User> loadAllUsers();


    @Query("select displayName from User")
    List<String> getDisplayName();

}
