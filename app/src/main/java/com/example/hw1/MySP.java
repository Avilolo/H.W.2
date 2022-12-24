package com.example.hw1;

import android.content.Context;
import android.content.SharedPreferences;

public class MySP {

    private final String DB_FILE = "DB_FILE";
    private SharedPreferences sp;
    private static MySP instance;

    public MySP(Context con) {
        sp = con.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public static void init(Context con) {
        if (instance == null)
            instance = new MySP(con);
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public static MySP getInstance() {
        return instance;
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, 0);
    }

    public void putString(String key, String str) { sp.edit().putString(key, str).apply(); }

    public String getString(String key, String defStr) {
        return sp.getString(key, defStr);
    }
}
