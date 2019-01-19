package com.example.applligent.nagoriengineering.view.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.applligent.nagoriengineering.GeneralPreference;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.UserDao;
import com.example.applligent.nagoriengineering.databinding.ActivityRegisterBinding;
import com.example.applligent.nagoriengineering.model.User;
import com.example.applligent.nagoriengineering.view.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private static final String TAG = "registerActivity";
    private ProgressDialog progressBar;
    UserDao registerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Register Yourself");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        registerDao = MyNagoriApplication.getDatabase().registerDao();
        progressBar = new ProgressDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                String userName = binding.etName.getText().toString();
                String userEmail = binding.etEmail.getText().toString();
                String userPassword = binding.etPassword.getText().toString();
                if (!TextUtils.isEmpty(userName) || !TextUtils.isEmpty(userEmail) || !TextUtils.isEmpty(userPassword)) {
                    progressBar.setTitle("Registration progress");
                    progressBar.setMessage("please wait");
                    progressBar.setCanceledOnTouchOutside(false);
                    progressBar.show();


                    registerUser(userName, userEmail, userPassword);
                }
            }
        });
    }

    private void registerUser(final String name, final String email, final String password) {
        Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.dismiss();
                            final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = currentUser.getUid();
                            String currentEmail = currentUser.getEmail();


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference().child("users").child(userId);

                            final User user = new User();
                            user.displayName = name;
                            user.email = email;
                            user.password = password;

                            GeneralPreference.setNameLoaded(getApplicationContext(), name);

                            GeneralPreference.setUserId(getApplicationContext(), userId);

                            GeneralPreference.setUserEmail(getApplicationContext(), currentEmail);

                            myRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.dismiss();
                                        registerDao.insert(user);
                                        startActivity(new Intent(RegisterActivity.this, ChatActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        finish();
                                    }
                                }
                            });


                        } else {
                            progressBar.hide();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
