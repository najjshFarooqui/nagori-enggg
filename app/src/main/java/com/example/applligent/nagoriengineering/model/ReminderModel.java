package com.example.applligent.nagoriengineering.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity
public class ReminderModel {

        @PrimaryKey(autoGenerate = true)
        public long id;
    }

