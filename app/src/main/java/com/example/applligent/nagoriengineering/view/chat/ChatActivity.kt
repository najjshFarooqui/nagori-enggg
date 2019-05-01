package com.example.applligent.nagoriengineering.view.chat

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.example.applligent.nagoriengineering.GeneralPreference
import com.example.applligent.nagoriengineering.MessageAdapter
import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.dao.ChatDao
import com.example.applligent.nagoriengineering.model.Chat
import com.example.applligent.nagoriengineering.view.StartActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var reference: DatabaseReference
    lateinit var chatDao: ChatDao
    lateinit var messageAdapter: MessageAdapter
    lateinit var messageView: RecyclerView
    lateinit var fab_bottom: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



        fab_bottom = findViewById<View>(R.id.fab_bottom) as ImageView
        messageView = findViewById<View>(R.id.list_of_messages) as RecyclerView
        chatDao = MyNagoriApplication.getDatabase(applicationContext).chatDao()
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "CHATS"
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        mAuth = FirebaseAuth.getInstance()
        //if internet is on

        chatDao.all.observe(this, Observer { messages ->

            messageView.layoutManager = LinearLayoutManager(applicationContext)
            messageAdapter = MessageAdapter(messages, applicationContext)
            messageView.adapter = messageAdapter
            messageView.scrollToPosition(messages!!.size - 1)


            fab_bottom.setOnClickListener {
                messageView.scrollToPosition(messages.size - 1)
            }
        })
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton

        fab.setOnClickListener {
            val message = findViewById<View>(R.id.input_message) as EditText
            val chatMessages = Chat()
            chatMessages.message = message.text.toString()
            val currentUser = FirebaseAuth.getInstance().currentUser


            chatMessages.displayName = GeneralPreference.getNameLoaded(applicationContext)
            chatMessages.userId = GeneralPreference.getUserId(applicationContext)
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val timeHourMin = SimpleDateFormat("HH:mm")
            chatMessages.timeStamp = sdf.format(Date())
            chatMessages.hourMinute = timeHourMin.format(Date())
            val chatMessagesList = ArrayList<Chat>()
            chatMessagesList.add(chatMessages)
            // if (GeneralPreference.getUserId(applicationContext) != chatMessages.userId) {
            //     chatDao.insertAll(chatMessagesList)
            // }

            reference = FirebaseDatabase.getInstance().reference.child("messages")
            reference.push().setValue(chatMessages)
            message.setText("")
            message.hint = "Type a message"
        }

        messageView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy < 0) {
                    fab_bottom.visibility = View.VISIBLE
                } else if (dy > 0) {
                    fab_bottom.visibility = View.GONE
                }
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                redirectUser()
                return true
            }
            R.id.home -> {
                startActivity(Intent(this@ChatActivity, StartActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun redirectUser() {
        startActivity(Intent(this@ChatActivity, StartActivity::class.java))
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this@ChatActivity, StartActivity::class.java))
            finish()
        }

    }


}
