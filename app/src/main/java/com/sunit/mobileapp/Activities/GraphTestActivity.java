package com.sunit.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sunit.mobileapp.MainActivity;
import com.sunit.mobileapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphTestActivity extends AppCompatActivity {

    private LineChart lineChart;

    private List<String> xValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_test);

        Spinner spinnerAttribute = (Spinner) findViewById(R.id.attributes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.attibutes,  // Create a string array resource in the 'res/values/arrays.xml' file
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerAttribute.setAdapter(adapter);

        Spinner spinnerTimeFrame = (Spinner) findViewById(R.id.timeFrame);
        ArrayAdapter<CharSequence> adapterTimeFrame = ArrayAdapter.createFromResource(
                this,
                R.array.timeframe,  // Create a string array resource in the 'res/values/arrays.xml' file
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapterTimeFrame.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerTimeFrame.setAdapter(adapterTimeFrame);

        lineChart = (LineChart) findViewById(R.id.lineChart);
        xValues = Arrays.asList("19:00","20:00","21:00","22:00","23:00","00:00","01:00","02:00","03:00","04:00","05:00");
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelCount(25);
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(35f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.GREEN);
        yAxis.setLabelCount(7);

        List<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(0,29.86f));
        entries1.add(new Entry(1,29.86f));
        entries1.add(new Entry(2,28.86f));
        entries1.add(new Entry(3,27.86f));
        entries1.add(new Entry(4,27.86f));
        entries1.add(new Entry(5,26.86f));
        entries1.add(new Entry(6,25.86f));
        entries1.add(new Entry(7,26.86f));
        entries1.add(new Entry(8,25.86f));
        entries1.add(new Entry(9,24.86f));
        entries1.add(new Entry(10,24.86f));



        LineDataSet dataSet1 = new LineDataSet(entries1,"Temperature");
        dataSet1.setColor(Color.BLUE);

        LineData lineData = new LineData(dataSet1);
        lineChart.setData(lineData);

        lineChart.invalidate();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.graph_menu);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_menu) {
                startActivity(new Intent(getApplicationContext(), MainHomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.graph_menu) {
                return true;
            } else if (itemId == R.id.location_menu) {
                startActivity(new Intent(getApplicationContext(), GoogleMap.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.setting_menu) {
                startActivity(new Intent(getApplicationContext(), SettingTestActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }
}