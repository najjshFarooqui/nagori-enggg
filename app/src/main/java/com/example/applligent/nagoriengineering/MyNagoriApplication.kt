package com.example.applligent.nagoriengineering

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.arch.persistence.room.Room
import android.content.Context
import android.os.Build

import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MyNagoriApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("notifications")
        FirebaseMessaging.getInstance().subscribeToTopic("reminders")
        applicationContext
        createNotificationChannels()
    }

    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nagori_chat = NotificationChannel(
                    chatChannel, "nagori chat",
                    NotificationManager.IMPORTANCE_HIGH)
            nagori_chat.description = "new message"

            val reminder = NotificationChannel(
                    reminderChannel, "reminders",
                    NotificationManager.IMPORTANCE_HIGH)
            reminder.description = "new reminder"
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(nagori_chat)
            manager.createNotificationChannel(reminder)
        }
    }

    companion object {
        val reminderChannel = "reminder"
        val chatChannel = "Nagori_chat"

        internal val DATABASE_NAME = "db-newNagori"
        private var myDatabase: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            if (myDatabase == null) {
                myDatabase = Room.databaseBuilder(context,
                        MyDatabase::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return myDatabase as MyDatabase
        }
    }
}
