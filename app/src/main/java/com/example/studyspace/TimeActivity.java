package com.example.studyspace;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.studyspace.Database.DBUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeActivity extends AppCompatActivity {
    private static final String TAG = "TimeActivity";
    DBUtil databaseHelper;
    ToolbarFragment toolbarFragment;
    String selectedBuilding, selectedRoom, timeOfDay, date;
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
        configureChronometer();
        setTimeOfDay();

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
                String time = formatTime(pauseOffset);
                timeRecord.setText("Time recorded  " + time);
                boolean insertSuccessful = databaseHelper.addStudyTime(userId, roomId, time, timeOfDay, date);
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

    @Override
    public void onBackPressed() {
        if (isRunning) {
            Toast.makeText(this, "Please stop the timer before leaving", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    private void setTimeOfDay() {
        Date currentDate = new Date();
        Log.d(TAG, "Current Date: " + currentDate);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        String currentHour = hourFormat.format(currentDate);
        if (currentHour.compareTo("12") < 0) {
            timeOfDay = "Morning";
        } else if (currentHour.compareTo("18") < 0) {
            timeOfDay = "Afternoon";
        } else {
            timeOfDay = "Evening";
        }
        Log.d(TAG, "Time of Day: " + timeOfDay);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(currentDate);
        Log.d(TAG, "Date: " + date);
    }

    private void configureChronometer() {
        chronometer.setOnChronometerTickListener(chronometer -> {
            long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
            chronometer.setText(formatTime(elapsedMillis));
        });
    }

    private String formatTime(long millis) {
        int hours = (int) (millis / 3600000);
        int minutes = (int) (millis - hours * 3600000) / 60000;
        int seconds = (int) (millis - hours * 3600000 - minutes * 60000) / 1000;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }
}