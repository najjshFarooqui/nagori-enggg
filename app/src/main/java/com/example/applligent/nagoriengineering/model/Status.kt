package com.example.applligent.nagoriengineering.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.firebase.database.Exclude


class Status {


    var displayName: String? = null
    var userId: String? = null
    var hourMinute: String? = null
    var message: String? = null
    var font :String?= null
    @Exclude
    var key :String?=null
    override fun toString(): String {
        return "Status(displayName=$displayName, userId=$userId, hourMinute=$hourMinute, message=$message, font=$font, key=$key)"
    }
}