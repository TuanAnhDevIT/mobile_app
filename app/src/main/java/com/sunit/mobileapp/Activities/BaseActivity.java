package com.sunit.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sunit.mobileapp.R;
import com.sunit.mobileapp.databinding.ActivityMainBinding;

public class BaseActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.bottomNavigationView2.setOnItemSelectedListener(item -> {
//
//            switch (item.getItemId()){
//                case R.id.home_menu:
//                    break;
//                case R.id.location:
//                    break;
//            }
//
//            return true;
//        });
    }
}