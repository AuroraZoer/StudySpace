package com.example.studyspace.Chart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian3d;
import com.anychart.core.cartesian.series.Column3d;
import com.example.studyspace.Database.DBUtil;
import com.example.studyspace.ProfileActivity;
import com.example.studyspace.R;

import java.util.ArrayList;
import java.util.List;

public class Bar3dFragment extends Fragment {

    private static final String TAG = "Bar3dFragment";
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
        return inflater.inflate(R.layout.fragment_bar3d, container, false);
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

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry("Morning", morningTime));
        seriesData.add(new CustomDataEntry("Afternoon", afternoonTime));
        seriesData.add(new CustomDataEntry("Evening", eveningTime));

        barChart.animation(true);
        barChart.title("Study Time by Period");
        barChart.yAxis(0).title("Study Time");
        barChart.yAxis(0).labels().format("function() {\n" +
                "   var date = new Date(this.value);\n" +
                "   var hours = date.getUTCHours();\n" +
                "   var minutes = date.getUTCMinutes();\n" +
                "   var seconds = date.getUTCSeconds();\n" +
                "   return ('0' + hours).slice(-2) + ':' + ('0' + minutes).slice(-2) + ':' + ('0' + seconds).slice(-2);\n" +
                "}");  // y-axis labels format HH:MM:SS
        barChart.xAxis(0).title("Period of Day");

        Column3d column = barChart.column(seriesData);
        column.tooltip().enabled(false);
        barChart.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                String period = event.getData().get("x");
                long time = Long.parseLong(event.getData().get("value"));
                String formattedTime = ProfileActivity.formatTime(time);
                String formattedMessage = period + ": " + formattedTime;
                Toast.makeText(getActivity(), formattedMessage, Toast.LENGTH_SHORT).show();
            }
        });

        anyChartView.setChart(barChart);
    }

    private static class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value) {
            super(x, value);
        }
    }
}