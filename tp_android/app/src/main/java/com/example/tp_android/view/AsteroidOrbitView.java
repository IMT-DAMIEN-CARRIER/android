package com.example.tp_android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.tp_android.R;
import com.example.tp_android.model.Asteroid;

public class AsteroidOrbitView extends View {
    private static final long SOLAR_DISTANCE = 149597870;

    private Paint orbitPaint;
    private Paint sunPaint;
    private Paint earthPaint;
    private Paint asteroidPaint;

    private Asteroid asteroid;

    public AsteroidOrbitView(Context context) {
        super(context);
        init();
    }

    public AsteroidOrbitView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AsteroidOrbitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.orbitPaint = new Paint();
        this.orbitPaint.setStyle(Paint.Style.STROKE);
        this.orbitPaint.setColor(getResources().getColor(R.color.colorOrbit));
        this.orbitPaint.setStrokeWidth(4f);

        this.sunPaint = new Paint();
        this.sunPaint.setStyle(Paint.Style.FILL);
        this.sunPaint.setColor(getResources().getColor(R.color.colorSolar));

        this.earthPaint = new Paint();
        this.earthPaint.setStyle(Paint.Style.FILL);
        this.earthPaint.setColor(getResources().getColor(R.color.colorEarth));

        this.asteroidPaint = new Paint();
        this.asteroidPaint.setStyle(Paint.Style.FILL);
        this.asteroidPaint.setColor(getResources().getColor(R.color.colorAsteroid));
    }

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();

        canvas.translate(width/2, width/2);
        canvas.drawCircle(0, 0, 60, sunPaint);
        canvas.drawCircle(0, 0, width/2 - 50, orbitPaint);
        canvas.translate(width/2 - 50, 0);
        canvas.drawCircle(0, 0, 30, earthPaint);

        if (asteroid != null) {
            float orbitAsteroid = asteroid.getDistance().longValue() * (width/2 - 50) / SOLAR_DISTANCE;
            orbitAsteroid = Math.max(orbitAsteroid, 35);
            canvas.drawCircle(0, 0, orbitAsteroid, orbitPaint);
            canvas.rotate(45);
            canvas.drawCircle(-orbitAsteroid, 0, 15, asteroidPaint);
        }
    }
}
