package com.example.studyspace.Chart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.example.studyspace.Database.DBUtil;
import com.example.studyspace.ProfileActivity;
import com.example.studyspace.R;

import java.util.List;
import java.util.Objects;

public class PieFragment extends Fragment {
    private static final String TAG = "PieFragment";
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
        return inflater.inflate(R.layout.fragment_pie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseHelper = new DBUtil(getActivity());

        AnyChartView anyChartView = view.findViewById(R.id.pie_chart_view);

        Pie pieChart = AnyChart.pie();
        long building1Time = databaseHelper.getUserStudyTimesInBuilding(userId, "Building 1");
        Log.d(TAG, "Building 1 Time: " + building1Time);
        long building2Time = databaseHelper.getUserStudyTimesInBuilding(userId, "Building 2");
        Log.d(TAG, "Building 2 Time: " + building2Time);
        long building3Time = databaseHelper.getUserStudyTimesInBuilding(userId, "Building 3");
        Log.d(TAG, "Building 3 Time: " + building3Time);
        long building4Time = databaseHelper.getUserStudyTimesInBuilding(userId, "Building 4");
        Log.d(TAG, "Building 4 Time: " + building4Time);

        List<DataEntry> data = new java.util.ArrayList<>();
        data.add(new ValueDataEntry("Building 1", building1Time));
        data.add(new ValueDataEntry("Building 2", building2Time));
        data.add(new ValueDataEntry("Building 3", building3Time));
        data.add(new ValueDataEntry("Building 4", building4Time));

        pieChart.data(data);
        pieChart.title("Study Time in Each Building");
        pieChart.labels().position("outside");
        pieChart.legend().title()
                .text("Buildings")
                .padding(0d, 0d, 10d, 0d);
        pieChart.legend()
                .position("center-bottom")
                .itemsLayout("horizontal")
                .align("center");
        pieChart.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                String buildingName = event.getData().get("x");
                long timeSpentMillis = Long.parseLong(Objects.requireNonNull(event.getData().get("value")));

                String formattedTime = ProfileActivity.formatTime(timeSpentMillis);
                String formattedMessage = buildingName + ": " + formattedTime;

                Toast.makeText(getActivity(), formattedMessage, Toast.LENGTH_SHORT).show();
            }
        });
        pieChart.tooltip().enabled(false);

        anyChartView.setChart(pieChart);
    }
}