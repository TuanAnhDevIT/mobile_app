package com.sunit.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sunit.mobileapp.Activities.HomeActivity;
import com.sunit.mobileapp.api.APIClient;

public class Logout extends AppCompatActivity {
    Button buttonLogout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buttonLogout = findViewById(R.id.btnLogout);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Logout", "Button clicked");

                // Check if the user is already logged in
                if (APIClient.getToken() != null) {
                    // Perform logout actions
                    APIClient.clearToken();
                    Toast.makeText(Logout.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

                    // Redirect to the login screen (or any other desired screen)
                    Intent intent = new Intent(Logout.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // User is not logged in
                    Toast.makeText(Logout.this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
