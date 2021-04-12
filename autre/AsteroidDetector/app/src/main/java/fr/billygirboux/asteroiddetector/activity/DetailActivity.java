package fr.billygirboux.asteroiddetector.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import fr.billygirboux.asteroiddetector.R;
import fr.billygirboux.asteroiddetector.model.Asteroid;
import fr.billygirboux.asteroiddetector.service.APIService;
import fr.billygirboux.asteroiddetector.service.Callback;
import fr.billygirboux.asteroiddetector.view.AsteroidOrbitView;

public class DetailActivity extends AppCompatActivity {

    private APIService apiService;

    private TextView tvName;
    private TextView tvDistance;
    private TextView tvMagnitude;
    private TextView tvPeriod;
    private AsteroidOrbitView asteroidOrbitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.tvName = findViewById(R.id.tvName);
        this.tvDistance = findViewById(R.id.tvDistance);
        this.tvMagnitude = findViewById(R.id.tvMagnitude);
        this.tvPeriod = findViewById(R.id.tvPeriod);
        this.asteroidOrbitView = findViewById(R.id.asteroidOrbitView);

        Asteroid asteroid = (Asteroid) getIntent().getSerializableExtra("asteroid");

        this.tvName.setText(asteroid.getName());
        this.tvDistance.setText(getString(R.string.distance) + asteroid.getDistance() + "km");
        this.tvMagnitude.setText(getString(R.string.magnitude) + asteroid.getMagnitude());



        this.apiService = APIService.getInstance(getApplicationContext());
        this.apiService.getAsteroidOrbitalPeriod(asteroid.getId(),
            new Callback<Integer>() {
                @Override
                public void onResponse(Integer orbitalPeriod) {
                    asteroid.setOrbitalPeriod(orbitalPeriod);
                    tvPeriod.setText(getString(R.string.period) + orbitalPeriod);
                    asteroidOrbitView.setAsteroid(asteroid);
                }
            }, new Callback<String>() {
                @Override
                public void onResponse(String s) {

                }
            }
        );
    }
}