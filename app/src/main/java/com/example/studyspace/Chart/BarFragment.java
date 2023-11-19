package com.example.studyspace.Chart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian3d;
import com.example.studyspace.Database.DBUtil;
import com.example.studyspace.ProfileActivity;
import com.example.studyspace.R;

import java.util.List;

public class BarFragment extends Fragment {
    private static final String TAG = "BarFragment";
    DBUtil databaseHelper;
    private int userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt("userId", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseHelper = new DBUtil(getActivity());

        AnyChartView anyChartView = view.findViewById(R.id.bar_chart_view);
        Cartesian3d barChart = AnyChart.column3d();

        long morningTime = getTotalStudyTimeInPeriod("Morning");
        long afternoonTime = getTotalStudyTimeInPeriod("Afternoon");
        long eveningTime = getTotalStudyTimeInPeriod("Evening");

        List<DataEntry> data = new java.util.ArrayList<>();


        anyChartView.setChart(barChart);

    }

    private long getTotalStudyTimeInPeriod(String period) {
        List<String> studyTimes = databaseHelper.getUserStudyTimesInPeriods(userId, period);
        long totalTime = 0;
        for (String time : studyTimes) {
            totalTime += ProfileActivity.convertTimeToMillis(time);
        }
        Log.d(TAG, period + " time: " + totalTime);
        return totalTime;
    }
}