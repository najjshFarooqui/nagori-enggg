package com.example.applligent.nagoriengineering;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.databinding.ActivityDetailsBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    String telPartNumberxx;
    SubItem subItem;
    SubItemDao subItemDao;
    ActivityDetailsBinding binding;
    TextView oem, telPartNumber, engine, application, mrp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        telPartNumberxx = getIntent().getStringExtra("tel_part_number");
        subItemDao = MyNagoriApplication.getDatabase().subItemDao();
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
                //intent.putExtra("parts_info", subItem.id);
                startActivity(intent);

            }
        });
        binding.service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ServiceInfoActivity.class);
                //intent.putExtra("service_info", subItem.id);
                startActivity(intent);

            }
        });
        readCsv();

    }

    public void readCsv() {
        InputStream is = getResources().openRawResource(R.raw.subparts);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens; // Read the data and store it in the WellData POJO.
                tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Read the data and store it in the WellData POJO.
                subItem = new SubItem();
                subItem.partNumber = tokens[0].trim();
                subItem.partNumber = (tokens[1].trim());
                subItem.description = tokens[2].trim();
                subItem.mrp = Float.parseFloat(tokens[3].trim());
                subItemDao.insert(subItem);

                Log.d("details12345", "Just Created " + subItem);
            }
        } catch (IOException e1) {
            Log.e("MainActivity", "Error" + line, e1);
            e1.printStackTrace();
        }
        List<SubItem> list = subItemDao.getAll();
        for (int i = 0; i < list.size(); i++) {
            Log.d("abc123", list.get(i).description);
        }
    }



}
