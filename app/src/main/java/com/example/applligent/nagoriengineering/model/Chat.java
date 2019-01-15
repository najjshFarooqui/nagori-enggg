package com.example.applligent.nagoriengineering.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Chat {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String displayName;
    public String userId;
    public String timeStamp;
    public String hourMinute;
    public String message;


    public Chat(){

    }

}
