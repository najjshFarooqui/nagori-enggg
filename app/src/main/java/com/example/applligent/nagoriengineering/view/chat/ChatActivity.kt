package com.example.applligent.nagoriengineering.view.chat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.view.StartActivity
import com.google.firebase.auth.FirebaseAuth

class ChatActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mAuth = FirebaseAuth.getInstance()

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "CHATS"
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
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


}
