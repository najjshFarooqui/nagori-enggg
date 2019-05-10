package com.example.applligent.nagoriengineering


import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_write_status.*
import android.graphics.Typeface
import com.example.applligent.nagoriengineering.model.Status
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class WriteStatusFrag : AppCompatActivity() {

    lateinit var status: EditText
    lateinit var color: ImageView
    lateinit var font: ImageView
    lateinit var send: ImageView
    lateinit var mAuth: FirebaseAuth
    lateinit var reference: DatabaseReference
    var colorState: Int = 0
    var textState: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_write_status)



        mAuth = FirebaseAuth.getInstance()
        status = findViewById(R.id.et_status)
        color = findViewById(R.id.background_color)
        font = findViewById(R.id.font)
        send = findViewById(R.id.send_status)

        color.setOnClickListener {
            val rn = Random()
            val answer = rn.nextInt(10) + 1

            when (answer) {
                0 -> {
                    bg_status.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.colorAccent));


                }
                1 -> {
                    bg_status.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.divider));


                }
                2 -> {
                    bg_status.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.light));
                }
                3 -> {
                    bg_status.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.primary_text));

                }
                4 -> {
                    bg_status.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.table_header));

                }
                5 -> {
                    bg_status.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.table_row));

                }
                6 -> {
                    bg_status.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.new_color));

                }
                7 -> {
                    bg_status.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.colorPrimary));

                }
                8 -> {
                    bg_status.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.light_red));

                }
            }


        }

        font.setOnClickListener {
            val rn = Random()
            val answer = rn.nextInt(10) + 1
            when (answer) {
                1 -> {
                    val face = Typeface.createFromAsset(assets,
                            "AlexBrush-Regular.ttf")
                    status.typeface = face
                }
                2 -> {
                    val face = Typeface.createFromAsset(assets,
                            "KaushanScript-Regular.otf")
                    status.typeface = face
                }
                3 -> {
                    val face = Typeface.createFromAsset(assets,
                            "Montserrat-ExtraBoldItalic.otf")
                    status.typeface = face
                }
                4 -> {
                    val face = Typeface.createFromAsset(assets,
                            "OpenSans-Bold.ttf")
                    status.typeface = face
                }
                5 -> {
                    val face = Typeface.createFromAsset(assets,
                            "OpenSans-Italic.ttf")
                    status.typeface = face
                }
            }

        }

        send.setOnClickListener {


            var status_text = status.text.toString()
            var status_font = status.typeface.toString()

            val statusClass = Status()
            statusClass.message = status_text
            statusClass.font = status_font
            statusClass.displayName = GeneralPreference.getNameLoaded(applicationContext)
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val timeHourMin = SimpleDateFormat("HH:mm")
            statusClass.hourMinute = timeHourMin.format(Date())
            val statusList = ArrayList<Status>()
            statusList.add(statusClass)
            reference = FirebaseDatabase.getInstance().reference.child("status")
            reference.push().setValue(statusClass)
            status.setText("")
            status.hint = "Type a status"



        }





    }
}

