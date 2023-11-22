package com.sunit.mobileapp;
import static com.android.volley.Request.Method.POST;

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
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.email_input);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.password_input);
        buttonBack = (ImageButton) findViewById(R.id.back_button);
        buttonLogin = (Button) findViewById(R.id.login_button);
        String api = "https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token";//getString(R.string.API);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(POST,api,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject= new JSONObject(response.toString());
                                    userAutherization.setAccess_token(jsonObject.getString("access_token"));
                                    userAutherization.setExpires_in(jsonObject.getInt("expires_in"));
                                    userAutherization.setRefresh_expires_in(jsonObject.getInt("refresh_expires_in"));
                                    userAutherization.setRefresh_token(jsonObject.getString("refresh_token"));
                                    userAutherization.setToken_type(jsonObject.getString("token_type"));
                                    userAutherization.setNot_before_policy(jsonObject.getInt("not-before-policy"));
                                    userAutherization.setSession_state(jsonObject.getString("session_state"));
                                    userAutherization.setScope(jsonObject.getString("scope"));
                                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    Toast.makeText(Login.this, "Vui lòng kiểm tra lại tài khoản", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        error -> Log.e("api", "onErrorResponse: " + error.getLocalizedMessage())){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String,String> params = new HashMap<>();
                        params.put("client_id","openremote");
                        params.put("username",textInputEditTextEmail.getText().toString().trim());
                        params.put("password",textInputEditTextPassword.getText().toString().trim());
                        params.put("grant_type","password");
                        return params;
                    }
                };

                queue.add(stringRequest);
                //Intent intent = new Intent(Login.this, HomeActivity.class);
               // startActivity(intent);
            }


        }
        );

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}