package com.sunit.mobileapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
        myMap.setOnMapClickListener(this::onMapClick);
    }
    public void onMapClick(LatLng latLng) {

    }

    private void getDataAsset(){
        APIInterface apiInterface1 = APIClient.getClient().create(APIInterface.class);
        Call<JsonElement> call1 = apiInterface1.getDataAsset();

        call1.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonElement jsonElement = response.body();
                    if (jsonElement.isJsonObject()) {
                        JsonObject optionsObject = jsonElement.getAsJsonObject().getAsJsonObject("attributes").getAsJsonObject("");
                        String name = optionsObject.get("name").getAsString();
                        String value = optionsObject.get("value").getAsString();

                }
            }

//            @Override
//            public void onFailure(Call<WeatherAsset> call, Throwable t) {
//                // Xử lý lỗi khi gọi API
//                Log.e("getDataAsset", "Error getting data asset: " + t.getMessage());
//            }
        });
    }


    private void getMapOptions(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonElement> call = apiInterface.getMapOptions();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonElement jsonElement = response.body();
                    if (jsonElement.isJsonObject()) {
                        JsonObject optionsObject = jsonElement.getAsJsonObject().getAsJsonObject("options").getAsJsonObject("default");
                        LatLng center = new LatLng(optionsObject.getAsJsonArray("center").get(1).getAsDouble(), optionsObject.getAsJsonArray("center").get(0).getAsDouble());
                        int zoom = optionsObject.get("zoom").getAsInt();
                        myMap.addMarker(new MarkerOptions().position(center).title("Điểm đánh dấu"));
                        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, zoom));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                // Handle API call failure
            }
        });
    }


}