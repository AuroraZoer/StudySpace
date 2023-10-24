package com.example.studyspace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studyspace.database.DBUtil;
import com.example.studyspace.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    DBUtil databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DBUtil(this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Input password, email
                String emailText = binding.email.getText().toString();
                String passwordText = binding.password.getText().toString();

                if (emailText.equals("") || passwordText.equals("")) {
                    // Check if the user has entered all the fields
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the email and password match
                    if (databaseHelper.checkEmailPassword(emailText, passwordText)) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect email or password. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class); // this is the intent to go to the register page
                startActivities(new Intent[]{intent});
            }
        });

    }
}