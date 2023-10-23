package com.example.studyspace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studyspace.database.DBUtil;
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

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       String emailText = binding.email.getText().toString();
                                                       String passwordText = binding.password.getText().toString();
                                                       String usernameText = binding.username.getText().toString();

                                                       if (emailText.equals("") || passwordText.equals("") || usernameText.equals("")) {
                                                           Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                                                       } else {
                                                           Boolean checkEmail = databaseHelper.checkEmail(emailText);
                                                           if (checkEmail == false) {
                                                               Boolean insert = databaseHelper.insertData(usernameText, passwordText, emailText);
                                                               if (insert == true) {
                                                                   Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                   Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                                   startActivity(intent);
                                                               } else {
                                                                   Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                                               }
                                                           } else {
                                                               Toast.makeText(RegisterActivity.this, "Email already exists, please login", Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   }
                                               }

        );

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class); // this is the intent to go to the login page
                startActivities(new Intent[]{intent});
            }
        });
    }
}