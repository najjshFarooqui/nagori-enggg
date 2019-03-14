package com.example.applligent.nagoriengineering.view.company;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.ItemDao;
import com.example.applligent.nagoriengineering.databinding.ActivityDetailsBinding;
import com.example.applligent.nagoriengineering.model.Item;

public class DetailsActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    long itemId;
    ItemDao itemDao;
    ActivityDetailsBinding binding;
    Item item;

    public int partsId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Detail");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        itemDao = MyNagoriApplication.getDatabase().itemDao();
        itemId = getIntent().getLongExtra("item_id", -1);
        showDetails();
        binding.parts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = itemDao.get(itemId);
                Intent intent = new Intent(getApplicationContext(), PartsInfoActivity.class);
                intent.putExtra("tel_part_number", item.getTelPartNumber());
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    public void showDetails() {
        item = itemDao.get(itemId);
        binding.telPartLabel.setText(item.getTelPartNumber());
        binding.oemPartLabel.setText(item.getOem());
        binding.engineLabel.setText(item.getEngine());
        binding.applicationLabel.setText(item.getApplication());
        binding.mrpLabel.setText(Float.toString(item.getMrp()));
        binding.telPartLabel.setText(item.getTelPartNumber());
        binding.alphaLabel.setText(Integer.toString(item.getOrientationAlpha()));
        binding.betaLabel.setText(Integer.toString(item.getOrientationBeta()));
        binding.strokingLabel.setText(item.getStrPre());
        binding.settingPrLabel.setText(item.getSettingPre());
        binding.liftLabel.setText(item.getLift());
        binding.remarksLabel.setText(item.getReman());
    }

    public void showServiceInfo(){

        partsId = getIntent().getIntExtra("parts_info", -1);
        item=itemDao.get(partsId);

    }
}




