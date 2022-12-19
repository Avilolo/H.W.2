package com.example.hw1;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;


public class GameData {

    private final int SPAWN_DELAY = 3;
    private final int SPEED_DELAY = 1;
    private Random rand;
    private Handler handle;
    private Runnable run;
    private Vibrator vibrator;
    private int life;

    public GameData(int life, Vibrator vibrator) {
        rand = new Random();
        handle = new Handler();
        this.life = life;
        this.vibrator = vibrator;
    }

    private void spawnCroc(ShapeableImageView crocs[][]) {
        for (int i = 0; i < crocs.length; i++) {
            if (rand.nextBoolean()) {
                crocs[i][0].setVisibility(View.VISIBLE);
            }
        }
    }

    private void moveCroc(ShapeableImageView crocs[][], ShapeableImageView frogs[],
                          ShapeableImageView hearts[], Context con) {
        if ((crocs[GameActivity.getFrogPos()][4].getVisibility() == View.VISIBLE) &&
                (crocs[GameActivity.getFrogPos()][4].getVisibility() == frogs[GameActivity.getFrogPos()].getVisibility())) {
            if (life > 0) { // last run will be 1->0 so cant be >=
                vibrator.vibrate(500);
                //vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                life--;
                hearts[life].setVisibility(View.INVISIBLE);
                Toast.makeText(con, "Opsi popsi", Toast.LENGTH_SHORT).show();
            }
        }

        for (int i = 0; i < crocs.length; i++) {
            for (int j = crocs[0].length - 1; j > 0; j--) {

                if (j == crocs[0].length - 1) {
                    crocs[i][j].setVisibility(View.INVISIBLE);
                }                                                           // at index 0 there's the 1st croc
                if (crocs[i][j - 1].getVisibility() == View.VISIBLE) {      //moving crocs only from the 2nd row hence this if is safe)
                    crocs[i][j - 1].setVisibility(View.INVISIBLE);
                    crocs[i][j].setVisibility(View.VISIBLE);
                }
            }
        }

    }

    public void makeCrocBySeconds(ShapeableImageView crocs[][], ShapeableImageView frogs[], int frogPos) {
        run = new Runnable() {
            @Override
            public void run() {
                handle.postDelayed(this, SPAWN_DELAY * 1000);
                spawnCroc(crocs);
            }
        };
        handle.post(run);
    }

    public void moveCrocBySeconds(ShapeableImageView crocs[][], ShapeableImageView frogs[],
                                  ShapeableImageView hearts[], Context con) {
        run = new Runnable() {
            @Override
            public void run() {
                handle.postDelayed(this, SPEED_DELAY * 1000);
                moveCroc(crocs, frogs, hearts, con);
            }
        };
        handle.postDelayed(run, 1000); // another delay for the first drop after creation
    }
}

