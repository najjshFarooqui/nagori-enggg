package com.example.applligent.nagoriengineering.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.example.applligent.nagoriengineering.model.RegisterModel;


import java.util.List;

@Dao
public interface RegisterDao {
    @Insert
    void insertAll(List<RegisterModel> register);
}
