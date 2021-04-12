package fr.billygirboux.asteroiddetector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.util.List;

import fr.billygirboux.asteroiddetector.R;
import fr.billygirboux.asteroiddetector.model.Asteroid;

public class AsteroidAdapter extends ArrayAdapter<Asteroid> {

    private List<Asteroid> asteroids;
    private LayoutInflater inflater;


    public AsteroidAdapter(@NonNull Context context, List<Asteroid> asteroids) {
        super(context, -1, asteroids);
        this.asteroids = asteroids;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Asteroid asteroid = this.asteroids.get(position);

        View lineView = this.inflater.inflate(R.layout.asteroidlvline, parent, false);

        TextView tvName = lineView.findViewById(R.id.tvLineName);
        TextView tvDistance = lineView.findViewById(R.id.tvLineDistance);
        TextView tvMagnitude = lineView.findViewById(R.id.tvLineMagnitude);

        tvName.setText(asteroid.getName());
        tvDistance.setText(getContext().getString(R.string.distance) + asteroid.getDistance().intValue());
        tvMagnitude.setText(getContext().getString(R.string.magnitude) + asteroid.getMagnitude().setScale(1, RoundingMode.HALF_UP));

        return lineView;
    }
}
