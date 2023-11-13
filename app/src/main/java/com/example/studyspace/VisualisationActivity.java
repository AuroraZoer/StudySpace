package com.example.studyspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.studyspace.Chart.BarFragment;
import com.example.studyspace.Chart.LineFragment;
import com.example.studyspace.Chart.PieFragment;
import com.example.studyspace.Database.DBUtil;

public class VisualisationActivity extends AppCompatActivity {
    private static final String TAG = "VisualisationActivity";
    ToolbarFragment toolbarFragment;
    int userId;
    TextView chartTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualisation);
        initVisualisation();

    }

    private void initVisualisation() {
        chartTitle = findViewById(R.id.title_chart);
        int visualisationType = getIntent().getIntExtra("visualisation_type", 0);
        if (visualisationType == 1) {
            loadFragment(new BarFragment());
            chartTitle.setText("Bar Chart");
        } else if (visualisationType == 2) {
            loadFragment(new PieFragment());
            chartTitle.setText("Pie Chart");
        } else if (visualisationType == 3) {
            loadFragment(new LineFragment());
            chartTitle.setText("Line Chart");
        }
        userId = getIntent().getIntExtra("user_id", -1);
        Log.d(TAG, "user id: " + userId);

        // Set up toolbar fragment
        toolbarFragment = new ToolbarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.toolbar_fragment, toolbarFragment).commit();


     }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}