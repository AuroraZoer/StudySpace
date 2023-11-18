package com.example.studyspace.Chart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.Cartesian;
import com.example.studyspace.Database.DBUtil;
import com.example.studyspace.R;

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

        anyChartView.setChart(lineChart);
    }
}