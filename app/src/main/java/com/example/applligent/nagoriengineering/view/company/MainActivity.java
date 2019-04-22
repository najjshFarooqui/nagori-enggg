package com.example.applligent.nagoriengineering.view.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.applligent.nagoriengineering.GeneralPreference;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.ItemDao;
import com.example.applligent.nagoriengineering.dao.SubItemDao;
import com.example.applligent.nagoriengineering.model.Item;
import com.example.applligent.nagoriengineering.model.SubItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ItemDao itemDao;
    SubItem subItem;
    SubItemDao subItemDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemDao = MyNagoriApplication.Companion.getDatabase(getApplicationContext()).itemDao();
        subItemDao = MyNagoriApplication.Companion.getDatabase(getApplicationContext()).subItemDao();
        if (!GeneralPreference.getDataLoaded(this)) {
            readPartsCsv();
            readSubPartsCsv();
            GeneralPreference.setDataLoaded(this);
        }
        startActivity(new Intent(this, CompanyListActivity.class));
    }

    public void readPartsCsv() {
        itemDao.deleteAll();
        InputStream is = getResources().openRawResource(R.raw.parts);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens; // Read the data and store it in the WellData POJO.
                tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Read the data and store it in the WellData POJO.
                Item items = new Item();
                items.setSNo(Long.parseLong(tokens[0].trim()));
                items.setOem((tokens[1].trim()));
                items.setTelPartNumber(tokens[2].trim());
                items.setEngine((tokens[3].trim()));
                items.setApplication((tokens[4].trim()));
                items.setMrp(Float.parseFloat(tokens[5].trim()));
                items.setOrientationAlpha(Integer.parseInt(tokens[6].trim()));
                items.setOrientationBeta(Integer.parseInt(tokens[7].trim()));
                try {
                    items.setStrPre((tokens[8].trim()));
                    items.setSettingPre((tokens[9].trim()));
                    items.setLift((tokens[10].trim()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //TODO items.reman = (tokens[11]);
                itemDao.insert(items);

                Log.d("MainActivity", "Just Created " + items);
            }
        } catch (IOException e1) {
            Log.e("MainActivity", "Error" + line, e1);
            e1.printStackTrace();
        }
        List<Item> list = itemDao.getAll();
        for (int i = 0; i < list.size(); i++) {
            Log.d("abc123", list.get(i).getApplication());
        }
    }

    public void readSubPartsCsv() {
        subItemDao.deleteAll();
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
                subItem.telPartNumber = tokens[0].trim();
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
