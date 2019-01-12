package com.example.applligent.nagoriengineering.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.applligent.nagoriengineering.DatePickerFragment;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.TimePickerFragment;
import com.example.applligent.nagoriengineering.dao.ReminderDao;
import com.example.applligent.nagoriengineering.databinding.ActivityReminderBinding;
import com.example.applligent.nagoriengineering.model.ReminderModel;

public class ReminderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener ,DatePickerDialog.OnDateSetListener {
    ActivityReminderBinding binding;
    ReminderDao reminderDao;
    ReminderModel reminderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=DataBindingUtil.setContentView(this,R.layout.activity_reminder);
        MyNagoriApplication.getDatabase().reminderDao();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(ReminderActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.reminde_spiner));
        binding.reminderSpinner.setAdapter(spinnerAdapter);

        binding.setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        binding.setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        binding.reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message= binding.message.getText().toString();
               String personName= (String) binding.reminderSpinner.getSelectedItem();
               ReminderModel reminderModel = new ReminderModel();
               if(personName.equalsIgnoreCase("select name")){
                   Toast.makeText(getApplicationContext(),"please select a name",Toast.LENGTH_SHORT).show();
               }else if(personName.equalsIgnoreCase("aakib")) {

                }
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        binding.setTime.setText(hourOfDay+ " : " +minute);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        binding.setDate.setText(year+"/"+month+"/"+dayOfMonth);
    }
    }

