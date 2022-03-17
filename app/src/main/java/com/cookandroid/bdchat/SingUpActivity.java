package com.cookandroid.bdchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cookandroid.bdchat.Models.Users;
import com.cookandroid.bdchat.databinding.ActivitySingUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SingUpActivity extends AppCompatActivity {

    ActivitySingUpBinding binding;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(SingUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account");

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.txtUsername.getText().toString().isEmpty() && !binding.txtEmail.getText().toString().isEmpty() && !binding.txtPassword.getText().toString().isEmpty())
                {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful())
                            {
                                Users user = new Users(binding.txtUsername.getText().toString(),binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString());
                                String id = task.getResult().getUser().getUid();
                                database.getReference().child("Users").child(id).setValue(user);

                                Toast.makeText(SingUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(SingUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(SingUpActivity.this,"Enter Credentials",Toast.LENGTH_SHORT).show();
                }

            }
        });
        // 이미 계정이 있습니다 페이지 전환
        binding.txtAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingUpActivity.this,SignInActivity.class);
                startActivity(intent);

            }
        });

    }
}