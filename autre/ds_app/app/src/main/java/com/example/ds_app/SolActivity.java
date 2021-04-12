package com.example.ds_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ds_app.modele.Sol;
import com.example.ds_app.service.NasaService;
import com.example.ds_app.view.WindView;

public class SolActivity extends AppCompatActivity {

    NasaService nasaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sol);
        Sol sol = (Sol) getIntent().getSerializableExtra("sol");

        this.nasaService = NasaService.getInstance(getApplicationContext());

        TextView name = findViewById(R.id.sol_name);
        TextView temperature_avg = findViewById(R.id.sol_temperature_avg);
        TextView pressure_avg = findViewById(R.id.sol_pre_avg);
        TextView temperature_max = findViewById(R.id.sol_temperature_max);
        TextView pressure_max = findViewById(R.id.sol_pre_max);
        TextView temperature_min = findViewById(R.id.sol_temperature_min);
        TextView pressure_min = findViewById(R.id.sol_pre_min);

        name.setText("Sol n°"+sol.getKey());
        temperature_avg.setText(getText(R.string.avg)+" : "+sol.getAvg_temperature());
        temperature_max.setText("Max : "+sol.getMax_temperature());
        temperature_min.setText("Min  : "+sol.getMin_temperature());
        pressure_avg.setText(getText(R.string.avg)+" : "+sol.getAvg_pre());
        pressure_max.setText("Max : "+sol.getMax_pre());
        pressure_min.setText("Min  : "+sol.getMin_pre());

        WindView view = findViewById(R.id.windView);
        view.setSol(sol);
    }
}