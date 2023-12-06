package com.sunit.mobileapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleMap extends AppCompatActivity implements OnMapReadyCallback {

    private com.google.android.gms.maps.GoogleMap myMap;
    private TextView deviceInfoTextView;

    private LinearLayout deviceInfoField;

    private boolean isDeviceInfoFieldVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        deviceInfoTextView = findViewById(R.id.deviceInfoTextView);

        deviceInfoField = findViewById(R.id.deviceInfoField);
        deviceInfoField.setVisibility(View.GONE);

        deviceInfoField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDeviceInfoFieldVisible = !isDeviceInfoFieldVisible;
                deviceInfoField.setVisibility(isDeviceInfoFieldVisible ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull com.google.android.gms.maps.GoogleMap googleMap) {

        myMap = googleMap;
        getMapOptions();
        myMap.setOnMapClickListener(this::onMapClick);
    }
    public void onMapClick(LatLng latLng) {

        getDataAsset();
        isDeviceInfoFieldVisible = !isDeviceInfoFieldVisible;
        deviceInfoField.setVisibility(isDeviceInfoFieldVisible ? View.VISIBLE : View.GONE);
    }

    private void updateDeviceInfo(List<String> attributeNames, List<String> attributeValues) {
        StringBuilder deviceInfo = new StringBuilder();
        for (int i = 0; i < attributeNames.size(); i++) {
            String attributeName = attributeNames.get(i);
            String attributeValue = attributeValues.get(i);

            if (attributeValue != null) {
                deviceInfo.append(attributeName).append(": ").append(attributeValue).append("\n");
            }
        }

        deviceInfoTextView.setText(deviceInfo.toString());

    }


    private void getDataAsset() {
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

//                        StringBuilder attributeInfo = new StringBuilder();
                        List<String> attributeNames = new ArrayList<>();
                        List<String> attributeValues = new ArrayList<>();

                        for (Map.Entry<String, JsonElement> entry : attributesObject.entrySet()) {
                            String attributeName = entry.getKey();
                            JsonElement attributeValueElement = entry.getValue().getAsJsonObject().get("value");
                            String attributeValue = (attributeValueElement != null && attributeValueElement.isJsonPrimitive()) ?
                                    attributeValueElement.getAsString() : null;
                            attributeNames.add(attributeName);
                            attributeValues.add(attributeValue);
                        }
//                        Log.d("getDataAsset", "Attribute Names: " + attributeNames.toString());
//                        Log.d("getDataAsset", "Attribute Values: " + attributeValues.toString());
                        updateDeviceInfo(attributeNames,attributeValues);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                // Handle API call failure
                Log.e("getDataAsset", "Error getting data asset: " + t.getMessage());
            }
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