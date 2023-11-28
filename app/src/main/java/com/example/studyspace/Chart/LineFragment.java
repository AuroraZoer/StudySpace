package com.example.studyspace.Chart;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.example.studyspace.Database.DBUtil;
import com.example.studyspace.Database.DailyStudyTimes;
import com.example.studyspace.ProfileActivity;
import com.example.studyspace.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LineFragment extends Fragment {
    private static final String TAG = "LineFragment";
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
        return inflater.inflate(R.layout.fragment_line, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseHelper = new DBUtil(getActivity());

        AnyChartView anyChartView = view.findViewById(R.id.line_chart_view);

        Cartesian lineChart = AnyChart.line();
        lineChart.animation(true);
        lineChart.title("Daily Study Time");
        lineChart.yAxis(0).title("Study Time");
        lineChart.xAxis(0).labels().padding(5d, 5d, 5d, 5d);  // x-axis labels padding
        lineChart.yAxis(0).labels().format("function() {\n" +
                "   var date = new Date(this.value);\n" +
                "   var hours = date.getUTCHours();\n" +
                "   var minutes = date.getUTCMinutes();\n" +
                "   var seconds = date.getUTCSeconds();\n" +
                "   return ('0' + hours).slice(-2) + ':' + ('0' + minutes).slice(-2) + ':' + ('0' + seconds).slice(-2);\n" +
                "}");  // y-axis labels format HH:MM:SS

        Pair<String, String> dateRange = databaseHelper.getUserStudyDateRange(userId);
        DailyStudyTimes dailyStudyTimes = databaseHelper.getDailyStudyTimesForRange(userId, dateRange.first, dateRange.second);
        addLineSeries(lineChart, dailyStudyTimes.getTotalTimes(), "Total Time");
        addLineSeries(lineChart, dailyStudyTimes.getMorningTimes(), "Morning");
        addLineSeries(lineChart, dailyStudyTimes.getAfternoonTimes(), "Afternoon");
        addLineSeries(lineChart, dailyStudyTimes.getEveningTimes(), "Evening");

        lineChart.legend().enabled(true);  // show legend
        lineChart.legend().fontSize(13d);
        lineChart.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(lineChart);
    }

    private void addLineSeries(Cartesian lineChart, Map<String, Long> studyTimes, String seriesName) {
        List<DataEntry> seriesData = new ArrayList<>();
        for (Map.Entry<String, Long> entry : studyTimes.entrySet()) {
            seriesData.add(new CustomDataEntry(entry.getKey(), entry.getValue(), seriesName, ProfileActivity.formatTime(entry.getValue())));
            Log.d(TAG, "Date: " + entry.getKey() + " Time: " + entry.getValue());
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping seriesMapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series = lineChart.line(seriesMapping);
        series.name(seriesName);
        series.hovered().markers().enabled(true);  // show markers on hover
        series.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d)
                .format("function() {\n" +
                        "   return this.getData('seriesName') + ': ' + this.getData('formattedTime');\n" +
                        "}");  // tooltip format: seriesName: HH:MM:SSwjd
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, String seriesName, String formattedTime) {
            super(x, value);
            setValue("seriesName", seriesName);
            setValue("formattedTime", formattedTime);
        }
    }

}