package com.example.applligent.nagoriengineering.view;

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
import com.example.applligent.nagoriengineering.databinding.ActivityServiceInfoBinding;
import com.example.applligent.nagoriengineering.model.Item;
import com.example.applligent.nagoriengineering.model.SubItem;

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
                intent.putExtra("tel_part_number", item.telPartNumber);
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
        binding.telPartLabel.setText(item.telPartNumber);
        binding.oemPartLabel.setText(item.oem);
        binding.engineLabel.setText(item.engine);
        binding.applicationLabel.setText(item.application);
        binding.mrpLabel.setText(Float.toString(item.mrp));
        binding.telPartLabel.setText(item.telPartNumber);
        binding.alphaLabel.setText(Integer.toString(item.orientationAlpha));
        binding.betaLabel.setText(Integer.toString(item.orientationBeta));
        binding.strokingLabel.setText(item.strPre);
        binding.settingPrLabel.setText(item.settingPre);
        binding.liftLabel.setText(item.lift);
        binding.remarksLabel.setText(item.reman);
    }

    public void showServiceInfo(){

        partsId = getIntent().getIntExtra("parts_info", -1);
        item=itemDao.get(partsId);

    }
}




