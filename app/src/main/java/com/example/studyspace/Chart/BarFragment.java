package com.example.studyspace.Chart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian;
import com.example.studyspace.Database.DBUtil;
import com.example.studyspace.ProfileActivity;
import com.example.studyspace.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        barChart.animation(true);
        barChart.title("Weekly Study Time");
        barChart.yAxis(0).title("Study Time (minutes)");

        Map<String, Long> weeklyStudyTimes = databaseHelper.getStudyTimeByDayOfWeek(userId);
        List<DataEntry> seriesData = new ArrayList<>();
        for (Map.Entry<String, Long> entry : weeklyStudyTimes.entrySet()) {
            seriesData.add(new ValueDataEntry(entry.getKey(), entry.getValue()));
            Log.d(TAG, "Day of Week: " + entry.getKey() + " Study Time: " + entry.getValue());
        }

        barChart.animation(true);
        barChart.title("Weekly Study Time");
        barChart.yAxis(0).title("Study Time (HH:MM:SS)");
        barChart.yAxis(0).labels().format("function() {\n" +
                "   var date = new Date(this.value);\n" +
                "   var hours = date.getUTCHours();\n" +
                "   var minutes = date.getUTCMinutes();\n" +
                "   var seconds = date.getUTCSeconds();\n" +
                "   return ('0' + hours).slice(-2) + ':' + ('0' + minutes).slice(-2) + ':' + ('0' + seconds).slice(-2);\n" +
                "}");  // y-axis labels format HH:MM:SS
        barChart.xAxis(0).title("Day of Week");
        barChart.xAxis(0).labels().padding(5d, 5d, 5d, 5d);  // x-axis labels padding
        barChart.data(seriesData);
        barChart.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                String dayOfWeek = event.getData().get("x");
                long timeSpentMillis = Long.parseLong(Objects.requireNonNull(event.getData().get("value")));
                String formattedTime = ProfileActivity.formatTime(timeSpentMillis);
                String formattedMessage = String.format("On %s, you studied for %s", dayOfWeek, formattedTime);
                Toast.makeText(getActivity(), formattedMessage, Toast.LENGTH_SHORT).show();
            }
        });
        barChart.tooltip().enabled(false);

        anyChartView.setChart(barChart);
    }

}