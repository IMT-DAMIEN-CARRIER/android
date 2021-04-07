package com.example.tp_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp_android.model.Asteroid;
import com.example.tp_android.service.APIService;
import com.example.tp_android.service.AsteroidArrayAdapter;
import com.example.tp_android.service.CallbackInterface;

import java.util.List;

public class Home extends AppCompatActivity {
    private APIService apiService;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ListView listView = (ListView) findViewById(R.id.listViewHome);
        final TextView numberOfAsteroidTextView = (TextView) findViewById(R.id.number_of_asteroid);
        final Button button = findViewById(R.id.return_home_button);

        this.apiService = APIService.getInstance(getApplicationContext());

        this.apiService.getAsteroids()
                .then(new CallbackInterface<List<Asteroid>>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onResponse(List<Asteroid> asteroids) {
                        numberOfAsteroidTextView.setText(
                                String.format(
                                        "%s %d",
                                        getString(R.string.number_of_asteroid),
                                        asteroids.size()
                                )
                        );

                        AsteroidArrayAdapter adapter = new AsteroidArrayAdapter(
                                getApplicationContext(),
                                asteroids
                        );

                        listView.setAdapter(adapter);

                        Toast.makeText(
                                Home.this,
                                R.string.data_received,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }).catchError(new CallbackInterface<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(Home.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

        ;

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
