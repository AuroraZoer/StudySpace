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

        long morningTime = databaseHelper.getUserStudyTimesInPeriods(userId,"Morning");
        Log.d(TAG, "Morning Time: " + morningTime);
        long afternoonTime = databaseHelper.getUserStudyTimesInPeriods(userId,"Afternoon");
        Log.d(TAG, "Afternoon Time: " + afternoonTime);
        long eveningTime = databaseHelper.getUserStudyTimesInPeriods(userId,"Evening");
        Log.d(TAG, "Evening Time: " + eveningTime);

        List<DataEntry> data = new java.util.ArrayList<>();


        anyChartView.setChart(barChart);

    }
}