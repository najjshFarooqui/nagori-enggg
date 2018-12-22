package com.example.applligent.nagoriengineering;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.databinding.ActivityPartsInfoBinding;

import org.w3c.dom.Text;

public class PartsInfoActivity extends AppCompatActivity {
    Item item;
    ItemDao itemDao;
    ActivityPartsInfoBinding binding;
    int partsId;
    TableLayout tableLayout;
    DrawerLayout drawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts_info);
        itemDao = MyNagoriApplication.getDatabase().itemDao();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parts_info);
        partsId = getIntent().getIntExtra("parts_info", -1);
        item = itemDao.get(partsId);
        binding.setItem(item);

        tableLayout=(TableLayout)findViewById(R.id.table);


        TableRow info = new TableRow(this);
       info.setBackgroundColor(Color.GRAY);
       info.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView partNo = new TextView(this);
        partNo.setText("PART NO.");

        TextView description = new TextView(this);
        description.setText("DESCRIPTION");

        TextView mrp = new TextView(this);
        mrp.setText("MRP");


        //drawer code
        //drawer code
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.nav_home) {
                    Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.nav_copyright) {
                    Intent intent = new Intent(getApplicationContext(), Copyright.class);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.nav_about) {
                    Intent intent = new Intent(getApplicationContext(), About.class);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.nav_exit) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        item = itemDao.get(partsId);
        binding.setItem(item);
    }
}
