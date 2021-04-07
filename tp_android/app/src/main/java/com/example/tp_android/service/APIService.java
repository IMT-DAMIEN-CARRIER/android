package com.example.tp_android.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tp_android.model.Asteroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class APIService {

    private RequestQueue queue;
    private static APIService instance;

    public static APIService getInstance(Context context)
    {
        if (null == instance) {
            instance = new APIService(context);
        }

        return instance;
    }

    private APIService(Context context)
    {
        queue = Volley.newRequestQueue(context);
    }

    public Promise<List<Asteroid>> getAsteroids()
    {
        SimpleDateFormat df = new SimpleDateFormat("y-MM-dd");
        String formatted = df.format(new Date());
        String key = "C58gONGJ6cmtuGpZmeFHfj6Xr2oBJ1l7XREtuUWF";
        String url ="https://api.nasa.gov/neo/rest/v1/feed?start_date=" + formatted + "&end_date=" + formatted + "&api_key=" + key;

        Promise<List<Asteroid>> promise = new Promise<>();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Asteroid> asteroids = new ArrayList<>();

                        try {
                            JSONObject nearEarthObjects = response.getJSONObject("near_earth_objects");
                            JSONArray asteroidsArray = nearEarthObjects.getJSONArray(formatted);

                            for (int i = 0; i < asteroidsArray.length(); i++) {
                                asteroids.add(new Asteroid(asteroidsArray.getJSONObject(i)));
                            }

                            promise.promiseThen.onResponse(asteroids);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            promise.promiseCatch.onResponse(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        promise.promiseCatch.onResponse(error.getMessage());
                    }
                }
        );

        queue.add(request);

        return promise;
    }
}
