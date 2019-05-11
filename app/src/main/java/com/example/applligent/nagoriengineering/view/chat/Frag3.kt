package com.example.applligent.nagoriengineering.view.chat


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.WriteStatusFrag
import com.example.applligent.nagoriengineering.model.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.frag3_layout.*
import java.util.*


class Frag3 : Fragment() {
    lateinit var mAuth: FirebaseAuth
    lateinit var reference: DatabaseReference
    lateinit var statusAdapter: StatusAdapter
    private var mStatus: MutableList<Status>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag3_layout, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statusView.setHasFixedSize(true)
        statusView.layoutManager = LinearLayoutManager(context)
        mStatus = ArrayList<Status>()
        statusAdapter = StatusAdapter(mStatus,context)
        statusView.adapter=statusAdapter


        reference = FirebaseDatabase.getInstance().getReference("status")
        /* reference.addValueEventListener(object : ValueEventListener {
             override fun onDataChange(dataSnapshot: DataSnapshot) {

                 for (postSnapshot in dataSnapshot.children) {
                     val status = postSnapshot.getValue(Status::class.java)
                     status!!.key = postSnapshot.key
                     mStatus!!.add(status)
                 }
             }

             override fun onCancelled(databaseError: DatabaseError) {
                 Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
             }
         })
         */
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("abc123", "onCancelled")
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("abc123", "onDataChange")
                for (postSnapshot in p0.children) {
                    val status = postSnapshot.getValue(Status::class.java)
                    status!!.key = postSnapshot.key
                    mStatus!!.add(status)
                }
            }
        })











        add_status.setOnClickListener {
            val myintent = Intent(activity, WriteStatusFrag::class.java)
            startActivity(myintent)
        }
    }


}


