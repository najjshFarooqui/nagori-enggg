package com.example.applligent.nagoriengineering;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    int itemId;
    Item item;
    ItemDao itemDao;
    ActivityDetailsBinding binding;
    TextView oem, telPartNumber, engine, application, mrp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        itemId = getIntent().getIntExtra("details", -1);
        itemDao = MyNagoriApplication.getDatabase().itemDao();
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
        //
        binding.parts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartsInfoActivity.class);
                intent.putExtra("parts_info", item.id);
                startActivity(intent);

            }
        });
        binding.service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ServiceInfoActivity.class);
                intent.putExtra("service_info", item.id);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        item = itemDao.get(itemId);
        binding.setItem(item);
    }
}
