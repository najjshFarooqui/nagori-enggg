package com.example.applligent.nagoriengineering;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyNagoriApplication extends Application {
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
    }
}
