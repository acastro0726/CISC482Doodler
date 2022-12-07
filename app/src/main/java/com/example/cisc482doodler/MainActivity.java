package com.example.cisc482doodler;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.Image;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import top.defaults.colorpicker.ColorPickerPopup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clearButton = findViewById(R.id.clearButton);
        Button colorButton = findViewById(R.id.colorButton);
        ImageButton undoButton = (ImageButton)findViewById(R.id.undoButton);
        ImageButton redoButton = (ImageButton)findViewById(R.id.redoButton);

        DoodleView Canvas = findViewById(R.id.doodleView);

        SeekBar brushBar = findViewById(R.id.brushBar);
        brushBar.setMax(30);


        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Canvas.clearButtonPressed();
            }
        });

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(MainActivity.this).initialColor(Canvas.getCurrColor())
                        .enableBrightness(true)
                        .enableAlpha(true)
                        .okTitle("Done")
                        .cancelTitle("Quit")
                        .showIndicator(true)
                        .showValue(false)
                        .build().show(v, new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void onColorPicked(int color) {
                                Canvas.changeColor(color);
                            }
                        });
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Canvas.undoButtonPressed();
            }
        });

        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Canvas.redoButtonPressed();
            }
        });


        brushBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Canvas.changeWidth(seekBar.getProgress());
            }
        });
    }




}