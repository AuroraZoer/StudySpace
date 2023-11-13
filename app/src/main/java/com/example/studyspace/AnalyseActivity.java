package com.example.studyspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.studyspace.Database.DBUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AnalyseActivity extends AppCompatActivity {
    private static final String TAG = "AnalyseActivity";
    DBUtil databaseHelper;
    int userId;
    TextView tvAnalyse1, tvAnalyse2, tvAnalyse3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse);
        bottomNavigationViewInit();
        initAnalyse();

    }

    private void initAnalyse() {
        databaseHelper = new DBUtil(this);
        userId = getIntent().getIntExtra("user_id", -1);
        Log.d(TAG, "User ID: " + userId);

        tvAnalyse1 = findViewById(R.id.show_visualisation1);
        tvAnalyse1.setOnClickListener(view -> openVisualisationActivity(1));
        tvAnalyse2 = findViewById(R.id.show_visualisation2);
        tvAnalyse2.setOnClickListener(view -> openVisualisationActivity(2));
        tvAnalyse3 = findViewById(R.id.show_visualisation3);
        tvAnalyse3.setOnClickListener(view -> openVisualisationActivity(3));

    }

    private void openVisualisationActivity(int visualisationType) {
        Intent intent = new Intent(getApplicationContext(), VisualisationActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("visualisation_type", visualisationType);
        startActivity(intent);
    }

    private void bottomNavigationViewInit() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_analyse);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_analyse) return true;
            else if (item.getItemId() == R.id.bottom_search) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
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
}