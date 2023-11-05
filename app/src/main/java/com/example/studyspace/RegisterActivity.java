package com.example.studyspace;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studyspace.Database.DBUtil;
import com.example.studyspace.Database.User;
import com.example.studyspace.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    DBUtil databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DBUtil(this);

        binding.btnRegister.setOnClickListener(v -> {
            // Input username, password, email
            String emailText = binding.email.getText().toString();
            String passwordText = binding.password.getText().toString();
            String usernameText = binding.username.getText().toString();

            if (emailText.equals("") || passwordText.equals("") || usernameText.equals("")) {
                // Check if any of the fields are empty
                Toast.makeText(RegisterActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            } else {
                // Check if email already exists
                if (databaseHelper.checkEmailExist(emailText)){
                    Toast.makeText(RegisterActivity.this, "Email already exists, please login", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a new user and insert into database
                    User user = new User(usernameText, emailText, passwordText);
                    if(databaseHelper.addUser(user)){
                        Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        );

        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class); // this is the intent to go to the login page
            startActivities(new Intent[]{intent});
        });
    }
}