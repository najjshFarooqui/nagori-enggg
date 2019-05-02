package com.example.applligent.nagoriengineering.view.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.view.SwipeGesture;
import com.example.applligent.nagoriengineering.view.company.MainActivity;
import com.example.applligent.nagoriengineering.view.reminders.ShowReminders;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button chats = findViewById(R.id.chatButton);
        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ChatActivity.class));
            }
        });
        Button companies = findViewById(R.id.companyButton);
        companies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        Button reminders = findViewById(R.id.reminderButton);
        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ShowReminders.class));
            }
        });

        Button images = findViewById(R.id.imagesfb);
        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SwipeGesture.class));
            }
        });
    }
}