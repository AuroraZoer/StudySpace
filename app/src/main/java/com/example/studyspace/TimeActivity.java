package com.example.studyspace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.studyspace.Database.DBUtil;

public class TimeActivity extends AppCompatActivity {
    private static final String TAG = "TimeActivity";
    DBUtil databaseHelper;
    ToolbarFragment toolbarFragment;
    String selectedBuilding, selectedRoom;
    int userId, roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        initTime();
    }

    private void initTime() {
        databaseHelper = new DBUtil(this);
        // Get user ID, selected building, and selected room from intent
        userId = getIntent().getIntExtra("user_id", -1);
        Log.d(TAG, "User ID: " + userId);
        selectedBuilding = getIntent().getStringExtra("building");
        selectedRoom = getIntent().getStringExtra("room");
        Log.d(TAG, selectedBuilding + ": " + selectedRoom);

        roomId = databaseHelper.getRoomID(selectedBuilding, selectedRoom);
        Log.d(TAG, "Room ID: " + roomId);

        // Set up toolbar fragment
        toolbarFragment = new ToolbarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.toolbar_fragment, toolbarFragment).commit();


    }
}