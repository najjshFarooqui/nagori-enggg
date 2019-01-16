package com.example.applligent.nagoriengineering.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity
public class ReminderModel {

        @PrimaryKey(autoGenerate = true)
        public long id;
        public String user;
        public String message;
        public String time;
        public String date;
        public String sendingTime;
}

