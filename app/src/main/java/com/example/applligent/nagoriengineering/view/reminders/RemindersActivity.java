package com.example.applligent.nagoriengineering.view.reminders;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.ReminderAdapter;
import com.example.applligent.nagoriengineering.dao.ReminderDao;
import com.example.applligent.nagoriengineering.databinding.ActivityRemindersBinding;
import com.example.applligent.nagoriengineering.model.ReminderModel;

import java.util.List;

public class RemindersActivity extends AppCompatActivity {
    ActivityRemindersBinding binding;
    ReminderAdapter reminderAdapter;
    ReminderDao reminderDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reminders);
        reminderDao = MyNagoriApplication.getDatabase().reminderDao();
        reminderDao.getAll().observe(this, new Observer<List<ReminderModel>>() {
            @Override
            public void onChanged(@Nullable List<ReminderModel> reminderModelList) {
                binding.remindersView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                reminderAdapter = new ReminderAdapter(reminderModelList);
                binding.remindersView.setAdapter(reminderAdapter);
                binding.remindersView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(RemindersActivity.this, GetReminderActivity.class));
                    }
                });
            }
        });


    }
}
