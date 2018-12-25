package com.example.applligent.nagoriengineering.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.applligent.nagoriengineering.model.SubItem;

import java.util.List;

@Dao
public interface SubItemDao {
    @Query("select * from SubItem")
    List<SubItem> getAll();

    @Query("select max(id) from SubItem limit 1 ")
    int getMaxId();

    @Query("select * from subitem where telPartNumber=:telPartNumber")
    List<SubItem> getSubParts(String telPartNumber);

    @Query("select * from SubItem where id =:sId ")
    int get(int sId);

    @Insert
    void insertAll(List<SubItem> subitems);

    @Insert
    void insert(SubItem subitems);


    @Query("delete from Subitem")
    void deleteAll();

}
