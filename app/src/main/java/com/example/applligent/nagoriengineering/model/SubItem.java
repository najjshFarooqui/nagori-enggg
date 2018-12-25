package com.example.applligent.nagoriengineering.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SubItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String telPartNumber;
    public String partNumber;
    public String description;
    public float mrp;
   public SubItem(){

    }
}
