package com.sunit.mobileapp;
import static com.android.volley.Request.Method.POST;

import com.sunit.mobileapp.Activities.GoogleMap;
import com.sunit.mobileapp.Activities.HomeActivity;
import  com.sunit.mobileapp.GetUserAutherization;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Login extends AppCompatActivity {

    ImageButton buttonBack;
    TextInputEditText textInputEditTextEmail;
    TextInputEditText textInputEditTextPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GetUserAutherization userAutherization = new GetUserAutherization();
        textInputEditTextEmail = findViewById(R.id.email_input);
        textInputEditTextPassword = findViewById(R.id.password_input);
        buttonBack = findViewById(R.id.back_button);
        buttonLogin = findViewById(R.id.login_button);
        String api = "https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token";

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Login", "Button cliked");
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                // Tạo JSON object với các thông tin cần thiết
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("client_id", "openremote");
                    jsonBody.put("username", textInputEditTextEmail.getText().toString().trim());
                    jsonBody.put("password", textInputEditTextPassword.getText().toString().trim());
                    jsonBody.put("grant_type", "password");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, api, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("API Response", response.toString());
                                try {
                                    userAutherization.setAccess_token(response.getString("access_token"));
                                    userAutherization.setExpires_in(response.getInt("expires_in"));
//                                    userAutherization.setRefresh_expires_in(response.getInt("refresh_expires_in"));
//                                    userAutherization.setRefresh_token(response.getString("refresh_token"));
//                                    userAutherization.setToken_type(response.getString("token_type"));
//                                    userAutherization.setNot_before_policy(response.getInt("not-before-policy"));
//                                    userAutherization.setSession_state(response.getString("session_state"));
//                                    userAutherization.setScope(response.getString("scope"));
                                    Log.d("Login", "Access Token: " + userAutherization.getAccess_token());
                                    Intent intent = new Intent(Login.this, GoogleMap.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(Login.this, "Access Token: " + userAutherization.getAccess_token(), Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    Toast.makeText(Login.this, "Vui lòng kiểm tra lại tài khoản", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Lỗi API", "onErrorResponse: " + error.toString());
                                Toast.makeText(Login.this, "Đã xảy ra lỗi. Vui lòng kiểm tra thông tin đăng nhập của bạn.", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };

                queue.add(jsonObjectRequest);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    }
