package com.example.applligent.nagoriengineering;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.applligent.nagoriengineering.dao.ItemDao;
import com.example.applligent.nagoriengineering.dao.SubItemDao;
import com.example.applligent.nagoriengineering.model.Item;
import com.example.applligent.nagoriengineering.model.SubItem;

@Database(entities = {Item.class,SubItem.class} , version = 2)
public abstract class MyDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    public abstract SubItemDao subItemDao();
}
