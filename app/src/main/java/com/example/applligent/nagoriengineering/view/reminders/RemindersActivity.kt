package com.example.applligent.nagoriengineering.view.reminders

import android.arch.lifecycle.Observer
import android.content.Intent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.ReminderAdapter
import com.example.applligent.nagoriengineering.dao.ReminderDao

import com.example.applligent.nagoriengineering.model.ReminderModel
import kotlinx.android.synthetic.main.activity_reminders.*

class RemindersActivity : AppCompatActivity() {

   lateinit var reminderAdapter: ReminderAdapter
   lateinit var reminderDao: ReminderDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   setContentView(R.layout.activity_reminders)
        reminderDao = MyNagoriApplication.getDatabase(applicationContext).reminderDao()
        reminderDao.all.observe(this, Observer { reminderModelList ->
         remindersView.layoutManager = LinearLayoutManager(applicationContext)
            reminderAdapter = ReminderAdapter(reminderModelList!!)
         remindersView.adapter = reminderAdapter
          remindersView.setOnClickListener { startActivity(Intent(this@RemindersActivity, GetReminderActivity::class.java)) }
        })


    }
}
