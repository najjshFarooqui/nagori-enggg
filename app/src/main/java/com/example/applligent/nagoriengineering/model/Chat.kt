package com.example.applligent.nagoriengineering.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
class Chat {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var displayName: String? = null
    var userId: String? = null
    var timeStamp: String? = null
    var hourMinute: String? = null
    var message: String? = null
    override fun toString(): String {
        return "Chat(id=$id, displayName=$displayName, userId=$userId, timeStamp=$timeStamp, hourMinute=$hourMinute, message=$message)"
    }

}
