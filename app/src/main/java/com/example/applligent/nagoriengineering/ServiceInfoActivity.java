package com.example.applligent.nagoriengineering;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.applligent.nagoriengineering.databinding.ActivityServiceInfoBinding;

public class ServiceInfoActivity extends AppCompatActivity {
    public int partsId;
    ItemDao itemDao;
    ActivityServiceInfoBinding binding;
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDao = MyNagoriApplication.getDatabase().itemDao();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_info);
        partsId = getIntent().getIntExtra("parts_info", -1);
        item=itemDao.get(partsId);
        binding.tcPartLabel.setText(item.telPartNumber);
        binding.alphaLabel.setText(item.orientationAlpha);
        binding.betaLabel.setText(item.orientationBeta);
        binding.strokingPrLabel.setText(item.strPre);
        binding.settingPrLabel.setText(item.settingPre);
        binding.liftLabel.setText(item.lift);
        binding.remarksLabel.setText(item.reman);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PartsInfoActivity.class);

                intent.putExtra("tel_part_number", item.telPartNumber);
                startActivity(intent);

            }
        });

    }

}
