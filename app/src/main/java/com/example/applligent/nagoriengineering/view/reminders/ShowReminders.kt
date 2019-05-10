package com.example.applligent.nagoriengineering.view.reminders

import android.content.Intent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


import com.example.applligent.nagoriengineering.R

import kotlinx.android.synthetic.main.activity_show_reminders.*

class ShowReminders : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Reminders"
        setContentView(R.layout.activity_show_reminders)
        setReminBtn.setOnClickListener { startActivity(Intent(this@ShowReminders, SetReminderActivity::class.java)) }
        showReminBtn.setOnClickListener { startActivity(Intent(this@ShowReminders, RemindersActivity::class.java)) }

    }
}
