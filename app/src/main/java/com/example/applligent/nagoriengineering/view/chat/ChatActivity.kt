package com.example.applligent.nagoriengineering.view.chat

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.example.applligent.nagoriengineering.ChatRecyclerAdapter
import com.example.applligent.nagoriengineering.GeneralPreference
import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.dao.ChatDao
import com.example.applligent.nagoriengineering.model.Chat
import com.example.applligent.nagoriengineering.model.LastSeen
import com.example.applligent.nagoriengineering.view.StartActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth


    lateinit var reference: DatabaseReference
    lateinit var chatDao: ChatDao
    lateinit var messageAdapter: ChatRecyclerAdapter
    lateinit var listOfMessage: RecyclerView
    lateinit var fab_bottom: ImageView
    lateinit var lastSeen: LastSeen


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mAuth = FirebaseAuth.getInstance()


        fab_bottom = findViewById<View>(R.id.fab_bottom) as ImageView
        listOfMessage = findViewById<View>(R.id.list_of_messages) as RecyclerView
        chatDao = MyNagoriApplication.getDatabase(applicationContext!!).chatDao()
        mAuth = FirebaseAuth.getInstance()
        chatDao.all.observe(this, android.arch.lifecycle.Observer { messages ->

            listOfMessage.layoutManager = LinearLayoutManager(applicationContext)
            messageAdapter = ChatRecyclerAdapter(messages, applicationContext)
            listOfMessage.adapter = messageAdapter
            listOfMessage.scrollToPosition(messages!!.size - 1)


            fab_bottom.setOnClickListener {
                listOfMessage.scrollToPosition(messages.size - 1)
            }

        })

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton

        fab.setOnClickListener {
            val message = findViewById<View>(R.id.input_message) as EditText
            val chatMessages = Chat()
            chatMessages.message = message.text.toString()
            chatMessages.displayName = GeneralPreference.getNameLoaded(applicationContext)
            chatMessages.userId = GeneralPreference.getUserId(applicationContext)
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val timeHourMin = SimpleDateFormat("HH:mm")
            chatMessages.timeStamp = sdf.format(Date())
            chatMessages.hourMinute = timeHourMin.format(Date())
            val chatMessagesList = ArrayList<Chat>()

            if (chatMessages.message!!.isNotEmpty()) {
                chatMessagesList.add(chatMessages)

                reference = FirebaseDatabase.getInstance().reference.child("messages")
                reference.push().setValue(chatMessages)
                message.setText("")
                message.hint = "Type a message"

            }

        }

        listOfMessage.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy < 0) {
                    fab_bottom.visibility = View.VISIBLE
                    view_fab.visibility = View.VISIBLE

                } else if (dy > 0) {
                    fab_bottom.visibility = View.GONE
                    view_fab.visibility = View.GONE
                }
            }

        })
        listOfMessage.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                listOfMessage.postDelayed(Runnable { listOfMessage.scrollToPosition(listOfMessage.adapter!!.itemCount - 1) }, 100)
            }
        }


        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "CHATS"
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.toolbar_chat))
        }








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
        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this@ChatActivity, StartActivity::class.java))
            finish()

        }


    }

    override fun onResume() {
        super.onResume()
        GeneralPreference.setFlag(applicationContext!!, "najish")


        reference = FirebaseDatabase.getInstance().reference.child("time")
        lastSeen = LastSeen()
        lastSeen.active = "online"
        lastSeen.time = ""
        lastSeen.name = mAuth.currentUser!!.email
        reference.push().setValue(lastSeen)



        reference.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Log.d("abc123", "onCancelled")
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("abc123", "onDataChange")
                for (postSnapshot in p0.children) {
                    val lastSeen = postSnapshot.getValue(LastSeen::class.java)
                    lastSeen!!.active = p0.key


                }
            }
        })

    }

    override fun onStop() {
        super.onStop()
        GeneralPreference.setFlag(applicationContext!!, "farooqui")
        if (mAuth.currentUser != null) {

            reference = FirebaseDatabase.getInstance().reference.child("users")
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            lastSeen = LastSeen()
            lastSeen.time = sdf.format(Date())
            lastSeen.active = ""
            lastSeen.name = mAuth.currentUser!!.email
            reference.push().setValue(lastSeen)
        }

    }
}
