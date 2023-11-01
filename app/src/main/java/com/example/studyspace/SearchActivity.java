package com.example.studyspace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studyspace.Database.DBUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    DBUtil databaseHelper;
    int userId;
    String timeOfDay;
    TextView availability, building_1, building_2, available_classroom_1, available_classroom_2;
    AutoCompleteTextView autoCompleteTextView;
    String[] buildings;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bottomNavigationViewInit();
        initSearch();

    }

    private void initSearch() {
        databaseHelper = new DBUtil(this);
        availability = findViewById(R.id.availability);
        getAvailability();
        userId = getIntent().getIntExtra("user_id", -1);
        Log.d(TAG, "User ID: " + userId);
        autoCompleteTextView = findViewById(R.id.search_building_text);
        buildings = databaseHelper.getAllBuildings();
        adapterItems = new ArrayAdapter<>(this, R.layout.list_item, buildings);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String building = adapterView.getItemAtPosition(i).toString();
            Log.d(TAG, "Building: " + building);
            Toast.makeText(SearchActivity.this, building, Toast.LENGTH_SHORT).show();
            
        });
        building_1 = findViewById(R.id.building_1);
        building_2 = findViewById(R.id.building_2);
        available_classroom_1 = findViewById(R.id.available_classroom_1);
        available_classroom_2 = findViewById(R.id.available_classroom_2);
        building_1.setText(buildings[0]);
        building_2.setText(buildings[1]);
        available_classroom_1.setText(String.valueOf(databaseHelper.getAvailableClassroomNumber(buildings[0], timeOfDay)));
        available_classroom_2.setText(String.valueOf(databaseHelper.getAvailableClassroomNumber(buildings[1], timeOfDay)));
        Log.d(TAG, buildings[0] + "\t" + databaseHelper.getAvailableClassroomNumber(buildings[0], timeOfDay));
        Log.d(TAG, buildings[1] + "\t" + databaseHelper.getAvailableClassroomNumber(buildings[1], timeOfDay));


    }

    private void bottomNavigationViewInit() {
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
        Log.d(TAG, "Current Date: " + currentDate);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        String currentHour = dateFormat.format(currentDate);
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