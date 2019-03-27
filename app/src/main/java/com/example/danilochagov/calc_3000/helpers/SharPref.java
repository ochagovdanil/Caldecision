package com.example.danilochagov.calc_3000.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharPref {

    private SharedPreferences mSharedPreferences;

    public SharPref(Context context) {
        mSharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public int getCurrentTheme() {
        return mSharedPreferences.getInt("theme", 0);
    }

    public void setTheme(int theme) {
        mSharedPreferences.edit().putInt("theme", theme).apply();
    }

}
