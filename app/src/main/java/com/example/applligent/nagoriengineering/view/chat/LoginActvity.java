package com.example.applligent.nagoriengineering.view.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applligent.nagoriengineering.GeneralPreference;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.UserDao;
import com.example.applligent.nagoriengineering.model.User;
import com.example.applligent.nagoriengineering.view.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LoginActvity extends AppCompatActivity {

    UserDao userDao;
    String name;
    private TextView signUpLabel;
    private EditText userPassword;
    private Button login;


    private FirebaseAuth mAuth;
    private static final String TAG = "login success";
    private ProgressDialog progress;
    private EditText userName;
    private EditText userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actvity);
        progress = new ProgressDialog(this);
        userDao = MyNagoriApplication.Companion.getDatabase(getApplicationContext()).registerDao();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Login Page");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mAuth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_emaill);
        userPassword = findViewById(R.id.user_password);
        signUpLabel = findViewById(R.id.signup_label);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = LoginActvity.this.userEmail.getText().toString();
                String password = userPassword.getText().toString();
                name = userName.getText().toString();

                if (!TextUtils.isEmpty(userEmail) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(name)) {
                    progress.setTitle("login in progress");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                    loginUser(userEmail, password);
                }
            }
        });

        signUpLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActvity.this, RegisterActivity.class));

            }
        });


    }

    private void loginUser(final String userEmail, final String password) {
        mAuth.signInWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            progress.dismiss();
                            GeneralPreference.setUserEmail(getApplicationContext(), name);
                            temp();
                            Intent intent = new Intent(LoginActvity.this, ChatActivityNew.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message_layout to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            progress.dismiss();
                            Toast.makeText(LoginActvity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void temp() {
        FirebaseDatabase.getInstance().getReference().child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> users = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                    userDao.insertAll(users);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(LoginActvity.this, StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
