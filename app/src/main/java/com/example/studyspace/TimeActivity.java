package com.example.studyspace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TimeActivity extends AppCompatActivity {
    private static final String TAG = "TimeActivity";
    ToolbarFragment toolbarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        initTime();
    }

    private void initTime() {
        // Set up toolbar fragment
        toolbarFragment = new ToolbarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.toolbar_fragment, toolbarFragment).commit();


    }
}