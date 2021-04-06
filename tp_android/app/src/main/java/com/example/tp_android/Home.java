package com.example.tp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final TextView textView = (TextView) findViewById(R.id.textView_home);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new Date();
        System.out.println(formatter.format(date));
        String url ="https://api.nasa.gov/neo/rest/v1/feed?start_date=" + formatter.format(date) + "&end_date=" + formatter.format(date) + "&api_key=C58gONGJ6cmtuGpZmeFHfj6Xr2oBJ1l7XREtuUWF";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    textView.setText("That is working!");
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    textView.setText("That didn't work!");
                }
            }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        final Button button = findViewById(R.id.return_home_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMainActivity();
            }
        });
    }

    public void returnMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}