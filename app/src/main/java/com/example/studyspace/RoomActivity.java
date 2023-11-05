package com.example.studyspace;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyspace.Adapter.RoomsAdapter;
import com.example.studyspace.Database.DBUtil;

import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private static final String TAG = "RoomActivity";
    DBUtil databaseHelper;
    int userId;
    String timeOfDay, selectedBuilding;
    TextView building;
    List<String> availableRooms;
    ToolbarFragment toolbarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        initRoom();
    }

    private void initRoom() {
        databaseHelper = new DBUtil(this);
        // Get user ID, time of day, and selected building from intent
        userId = getIntent().getIntExtra("user_id", -1);
        timeOfDay = getIntent().getStringExtra("time_of_day");
        selectedBuilding = getIntent().getStringExtra("building");
        Log.d(TAG, "User ID: " + userId);
        Log.d(TAG, "Time of Day: " + timeOfDay);
        Log.d(TAG, "Building: " + selectedBuilding);

        // Set up toolbar fragment
        toolbarFragment = new ToolbarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.toolbar_fragment, toolbarFragment).commit();

        // Set up building title
        building = findViewById(R.id.title_building);
        building.setText(selectedBuilding);

        // Set up recycler view
        availableRooms = databaseHelper.getAvailableClassrooms(selectedBuilding, timeOfDay);
        Log.d(TAG, selectedBuilding + ": " + availableRooms);
        RecyclerView recyclerView = findViewById(R.id.recycler_room);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns
        recyclerView.setAdapter(new RoomsAdapter(availableRooms, this, selectedBuilding));
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