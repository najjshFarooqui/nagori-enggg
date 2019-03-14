package com.example.applligent.nagoriengineering.model


@Entity
class Item {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var sNo: Long = 0
    var oem = ""
    var telPartNumber = ""
    var engine = ""
    var application = ""
    var mrp = 0.0f
    var orientationAlpha = 0
    var orientationBeta = 0
    var strPre = ""
    var settingPre = ""
    var lift = ""
    var reman = ""


}
