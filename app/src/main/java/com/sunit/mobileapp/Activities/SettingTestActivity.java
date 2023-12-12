package com.sunit.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sunit.mobileapp.Login;
import com.sunit.mobileapp.R;

public class SettingTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_test);

        Button btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thực hiện đăng xuất ở đây, có thể làm các xử lý cần thiết

                // Hiển thị thông báo "Logout successfully"
                Toast.makeText(SettingTestActivity.this, "Logout successfully", Toast.LENGTH_SHORT).show();

                // Sau khi đăng xuất, chuyển đến trang login
                Intent intent = new Intent(SettingTestActivity.this, Login.class);
                // Đặt cờ để xóa hết các Activity khác và tạo mới LoginActivity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.setting_menu);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_menu) {
                startActivity(new Intent(getApplicationContext(), MainHomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
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

                return true;
            }
            return false;
        });

    }
}