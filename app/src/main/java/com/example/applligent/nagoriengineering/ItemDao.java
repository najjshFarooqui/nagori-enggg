package com.example.applligent.nagoriengineering;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("select * from Item")
    List<Item> getAll();

    @Query("delete  from item")
    void deleteAll();

    @Query("select * from item where oem=:oem")
    List<Item> getParts(String oem);

    @Query("select max(id) from Item limit 1 ")
    int getMaxId();

/*
    @Query("select telPartNumber, oem, mrp, engine ,application from item")
          void details();
*/

    @Query("select distinct oem from item ")
    List<String> distOem();

    @Query("select * from Item where id =:sId ")
    Item get(int sId);

    @Query("Delete from Item where Id= :sid ")
    void deleteMany(int sid);

    @Query("SELECT * from Item where telPartNumber like :name limit 1")
    Item findByName(String name);

    @Insert
    void insertAll(List<Item> items);

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);
}



