package com.example.hw1;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;


public class GameActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton rightButton;
    private ExtendedFloatingActionButton leftButton;
    private ShapeableImageView hearts[];
    private ShapeableImageView frogs[];
    private ShapeableImageView crocs[][];
    private Vibrator vibrator;
    private static int frogPos = 1;

    GameData gameData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gameData = new GameData(hearts.length, vibrator);

        rightButton.setOnClickListener(view -> {
            rightClick();
        });
        leftButton.setOnClickListener(view -> {
            leftClick();
        });

        gameData.makeCrocBySeconds(crocs, frogs, frogPos);
        gameData.moveCrocBySeconds(crocs, frogs, hearts, this.getApplicationContext());
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
                {findViewById(R.id.game_croc_10),
                        findViewById(R.id.game_croc_20),
                        findViewById(R.id.game_croc_30),
                        findViewById(R.id.game_croc_40)},
                {findViewById(R.id.game_croc_11),
                        findViewById(R.id.game_croc_21),
                        findViewById(R.id.game_croc_31),
                        findViewById(R.id.game_croc_41)},
                {findViewById(R.id.game_croc_12),
                        findViewById(R.id.game_croc_22),
                        findViewById(R.id.game_croc_32),
                        findViewById(R.id.game_croc_42)}};
    }

    public void rightClick() {
        if (frogPos != 2) {
            frogs[frogPos].setVisibility(View.INVISIBLE);
            frogPos++;
            frogs[frogPos].setVisibility(View.VISIBLE);
        }
    }

    public void leftClick() {
        if (frogPos != 0) {
            frogs[frogPos].setVisibility(View.INVISIBLE);
            frogPos--;
            frogs[frogPos].setVisibility(View.VISIBLE);
        }
    }

    public static int getFrogPos() {
        return frogPos;
    }
}





