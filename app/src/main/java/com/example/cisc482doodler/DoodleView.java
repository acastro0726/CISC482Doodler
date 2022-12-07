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

    private static class UserMovement {

        public Paint paintBrush;
        public Path path;

        public UserMovement(Path path, Paint paintBrush) {
            this.paintBrush = paintBrush;
            this.path = path;
        }
    }

    private Paint paintBrush = new Paint();
    private Path path = new Path();

    private ArrayList<UserMovement> movements = new ArrayList<>();
    private ArrayList<UserMovement> undoneMovements = new ArrayList<>();


    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBrush.setAntiAlias(true);
        paintBrush.setColor(Color.BLACK);
        paintBrush.setStyle(Paint.Style.STROKE);
        paintBrush.setStrokeJoin(Paint.Join.ROUND);
        paintBrush.setStrokeWidth(8f);

    }

    public DoodleView(Context context) {
        this(context, null);
    }

    public void changeColor(int color) {
        float currentWidth = paintBrush.getStrokeWidth();
        paintBrush = new Paint();
        paintBrush.setAntiAlias(true);
        paintBrush.setStyle(Paint.Style.STROKE);
        paintBrush.setStrokeJoin(Paint.Join.ROUND);
        paintBrush.setStrokeWidth(currentWidth);
        paintBrush.setColor(color);
        path = new Path();
        invalidate();
    }

    public void changeWidth(int width) {
        int currentColor = paintBrush.getColor();
        paintBrush = new Paint();
        paintBrush.setAntiAlias(true);
        paintBrush.setStyle(Paint.Style.STROKE);
        paintBrush.setStrokeJoin(Paint.Join.ROUND);
        paintBrush.setColor(currentColor);
        int newWidth = 8 + width;
        paintBrush.setStrokeWidth(newWidth);
        path = new Path();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path.moveTo(pointX, pointY);
            movements.add(new UserMovement(path, paintBrush));
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (undoneMovements.size() > 0) {
                undoneMovements.clear();
            }
            path.lineTo(pointX, pointY);
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            movements.remove(movements.size() - 1);
            movements.add(new UserMovement(path, paintBrush));
            path = new Path();
        }
        postInvalidate();
        return false;
    }

    public void clearButtonPressed() {
        path = new Path();
        movements.clear();
        undoneMovements.clear();
        invalidate();
    }

    public void undoButtonPressed() {
        if (movements.size() > 0) {
            UserMovement undoneMovement = movements.get(movements.size() - 1);
            undoneMovements.add(undoneMovement);
            movements.remove(movements.size() - 1);
            path = new Path();
            invalidate();
        }
    }

    public void redoButtonPressed() {
        if (undoneMovements.size() > 0) {
            movements.add(undoneMovements.get(undoneMovements.size() - 1));
            undoneMovements.remove(undoneMovements.size() - 1);
            invalidate();
        }
    }

    public int getCurrColor() {
        return paintBrush.getColor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (UserMovement movement : movements) {
            canvas.drawPath(movement.path, movement.paintBrush);
        }
    }
}
