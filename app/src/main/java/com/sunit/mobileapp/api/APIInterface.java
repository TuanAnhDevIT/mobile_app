package com.sunit.mobileapp.api;


import com.google.gson.JsonElement;
import com.sunit.mobileapp.Model.Asset;
import com.sunit.mobileapp.Model.Status;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {


    @GET("api/master/asset/{assetID}")
    Call<Asset> getAsset(@Path("assetID") String assetID);//, @Header("Authorization") String auth);

    @GET("api/master/info")
    Call<Status> getStatus();//, @Header("Authorization") String auth);

    @GET("api/master/map")
    Call<JsonElement> getMapOptions();

    @GET("api/master/asset/5zI6XqkQVSfdgOrZ1MyWEf")
    Call<JsonElement> getDataAsset();
}


