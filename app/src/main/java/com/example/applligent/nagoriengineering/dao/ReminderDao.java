package com.example.applligent.nagoriengineering.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.applligent.nagoriengineering.model.ReminderModel;

import java.util.List;

@Dao
public interface ReminderDao {
    @Query("select * from ReminderModel")
    LiveData<List<ReminderModel>> getAll();

    @Insert
    void insert(ReminderModel model);

}
