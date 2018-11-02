package com.example.danilochagov.calc_3000.Main;

import android.view.View;

public interface ICalculator {
    String makeResult();

    void onAddNumber (View v);
    void onAddOperator (View v);
    void onDel (View v);
    void onDelAll (View v);
    void onEqual (View v);
    void onSqrt (View v);
    void onPercent (View v);
    void onNumberPI (View v);
    void onSin (View v);
    void onPow (View v);
    void onCos (View v);
    void onTan (View v);
    void onLog (View v);
    void onE (View v);
}
