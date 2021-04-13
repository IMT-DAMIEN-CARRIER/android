package fr.billygirboux.asteroiddetector.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import fr.billygirboux.asteroiddetector.R;
import fr.billygirboux.asteroiddetector.model.Asteroid;

public class AsteroidOrbitView extends View {

    private static final long SOLAR_DISTANCE = 149597870;

    private Paint orbitPaint;
    private Paint sunPaint;
    private Paint earthPaint;
    private Paint asteroidPaint;

    private Asteroid asteroid;

    private GestureDetector.OnGestureListener gestureListener;
    private GestureDetectorCompat gestureDetectorCompat;

    private int increment;
    private float xInit;
    private float yInit;

    private boolean inAnimation = true;

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

        this.gestureListener = new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                xInit -= distanceX;
                yInit -= distanceY;
                invalidate();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                xInit = 0;
                yInit = 0;
                invalidate();
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                inAnimation = !inAnimation;
                invalidate();
                return true;
            }
        };

        gestureDetectorCompat = new GestureDetectorCompat(
                this.getContext(),
                this.gestureListener
        );

    }

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();

        canvas.translate(xInit, yInit);

        canvas.translate(width/2, width/2);

        canvas.drawCircle(0, 0, 60, sunPaint);
        canvas.drawCircle(0, 0, width/2 - 50, orbitPaint);

        canvas.rotate(increment % 360f);
        canvas.translate(width/2 - 50, 0);

        canvas.drawCircle(0, 0, 30, earthPaint);

        if (asteroid != null) {

            float orbitAsteroid = asteroid.getDistance().longValue() * (width/2 - 50) / SOLAR_DISTANCE;
            orbitAsteroid = Math.max(orbitAsteroid, 35);
            canvas.drawCircle(0, 0, orbitAsteroid, orbitPaint);

            float rotationAsteroid = (increment * 365f / asteroid.getOrbitalPeriod()) % 360f;
            canvas.rotate(rotationAsteroid);
            canvas.drawCircle(-orbitAsteroid, 0, 15, asteroidPaint);

        }

        if (inAnimation) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    increment++;
                    invalidate();
                }
            }, 20);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /*
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (event.getHistorySize() == 0) return false;

                float dx = event.getX() - event.getHistoricalX(0);
                float dy = event.getY() - event.getHistoricalY(0);

                xInit += dx;
                yInit += dy;

                break;
        }

         */


        gestureDetectorCompat.onTouchEvent(event);

        return true;
    }
}
