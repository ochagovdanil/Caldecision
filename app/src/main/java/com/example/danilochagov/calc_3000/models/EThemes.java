package com.example.danilochagov.calc_3000.models;

public enum  EThemes {

    GREEN(0), ORANGE(1), BLUE(2);

    private int mTheme;

    EThemes(int theme) {
        mTheme = theme;
    }

    public int getTheme() {
        return mTheme;
    }

}
