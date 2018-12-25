package com.example.applligent.nagoriengineering;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.databinding.ActivityDetailsBinding;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    long itemId;
    ItemDao itemDao;
    SubItem subItem;
    ActivityDetailsBinding binding;
    Item item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        //subItemDao = MyNagoriApplication.getDatabase().subItemDao();
        itemDao = MyNagoriApplication.getDatabase().itemDao();
        itemId = getIntent().getLongExtra("item_id", -1);
        showDetails();
        binding.parts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = itemDao.get(itemId);
                Intent intent = new Intent(getApplicationContext(), PartsInfoActivity.class);
                intent.putExtra("tel_part_number", item.telPartNumber);
                startActivity(intent);

            }
        });
        binding.service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = itemDao.get(itemId);
                Intent intent = new Intent(getApplicationContext(), ServiceInfoActivity.class);
                //intent.putExtra("tel_part_number",item.telPartNumber );
                startActivity(intent);
            }
        });
    }

    public void showDetails() {
        item = itemDao.get(itemId);
        binding.telPartLabel.setText(item.telPartNumber);
        binding.oemPartLabel.setText(item.oem);
        binding.engineLabel.setText(item.engine);
        binding.applicationLabel.setText(item.application);
        binding.mrpLabel.setText(Float.toString( item.mrp));
    }
}




