package com.sunit.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WevViewRegister extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wev_view_register);
        webView = (WebView) findViewById(R.id.Web_View_Register);

        String url="https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/registrations";
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                if(url.contains("openid-connect/registrations")){
                    Log.d("Page","onPageFinished: Fill form");

                }
            }
        });

    }
}