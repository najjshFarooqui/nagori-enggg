package com.example.applligent.nagoriengineering.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.applligent.nagoriengineering.DatePickerFragment;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.ReminderAdapter;
import com.example.applligent.nagoriengineering.TimePickerFragment;
import com.example.applligent.nagoriengineering.dao.ReminderDao;
import com.example.applligent.nagoriengineering.databinding.ActivityReminderBinding;
import com.example.applligent.nagoriengineering.model.ReminderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ReminderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener ,DatePickerDialog.OnDateSetListener {
    ActivityReminderBinding binding;
    ReminderDao reminderDao;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    ReminderAdapter reminderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=DataBindingUtil.setContentView(this,R.layout.activity_reminder);
        mAuth = FirebaseAuth.getInstance();

        reminderDao = MyNagoriApplication.getDatabase().reminderDao();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Reminder");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        reminderDao.getAll().observe(this, new Observer<List<ReminderModel>>() {
            @Override
            public void onChanged(@Nullable List<ReminderModel> reminders) {
                RecyclerView messageView = (RecyclerView) findViewById(R.id.reminderView);
                messageView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                reminderAdapter = new ReminderAdapter(reminders);
                messageView.setAdapter(reminderAdapter);
            }
        });

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
                String message = binding.input.getText().toString();
                String date = binding.setDate.getText().toString();
                String time = binding.setTime.getText().toString();
                String name = binding.reminderSpinner.getSelectedItem().toString();

               String personName= (String) binding.reminderSpinner.getSelectedItem();
               ReminderModel reminderModel = new ReminderModel();
               if(personName.equalsIgnoreCase("select name")){
                   Toast.makeText(getApplicationContext(),"please select a name",Toast.LENGTH_SHORT).show();
                   reminderModel.id = UUID.randomUUID().toString();
                   reminderModel.message = message;
                   reminderModel.user = name;
                   reminderModel.date = date;
                   reminderModel.time = time;
                   SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
                   reminderModel.sendingTime = sdf.format(new Date());
                   reminderDao.insert(reminderModel);
                   reference = FirebaseDatabase.getInstance().getReference().child("reminders");
                   reference.push().setValue(reminderModel);
                   binding.input.setText("");
                   binding.input.setHint("Type a reminder");

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                startActivity(new Intent(ReminderActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    }

