package com.example.applligent.nagoriengineering.view.reminders

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent

import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem

import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast

import com.example.applligent.nagoriengineering.DatePickerFragment
import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.ReminderAdapter
import com.example.applligent.nagoriengineering.TimePickerFragment
import com.example.applligent.nagoriengineering.dao.ReminderDao

import com.example.applligent.nagoriengineering.model.ReminderModel
import com.example.applligent.nagoriengineering.view.chat.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_set_reminder.*

import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class SetReminderActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    lateinit var reminderDao: ReminderDao
    lateinit var mAuth: FirebaseAuth
    lateinit var reference: DatabaseReference
    lateinit var reminderAdapter: ReminderAdapter
    lateinit var date: String
    lateinit var time: String
    lateinit var reminderModel: ReminderModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_reminder)

        mAuth = FirebaseAuth.getInstance()
        reminderDao = MyNagoriApplication.getDatabase(applicationContext).reminderDao()
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Set a Reminder"
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        val spinnerAdapter = ArrayAdapter(this@SetReminderActivity, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.reminde_spiner))
        reminder_spinner.adapter = spinnerAdapter

        set_date.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.show(supportFragmentManager, "date picker")
        }

        set_time.setOnClickListener {
            val timepIcker = TimePickerFragment()
            timepIcker.show(supportFragmentManager, "time picker")
        }

        reminderButton.setOnClickListener {
            val message =input.text.toString()
            val title = reminder_title.text.toString()
            val name = reminder_spinner.selectedItem.toString()

            if (set_date.text.toString().isEmpty() && set_time.text.toString().isEmpty()
                    && input.text.toString().isEmpty()
                    &&reminder_spinner.selectedItem.toString().isEmpty()
                    && reminder_title.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "please provide all info. carefully", Toast.LENGTH_SHORT).show()
            } else {
                reminderModel = ReminderModel()
                if (name.equals("select name", ignoreCase = true)) {
                    Toast.makeText(applicationContext, "please select a name", Toast.LENGTH_SHORT).show()
                }
                val uuid = UUID.randomUUID().toString()
                reminderModel.userID = uuid
                reminderModel.message = message
                reminderModel.user = name
                reminderModel.title = title
                Log.i("abc1234", reminderModel.title)
                val sdf = SimpleDateFormat("yyyyMMddHHmmss")
                reminderModel.sendingTime = sdf.format(Date())
                reminderModel.time = time
                reminderModel.date = date
                reminderDao.insert(reminderModel)
                reference = FirebaseDatabase.getInstance().reference.child("reminders")
                reference.push().setValue(reminderModel)
                input.setText("")
                input.hint = "Type a reminder"
                reminder_title.hint = "Type a title"

                startActivity(Intent(this@SetReminderActivity, RemindersActivity::class.java))
            }
        }

    }


    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        set_time.text = hourOfDay.toString() + " : " + minute
        time = hourOfDay.toString() + " : " + minute

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        set_date.text = year.toString() + "/" + month + "/" + dayOfMonth
        date = year.toString() + "/" + month + "/" + dayOfMonth

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.home -> {
                startActivity(Intent(this@SetReminderActivity, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}

