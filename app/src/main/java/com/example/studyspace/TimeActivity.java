package com.example.studyspace;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.studyspace.Database.DBUtil;

import java.util.Locale;

public class TimeActivity extends AppCompatActivity {
    private static final String TAG = "TimeActivity";
    DBUtil databaseHelper;
    ToolbarFragment toolbarFragment;
    String selectedBuilding, selectedRoom;
    int userId, roomId;
    TextView building, classroom, timeRecord;
    Chronometer chronometer;
    Typeface typeface;
    Button start, stop;
    boolean isRunning;
    long pauseOffset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        initTime();

        chronometer.setOnChronometerTickListener(chronometer -> {
            long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
            int hours = (int) (elapsedMillis / 3600000);
            int minutes = (int) (elapsedMillis - hours * 3600000) / 60000;
            int seconds = (int) (elapsedMillis / 1000) % 60;
            String text = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
            chronometer.setText(text);
        });


        start.setOnClickListener(view -> {
            if (!isRunning) {
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                isRunning = true;
                start.setEnabled(false);
                stop.setEnabled(true);
            }
        });

        stop.setOnClickListener(view -> {
            if (isRunning) {
                chronometer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                isRunning = false;
                start.setEnabled(true);
                stop.setEnabled(false);
                String time = formatChronometerTime(pauseOffset);
                timeRecord.setText("Time recorded  " + time);
                databaseHelper.addStudyTime(userId, roomId, time);
                boolean insertSuccessful = databaseHelper.addStudyTime(userId, roomId, time);
                if (insertSuccessful) {
                    Log.d(TAG, "Data insert successful " + time);
                } else {
                    Log.d(TAG, "Data insert failed");
                }
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                chronometer.setText("00:00:00");
            }
        });
        
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

        // Set up building and classroom text view
        building = findViewById(R.id.clock_title_building);
        building.setText(selectedBuilding);
        classroom = findViewById(R.id.clock_title_classroom);
        classroom.setText(selectedRoom);

        // Set up chronometer
        chronometer = findViewById(R.id.chronometer);
        typeface = ResourcesCompat.getFont(this, R.font.baloo_bhaina);
        chronometer.setTypeface(typeface);
        // Set the initial text to 00:00:00
        chronometer.setText("00:00:00");

        // Set up start and stop buttons
        start = findViewById(R.id.btn_start);
        stop = findViewById(R.id.btn_stop);
        timeRecord = findViewById(R.id.time_recorded);

    }

    private String formatChronometerTime(long time) {
        int hours = (int) (time / 3600000);
        int minutes = (int) (time - hours * 3600000) / 60000;
        int seconds = (int) (time - hours * 3600000 - minutes * 60000) / 1000;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }
}