package com.example.tp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp_android.model.Asteroid;
import com.example.tp_android.service.APIService;
import com.example.tp_android.service.CallbackInterface;

import java.io.Serializable;

public class AsteroidView extends AppCompatActivity {
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asteroid_view);

        Asteroid asteroid = (Asteroid) getIntent().getSerializableExtra("ASTEROID");

        final TextView asteroidName = (TextView) findViewById(R.id.asteroid_name);
        final TextView asteroidDistance = (TextView) findViewById(R.id.asteroid_distance);
        final TextView asteroidMagnitude = (TextView) findViewById(R.id.asteroid_magnitude);
        final TextView asteroidPeriode = (TextView) findViewById(R.id.asteroid_periode);

        asteroidName.setText(asteroid.getName());
        asteroidDistance.setText(String.format("Distance : %s km", asteroid.getDistance()));
        asteroidMagnitude.setText(String.format("Magnitude : %s", asteroid.getMagnitude()));

        APIService apiService = APIService.getInstance(getApplicationContext());

        apiService.getAsteroid(asteroid.getId())
                .then(new CallbackInterface<Integer>() {
                    @Override
                    public void onResponse(Integer periodOrbital) {
                        asteroidPeriode.setText(String.format("Periode orbitale : %d", periodOrbital));

                        Toast.makeText(
                                AsteroidView.this,
                                R.string.data_received,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }).catchError(new CallbackInterface<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(AsteroidView.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}