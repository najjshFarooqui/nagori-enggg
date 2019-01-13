package com.example.applligent.nagoriengineering.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class User {
    @NonNull
    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    public String displayName;
    public String email;
    public String password;


    public User() {
    }
}
