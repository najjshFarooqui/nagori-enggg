package com.example.applligent.nagoriengineering

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker

class ServiceReminder : TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    var time: String = ""
    var date: String = ""
    var temp: String? = null


    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        time = hourOfDay.toString() + " : " + minute
        temp = "abc"

        Log.d("abc", temp?.length.toString())

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        date = year.toString() + "/" + month + "/" + dayOfMonth

    }

}
