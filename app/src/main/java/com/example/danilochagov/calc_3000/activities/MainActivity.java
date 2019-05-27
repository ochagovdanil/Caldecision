package com.example.danilochagov.calc_3000.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danilochagov.calc_3000.R;
import com.example.danilochagov.calc_3000.fragments.ListDialogFragment;
import com.example.danilochagov.calc_3000.helpers.SharPref;
import com.example.danilochagov.calc_3000.logic.ExpressionsCalculator;
import com.example.danilochagov.calc_3000.models.EThemes;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final int GREEN_THEME = EThemes.GREEN.getTheme();
    private static final int ORANGE_THEME = EThemes.ORANGE.getTheme();
    private static final int BLUE_THEME = EThemes.BLUE.getTheme();

    private final static String ADDITION = "+";
    private final static String MINUS = "-";
    private final static String DIVIDE = "/";
    private final static String MULTIPLY = "x";

    private static String sCurrentOperator = "0";
    private TextView mMain_display, mOld_display;
    private ExpressionsCalculator mExpressionsCalculator;
    private DecimalFormat mDecimalFormat;
    private SharPref mSharPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMain_display = findViewById(R.id.text_main_display);
        mOld_display = findViewById(R.id.text_old_display);

        mExpressionsCalculator = new ExpressionsCalculator();
        mDecimalFormat = new DecimalFormat("#.######");

        ImageView imageView = findViewById(R.id.image_settings);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListOfThemes();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onAddNumber(View view) {
        Button button = (Button) view;
        mMain_display.append(button.getText().toString());
    }

    @SuppressLint("SetTextI18n")
    public void onAddOperator(View view) {
        Button button = (Button) view;
        String operator = button.getText().toString();
        String dis = mMain_display.getText().toString();

        try {
            if (sCurrentOperator.equals("0")) {
                // add '-' or '+' in the start of the expression or user will get the error
                if (dis.equals("") && (operator.equals(MINUS) || operator.equals(ADDITION))) {
                    mMain_display.append(operator);
                    sCurrentOperator = "0";

                    return;
                }

                mMain_display.append(operator);
                sCurrentOperator = operator;
            } else {
                // replace the operator after the first number
                if (dis.indexOf(sCurrentOperator) == (dis.length() - 1)) {
                    dis = dis.substring(0, dis.length() - 1);
                    dis += operator;

                    mMain_display.setText(dis);
                    sCurrentOperator = operator;

                    return;
                }

                // make result of two numbers
                double[] numbers = mExpressionsCalculator.getTwoNumbers(
                        mMain_display.getText().toString(),
                        sCurrentOperator);

                switch (sCurrentOperator) {
                    case ADDITION:
                        mMain_display.setText(
                                mDecimalFormat.format(
                                        mExpressionsCalculator.addition(numbers[0],
                                        numbers[1])) + operator);
                        break;

                    case MINUS:
                        mMain_display.setText(
                                mDecimalFormat.format(
                                        mExpressionsCalculator.minus(numbers[0],
                                        numbers[1])) + operator);
                        break;

                    case MULTIPLY:
                        mMain_display.setText(
                                mDecimalFormat.format(
                                        mExpressionsCalculator.multiply(numbers[0],
                                        numbers[1])) + operator);
                        break;

                    case DIVIDE:
                        mMain_display.setText(
                                mDecimalFormat.format(
                                        mExpressionsCalculator.divide(numbers[0],
                                        numbers[1])) + operator);
                }

                mOld_display.setText(numbers[0] + sCurrentOperator + numbers[1]);

                sCurrentOperator = operator;
            }
        } catch (Exception e) {
            showError();
        }
    }

    public void onNumberPI(View view) {
        mMain_display.append(mDecimalFormat.format(mExpressionsCalculator.getNumberPI()));
    }

    public void onNumberE(View view) {
        mMain_display.append(mDecimalFormat.format(mExpressionsCalculator.getNumberE()));
    }

    @SuppressLint("SetTextI18n")
    public void onSin(View view) {
        String dis = mMain_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // SINUS from the first number
                if (sCurrentOperator.equals("0")) {
                    mOld_display.setText("SIN(" + dis + ")");
                    mMain_display.setText(
                            mDecimalFormat.format(
                                    mExpressionsCalculator.sinus(Double.parseDouble(dis))));
                } else {
                    // SINUS from the second number
                    double[] numbers = mExpressionsCalculator.getTwoNumbers(dis, sCurrentOperator);
                    String new_expression =
                            mDecimalFormat.format(numbers[0]) +
                            sCurrentOperator +
                            mDecimalFormat.format(mExpressionsCalculator.sinus(numbers[1]));

                    mMain_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onCos(View view) {
        String dis = mMain_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // COSINUS from the first number
                if (sCurrentOperator.equals("0")) {
                    mOld_display.setText("COS(" + dis + ")");
                    mMain_display.setText(
                            mDecimalFormat.format(
                                    mExpressionsCalculator.cosinus(Double.parseDouble(dis))));
                } else {
                    // COSINUS from the second number
                    double[] numbers = mExpressionsCalculator.getTwoNumbers(dis, sCurrentOperator);
                    String new_expression =
                            mDecimalFormat.format(numbers[0]) +
                            sCurrentOperator +
                            mDecimalFormat.format(mExpressionsCalculator.cosinus(numbers[1]));

                    mMain_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onTan(View view) {
        String dis = mMain_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // TANGENT from the first number
                if (sCurrentOperator.equals("0")) {
                    mOld_display.setText("TAN(" + dis + ")");
                    mMain_display.setText(
                            mDecimalFormat.format(
                                    mExpressionsCalculator.tangent(Double.parseDouble(dis))));
                } else {
                    // TANGENT from the second number
                    double[] numbers = mExpressionsCalculator.getTwoNumbers(dis, sCurrentOperator);
                    String new_expression =
                            mDecimalFormat.format(numbers[0]) +
                            sCurrentOperator +
                            mDecimalFormat.format(mExpressionsCalculator.tangent(numbers[1]));

                    mMain_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onLog(View view) {
        String dis = mMain_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // LOGARITHM from the first number
                if (sCurrentOperator.equals("0")) {
                    mOld_display.setText("LOG(" + dis + ")");
                    mMain_display.setText(
                            mDecimalFormat.format(
                                    mExpressionsCalculator.logarithm(Double.parseDouble(dis))));
                } else {
                    // LOGARITHM from the second number
                    double[] numbers = mExpressionsCalculator.getTwoNumbers(dis, sCurrentOperator);
                    String new_expression =
                            mDecimalFormat.format(numbers[0]) +
                            sCurrentOperator +
                            mDecimalFormat.format(mExpressionsCalculator.logarithm(numbers[1]));

                    mMain_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onSqrt(View view) {
        String dis = mMain_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // SQUARE from the first number
                if (sCurrentOperator.equals("0")) {
                    mOld_display.setText("√" + dis);
                    mMain_display.setText(
                            mDecimalFormat.format(
                                    mExpressionsCalculator.square(Double.parseDouble(dis))));
                } else {
                    // SQUARE from the second number
                    double[] numbers = mExpressionsCalculator.getTwoNumbers(dis, sCurrentOperator);
                    String new_expression =
                            mDecimalFormat.format(numbers[0]) +
                            sCurrentOperator +
                            mDecimalFormat.format(mExpressionsCalculator.square(numbers[1]));

                    mMain_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onPercent(View view) {
        String dis = mMain_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // PERCENT from the first number
                if (sCurrentOperator.equals("0")) {
                    mOld_display.setText("%(" + dis + ")");
                    mMain_display.setText(
                            mDecimalFormat.format(
                                    mExpressionsCalculator.percent(Double.parseDouble(dis))));
                } else {
                    // PERCENT from the second number
                    double[] numbers = mExpressionsCalculator.getTwoNumbers(dis, sCurrentOperator);
                    String new_expression =
                            mDecimalFormat.format(numbers[0]) +
                            sCurrentOperator +
                            mDecimalFormat.format(mExpressionsCalculator.percent(numbers[1]));

                    mMain_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    public void onPow(View view) {
        mMain_display.append("^");
    }

    @SuppressLint("SetTextI18n")
    public void onEqual(View view) {
        String dis = mMain_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // if the first number has POWER statement (^)
                if (dis.contains("^") && sCurrentOperator.equals("0")) {
                    double[] number = mExpressionsCalculator.getTwoNumbersPower(dis);

                    mOld_display.setText(dis);
                    mMain_display.setText(mDecimalFormat.format(
                            mExpressionsCalculator.pow(number[0],
                            number[1])));

                    return;
                }

                double[] numbers = mExpressionsCalculator.getTwoNumbers(dis, sCurrentOperator);

                switch (sCurrentOperator) {
                    case ADDITION:
                        mMain_display.setText(
                                mDecimalFormat.format(
                                        mExpressionsCalculator.addition(numbers[0],
                                        numbers[1])));
                        break;

                    case MINUS:
                        mMain_display.setText(
                                mDecimalFormat.format(
                                        mExpressionsCalculator.minus(numbers[0],
                                        numbers[1])));
                        break;

                    case MULTIPLY:
                        mMain_display.setText(
                                mDecimalFormat.format(
                                        mExpressionsCalculator.multiply(numbers[0],
                                        numbers[1])));
                        break;

                    case DIVIDE:
                        mMain_display.setText(
                                mDecimalFormat.format(
                                        mExpressionsCalculator.divide(numbers[0],
                                        numbers[1])));
                }

                mOld_display.setText(numbers[0] + sCurrentOperator + numbers[1]);
                sCurrentOperator = "0";
            } catch (Exception e) {
                showError();
            }
        }
    }

    public void onDelAll(View view) {
        String mainDis = mMain_display.getText().toString();
        String oldDis = mOld_display.getText().toString();

        if (!mainDis.equals("") || !oldDis.equals("")) deleteAllWithAnimation();
    }

    public void onDel(View view) {
        String dis = mMain_display.getText().toString();

        if (!dis.equals("")) {
            mMain_display.setText(dis.substring(0, dis.length() - 1));

            // if the last symbol is an operator
            if (dis.indexOf(sCurrentOperator) == (dis.length() - 1)) {
                sCurrentOperator = "0";
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void showError() {
        showErrorWithAnimation();
    }

    private void deleteAllWithAnimation() {
        if (Build.VERSION.SDK_INT >= 21) {
            View myView = findViewById(R.id.layout_display);
            final View viewDel = findViewById(R.id.view_delete_all);

            int x = myView.getRight();
            int y = myView.getBottom();

            int finalRadius = (int) Math.hypot(myView.getWidth(), myView.getHeight());

            Animator anim = ViewAnimationUtils.createCircularReveal(
                    myView,
                    x,
                    y,
                    0,
                    finalRadius
            );
            anim.setDuration(500);
            viewDel.setVisibility(View.VISIBLE);
            anim.start();

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    viewDel.setVisibility(View.GONE);

                    mMain_display.setText("");
                    mOld_display.setText("");

                    sCurrentOperator = "0";
                }
            });
        } else {
            mMain_display.setText("");
            mOld_display.setText("");

            sCurrentOperator = "0";
        }
    }

    @SuppressLint("SetTextI18n")
    private void showErrorWithAnimation() {
        if (Build.VERSION.SDK_INT >= 21) {
            View myView = findViewById(R.id.layout_display);
            final View viewDel = findViewById(R.id.view_error);

            int x = myView.getRight();
            int y = myView.getBottom();

            int finalRadius = (int) Math.hypot(myView.getWidth(), myView.getHeight());

            Animator anim = ViewAnimationUtils.createCircularReveal(
                    myView,
                    x,
                    y,
                    0,
                    finalRadius
            );
            anim.setDuration(500);
            viewDel.setVisibility(View.VISIBLE);
            anim.start();

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    viewDel.setVisibility(View.GONE);

                    mOld_display.setText("");
                    mMain_display.setText("Error");
                }
            });
        } else {
            mOld_display.setText("");
            mMain_display.setText("Error");
        }
    }

    private void initTheme() {
        mSharPref = new SharPref(MainActivity.this);
        int theme = mSharPref.getCurrentTheme();

        if (theme == GREEN_THEME) {
            setTheme(R.style.GreenTheme);
            return;
        }
        if (theme == ORANGE_THEME) {
            setTheme(R.style.OrangeTheme);
            return;
        }
        if (theme == BLUE_THEME) {
            setTheme(R.style.BlueTheme);
        }
    }

    private void showListOfThemes() {
        Bundle bundle = new Bundle();
        bundle.putInt("theme", mSharPref.getCurrentTheme());

        ListDialogFragment dialog = new ListDialogFragment();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "ListDialogFragment");

        dialog.setOnSelectThemeListener(new ListDialogFragment.SelectThemeListener() {
            @Override
            public void setSelectThemeListener(int theme) {
                setAppTheme(theme);
            }
        });
    }

    private void setAppTheme(int theme) {
        mSharPref.setTheme(theme);
        sCurrentOperator = "0";

        startActivity(new Intent(MainActivity.this, MainActivity.class));
        finish();
    }

}