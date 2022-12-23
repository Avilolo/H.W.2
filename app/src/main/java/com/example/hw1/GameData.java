package com.example.hw1;

import android.app.Activity;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;


import com.google.android.material.imageview.ShapeableImageView;
import java.util.Random;


public class GameData {

    private final int SPAWN_DELAY = 3;
    private final int SPEED_DELAY = 1;
    private final Random rand;
    private final Handler handle;
    private Runnable run;
    private final Vibrator vibrator;
    private int meterCounter;
    private int life;
    private FragmentManager fragmentManager;
    private TopThreeFragment leaderboard;


    public GameData(int life, Vibrator vibrator, FragmentManager fragmentManager) {
        rand = new Random();
        handle = new Handler();
        this.life = life;
        this.vibrator = vibrator;
        meterCounter = 0;
        this.fragmentManager = fragmentManager;
    }

    private void spawnCroc(ShapeableImageView crocs[][]) {
        int crocsCounter = 0;
        boolean flyCoin = false;
        for (int i = 0; i < crocs.length; i++) {
            if (rand.nextBoolean() && crocsCounter < 4) {
                crocs[i][0].setImageResource(R.drawable.crocodile);
                crocs[i][0].setTag("croc");
                crocs[i][0].setVisibility(View.VISIBLE);
                crocsCounter++;
            }
            else if ( !flyCoin && rand.nextBoolean()) {    //if rand is false = no croc -> possibility for fly
                crocs[i][0].setImageResource(R.drawable.fly);
                crocs[i][0].setTag("fly");
                crocs[i][0].setVisibility(View.VISIBLE);
                flyCoin = true;
            }
        }

    }

    private void moveCroc(ShapeableImageView crocs[][], ShapeableImageView frogs[],
                          ShapeableImageView hearts[], Context con, TextView meter) {
       checkCollision(crocs, frogs, hearts, con, meter);

        for (int i = 0; i < crocs.length; i++) {
            for (int j = crocs[0].length - 1; j > 0; j--) {

                if (j == crocs[0].length - 1) {
                    crocs[i][j].setVisibility(View.INVISIBLE);
                }                                                           // at index 0 there's the 1st croc
                if (crocs[i][j - 1].getVisibility() == View.VISIBLE) {      //moving crocs only from the 2nd row hence this if is safe)
                    if (crocs[i][j - 1].getTag().equals("fly")) {
                        crocs[i][j].setImageResource(R.drawable.fly);
                        crocs[i][j].setTag("fly");
                    }
                    else {
                        crocs[i][j].setImageResource(R.drawable.crocodile);
                        crocs[i][j].setTag("croc");
                    }
                    crocs[i][j - 1].setVisibility(View.INVISIBLE);
                    crocs[i][j].setVisibility(View.VISIBLE);
                }
            }
        }
        meterCounter++;
        meter.setText(String.valueOf(meterCounter));
    }

    private void checkCollision(ShapeableImageView crocs[][], ShapeableImageView frogs[],
                                   ShapeableImageView hearts[], Context con, TextView meter) {
        MediaPlayer mp = MediaPlayer.create(con, R.raw.wilhelm_scream);
        if ((crocs[GameActivity.getFrogPos()][4].getVisibility() == View.VISIBLE) &&
                (crocs[GameActivity.getFrogPos()][4].getVisibility() == frogs[GameActivity.getFrogPos()].getVisibility())) {
            if (life > 0 && crocs[GameActivity.getFrogPos()][4].getTag().equals("croc")) { // last run will be 1->0 so cant be >=
                vibrator.vibrate(500);
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                life--;
                if (life == 2) {
                    handle.removeCallbacks(run);    //stops game from running
                    inflateLeaderBoardFragment(con);
              //      leaderboard.submitRecord(meterCounter);
                }
                hearts[life].setVisibility(View.INVISIBLE);
                mp.start();
                Toast.makeText(con, "Opsi popsi", Toast.LENGTH_SHORT).show();
            }
            if (crocs[GameActivity.getFrogPos()][4].getTag().equals("fly")) {
                meterCounter += 100;
            }
        }
    }

    public void makeCrocBySeconds(ShapeableImageView crocs[][]) {
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
                                  ShapeableImageView hearts[], Context con, TextView meter) {
        run = new Runnable() {
            @Override
            public void run() {
                handle.postDelayed(this, SPEED_DELAY * 500);
                moveCroc(crocs, frogs, hearts, con, meter);
            }
        };
        handle.postDelayed(run, 1000); // another delay for the first drop after creation
    }

    public void inflateLeaderBoardFragment(Context con) {
        Fragment leaderboard = new TopThreeFragment();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
        Bundle score = new Bundle();
        score.putInt("score", meterCounter);
        leaderboard.setArguments(score);
        fragTransaction.replace(R.id.leaderboard_frame, leaderboard);
        fragTransaction.commit();

    }


}

