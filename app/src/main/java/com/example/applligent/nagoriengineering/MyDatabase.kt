package com.example.applligent.nagoriengineering

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.applligent.nagoriengineering.dao.*
import com.example.applligent.nagoriengineering.model.*

@Database(entities = arrayOf(Item::class, SubItem::class, Chat::class, User::class, ReminderModel::class), version = 12)
abstract class MyDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun subItemDao(): SubItemDao
    abstract fun chatDao(): ChatDao

    abstract fun registerDao(): UserDao
    abstract fun reminderDao(): ReminderDao
}
