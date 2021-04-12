package com.example.ds_app.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ds_app.R;
import com.example.ds_app.modele.Sol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NasaService {
    private static NasaService instance;
    private Context context;
    private static final String API_KEY = "9xlcdGamYDnqdTn6igiuLPFBSdBsDUuJWlV9E7ze";
    RequestQueue requestQueue;
    private Log log;

    public static NasaService getInstance(Context context) {
        if (instance == null) {
            instance = new NasaService(context);
        }
        return instance;
    }

    private NasaService(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public Promise<JSONObject> getSol() {

        Promise<JSONObject> promise = new Promise<>();
        ArrayList<Sol> sol_list = new ArrayList<Sol>();

        String url = "https://mylovelyphoto.com/sols.json";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (promise.onSuccess != null) {
                            Toast.makeText(context, R.string.data_loaded, Toast.LENGTH_SHORT).show();
                            try {
                                ArrayList<String> array_sol_list = new ArrayList<String>();
                                JSONArray jsonSols = (JSONArray) response.get("sol_keys");
                                for (int i=0; i<jsonSols.length();i++) {
                                    array_sol_list.add(jsonSols.getString(i));
                                }
                                log.i("SOL-APP", array_sol_list.get(0));
                                for (String sol : array_sol_list){
                                    sol_list.add(new Sol(sol ,response.getJSONObject(sol)));
                                }
                                promise.onSuccess.treatResponse(sol_list);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (promise.onError != null) {
                            promise.onError.treatError();
                        }
                    }
                }
        );
        requestQueue.add(request);
        return promise;
    }
}
