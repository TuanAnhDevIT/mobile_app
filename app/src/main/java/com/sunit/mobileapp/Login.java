package com.sunit.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.sunit.mobileapp.Activities.GoogleMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextInputEditText textInputEditTextEmail;
    TextInputEditText textInputEditTextPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextEmail = findViewById(R.id.email_input);
        textInputEditTextPassword = findViewById(R.id.password_input);
        buttonLogin = findViewById(R.id.login_button);
        String api = "https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token";

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Login", "Button clicked");
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, api,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("API Response", response);
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String accessToken = jsonResponse.getString("access_token");
                                    int expiresIn = jsonResponse.getInt("expires_in");

                                    Log.d("Login", "Access Token: " + accessToken);
                                    Toast.makeText(Login.this, "Access Token: " + accessToken, Toast.LENGTH_SHORT).show();

                                    // Chuyển sang màn hình GoogleMap (hoặc màn hình khác)
                                    Intent intent = new Intent(Login.this, GoogleMap.class);
                                    startActivity(intent);
                                    finish();
                                } catch (JSONException e) {
                                    Toast.makeText(Login.this, "Vui lòng kiểm tra lại tài khoản", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("API Error", "onErrorResponse: " + error.toString());
                                Toast.makeText(Login.this, "Đã xảy ra lỗi. Vui lòng kiểm tra thông tin đăng nhập của bạn.", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("client_id", "openremote");
                        params.put("username", textInputEditTextEmail.getText().toString().trim());
                        params.put("password", textInputEditTextPassword.getText().toString().trim());
                        params.put("grant_type", "password");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        return headers;
                    }
                };

                queue.add(stringRequest);
            }
        });
    }
}
