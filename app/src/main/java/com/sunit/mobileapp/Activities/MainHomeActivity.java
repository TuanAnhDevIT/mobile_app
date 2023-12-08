package com.sunit.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.sunit.mobileapp.R;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainHomeActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;

    private TextView textViewTime;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);
        textViewTime = findViewById(R.id.textViewTime);
        handler.postDelayed(runnable, 1000);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        ArrayList<String> attributeNames = intent.getStringArrayListExtra("attributeNames");
        ArrayList<String> attributeValues = intent.getStringArrayListExtra("attributeValues");

        if (attributeValues != null && attributeValues.size() > 1) {

            String rainfallValue = attributeValues.get(1);
            String humidityVallue  = attributeValues.get(9);
            String windspeedValue = attributeValues.get(13);
            String temperatureValue = attributeValues.get(8);
            if (temperatureValue != null) {
                temperatureValue += "°";
            }

            TextView textViewRainfallData = findViewById(R.id.textViewRainfallData);
            TextView textViewHumidityData = findViewById(R.id.textViewHumidityData);
            TextView textViewWindspeedData = findViewById(R.id.textViewWindspeedData);
            TextView textViewTemperatureData = findViewById(R.id.textViewTemperatureData);

            textViewRainfallData.setText(rainfallValue);
            textViewHumidityData.setText(humidityVallue);
            textViewWindspeedData.setText(windspeedValue);
            textViewTemperatureData.setText(temperatureValue);
        }

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateText();
            handler.postDelayed(this,1000);
        }
    };

    private void updateText(){
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd | hh:mm a", Locale.getDefault());
        String dateString = sdf.format(new Date(currentTimeMillis));

        textViewTime.setText(dateString);
    }

    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

}
