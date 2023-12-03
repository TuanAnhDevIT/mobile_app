package com.sunit.mobileapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sunit.mobileapp.Model.MapOptions;
import com.sunit.mobileapp.R;
import com.sunit.mobileapp.api.APIClient;
import com.sunit.mobileapp.api.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleMap extends AppCompatActivity implements OnMapReadyCallback {

    private com.google.android.gms.maps.GoogleMap myMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull com.google.android.gms.maps.GoogleMap googleMap) {

        myMap = googleMap;

//        LatLng sydney = new LatLng(10.87, 106.80324);
//        myMap.addMarker(new MarkerOptions().position(sydney).title("Sydney"));
//        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));
        getMapOptions();
    }

    private void getMapOptions(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<MapOptions> call = apiInterface.getMapOptions();

        call.enqueue(new Callback<MapOptions>() {
            @Override
            public void onResponse(Call<MapOptions> call, Response<MapOptions> response) {
                if (response.isSuccessful()) {
                    MapOptions mapOptions = response.body();

                    // Trích xuất thông tin từ mapOptions và cập nhật bản đồ
                    if (mapOptions != null) {
                        LatLng center = new LatLng(mapOptions.getOptions().getCenter()[1], mapOptions.getOptions().getCenter()[0]);
                        myMap.addMarker(new MarkerOptions().position(center).title("Điểm đánh dấu"));
                        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, mapOptions.getOptions().getZoom()));
                    }
                }
            }

            @Override
            public void onFailure(Call<MapOptions> call, Throwable t) {
                // Xử lý lỗi khi gọi API
            }
        });
    }
}