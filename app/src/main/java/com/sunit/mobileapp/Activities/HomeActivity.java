package com.sunit.mobileapp.Activities;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sunit.mobileapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends Activity {

    EditText editTextID;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextEmail;
    String api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        editTextID = (EditText) findViewById(R.id.e1);
        editTextFirstName = (EditText) findViewById(R.id.e2);
        editTextLastName = (EditText) findViewById(R.id.e3);
        editTextEmail = (EditText) findViewById(R.id.e5);
        api = "https://uiot.ixxc.dev/api/master/user/user";//getString(R.string.GetUserDetail);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(GET, api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response.toString());
                    editTextID.setText(jsonObject.getString("id"));
                    editTextFirstName.setText(jsonObject.getString("firstName"));
                    editTextLastName.setText(jsonObject.getString("lastName"));
                    editTextEmail.setText(jsonObject.getString("email"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        },error -> Log.e("api", "onErrorResponse: " + error.getLocalizedMessage()));
        queue.add(stringRequest);



    }
}
