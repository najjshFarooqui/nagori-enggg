package com.example.applligent.nagoriengineering.view.reminders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.applligent.nagoriengineering.databinding.ActivitySetReminderBinding;
import com.example.applligent.nagoriengineering.model.ReminderModel;
import com.example.applligent.nagoriengineering.view.chat.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SetReminderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    ActivitySetReminderBinding binding;
    ReminderDao reminderDao;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    ReminderAdapter reminderAdapter;
    String date = null;
    String time = null;
    ReminderModel reminderModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_reminder);
        mAuth = FirebaseAuth.getInstance();
        reminderDao = MyNagoriApplication.Companion.getDatabase(getApplicationContext()).reminderDao();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Set a Reminder");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(SetReminderActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.reminde_spiner));
        binding.reminderSpinner.setAdapter(spinnerAdapter);

        binding.setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });

        binding.setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepIcker = new TimePickerFragment();
                timepIcker.show(getSupportFragmentManager(), "time picker");
            }
        });

        binding.reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.input.getText().toString();
                String title = binding.reminderTitle.getText().toString();
                String name = binding.reminderSpinner.getSelectedItem().toString();

                if (binding.setDate.getText().toString().isEmpty() && binding.setTime.getText().toString().isEmpty()
                        && binding.input.getText().toString().isEmpty()
                        && binding.reminderSpinner.getSelectedItem().toString().isEmpty()
                        && binding.reminderTitle.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "please provide all info. carefully", Toast.LENGTH_SHORT).show();
                } else {
                    reminderModel = new ReminderModel();
                    if (name.equalsIgnoreCase("select name")) {
                        Toast.makeText(getApplicationContext(), "please select a name", Toast.LENGTH_SHORT).show();
                    }
                    String uuid = UUID.randomUUID().toString();
                    reminderModel.setUserID(uuid);
                    reminderModel.setMessage(message);
                    reminderModel.setUser(name);
                    reminderModel.setTitle(title);
                    Log.i("abc1234", reminderModel.getTitle());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    reminderModel.setSendingTime(sdf.format(new Date()));
                    reminderModel.setTime(time);
                    reminderModel.setDate(date);
                    reminderDao.insert(reminderModel);
                    reference = FirebaseDatabase.getInstance().getReference().child("reminders");
                    reference.push().setValue(reminderModel);
                    binding.input.setText("");
                    binding.input.setHint("Type a reminder");
                    binding.reminderTitle.setHint("Type a title");

                    startActivity(new Intent(SetReminderActivity.this, RemindersActivity.class));
                }
            }

        });

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        binding.setTime.setText(hourOfDay + " : " + minute);
        time = hourOfDay + " : " + minute;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        binding.setDate.setText(year + "/" + month + "/" + dayOfMonth);
        date = year + "/" + month + "/" + dayOfMonth;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                startActivity(new Intent(SetReminderActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

