package com.sunit.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.sunit.mobileapp.R;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainHomeActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;

    private TextView textView;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);

        textView = findViewById(R.id.textView2);


        handler.postDelayed(runnable, 1000);

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

        textView.setText(dateString);
    }

    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

}
