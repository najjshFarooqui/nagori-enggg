package com.example.applligent.nagoriengineering;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SubItem {
    @PrimaryKey(autoGenerate = true)
    int id;
    String telPartNumber;
    String partNumber;
    String description;
    float mrp;
   public SubItem(){

    }
}
