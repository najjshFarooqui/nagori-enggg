package com.example.applligent.nagoriengineering.view.company;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.applligent.nagoriengineering.ItemAdapter;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.ItemDao;
import com.example.applligent.nagoriengineering.databinding.ActivityItemListBinding;

import java.util.List;

public class CompanyListActivity extends AppCompatActivity {
    ActivityItemListBinding binding;
    ItemDao itemDao;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_list);
        itemDao = MyNagoriApplication.Companion.getDatabase(getApplicationContext()).itemDao();

        getSupportActionBar().setTitle("Company List");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> companyList = itemDao.distOem();
        adapter = new ItemAdapter(companyList);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
}
