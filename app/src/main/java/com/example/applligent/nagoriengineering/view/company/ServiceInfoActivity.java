package com.example.applligent.nagoriengineering.view.company;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.ItemDao;
import com.example.applligent.nagoriengineering.databinding.ActivityServiceInfoBinding;
import com.example.applligent.nagoriengineering.model.Item;

public class ServiceInfoActivity extends AppCompatActivity {
    public int partsId;
    ItemDao itemDao;
    ActivityServiceInfoBinding binding;
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDao = MyNagoriApplication.Companion.getDatabase(getApplicationContext()).itemDao();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_info);
        partsId = getIntent().getIntExtra("parts_info", -1);
        item=itemDao.get(partsId);
        binding.tcPartLabel.setText(item.getTelPartNumber());
        binding.alphaLabel.setText(item.getOrientationAlpha());
        binding.betaLabel.setText(item.getOrientationBeta());
        binding.strokingPrLabel.setText(item.getStrPre());
        binding.settingPrLabel.setText(item.getSettingPre());
        binding.liftLabel.setText(item.getLift());
        binding.remarksLabel.setText(item.getReman());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PartsInfoActivity.class);

                intent.putExtra("tel_part_number", item.getTelPartNumber());
                startActivity(intent);

            }
        });

    }

}
