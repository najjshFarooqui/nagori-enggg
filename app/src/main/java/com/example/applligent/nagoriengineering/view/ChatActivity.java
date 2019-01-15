package com.example.applligent.nagoriengineering.view;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.applligent.nagoriengineering.GeneralPreference;
import com.example.applligent.nagoriengineering.MessageAdapter;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.ChatDao;
import com.example.applligent.nagoriengineering.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference reference;
    ChatDao chatDao;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatDao = MyNagoriApplication.getDatabase().chatDao();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("CHATS");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mAuth = FirebaseAuth.getInstance();
        //if internet is on

        chatDao.getAll().observe(this, new Observer<List<Chat>>() {
            @Override
            public void onChanged(@Nullable List<Chat> messages) {
                RecyclerView messageView = (RecyclerView) findViewById(R.id.list_of_messages);
                messageView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                messageAdapter = new MessageAdapter(messages);
                messageView.setAdapter(messageAdapter);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText message = (EditText) findViewById(R.id.input);
                Chat chatMessages = new Chat();
                chatMessages.message = message.getText().toString();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


                chatMessages.displayName = GeneralPreference.getNameLoaded(getApplicationContext());
                chatMessages.userId = GeneralPreference.getUserId(getApplicationContext());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat timeHourMin = new SimpleDateFormat("HH:mm");
                chatMessages.timeStamp = sdf.format(new Date());
                chatMessages.hourMinute = timeHourMin.format(new Date());
                List<Chat> chatMessagesList = new ArrayList<>();
                chatMessagesList.add(chatMessages);
                if (GeneralPreference.getUserId(getApplicationContext()) != chatMessages.userId) {
                    chatDao.insertAll(chatMessagesList);
                }

                reference = FirebaseDatabase.getInstance().getReference().child("messages");
                reference.push().setValue(chatMessages);
                message.setText("");
                message.setHint("Type a message");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                redirectUser();
                return true;
            case R.id.home:
                startActivity(new Intent(ChatActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void redirectUser() {
        startActivity(new Intent(ChatActivity.this, StartActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(ChatActivity.this, StartActivity.class));
            finish();
        }

    }


}

