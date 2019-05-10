package com.example.applligent.nagoriengineering.view.company

import android.annotation.SuppressLint
import android.content.Intent

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.dao.ItemDao

import com.example.applligent.nagoriengineering.model.Item
import kotlinx.android.synthetic.main.activity_company_details.*

class CompanyDetailsActivity : AppCompatActivity() {
   lateinit var itemDao: ItemDao

   lateinit var oem: String
   lateinit var itemList: List<Item>

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemDao = MyNagoriApplication.getDatabase(applicationContext).itemDao()
       setContentView(R.layout.activity_company_details)
        oem = intent.getStringExtra("oem")
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = oem
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        itemList = itemDao.getParts(oem)
        headerData()
        rowData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("ResourceType")
    fun headerData() {
        val data = TableRow(this)
        data.id = -1
        data.setBackgroundColor(Color.GRAY)
        data.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT)

        val headers = arrayOf("SL No", "OEM Part Number", "TEL Part Number", "Engine", "Application", "MRP", "Orientation Alpha", "Orientation Beta", "Str Pre", "Setting pr", "Lift", "Reman")
        for (i in headers.indices) {
            data.addView(getHeaderLabel(i, headers[i]))
        }
        tableLayout.addView(data, TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT))
    }


    fun rowData() {

        for (i in itemList.indices) {
            val row = TableRow(this)
            row.setOnClickListener { v ->
                val intent = Intent(applicationContext, DetailsActivity::class.java)
                //intent.putExtra("tel_part_number", itemList.get(v.getId()).telPartNumber);
                val rowId = v.id
                val item = itemList[rowId]
                intent.putExtra("item_id", item.id)
                startActivity(intent)
            }
            row.id = i
            row.setBackgroundColor(Color.GRAY)
            row.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT)
            val item = itemList[i]
            row.addView(getRowLabel(i, i * 1, item.sNo.toString() + ""))
            row.addView(getRowLabel(i, i * 2, item.telPartNumber))
            row.addView(getRowLabel(i, i * 3, item.oem))
            row.addView(getRowLabel(i, i * 4, item.engine))
            row.addView(getRowLabel(i, i * 5, item.application))
            row.addView(getRowLabel(i, i * 6, java.lang.Float.toString(item.mrp)))
            row.addView(getRowLabel(i, i * 7, Integer.toString(item.orientationAlpha)))
            row.addView(getRowLabel(i, i * 8, Integer.toString(item.orientationBeta)))
            row.addView(getRowLabel(i, i * 9, item.strPre))
            row.addView(getRowLabel(i, i * 10, item.settingPre))
            row.addView(getRowLabel(i, i * 11, item.lift))
            row.addView(getRowLabel(i, i * 12, item.reman))

            tableLayout.addView(row, TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT))
        }


    }


    private fun getHeaderLabel(id: Int, text: String): TextView {
        val label = TextView(this)
        label.id = 100 * id
        label.setBackgroundColor(ContextCompat.getColor(this, R.color.table_header))
        label.text = text
        label.setTypeface(null, Typeface.BOLD)
        label.setTextColor(ContextCompat.getColor(this, R.color.primary_text))
        label.setPadding(24, 24, 24, 24)
        return label
    }

    private fun getRowLabel(rowId: Int, id: Int, text: String): TextView {
        val label = TextView(this)
        label.id = 1000 * id
        label.text = text
        label.setTextColor(ContextCompat.getColor(this, R.color.primary_text))
        label.setPadding(24, 24, 24, 24)
        if (rowId % 2 == 0)
            label.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        else
            label.setBackgroundColor(ContextCompat.getColor(this, R.color.table_row))

        return label
    }


}
