package com.example.applligent.nagoriengineering.service;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.applligent.nagoriengineering.model.ReminderModel;
import com.google.firebase.database.FirebaseDatabase;

public class ServiceReminder implements TimePickerDialog.OnTimeSetListener ,DatePickerDialog.OnDateSetListener {
public String time ;
public String date ;



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        time =hourOfDay+ " : " +minute;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date= year+"/"+month+"/"+dayOfMonth;

    }

}
