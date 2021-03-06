package com.example.applligent.nagoriengineering.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class ReminderModel {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var user: String? = null
    var message = ""
    var time: String? = null
    var date: String? = null
    var sendingTime: String? = null
    var title: String? = null
    var userID: String? = null
}

