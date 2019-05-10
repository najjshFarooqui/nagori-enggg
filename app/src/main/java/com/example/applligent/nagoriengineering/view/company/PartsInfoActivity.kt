package com.example.applligent.nagoriengineering.view.company


import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.dao.SubItemDao
import com.example.applligent.nagoriengineering.model.SubItem
import kotlinx.android.synthetic.main.activity_service_info.*

class PartsInfoActivity : AppCompatActivity() {
    lateinit var subItem: SubItem

    lateinit var telPart: String
    lateinit var tableLayout: TableLayout
    lateinit var subItemDao: SubItemDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Serviceble Parts"
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        subItemDao = MyNagoriApplication.getDatabase(applicationContext).subItemDao()
      setContentView(R.layout.activity_parts_info)
        telPart = intent.getStringExtra("tel_part_number")
        tcPartLabel.text = "TC PART NO: $telPart"

        val info = TableRow(this)
        info.setBackgroundColor(Color.GRAY)
        info.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT)

        val headingRow = TableRow(this)
        headingRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT)
        headingRow.id = -1
        headingRow.addView(getHeaderLabel(1 * 100, "PART NO."))
        headingRow.addView(getHeaderLabel(2 * 100, "DESCRIPTION"))
        headingRow.addView(getHeaderLabel(3 * 100, "MRP"))
        tableLayout.addView(headingRow, TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT))


        val subItemDetail = subItemDao.getSubParts(telPart)
        for (i in subItemDetail.indices) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT)
            tableRow.id = i
            subItem = subItemDetail[i]
            tableRow.addView(getRowLabel(i, i * 1000, subItem.partNumber))
            tableRow.addView(getRowLabel(i, i * 1000, subItem.description))
            tableRow.addView(getRowLabel(i, i * 1000, subItem.mrp.toString() + ""))
            tableLayout.addView(tableRow, TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT))


        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
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

