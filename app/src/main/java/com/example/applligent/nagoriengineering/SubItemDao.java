package com.example.applligent.nagoriengineering;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SubItemDao {
    @Query("select * from SubItem")
    List<SubItem> getAll();

    @Query("select max(id) from SubItem limit 1 ")
    int getMaxId();

    @Insert
    void insertAll(List<SubItem> subitems);

    @Insert
    void insert(SubItem subitems);

}
