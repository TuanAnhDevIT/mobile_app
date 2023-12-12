package com.sunit.mobileapp;

import static com.sunit.mobileapp.api.APIClient.setToken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
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


public class Register extends AppCompatActivity {

    ImageButton buttonBack;

    Button buttonRegister;

    WebView webViewRegister;
    TextInputEditText textInputEditTextUsername;
    TextInputEditText textInputEditTextEmail;
    TextInputEditText textInputEditTextPassword;
    TextInputEditText textInputEditTextRepassword;
    Integer integerCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonBack = (ImageButton) findViewById(R.id.back_button);
        buttonRegister = (Button) findViewById(R.id.register_button);

        textInputEditTextUsername = (TextInputEditText) findViewById(R.id.username_input);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.email_input);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.password_input);
        textInputEditTextRepassword = (TextInputEditText) findViewById(R.id.confirm_password_input);

        String url = getString(R.string.WebLoginURL)  + "?client_id=" +  getString(R.string.ClientID) + "&redirect_uri=" +  getString(R.string.RedirectURI);
        String api = getString(R.string.LoginAPI);
        webViewRegister = (WebView) findViewById(R.id.register_web);
        webViewRegister.getSettings().setJavaScriptEnabled(true);
        webViewRegister.clearCache(true);
        webViewRegister.clearHistory();
        integerCount = 0;

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookieManager cookieManager=CookieManager.getInstance();
                cookieManager.removeAllCookies(null);
                webViewRegister.loadUrl(url);


                webViewRegister.setWebViewClient(new WebViewClient() {

                    @Deprecated
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        if(request.getUrl().toString().contains("session_state")){
                            Toast.makeText(getApplicationContext(),"Đăng ký tài khoản thành công",Toast.LENGTH_LONG).show();
                            getToken(getApplicationContext(),api);
                        }
                        return false;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        String clickSignUpButton = "document.getElementsByClassName('btn waves-effect waves-light')[1].click()";
                        webViewRegister.evaluateJavascript(clickSignUpButton,null);
                        if(integerCount == 5){
                            view.stopLoading();
                            Toast.makeText(getApplicationContext(),"Đã tồn tại tài khoản trên!!!",Toast.LENGTH_LONG).show();
                        }
                        if (url.contains("login-actions/registration")) {
                            String usrScript = "document.getElementById('username').value ='" + textInputEditTextUsername.getText() + "';";
                            String emailScript = "document.getElementById('email').value ='" + textInputEditTextEmail.getText() + "';";
                            String pwdScript = "document.getElementById('password').value ='" + textInputEditTextPassword.getText() + "';";
                            String rePwdScript = "document.getElementById('password-confirm').value ='" + textInputEditTextRepassword.getText() + "';";

                            view.evaluateJavascript(usrScript, null);
                            view.evaluateJavascript(emailScript, null);
                            view.evaluateJavascript(pwdScript, null);
                            view.evaluateJavascript(rePwdScript, null);

                            String btnRegisterScript = "document.getElementsByName('register')[0].click()";
                            view.evaluateJavascript(btnRegisterScript, null);


                            integerCount++;

                        }
                    }

                });
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void getToken(Context context, String api){
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String accessToken = jsonResponse.getString("access_token");
                    setToken(accessToken);
                    int expiresIn = jsonResponse.getInt("expires_in");

                    // Chuyển sang màn hình GoogleMap (hoặc màn hình khác)
                    Intent intent = new Intent(Register.this, GoogleMap.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API Error", "onErrorResponse: " + error.toString());
            }
        }
        ){
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

}