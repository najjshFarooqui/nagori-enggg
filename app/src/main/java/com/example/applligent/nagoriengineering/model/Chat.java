package com.example.applligent.nagoriengineering.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Chat {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String displayName;
    public String userId="12345";
    public String timeStamp;
    public String message;


    public Chat(){

    }

}
