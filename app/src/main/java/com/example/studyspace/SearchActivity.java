package com.example.studyspace;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.studyspace.database.DBUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    DBUtil databaseHelper;
    int userId;
    TextView availability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bottomNavigationViewInit();
        initSearch();

//        List<String> allBuildings = databaseHelper.getBuilding();

    }

    public void initSearch() {
        databaseHelper = new DBUtil(this);
        availability = findViewById(R.id.availability);
        getAvailability();
        userId = getIntent().getIntExtra("user_id", -1);
        Log.d(TAG, "User ID: " + userId);

    }

    public void bottomNavigationViewInit(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_search);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_search) return true;
            else if (item.getItemId() == R.id.bottom_analyse) {
                Intent intent = new Intent(getApplicationContext(), AnalyseActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else if (item.getItemId() == R.id.bottom_profile) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            return false;
        });
    }

    @SuppressLint("SetTextI18n")
    public String getAvailability() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        String currentHour = dateFormat.format(currentDate);
        String timeOfDay;
        if (currentHour.compareTo("12") < 0) {
            timeOfDay = "MorningAvailability";
            availability.setText("Good Morning!");
        } else if (currentHour.compareTo("18") < 0) {
            timeOfDay = "AfternoonAvailability";
            availability.setText("Good Afternoon!");
        } else {
            timeOfDay = "EveningAvailability";
            availability.setText("Good Evening!");
        }
        return timeOfDay;
    }
}