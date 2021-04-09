package com.example.tp_android.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Asteroid implements Serializable {
    private String id;
    private String name;
    private BigDecimal magnitude;
    private BigDecimal distance;
    private Integer orbitalPeriod;

    public Asteroid(JSONObject jsonObject) throws JSONException {
        this.loadFromJSONObject(jsonObject);
    }

    public Asteroid() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(BigDecimal magnitude) {
        this.magnitude = magnitude;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Integer getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(Integer orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public void loadFromJSONObject(JSONObject json) {
        try {
            this.id = json.getString("id");
            this.name = json.getString("name");
            this.magnitude = new BigDecimal(
                    json.getString("absolute_magnitude_h")
            ).setScale(1, RoundingMode.HALF_DOWN);

            JSONArray closeApproachData = json.getJSONArray("close_approach_data");
            JSONObject closeApproachDataObject = closeApproachData.getJSONObject(0);
            JSONObject missDistance = closeApproachDataObject.getJSONObject("miss_distance");

            this.distance = new BigDecimal(
                    missDistance.getString("kilometers")
            ).setScale(0, RoundingMode.HALF_UP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
