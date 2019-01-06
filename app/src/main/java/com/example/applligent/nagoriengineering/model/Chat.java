package com.example.applligent.nagoriengineering.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Chat {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String displayName;
    public String userId;
    public long timeStamp;

    public String message;

    public Chat(){

    }

    public Chat(String message, String displayName) {
        this.message=message;
        this.displayName=displayName;
    }
}
