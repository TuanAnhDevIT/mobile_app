package com.sunit.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        ///Nav bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home_menu);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_menu) {
                return true;
            } else if (itemId == R.id.graph_menu) {
                startActivity(new Intent(getApplicationContext(), GraphTestActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
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

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        ArrayList<String> attributeNames = intent.getStringArrayListExtra("attributeNames");
        ArrayList<String> attributeValues = intent.getStringArrayListExtra("attributeValues");

        if (attributeValues != null && attributeValues.size() > 1) {

            String rainfallValue = attributeValues.get(1);
            String humidityVallue  = attributeValues.get(9);
            String windspeedValue = attributeValues.get(13);
            String temperatureValue = attributeValues.get(8);
            String textViewPlaceValue = attributeValues.get(11);
            if (temperatureValue != null) {
                temperatureValue += "°";
            }

            TextView textViewRainfallData = findViewById(R.id.textViewRainfallData);
            TextView textViewHumidityData = findViewById(R.id.textViewHumidityData);
            TextView textViewWindspeedData = findViewById(R.id.textViewWindspeedData);
            TextView textViewTemperatureData = findViewById(R.id.textViewTemperatureData);
            TextView textViewPlace = findViewById(R.id.textViewPlace);

            textViewRainfallData.setText(rainfallValue);
            textViewHumidityData.setText(humidityVallue);
            textViewWindspeedData.setText(windspeedValue);
            textViewTemperatureData.setText(temperatureValue);
            textViewPlace.setText(textViewPlaceValue);
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
