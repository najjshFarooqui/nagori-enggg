package com.example.applligent.nagoriengineering;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.persistence.room.Room;
import android.os.Build;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyNagoriApplication extends Application {
    public static final String reminderChannel = "reminder";
    public static final String chatChannel = "Nagori_chat";

    final static String DATABASE_NAME = "db-newNagori";
    private static MyDatabase myDatabase;
    private static MyNagoriApplication myNagoriApplication;

    public static MyDatabase getDatabase() {
        if (myDatabase == null) {
            myDatabase = Room.databaseBuilder(myNagoriApplication,
                    MyDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return myDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic("notifications");
        myNagoriApplication = this;
        createNotificationChannels();
    }

    public void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nagori_chat = new NotificationChannel(
                    chatChannel, "nagori chat",
                    NotificationManager.IMPORTANCE_HIGH);
            nagori_chat.setDescription("new message");

            NotificationChannel reminder = new NotificationChannel(
                    reminderChannel, "reminders",
                    NotificationManager.IMPORTANCE_HIGH);
            reminder.setDescription("new reminder");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(nagori_chat);
            manager.createNotificationChannel(reminder);
        }
    }
}
