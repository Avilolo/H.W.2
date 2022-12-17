package com.example.hw1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

public class GameActivity extends AppCompatActivity {

    FloatingActionButton rightButton;
    FloatingActionButton leftButton;
    ShapeableImageView hearts[];
    ShapeableImageView frogs[];
    ShapeableImageView crocs[][];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        rightButton.setOnClickListener(view -> {});
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
                findViewById(R.id.game_frog_50),
                findViewById(R.id.game_frog_51),
                findViewById(R.id.game_frog_52)
        };
        crocs = new ShapeableImageView[][]{
                { findViewById(R.id.game_croc_10),
                  findViewById(R.id.game_croc_20),
                  findViewById(R.id.game_croc_30),
                  findViewById(R.id.game_croc_40) },
                { findViewById(R.id.game_croc_11),
                  findViewById(R.id.game_croc_21),
                  findViewById(R.id.game_croc_31),
                  findViewById(R.id.game_croc_41) },
                { findViewById(R.id.game_croc_12),
                  findViewById(R.id.game_croc_22),
                  findViewById(R.id.game_croc_32),
                  findViewById(R.id.game_croc_42) }};
    }

    public void onClick(){

    }
}

