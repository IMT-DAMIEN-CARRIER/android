package com.example.ds_app.modele;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Sol implements Serializable {
    String key;
    int avg_temperature;
    int max_temperature;
    int min_temperature;
    int avg_pre;
    int max_pre;
    int min_pre;
    ArrayList wind = new ArrayList();

    public Sol(String sol, JSONObject jsonObject) throws JSONException {
        this.key = sol;
        JSONObject jsonTemp = (JSONObject) jsonObject.get("AT");
        this.avg_temperature = jsonTemp.getInt("av");
        this.max_temperature = jsonTemp.getInt("mx");
        this.min_temperature = jsonTemp.getInt("mn");

        JSONObject jsonPre = (JSONObject) jsonObject.get("PRE");
        this.avg_pre = jsonPre.getInt("av");
        this.max_pre = jsonPre.getInt("mx");
        this.min_pre = jsonPre.getInt("mn");

        JSONObject jsonWind = (JSONObject) jsonObject.get("WD");

        int length = jsonWind.length();
        for (int i = 0; i < length; i++) {
            if(jsonWind.has(String.valueOf(i))) {
                JSONObject wind_detail = (JSONObject) jsonWind.get(String.valueOf(i));
                wind.add(new ArrayList(Arrays.asList(wind_detail.getInt("compass_degrees"), wind_detail.getInt("ct"))));
            }else{
                length++;
            }
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getAvg_temperature() {
        return avg_temperature;
    }

    public void setAvg_temperature(int avg_temperature) {
        this.avg_temperature = avg_temperature;
    }

    public int getMax_temperature() {
        return max_temperature;
    }

    public void setMax_temperature(int max_temperature) {
        this.max_temperature = max_temperature;
    }

    public int getMin_temperature() {
        return min_temperature;
    }

    public void setMin_temperature(int min_temperature) {
        this.min_temperature = min_temperature;
    }

    public int getAvg_pre() {
        return avg_pre;
    }

    public void setAvg_pre(int avg_pre) {
        this.avg_pre = avg_pre;
    }

    public int getMax_pre() {
        return max_pre;
    }

    public void setMax_pre(int max_pre) {
        this.max_pre = max_pre;
    }

    public int getMin_pre() {
        return min_pre;
    }

    public void setMin_pre(int min_pre) {
        this.min_pre = min_pre;
    }

    public ArrayList getWind() {
        return wind;
    }

    public void setWind(ArrayList wind) {
        this.wind = wind;
    }

    public int getMaxWind(){
        int max = 0;

        for (int i=0; i<wind.size(); i++){
            ArrayList wind_d = (ArrayList) wind.get(i);
            int value = (int) wind_d.get(i);
            if (value>max){
                max=value;
            }
        }
        return max;
    }
}
