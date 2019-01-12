package com.example.applligent.nagoriengineering;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.applligent.nagoriengineering.dao.ChatDao;
import com.example.applligent.nagoriengineering.dao.ItemDao;
import com.example.applligent.nagoriengineering.dao.RegisterDao;
import com.example.applligent.nagoriengineering.dao.ReminderDao;
import com.example.applligent.nagoriengineering.dao.SubItemDao;
import com.example.applligent.nagoriengineering.model.Chat;
import com.example.applligent.nagoriengineering.model.Item;
import com.example.applligent.nagoriengineering.model.RegisterModel;
import com.example.applligent.nagoriengineering.model.ReminderModel;
import com.example.applligent.nagoriengineering.model.SubItem;

@Database(entities = {Item.class,SubItem.class,Chat.class,RegisterModel.class,ReminderModel.class} , version = 5)
public abstract class MyDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    public abstract SubItemDao subItemDao();
    public abstract ChatDao chatDao();
    public abstract RegisterDao registerDao();
    public abstract ReminderDao reminderDao();
}
