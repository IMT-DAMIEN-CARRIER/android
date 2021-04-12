package com.example.ds_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ds_app.adapter.SolAdaptater;
import com.example.ds_app.modele.Sol;
import com.example.ds_app.service.NasaService;
import com.example.ds_app.service.OnError;
import com.example.ds_app.service.OnSuccess;

import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private NasaService nasaService;
    private Log log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.nasaService = NasaService.getInstance(getApplicationContext());

        this.nasaService.getSol().then(new OnSuccess<JSONObject>() {
            @Override
            public void treatResponse(ArrayList<Sol> sol_list) {
                SolAdaptater solAdaptater = new SolAdaptater(HomeActivity.this, sol_list);
                Toast.makeText(HomeActivity.this, "NB SOLS : "+sol_list.size(), Toast.LENGTH_SHORT).show();
                ListView listview = findViewById(R.id.listView);
                listview.setAdapter(solAdaptater);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(HomeActivity.this, SolActivity.class);
                        Sol clicked = (Sol) parent.getItemAtPosition(position);
                        intent.putExtra("sol", sol_list.get(position));
                        startActivity(intent);
                    }
                });
            }
        }).catchError(new OnError() {
            @Override
            public void treatError() {
                Toast.makeText(HomeActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });
    }
}