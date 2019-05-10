package com.example.applligent.nagoriengineering.view.company

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.example.applligent.nagoriengineering.GeneralPreference
import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.dao.ItemDao
import com.example.applligent.nagoriengineering.dao.SubItemDao
import com.example.applligent.nagoriengineering.model.Item
import com.example.applligent.nagoriengineering.model.SubItem

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
   lateinit var itemDao: ItemDao
   lateinit var subItem: SubItem
   lateinit var subItemDao: SubItemDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemDao = MyNagoriApplication.getDatabase(applicationContext).itemDao()
        subItemDao = MyNagoriApplication.getDatabase(applicationContext).subItemDao()
        if (!GeneralPreference.getDataLoaded(this)) {
            readPartsCsv()
            readSubPartsCsv()
            GeneralPreference.setDataLoaded(this)
        }
        startActivity(Intent(this, CompanyListActivity::class.java))
    }

    fun readPartsCsv() {
        itemDao.deleteAll()
        val `is` = resources.openRawResource(R.raw.parts)
        val reader = BufferedReader(
                InputStreamReader(`is`, Charset.forName("UTF-8")))
        var line = ""

        try {
            while ((line == reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                val tokens: Array<String> // Read the data and store it in the WellData POJO.
                tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                // Read the data and store it in the WellData POJO.
                val items = Item()
                items.sNo = java.lang.Long.parseLong(tokens[0].trim { it <= ' ' })
                items.oem = tokens[1].trim { it <= ' ' }
                items.telPartNumber = tokens[2].trim { it <= ' ' }
                items.engine = tokens[3].trim { it <= ' ' }
                items.application = tokens[4].trim { it <= ' ' }
                items.mrp = java.lang.Float.parseFloat(tokens[5].trim { it <= ' ' })
                items.orientationAlpha = Integer.parseInt(tokens[6].trim { it <= ' ' })
                items.orientationBeta = Integer.parseInt(tokens[7].trim { it <= ' ' })
                try {
                    items.strPre = tokens[8].trim { it <= ' ' }
                    items.settingPre = tokens[9].trim { it <= ' ' }
                    items.lift = tokens[10].trim { it <= ' ' }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                //TODO items.reman = (tokens[11]);
                itemDao.insert(items)

                Log.d("MainActivity", "Just Created $items")
            }
        } catch (e1: IOException) {
            Log.e("MainActivity", "Error$line", e1)
            e1.printStackTrace()
        }

        val list = itemDao.all
        for (i in list.indices) {
            Log.d("abc123", list[i].application)
        }
    }

    fun readSubPartsCsv() {
        subItemDao.deleteAll()
        val `is` = resources.openRawResource(R.raw.subparts)
        val reader = BufferedReader(
                InputStreamReader(`is`, Charset.forName("UTF-8")))
        var line = ""

        try {
            while ((line == reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                val tokens: Array<String> // Read the data and store it in the WellData POJO.
                tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                // Read the data and store it in the WellData POJO.
                subItem = SubItem()
                subItem.telPartNumber = tokens[0].trim { it <= ' ' }
                subItem.partNumber = tokens[1].trim { it <= ' ' }
                subItem.description = tokens[2].trim { it <= ' ' }
                subItem.mrp = java.lang.Float.parseFloat(tokens[3].trim { it <= ' ' })
                subItemDao.insert(subItem)

                Log.d("details12345", "Just Created $subItem")
            }
        } catch (e1: IOException) {
            Log.e("MainActivity", "Error$line", e1)
            e1.printStackTrace()
        }

        val list = subItemDao.all
        for (i in list.indices) {
            Log.d("abc123", list[i].description)
        }
    }
}
