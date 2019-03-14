package com.example.applligent.nagoriengineering.model


@Entity
class Chat {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var displayName: String? = null
    var userId: String? = null
    var timeStamp: String? = null
    var hourMinute: String? = null
    var message: String? = null

}
