package com.example.hw1;

import android.location.Location;

public class Record {
    private int score;
    private String name;
    private Location location;

    public Record(int score, String name) {
        this.score = score;
        this.name = name;
        location = null;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
