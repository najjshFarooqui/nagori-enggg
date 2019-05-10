package com.example.applligent.nagoriengineering.view.company


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu


import com.example.applligent.nagoriengineering.ItemAdapter
import com.example.applligent.nagoriengineering.MyNagoriApplication
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.dao.ItemDao

import kotlinx.android.synthetic.main.activity_item_list.*

class CompanyListActivity : AppCompatActivity() {

    lateinit var itemDao: ItemDao
    lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_item_list)
        itemDao = MyNagoriApplication.getDatabase(applicationContext).itemDao()

        supportActionBar!!.title = "Company List"

      recyclerView.layoutManager = LinearLayoutManager(this)
        val companyList = itemDao.distOem()
        adapter = ItemAdapter(companyList)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                adapter.filter.filter(s)
                return false
            }
        })
        return true
    }
}
