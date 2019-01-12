package com.example.applligent.nagoriengineering.view;

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

import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.RegisterDao;
import com.example.applligent.nagoriengineering.databinding.ActivityRegisterBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;


import java.util.ArrayList;
import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private static final String TAG = "registerActivity";
    private ProgressDialog progressBar;
    RegisterDao registerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        ActionBar actionBar =getSupportActionBar();
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
                String etName = binding.etName.getText().toString();
                String etEmail = binding.etEmail.getText().toString();
                String etPassword = binding.etPassword.getText().toString();
                if (!TextUtils.isEmpty(etName) || !TextUtils.isEmpty(etEmail) || !TextUtils.isEmpty(etPassword)) {
                    progressBar.setTitle("Registration progress");
                    progressBar.setMessage("please wait");
                    progressBar.setCanceledOnTouchOutside(false);
                    progressBar.show();
                    registerUser(etName, etEmail, etPassword);
                    ArrayList registerModel = new ArrayList();
                    registerModel.add(etName);
                    registerModel.add(etEmail);
                    registerModel.add(etPassword);

                   registerDao.insertAll(registerModel);
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
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = currentUser.getUid();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference().child("users").child(userId);

                            HashMap<String, String> userDAta = new HashMap<>();
                            userDAta.put("email", email);
                            userDAta.put("name", name);
                            userDAta.put("password", password);
                            userDAta.put("token", FirebaseInstanceId.getInstance().getToken());
                            myRef.setValue(userDAta).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.dismiss();
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
