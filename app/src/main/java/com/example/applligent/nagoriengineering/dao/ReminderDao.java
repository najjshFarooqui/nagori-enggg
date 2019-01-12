package com.example.applligent.nagoriengineering.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.example.applligent.nagoriengineering.model.ReminderModel;

import java.util.List;

@Dao
public interface ReminderDao {
    @Query("select * from ReminderModel")
    List<ReminderModel> getAll();
}
