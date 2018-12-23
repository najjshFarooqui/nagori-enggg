package com.example.applligent.nagoriengineering;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Item.class,SubItem.class} , version = 2)
public abstract class MyDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    public abstract SubItemDao subItemDao();
}
