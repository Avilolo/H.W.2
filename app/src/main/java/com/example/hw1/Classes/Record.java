package com.example.hw1.Classes;
import com.google.android.gms.maps.model.LatLng;

public class Record {
    private int score;
    private String name;
    private LatLng latLng;

    public Record(int score, String name) {
        this.score = score;
        this.name = name;
        latLng = null;
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

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
