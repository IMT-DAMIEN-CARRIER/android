package com.example.tp_android.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tp_android.R;
import com.example.tp_android.model.Asteroid;

import java.util.List;

public class AsteroidArrayAdapter extends ArrayAdapter<Asteroid> {
    List<Asteroid> asteroids;

    public AsteroidArrayAdapter(Context context, List<Asteroid> asteroids) {
        super(context, 0, asteroids);
        this.asteroids = asteroids;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (null == rowView) {
            rowView = LayoutInflater.from(this.getContext()).inflate(
                    R.layout.layout_asteroid,
                    parent,
                    false
            );
        }

        Asteroid asteroid = this.asteroids.get(position);
        final TextView textView = (TextView) rowView.findViewById(R.id.textView_asteroid);
        final TextView textViewMagnitude = (TextView) rowView.findViewById(R.id.textView_asteroid_magnitude);
        final TextView textViewDistance = (TextView) rowView.findViewById(R.id.textView_asteroid_distance);

        textView.setText(asteroid.getName());
        textViewMagnitude.setText(String.format("Magnitude : %s", asteroid.getMagnitude()));
        textViewDistance.setText(String.format("Distance : %s km", asteroid.getDistance()));

        return rowView;
    }
}
