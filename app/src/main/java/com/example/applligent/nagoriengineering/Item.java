package com.example.applligent.nagoriengineering;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long sNo;
    public String oem = "";
    public String telPartNumber = "";
    public String engine = "";
    public String application = "";
    public float mrp = 0.0f;
    public int orientationAlpha = 0;
    public int orientationBeta = 0;
    public String strPre = "";
    public String settingPre = "";
    public String lift = "";
    public String reman = "";


    public Item() {

    }


}
