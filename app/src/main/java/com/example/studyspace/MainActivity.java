package com.example.studyspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.studyspace.database.DBUtil;
import com.example.studyspace.database.StudyRoom;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button loginButton, registerButton;
    DBUtil databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initApp();

        SharedPreferences sharedPreferences = getSharedPreferences("studyRoomData", MODE_PRIVATE);
        boolean hasInserted = sharedPreferences.getBoolean("hasInserted", false);
        if (!hasInserted) {
            List<StudyRoom> studyRooms = new ArrayList<>();
            studyRooms.add(new StudyRoom(1, "201", "Building 1", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "202", "Building 1", 0, 1, 0));
            studyRooms.add(new StudyRoom(1, "203", "Building 1", 0, 0, 1));
            studyRooms.add(new StudyRoom(1, "204", "Building 1", 1, 1, 0));
            studyRooms.add(new StudyRoom(1, "205", "Building 1", 1, 0, 1));
            studyRooms.add(new StudyRoom(1, "206", "Building 1", 0, 1, 1));
            studyRooms.add(new StudyRoom(1, "207", "Building 1", 1, 1, 1));
            studyRooms.add(new StudyRoom(1, "301", "Building 1", 1, 1, 1));
            studyRooms.add(new StudyRoom(1, "302", "Building 1", 0, 1, 1));
            studyRooms.add(new StudyRoom(1, "303", "Building 1", 1, 0, 1));
            studyRooms.add(new StudyRoom(1, "304", "Building 1", 1, 1, 0));
            studyRooms.add(new StudyRoom(1, "305", "Building 1", 0, 0, 1));
            studyRooms.add(new StudyRoom(1, "306", "Building 1", 0, 1, 0));
            studyRooms.add(new StudyRoom(1, "307", "Building 1", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "401", "Building 1", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "402", "Building 1", 0, 1, 0));
            studyRooms.add(new StudyRoom(1, "403", "Building 1", 0, 0, 1));
            studyRooms.add(new StudyRoom(1, "501", "Building 1", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "502", "Building 1", 0, 1, 0));
            studyRooms.add(new StudyRoom(1, "503", "Building 1", 0, 0, 1));
            studyRooms.add(new StudyRoom(1, "504", "Building 1", 1, 1, 0));
            studyRooms.add(new StudyRoom(1, "505", "Building 1", 1, 0, 1));
            studyRooms.add(new StudyRoom(1, "506", "Building 1", 0, 1, 1));
            studyRooms.add(new StudyRoom(1, "507", "Building 1", 1, 1, 1));

            studyRooms.add(new StudyRoom(1, "409", "Building 2", 1, 1, 1));
            studyRooms.add(new StudyRoom(1, "415", "Building 2", 1, 1, 1));
            studyRooms.add(new StudyRoom(1, "416", "Building 2", 1, 1, 1));
            studyRooms.add(new StudyRoom(1, "515", "Building 2", 1, 1, 1));
            studyRooms.add(new StudyRoom(1, "407", "Building 2", 0, 1, 1));
            studyRooms.add(new StudyRoom(1, "412", "Building 2", 0, 0, 1));
            studyRooms.add(new StudyRoom(1, "413", "Building 2", 0, 1, 0));
            studyRooms.add(new StudyRoom(1, "414", "Building 2", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "402", "Building 2", 1, 1, 0));
            studyRooms.add(new StudyRoom(1, "405", "Building 2", 0, 1, 1));

            studyRooms.add(new StudyRoom(1, "527", "Building 3", 1, 1, 1));
            studyRooms.add(new StudyRoom(1, "111", "Building 3", 0, 1, 1));
            studyRooms.add(new StudyRoom(1, "203", "Building 3", 0, 1, 1));
            studyRooms.add(new StudyRoom(1, "317", "Building 3", 0, 1, 1));
            studyRooms.add(new StudyRoom(1, "530", "Building 3", 0, 1, 1));
            studyRooms.add(new StudyRoom(1, "210", "Building 3", 0, 0, 1));
            studyRooms.add(new StudyRoom(1, "324", "Building 3", 0, 1, 0));
            studyRooms.add(new StudyRoom(1, "534", "Building 3", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "211", "Building 3", 1, 1, 0));
            studyRooms.add(new StudyRoom(1, "325", "Building 3", 0, 1, 1));

            studyRooms.add(new StudyRoom(1, "514", "Building 4", 1, 1, 1));
            studyRooms.add(new StudyRoom(1, "504", "Building 4", 1, 1, 1));
            studyRooms.add(new StudyRoom(1, "310", "Building 4", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "508", "Building 4", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "216", "Building 4", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "218", "Building 4", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "420", "Building 4", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "516", "Building 4", 1, 0, 0));
            studyRooms.add(new StudyRoom(1, "416", "Building 4", 0, 1, 0));
            studyRooms.add(new StudyRoom(1, "418", "Building 4", 0, 1, 0));
            studyRooms.add(new StudyRoom(1, "308", "Building 4", 0, 0, 1));
            studyRooms.add(new StudyRoom(1, "410", "Building 4", 0, 0, 1));
            studyRooms.add(new StudyRoom(1, "614", "Building 4", 0, 0, 1));
            studyRooms.add(new StudyRoom(1, "512", "Building 4", 0, 0, 1));

            int successfulInserts = databaseHelper.insertStudyRooms(studyRooms);

            if (successfulInserts > 0) {
                Log.d("InsertStudyRooms", "Successfully inserted " + successfulInserts + " StudyRoom records.");
                sharedPreferences.edit().putBoolean("hasInserted", true).apply();
            } else {
                Log.d("InsertStudyRooms", "Failed to insert StudyRoom records.");
            }
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class); // this is the intent to go to the login page
                startActivities(new Intent[]{intent});
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class); // this is the intent to go to the register page
                startActivities(new Intent[]{intent});
            }
        });

    }

    private void initApp() {
        databaseHelper = new DBUtil(this);
        loginButton = (Button) findViewById(R.id.btn_login);
        registerButton = (Button) findViewById(R.id.btn_register);
    }
}