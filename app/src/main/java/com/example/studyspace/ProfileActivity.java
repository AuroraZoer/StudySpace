package com.example.studyspace;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.studyspace.Database.DBUtil;
import com.example.studyspace.Database.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    DBUtil databaseHelper;
    int userId;
    TextView username, email, count, duration;
    ImageButton infoButton, logoutButton;
    Dialog dialog;
    GridLayout gridLayout;
    long totalMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationViewInit();
        initProfile();

        User user = databaseHelper.getUserById(userId);
        if (user != null) {
            Log.d(TAG, "User: " + user);
            username.setText(user.getUsername());
            email.setText(user.getEmail());
        }

    }

    private void initProfile() {
        databaseHelper = new DBUtil(this);
        username = findViewById(R.id.profile_username);
        email = findViewById(R.id.profile_email);
        userId = getIntent().getIntExtra("user_id", -1);
        Log.d(TAG, "User ID: " + userId);
        infoButton = findViewById(R.id.info_button);
        dialog = new Dialog(this);
        infoButton.setOnClickListener(view -> {
            Log.d(TAG, "Info button clicked");
            showInfoDialog();
        });
        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(view -> {
            Log.d(TAG, "Logout button clicked");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        count = findViewById(R.id.profile_data_count);
        duration = findViewById(R.id.profile_data_duration);
        count.setText(String.valueOf(databaseHelper.gerUserStudyCount(userId)));
        List<String> studyTimes = databaseHelper.getUserStudyTimes(userId);
        totalMillis = 0;
        for (String time : studyTimes) {
            totalMillis += convertTimeToMillis(time);
        }
        Log.d(TAG, "Total milliseconds: " + totalMillis);
        duration.setText(formatTime(totalMillis));

        gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < 6; i++) {
            setupMedalView(gridLayout, i);
        }
    }

    private void setupMedalView(GridLayout gridLayout, int position) {
        int imageViewId = getResources().getIdentifier("medal_image" + (position + 1), "id", getPackageName());
        ImageView imageView = gridLayout.findViewById(imageViewId);

        int lottieViewId = getResources().getIdentifier("medal_animation" + (position + 1), "id", getPackageName());
        LottieAnimationView lottieView = gridLayout.findViewById(lottieViewId);

        Log.d(TAG, position + " " + shouldShowAnimation(position) + " ");
        if (shouldShowAnimation(position)) {
            if (imageView != null) imageView.setVisibility(View.GONE);
            if (lottieView != null) {
                lottieView.setVisibility(View.VISIBLE);
                lottieView.playAnimation();
            }
        } else {
            if (lottieView != null) lottieView.setVisibility(View.GONE);
            if (imageView != null) imageView.setVisibility(View.VISIBLE);
        }
    }

    private boolean shouldShowAnimation(int position) {
        // 将总毫秒数转换为分钟
        long totalMinutes = totalMillis / 60000;

        // 检查当前位置的勋章是否应该显示动画
        // 每30分钟显示一个勋章的动画
        // position + 1 是因为 position 是从0开始的，而我们需要从1开始计数
        return totalMinutes >= (position + 1) * 30;
    }

    private void showInfoDialog() {
        dialog.setContentView(R.layout.info_popup);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    /**
     * Convert "HH:mm:ss" format time string to milliseconds
     *
     * @param time "HH:mm:ss" format time string
     * @return milliseconds
     */
    private long convertTimeToMillis(String time) {
        long millis = 0;
        String[] parts = time.split(":");
        if (parts.length == 3) {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            int seconds = Integer.parseInt(parts[2]);
            millis = (hours * 3600 + minutes * 60 + seconds) * 1000L;
        }
        return millis;
    }

    /**
     * Format milliseconds to "HH:mm:ss" format time string
     *
     * @param millis milliseconds
     * @return "HH:mm:ss" format time string
     */
    private String formatTime(long millis) {
        int hours = (int) (millis / 3600000);
        int minutes = (int) (millis - hours * 3600000) / 60000;
        int seconds = (int) (millis - hours * 3600000 - minutes * 60000) / 1000;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void bottomNavigationViewInit() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_profile) return true;
            else if (item.getItemId() == R.id.bottom_search) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else if (item.getItemId() == R.id.bottom_analyse) {
                Intent intent = new Intent(getApplicationContext(), AnalyseActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            return false;
        });
    }
}