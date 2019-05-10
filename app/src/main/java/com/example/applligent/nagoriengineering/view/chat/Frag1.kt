package com.example.applligent.nagoriengineering.view.chat

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.example.applligent.nagoriengineering.*
import com.example.applligent.nagoriengineering.dao.ChatDao
import com.example.applligent.nagoriengineering.model.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class Frag1 : Fragment() {
    lateinit var mAuth: FirebaseAuth
    lateinit var reference: DatabaseReference
    lateinit var chatDao: ChatDao
    lateinit var messageAdapter: ChatRecyclerAdapter
    lateinit var messageView: RecyclerView
    lateinit var fab_bottom: ImageView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag1_layout, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab_bottom = view.findViewById<View>(R.id.fab_bottom) as ImageView
        messageView = view.findViewById<View>(R.id.list_of_messages) as RecyclerView
        chatDao = MyNagoriApplication.getDatabase(context!!).chatDao()
        mAuth = FirebaseAuth.getInstance()
        chatDao.all.observe(this, Observer { messages ->

            messageView.layoutManager = LinearLayoutManager(context)
            messageAdapter = ChatRecyclerAdapter(messages, context)
            messageView.adapter = messageAdapter
            messageView.scrollToPosition(messages!!.size - 1)


            fab_bottom.setOnClickListener {
                messageView.scrollToPosition(messages.size - 1)
            }
        })
        val fab = view.findViewById<View>(R.id.fab) as FloatingActionButton

        fab.setOnClickListener {
            val message = view.findViewById<View>(R.id.input_message) as EditText
            val chatMessages = Chat()
            chatMessages.message = message.text.toString()
            chatMessages.displayName = GeneralPreference.getNameLoaded(context)
            chatMessages.userId = GeneralPreference.getUserId(context)
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val timeHourMin = SimpleDateFormat("HH:mm")
            chatMessages.timeStamp = sdf.format(Date())
            chatMessages.hourMinute = timeHourMin.format(Date())
            val chatMessagesList = ArrayList<Chat>()
            chatMessagesList.add(chatMessages)

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
        messageView.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                messageView.postDelayed(Runnable { messageView.scrollToPosition(messageView.adapter!!.itemCount - 1) }, 100)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        GeneralPreference.setFlag(context!!,"najish")



    }

    override fun onStop() {
        super.onStop()
        GeneralPreference.setFlag(context!!,"farooqui")

    }


}




