package com.example.studyspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        int userId = getIntent().getIntExtra("user_id", -1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_search);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_search) return true;
            else if (item.getItemId() == R.id.bottom_analyse) {
                startActivity(new Intent(getApplicationContext(),AnalyseActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            else if (item.getItemId() == R.id.bottom_profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            return false;
        });
    }
}