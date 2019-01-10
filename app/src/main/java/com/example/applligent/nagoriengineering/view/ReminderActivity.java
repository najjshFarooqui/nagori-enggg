package com.example.applligent.nagoriengineering.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.databinding.ActivityReminderBinding;

public class ReminderActivity extends AppCompatActivity {
    ActivityReminderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_reminder);
        binding.setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ReminderActivity.this);
                View view =getLayoutInflater().inflate(R.layout.alert_builder,null);
                alertBuilder.setTitle("set reminder");
                Spinner spinner =(Spinner)view.findViewById(R.id.alert_spinner);
                ArrayAdapter<String> spinnerAdaqpter = new ArrayAdapter<>(ReminderActivity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinner_item));
                spinnerAdaqpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdaqpter);
            }
        });

    }
}
