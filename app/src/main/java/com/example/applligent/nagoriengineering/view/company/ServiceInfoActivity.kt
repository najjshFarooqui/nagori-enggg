package com.example.applligent.nagoriengineering.view.company

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.dao.ItemDao

import com.example.applligent.nagoriengineering.model.Item
import kotlinx.android.synthetic.main.activity_service_info.*

class ServiceInfoActivity : AppCompatActivity() {
    var partsId: Int = 0
   lateinit var itemDao: ItemDao

   lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemDao = MyNagoriApplication.getDatabase(applicationContext).itemDao()
     setContentView(R.layout.activity_service_info)
        partsId = intent.getIntExtra("parts_info", -1)
        item = itemDao.get(partsId.toLong())
        tcPartLabel.text = item.telPartNumber
        alphaLabel.setText(item.orientationAlpha)
        betaLabel.setText(item.orientationBeta)
        strokingPrLabel.text = item.strPre
        settingPrLabel.text = item.settingPre
        liftLabel.text = item.lift
        remarksLabel.text = item.reman

        button.setOnClickListener {
            val intent = Intent(applicationContext, PartsInfoActivity::class.java)

            intent.putExtra("tel_part_number", item.telPartNumber)
            startActivity(intent)
        }

    }

}
