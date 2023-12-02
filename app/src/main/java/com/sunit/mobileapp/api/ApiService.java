package com.sunit.mobileapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sunit.mobileapp.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://uiot.ixxc.dev/api")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("/master/user/user")
    Call<List<User>> getListUsers();

}
