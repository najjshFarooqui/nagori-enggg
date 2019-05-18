package com.example.applligent.nagoriengineering.view.chat

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.applligent.nagoriengineering.GeneralPreference
import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.dao.UserDao
import com.example.applligent.nagoriengineering.model.User
import com.example.applligent.nagoriengineering.view.StartActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    lateinit var mToolbar: Toolbar
    lateinit var registerDao: UserDao
    private var mAuth: FirebaseAuth? = null
    private var progressBar: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_register)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Register Yourself"
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        registerDao = MyNagoriApplication.getDatabase(applicationContext).registerDao()
        progressBar = ProgressDialog(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        button.setOnClickListener(View.OnClickListener {
            val user = User()
            val userName = etName.text.toString()
            val userEmail = etEmail.text.toString()
            val userPassword = etPassword.text.toString()
            if (!TextUtils.isEmpty(userName) || !TextUtils.isEmpty(userEmail) || !TextUtils.isEmpty(userPassword)) {
                progressBar!!.setTitle("Registration progress")
                progressBar!!.setMessage("please wait")
                progressBar!!.setCanceledOnTouchOutside(false)
                progressBar!!.show()


                registerUser(userName, userEmail, userPassword)
            }
        })
    }

    private fun registerUser(name: String, email: String, password: String) {
        val authResultTask = mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        progressBar!!.dismiss()
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val userName = currentUser!!.displayName
                        val userId = currentUser.uid
                        val currentEmail = currentUser.email
                        val e = currentEmail

                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.reference.child("users").child(userId)

                        val user = User()
                        user.displayName = name
                        user.email = email
                        user.password = password

                        GeneralPreference.setNameLoaded(applicationContext, name)

                        GeneralPreference.setUserId(applicationContext, userId)

                        GeneralPreference.setUserEmail(applicationContext, currentEmail)

                        myRef.setValue(user).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                progressBar!!.dismiss()
                                registerDao.insert(user)
                                startActivity(Intent(this@RegisterActivity, ChatActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                                finish()
                            }
                        }


                    } else {
                        progressBar!!.hide()
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@RegisterActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this@RegisterActivity, StartActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private val TAG = "registerActivity"
    }

}