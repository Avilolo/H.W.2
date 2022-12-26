package com.example.hw1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hw1.Activities.GameActivity;
import com.example.hw1.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class HomeScreenActivity extends AppCompatActivity {
    private boolean gameType;   // false = arrows || true == sensors
    private ExtendedFloatingActionButton arrowBtn;
    private ExtendedFloatingActionButton sensorBtn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        intent  = new Intent(this, GameActivity.class);

        findViews();
        arrowBtn.setOnClickListener(view -> startGameActivity(false));

        sensorBtn.setOnClickListener(view -> startGameActivity(true));
    }

    public void findViews() {
        arrowBtn = findViewById(R.id.arrow_btn);
        sensorBtn = findViewById(R.id.sensor_btn);
    }

    public void startGameActivity(boolean type) {
        gameType = type;
        intent.putExtra("gameType", gameType);
        startActivity(intent);
    }
}