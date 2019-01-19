package com.example.applligent.nagoriengineering.view.reminders;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.databinding.ActivityShowRemindersBinding;

public class ShowReminders extends AppCompatActivity {
    ActivityShowRemindersBinding showReminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Reminders");
        showReminders = DataBindingUtil.setContentView(this, R.layout.activity_show_reminders);
        showReminders.setReminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowReminders.this, SetReminderActivity.class));
            }
        });
        showReminders.showReminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowReminders.this, RemindersActivity.class));
            }
        });

    }
}
