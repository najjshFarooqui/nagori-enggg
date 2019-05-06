package com.example.applligent.nagoriengineering.view.chat

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.view.StartActivity
import com.google.firebase.auth.FirebaseAuth

class ChatActivityNew : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth

    lateinit var fragmentCollectionAdapter: FragmentCollectionAdapter
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_new)

        mAuth = FirebaseAuth.getInstance()

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "CHATS"
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewPager)

        fragmentCollectionAdapter = FragmentCollectionAdapter(supportFragmentManager)
        viewPager.adapter = fragmentCollectionAdapter
        tabLayout.setupWithViewPager(viewPager)

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
                startActivity(Intent(this@ChatActivityNew, StartActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun redirectUser() {
        startActivity(Intent(this@ChatActivityNew, StartActivity::class.java))
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this@ChatActivityNew, StartActivity::class.java))
            finish()
        }

    }
}
