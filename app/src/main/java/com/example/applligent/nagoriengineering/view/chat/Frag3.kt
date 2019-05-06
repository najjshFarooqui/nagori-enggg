package com.example.applligent.nagoriengineering.view.chat

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.dao.StatusDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference


class Frag3 : Fragment() {
    lateinit var mAuth: FirebaseAuth
    lateinit var reference: DatabaseReference
    lateinit var statusDao: StatusDao


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag3_layout, container, false)

        statusDao = MyNagoriApplication.getDatabase(context!!).statusDao()
        mAuth = FirebaseAuth.getInstance()

    }

}


