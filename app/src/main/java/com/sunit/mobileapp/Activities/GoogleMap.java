package com.sunit.mobileapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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

import java.util.Map;

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

        getMapOptions();
        myMap.setOnMapClickListener(this::onMapClick);
    }
    public void onMapClick(LatLng latLng) {
    getDataAsset();
    }

    private String getDataAsset() {
        final StringBuilder result = new StringBuilder();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonElement> call = apiInterface.getDataAsset();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonElement jsonElement = response.body();

                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();

                        JsonObject attributesObject = jsonObject.getAsJsonObject("attributes");
                        System.out.println(attributesObject);
                        for (Map.Entry<String, JsonElement> entry : attributesObject.entrySet()) {
                            String attributeName = entry.getKey();
                            JsonElement attributeValue = entry.getValue();

                            // Kiểm tra nếu giá trị của thuộc tính là một đối tượng JSON
                            if (attributeValue.isJsonObject()) {
                                JsonObject attributeObject = attributeValue.getAsJsonObject();

                                // Lấy giá trị của thuộc tính name và value
                                String name = attributeObject.get("name").getAsString();
                                JsonElement value = attributeObject.get("value");

                                // Thêm thông tin vào chuỗi kết quả
                                result.append("Name: ").append(name).append("\n");
                                result.append("Value: ").append(value).append("\n");


                            }
                        }
                        Log.d("getDataAsset", result.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                // Handle API call failure
                Log.e("getDataAsset", "Error getting data asset: " + t.getMessage());
            }
        });

        return result.toString();
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