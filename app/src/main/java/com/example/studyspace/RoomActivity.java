package com.example.studyspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.studyspace.Database.DBUtil;

import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private static final String TAG = "RoomActivity";
    DBUtil databaseHelper;
    int userId;
    String timeOfDay, selectedBuilding;
    Toolbar toolbar;
    TextView building;
    List<String> availableRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        initRoom();

    }

    private void initRoom() {
        databaseHelper = new DBUtil(this);
        userId = getIntent().getIntExtra("user_id", -1);
        timeOfDay = getIntent().getStringExtra("time_of_day");
        selectedBuilding = getIntent().getStringExtra("building");
        Log.d(TAG, "User ID: " + userId);
        Log.d(TAG, "Time of Day: " + timeOfDay);
        Log.d(TAG, "Building: " + selectedBuilding);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        building = findViewById(R.id.title_building);
        building.setText(selectedBuilding);

        availableRooms = databaseHelper.getAvailableClassrooms(selectedBuilding, timeOfDay);
        Log.d(TAG, selectedBuilding + ": " + availableRooms);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}