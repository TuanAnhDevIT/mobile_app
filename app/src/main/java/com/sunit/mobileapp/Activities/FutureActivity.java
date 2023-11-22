package com.sunit.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sunit.mobileapp.Adapters.FutureAdapter;
import com.sunit.mobileapp.Domains.FutureDomain;
import com.sunit.mobileapp.R;

import java.util.ArrayList;

public class FutureActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterTomorrow;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);

        initRecyclerView();
        setVariable();
    }

    private void setVariable() {
        ConstraintLayout backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> startActivity(new Intent(FutureActivity.this, MainHomeActivity.class)));
    }

    private void initRecyclerView() {
        ArrayList<FutureDomain> items=new ArrayList<>();
        items.add(new FutureDomain("Thu", "storm", "Storm", 29, 23));
        items.add(new FutureDomain("Fri", "cloudy", "Cloudy", 30, 25));
        items.add(new FutureDomain("Sat", "sunny", "Sunny", 35, 29));
        items.add(new FutureDomain("Sun", "rainy", "Rainy", 32, 26));
        items.add(new FutureDomain("Mon", "windy", "Windy", 31, 27));
        items.add(new FutureDomain("Tue", "cloudy_sunny", "Cloudy_Sunny", 33, 29));
        items.add(new FutureDomain("Web", "rain", "Rain", 32, 28));

        recyclerView=findViewById(R.id.view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapterTomorrow=new FutureAdapter(items);
        recyclerView.setAdapter(adapterTomorrow);
    }
}