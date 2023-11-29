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
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.example.studyspace.Database.DBUtil;
import com.example.studyspace.ProfileActivity;
import com.example.studyspace.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Cartesian barChart = AnyChart.column();

        Map<String, Long> weeklyStudyTimes = databaseHelper.getStudyTimeByDayOfWeek(userId);
        List<DataEntry> data = new ArrayList<>();

        for (Map.Entry<String, Long> entry : weeklyStudyTimes.entrySet()) {
            Log.d(TAG, "Day of Week: " + entry.getKey() + " Study Time: " + entry.getValue());
            data.add(new ValueDataEntry(convertDayOfWeek(entry.getKey()), entry.getValue()));
        }

        barChart.data(data);
        anyChartView.setChart(barChart);
    }

    private String convertDayOfWeek(String dayOfWeek) {
        switch (dayOfWeek) {
            case "1":
                return "Sunday";
            case "2":
                return "Monday";
            case "3":
                return "Tuesday";
            case "4":
                return "Wednesday";
            case "5":
                return "Thursday";
            case "6":
                return "Friday";
            case "0":
                return "Saturday";
            default:
                return "";
        }
    }
}