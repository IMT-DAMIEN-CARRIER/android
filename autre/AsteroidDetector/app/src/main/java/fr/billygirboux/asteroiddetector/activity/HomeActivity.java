package fr.billygirboux.asteroiddetector.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fr.billygirboux.asteroiddetector.R;
import fr.billygirboux.asteroiddetector.adapter.AsteroidAdapter;
import fr.billygirboux.asteroiddetector.model.Asteroid;
import fr.billygirboux.asteroiddetector.service.APIService;
import fr.billygirboux.asteroiddetector.service.Callback;

public class HomeActivity extends AppCompatActivity {

    private APIService apiService;

    private ListView lvAsteroids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.lvAsteroids = findViewById(R.id.lvAsteroids);

        this.apiService = APIService.getInstance(getApplicationContext());


        /*
        this.apiService.getAsteroids()
                .then(new Callback<List<Asteroid>>() {
                    @Override
                    public void onResponse(List<Asteroid> asteroids) {
                        Toast.makeText(HomeActivity.this, "NB Asteroids" + asteroids.size(), Toast.LENGTH_SHORT).show();
                    }
                }).catchError(new Callback<String>() {
                    @Override
                    public void onResponse(String error) {
                        Toast.makeText(HomeActivity.this, "Error:" + error, Toast.LENGTH_SHORT).show();
                    }
                });
        */


        this.apiService.getAsteroids(
                new Callback<List<Asteroid>>() {
                    @Override
                    public void onResponse(List<Asteroid> asteroids) {
                        Toast.makeText(HomeActivity.this, "NB Asteroids" + asteroids.size(), Toast.LENGTH_SHORT).show();

                        /*
                        ArrayAdapter<Asteroid> adapter = new ArrayAdapter<>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                asteroids);

                        lvAsteroids.setAdapter(adapter);
                         */

                        ArrayAdapter<Asteroid> adapter = new AsteroidAdapter(
                                getApplicationContext(),
                                asteroids);

                        lvAsteroids.setAdapter(adapter);


                        lvAsteroids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Asteroid asteroid = asteroids.get(position);
                                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                intent.putExtra("asteroid", asteroid);
                                startActivity(intent);
                            }
                        });


                    }
                },
                new Callback<String>() {
                    @Override
                    public void onResponse(String error) {
                        Toast.makeText(HomeActivity.this, "Error:" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}