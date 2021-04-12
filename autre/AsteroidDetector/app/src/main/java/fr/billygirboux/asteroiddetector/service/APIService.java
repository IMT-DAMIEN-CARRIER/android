package fr.billygirboux.asteroiddetector.service;

import android.content.Context;
import android.telecom.Call;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.billygirboux.asteroiddetector.model.Asteroid;

public class APIService {

    private static final String API_KEY = "i2ZetF7sSQzt7L7MwbdplgGHRfucuUrcgiHbJxnu";

    private RequestQueue queue;

    private static APIService instance;

    public static APIService getInstance(Context context) {
        if (instance == null) {
            instance = new APIService(context);
        }
        return instance;
    }

    private APIService(Context context) {
        queue = Volley.newRequestQueue(context);
    }



    public Promise<List<Asteroid>> getAsteroids() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String dateStr = format.format(new Date());

        String url = String.format("https://api.nasa.gov/neo/rest/v1/feed?start_date=%s&end_date=%s&api_key=%s", dateStr, dateStr, API_KEY);

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
                            JSONObject near_earth_objects = response.getJSONObject("near_earth_objects");
                            JSONArray asteroidArray = near_earth_objects.getJSONArray(dateStr);

                            for (int i=0; i<asteroidArray.length(); i++) {
                                asteroids.add(new Asteroid(asteroidArray.getJSONObject(i)));
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




    public void getAsteroids(Callback<List<Asteroid>> onSuccess, Callback<String> onError) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String dateStr = format.format(new Date());

        String url = String.format("https://api.nasa.gov/neo/rest/v1/feed?start_date=%s&end_date=%s&api_key=%s", dateStr, dateStr, API_KEY);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Asteroid> asteroids = new ArrayList<>();

                        try {
                            JSONObject near_earth_objects = response.getJSONObject("near_earth_objects");
                            JSONArray asteroidArray = near_earth_objects.getJSONArray(dateStr);

                            for (int i=0; i<asteroidArray.length(); i++) {
                                asteroids.add(new Asteroid(asteroidArray.getJSONObject(i)));
                            }

                            onSuccess.onResponse(asteroids);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            onError.onResponse(e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onError.onResponse(error.getMessage());
                    }
                }
        );

        queue.add(request);
    }





    public void getAsteroidOrbitalPeriod(String id, Callback<Integer> onSuccess, Callback<String> onError) {

        String url = String.format("https://api.nasa.gov/neo/rest/v1/neo/%s?api_key=%s", id, API_KEY);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONObject orbital_data = response.getJSONObject("orbital_data");
                            BigDecimal orbitalPeriod = new BigDecimal(orbital_data.getString("orbital_period"));

                            onSuccess.onResponse(orbitalPeriod.intValue());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            onError.onResponse(e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onError.onResponse(error.getMessage());
                    }
                }
        );

        queue.add(request);
    }












}
