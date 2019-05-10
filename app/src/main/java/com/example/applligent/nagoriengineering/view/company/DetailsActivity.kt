package com.example.applligent.nagoriengineering.view.company

import android.content.Intent

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View

import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.dao.ItemDao
import com.example.applligent.nagoriengineering.model.Item
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
     var itemId: Long =0
    lateinit var itemDao: ItemDao

    lateinit var item: Item

    var partsId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_details)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Detail"
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        itemDao = MyNagoriApplication.getDatabase(applicationContext).itemDao()
        itemId = intent.getLongExtra("item_id", -1)
        showDetails()
        parts.setOnClickListener {
            item = itemDao.get(itemId)
            val intent = Intent(applicationContext, PartsInfoActivity::class.java)
            intent.putExtra("tel_part_number", item.telPartNumber)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    fun showDetails() {
        item = itemDao.get(itemId)
       telPartLabel.text = item.telPartNumber
       oemPartLabel.text = item.oem
       engineLabel.text = item.engine
       applicationLabel.text = item.application
       mrpLabel.text = java.lang.Float.toString(item.mrp)
       telPartLabel.text = item.telPartNumber
       alphaLabel.text = Integer.toString(item.orientationAlpha)
       betaLabel.text = Integer.toString(item.orientationBeta)
       strokingLabel.text = item.strPre
       settingPrLabel.text = item.settingPre
       liftLabel.text = item.lift
       remarksLabel.text = item.reman
    }

    fun showServiceInfo() {

        partsId = intent.getIntExtra("parts_info", -1)
        item = itemDao.get(partsId.toLong())

    }
}




