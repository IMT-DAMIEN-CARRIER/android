package com.example.ds_app.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.ds_app.R;
import com.example.ds_app.modele.Sol;

import java.util.ArrayList;

public class WindView extends View {
    Paint mars;
    Paint wind_paint;
    Paint line;
    Sol sol;

    public WindView(Context context) {
        super(context);
        init();
    }

    public WindView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WindView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        canvas.translate(width / 2, height / 2);
        canvas.save();
        canvas.drawCircle(0, 0, width / 2, mars);
        canvas.drawLine(-width / 2, 0, width / 2, 0, line);
        canvas.drawLine(0, -width / 2, 0, width / 2, line);

        double teta = 360 / 16;

        if(sol.getWind() == null){
        }else{
            ArrayList wind = sol.getWind();
            for (int i = 0; i < wind.size(); i++){
                ArrayList wind_d = (ArrayList) wind.get(i);
                int degree = (int) wind_d.get(0);
                int ct = (int) wind_d.get(1);
                double ct_rap = ct/1000;
                canvas.rotate(degree);

                Path path = new Path();
                path.lineTo((float) -Math.cos(teta/2)*ct,-ct);
                path.lineTo((float) -Math.cos(teta/2)*ct,-ct);
                path.lineTo(0,0);
                path.close();
                canvas.drawPath(path, wind_paint);
            }
        }

    }

    private void init() {
        Resources resources = getResources();
        mars = new Paint();
        mars.setStyle(Paint.Style.FILL);
        mars.setColor(resources.getColor(R.color.copper_red));

        wind_paint = new Paint();
        wind_paint.setStyle(Paint.Style.FILL);
        wind_paint.setStrokeWidth(4);
        wind_paint.setColor(resources.getColor(R.color.celeste_blue));

        line = new Paint();
        line.setStyle(Paint.Style.FILL);
        line.setStrokeWidth(5);
        line.setColor(resources.getColor(R.color.black));
    }

    public void setSol(Sol sol) {
        this.sol = sol;
        invalidate();
    }
}
