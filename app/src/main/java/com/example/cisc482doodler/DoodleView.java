package com.example.cisc482doodler;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DoodleView extends View {

    private Paint brush = new Paint();
    private Path path = new Path();

    private ArrayList<Path> paths = new ArrayList<>();
    private ArrayList<Paint> brushes = new ArrayList<>();


    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        brush.setAntiAlias(true);
        brush.setColor(Color.RED);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);

        paths.add(path);
        brushes.add(brush);
    }

    public DoodleView(Context context) {
        this(context, null);
    }

    public void changeColor(int color) {
        float currWidth = brush.getStrokeWidth();
        brush = new Paint();
        brush.setAntiAlias(true);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(currWidth);
        brush.setColor(color);
        brushes.add(brush);
        path = new Path();
        paths.add(path);
        invalidate();
    }

    public void changeWidth(int width) {
        int currColor = brush.getColor();
        brush = new Paint();
        brush.setAntiAlias(true);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setColor(currColor);
        int newWidth = 8 + width;
        brush.setStrokeWidth(newWidth);
        brushes.add(brush);
        path = new Path();
        paths.add(path);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path.moveTo(pointX, pointY);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.lineTo(pointX, pointY);
        }
        postInvalidate();
        return false;
    }

    public void clearButtonPressed() {
        path = new Path();
        paths.clear();
        brushes.clear();
        paths.add(path);
        brushes.add(brush);
        invalidate();
    }

    public int getCurrColor() {
        return brush.getColor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < paths.size(); i++) {
            path = paths.get(i);
            brush = brushes.get(i);
            canvas.drawPath(path, brush);
        }
    }
}
