package com.example.hw1;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;


public class GameActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton rightButton;
    private ExtendedFloatingActionButton leftButton;
    private Sensor sensor;
    private SensorManager sensorManager;
    private SensorEventListener sensorEL;
    private long timestemp = 0;
    private ShapeableImageView hearts[];
    private ShapeableImageView frogs[];
    private ShapeableImageView crocs[][];
    private Vibrator vibrator;
    private TextView meter;
    private static int frogPos = 2;

    GameData gameData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gameData = new GameData(hearts.length, vibrator);

        if (getIntent().getExtras().getBoolean("gameType")) { // if its true we need a sensor
            //    if (sensor != null) {
            rightButton.setVisibility(View.INVISIBLE);
            leftButton.setVisibility(View.INVISIBLE);
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(sensorEL, sensor, sensorManager.SENSOR_DELAY_NORMAL);
            sensorEL = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float x = event.values[0];
                    moveFrog(x);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };
            //   };
        }
        rightButton.setOnClickListener(view -> {
            moveFrogRight();
        });
        leftButton.setOnClickListener(view -> {
            moveFrogLeft();
        });

        gameData.makeCrocBySeconds(crocs);
        gameData.moveCrocBySeconds(crocs, frogs, hearts, this.getApplicationContext(), meter);
    }

    public void findViews() {
        rightButton = findViewById(R.id.game_BTN_right);
        leftButton = findViewById(R.id.game_BTN_left);

        hearts = new ShapeableImageView[]{
                findViewById(R.id.game_heart1),
                findViewById(R.id.game_heart2),
                findViewById(R.id.game_heart3)
        };
        frogs = new ShapeableImageView[]{
                findViewById(R.id.game_frog_60),
                findViewById(R.id.game_frog_61),
                findViewById(R.id.game_frog_62),    //added 2 more frog positions
                findViewById(R.id.game_frog_63),
                findViewById(R.id.game_frog_64)
        };
        crocs = new ShapeableImageView[][]{
                {findViewById(R.id.game_croc_10),
                        findViewById(R.id.game_croc_20),
                        findViewById(R.id.game_croc_30),
                        findViewById(R.id.game_croc_40),
                        findViewById(R.id.game_croc_50)},
                {findViewById(R.id.game_croc_11),
                        findViewById(R.id.game_croc_21),
                        findViewById(R.id.game_croc_31),
                        findViewById(R.id.game_croc_41),
                        findViewById(R.id.game_croc_51)},
                {findViewById(R.id.game_croc_12),       // added 2 more lanes
                        findViewById(R.id.game_croc_22),
                        findViewById(R.id.game_croc_32),
                        findViewById(R.id.game_croc_42),
                        findViewById(R.id.game_croc_52)},
                {findViewById(R.id.game_croc_13),
                        findViewById(R.id.game_croc_23),
                        findViewById(R.id.game_croc_33),
                        findViewById(R.id.game_croc_43),
                        findViewById(R.id.game_croc_53)},
                {findViewById(R.id.game_croc_14),
                        findViewById(R.id.game_croc_24),
                        findViewById(R.id.game_croc_34),
                        findViewById(R.id.game_croc_44),
                        findViewById(R.id.game_croc_54)}};
        meter = findViewById(R.id.meter_counter);
    }

    public void moveFrog(float x) {
        if (System.currentTimeMillis() - timestemp > 50) {
            timestemp = System.currentTimeMillis();
            if (x > 6.0) {
                moveFrogRight();
            } else if (x < -6.0) {
                moveFrogLeft();
            }
        }
    }

    public void moveFrogRight() {
        if (frogPos != 4) { // now 4 because we have more lanes = longer frog array
            frogs[frogPos].setVisibility(View.INVISIBLE);
            frogPos++;
            frogs[frogPos].setVisibility(View.VISIBLE);
        }
    }

    public void moveFrogLeft() {
        if (frogPos != 0) {
            frogs[frogPos].setVisibility(View.INVISIBLE);
            frogPos--;
            frogs[frogPos].setVisibility(View.VISIBLE);
        }
    }

    public static int getFrogPos() {
        return frogPos;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEL, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEL);
    }
}





