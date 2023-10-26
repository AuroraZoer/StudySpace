package com.example.studyspace;

import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.studyspace.database.DBUtil;
import com.example.studyspace.database.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    int userId;
    TextView username, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userId = getIntent().getIntExtra("user_id", -1);
        Log.d("ProfileActivity", "User ID: " + userId);
        
//        User user = new DBUtil(this).getUserById(userId);
        username = findViewById(R.id.profile_username);
        email = findViewById(R.id.profile_email);
        username.setText(user.getUsername());
        email.setText(user.getEmail());


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_profile) return true;
            else if (item.getItemId() == R.id.bottom_search) {
                startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            else if (item.getItemId() == R.id.bottom_analyse) {
                startActivity(new Intent(getApplicationContext(), AnalyseActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            return false;
        });
    }
}