package com.example.applligent.nagoriengineering;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = Item.class, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
}
