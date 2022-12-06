package com.example.cisc482doodler;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import top.defaults.colorpicker.ColorPickerPopup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clearButton = findViewById(R.id.clearButton);
        Button colorButton = findViewById(R.id.colorButton);

        DoodleView doodleCanvas = findViewById(R.id.doodleView);

        SeekBar brushBar = findViewById(R.id.brushBar);
        brushBar.setMax(30);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleCanvas.clearButtonPressed();
            }
        });

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(MainActivity.this).initialColor(doodleCanvas.getCurrColor())
                        .enableBrightness(true)
                        .enableAlpha(true)
                        .okTitle("Finish")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(false)
                        .build().show(v, new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void onColorPicked(int color) {
                                doodleCanvas.changeColor(color);
                            }
                        });
            }
        });

        brushBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                doodleCanvas.changeWidth(seekBar.getProgress());
            }
        });
    }

}