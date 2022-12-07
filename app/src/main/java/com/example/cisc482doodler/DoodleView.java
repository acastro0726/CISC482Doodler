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

    private Paint paintBrush = new Paint();
    private Path path = new Path();

    private ArrayList<Path> paths = new ArrayList<>();
    private ArrayList<Paint> paintBrushes = new ArrayList<>();


    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBrush.setAntiAlias(true);
        paintBrush.setColor(Color.RED);
        paintBrush.setStyle(Paint.Style.STROKE);
        paintBrush.setStrokeJoin(Paint.Join.ROUND);
        paintBrush.setStrokeWidth(8f);

        paths.add(path);
        paintBrushes.add(paintBrush);
    }

    public DoodleView(Context context) {
        this(context, null);
    }

    public void changeColor(int color) {
        float currWidth = paintBrush.getStrokeWidth();
        paintBrush = new Paint();
        paintBrush.setAntiAlias(true);
        paintBrush.setStyle(Paint.Style.STROKE);
        paintBrush.setStrokeJoin(Paint.Join.ROUND);
        paintBrush.setStrokeWidth(currWidth);
        paintBrush.setColor(color);
        paintBrushes.add(paintBrush);
        path = new Path();
        paths.add(path);
        invalidate();
    }

    public void changeWidth(int width) {
        int currColor = paintBrush.getColor();
        paintBrush = new Paint();
        paintBrush.setAntiAlias(true);
        paintBrush.setStyle(Paint.Style.STROKE);
        paintBrush.setStrokeJoin(Paint.Join.ROUND);
        paintBrush.setColor(currColor);
        int newWidth = 8 + width;
        paintBrush.setStrokeWidth(newWidth);
        paintBrushes.add(paintBrush);
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
        paintBrushes.clear();
        paths.add(path);
        paintBrushes.add(paintBrush);
        invalidate();
    }

    public int getCurrColor() {
        return paintBrush.getColor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < paths.size(); i++) {
            path = paths.get(i);
            paintBrush = paintBrushes.get(i);
            canvas.drawPath(path, paintBrush);
        }
    }
}
