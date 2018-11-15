package com.example.danilochagov.calc_3000.Main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.danilochagov.calc_3000.R;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private final static String ADDITION = "+";
    private final static String MINUS = "-";
    private final static String DIVIDE = "/";
    private final static String MULTIPLY = "x";
    private static String CURRENT_OPERATOR = "0";

    private TextView main_display, old_display;

    private ExpressionsCalculator expressionsCalculator;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_display = findViewById(R.id.main_display);
        old_display = findViewById(R.id.old_display);

        expressionsCalculator = new ExpressionsCalculator();
        decimalFormat = new DecimalFormat("#.######");
    }

    public void onAddNumber(View view) {
        Button button = (Button) view;
        main_display.append(button.getText().toString());
    }

    @SuppressLint("SetTextI18n")
    public void onAddOperator(View view) {
        Button button = (Button) view;
        String operator = button.getText().toString();
        String dis = main_display.getText().toString();

        try {
            if (CURRENT_OPERATOR.equals("0")) {
                // add '-' or '+' in the start of the expression or user will get the error
                if (dis.equals("") && (operator.equals(MINUS) || operator.equals(ADDITION))) {
                    main_display.append(operator);

                    CURRENT_OPERATOR = "0";
                    return;
                }

                main_display.append(operator);
                CURRENT_OPERATOR = operator;
            } else {
                // replace the operator after the first number
                if (dis.indexOf(CURRENT_OPERATOR) == (dis.length() - 1)) {
                    dis = dis.substring(0, dis.length() - 1);
                    dis += operator;

                    main_display.setText(dis);

                    CURRENT_OPERATOR = operator;
                    return;
                }

                // make result of two numbers
                double[] numbers = expressionsCalculator.getTwoNumbers(main_display.getText().toString(), CURRENT_OPERATOR);

                switch (CURRENT_OPERATOR) {
                    case ADDITION:
                        main_display.setText(decimalFormat.format(expressionsCalculator.addition(numbers[0], numbers[1])) + operator);
                        break;

                    case MINUS:
                        main_display.setText(decimalFormat.format(expressionsCalculator.minus(numbers[0], numbers[1])) + operator);
                        break;

                    case MULTIPLY:
                        main_display.setText(decimalFormat.format(expressionsCalculator.multiply(numbers[0], numbers[1])) + operator);
                        break;

                    case DIVIDE:
                        main_display.setText(decimalFormat.format(expressionsCalculator.divide(numbers[0], numbers[1])) + operator);
                }

                old_display.setText(numbers[0] + CURRENT_OPERATOR + numbers[1]);

                CURRENT_OPERATOR = operator;
            }
        } catch (Exception e) {
            showError();
        }
    }

    public void onNumberPI(View view) {
        main_display.append(decimalFormat.format(expressionsCalculator.getNumberPI()));
    }

    public void onNumberE(View view) {
        main_display.append(decimalFormat.format(expressionsCalculator.getNumberE()));
    }

    @SuppressLint("SetTextI18n")
    public void onSin(View view) {
        String dis = main_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // SINUS from the first number
                if (CURRENT_OPERATOR.equals("0")) {
                    old_display.setText("SIN(" + dis + ")");
                    main_display.setText(decimalFormat.format(expressionsCalculator.sinus(Double.parseDouble(dis))));
                } else {
                    // SINUS from the second number
                    double[] numbers = expressionsCalculator.getTwoNumbers(dis, CURRENT_OPERATOR);
                    String new_expression = decimalFormat.format(numbers[0]) + CURRENT_OPERATOR + decimalFormat.format(expressionsCalculator.sinus(numbers[1]));

                    main_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onCos(View view) {
        String dis = main_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // COSINUS from the first number
                if (CURRENT_OPERATOR.equals("0")) {
                    old_display.setText("COS(" + dis + ")");
                    main_display.setText(decimalFormat.format(expressionsCalculator.cosinus(Double.parseDouble(dis))));
                } else {
                    // COSINUS from the second number
                    double[] numbers = expressionsCalculator.getTwoNumbers(dis, CURRENT_OPERATOR);
                    String new_expression = decimalFormat.format(numbers[0]) + CURRENT_OPERATOR + decimalFormat.format(expressionsCalculator.cosinus(numbers[1]));

                    main_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onTan(View view) {
        String dis = main_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // TANGENT from the first number
                if (CURRENT_OPERATOR.equals("0")) {
                    old_display.setText("TAN(" + dis + ")");
                    main_display.setText(decimalFormat.format(expressionsCalculator.tangent(Double.parseDouble(dis))));
                } else {
                    // TANGENT from the second number
                    double[] numbers = expressionsCalculator.getTwoNumbers(dis, CURRENT_OPERATOR);
                    String new_expression = decimalFormat.format(numbers[0]) + CURRENT_OPERATOR + decimalFormat.format(expressionsCalculator.tangent(numbers[1]));

                    main_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onLog(View view) {
        String dis = main_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // LOGARITHM from the first number
                if (CURRENT_OPERATOR.equals("0")) {
                    old_display.setText("LOG(" + dis + ")");
                    main_display.setText(decimalFormat.format(expressionsCalculator.logarithm(Double.parseDouble(dis))));
                } else {
                    // LOGARITHM from the second number
                    double[] numbers = expressionsCalculator.getTwoNumbers(dis, CURRENT_OPERATOR);
                    String new_expression = decimalFormat.format(numbers[0]) + CURRENT_OPERATOR + decimalFormat.format(expressionsCalculator.logarithm(numbers[1]));

                    main_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onSqrt(View view) {
        String dis = main_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // SQUARE from the first number
                if (CURRENT_OPERATOR.equals("0")) {
                    old_display.setText("√" + dis);
                    main_display.setText(decimalFormat.format(expressionsCalculator.square(Double.parseDouble(dis))));
                } else {
                    // SQUARE from the second number
                    double[] numbers = expressionsCalculator.getTwoNumbers(dis, CURRENT_OPERATOR);
                    String new_expression = decimalFormat.format(numbers[0]) + CURRENT_OPERATOR + decimalFormat.format(expressionsCalculator.square(numbers[1]));

                    main_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onPercent(View view) {
        String dis = main_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // PERCENT from the first number
                if (CURRENT_OPERATOR.equals("0")) {
                    old_display.setText("%(" + dis + ")");
                    main_display.setText(decimalFormat.format(expressionsCalculator.percent(Double.parseDouble(dis))));
                } else {
                    // PERCENT from the second number
                    double[] numbers = expressionsCalculator.getTwoNumbers(dis, CURRENT_OPERATOR);
                    String new_expression = decimalFormat.format(numbers[0]) + CURRENT_OPERATOR + decimalFormat.format(expressionsCalculator.percent(numbers[1]));

                    main_display.setText(new_expression);
                }
            } catch (Exception e) {
                showError();
            }
        }
    }

    public void onPow(View view) {
        main_display.append("^");
    }

    @SuppressLint("SetTextI18n")
    public void onEqual(View view) {
        String dis = main_display.getText().toString();

        if (!dis.equals("")) {
            try {
                // if the first number has POWER statement (^)
                if (dis.contains("^") && CURRENT_OPERATOR.equals("0")) {
                    double[] number = expressionsCalculator.getTwoNumbersPower(dis);

                    old_display.setText(dis);
                    main_display.setText(decimalFormat.format(expressionsCalculator.pow(number[0], number[1])));

                    return;
                }

                double[] numbers = expressionsCalculator.getTwoNumbers(dis, CURRENT_OPERATOR);

                switch (CURRENT_OPERATOR) {
                    case ADDITION:
                        main_display.setText(decimalFormat.format(expressionsCalculator.addition(numbers[0], numbers[1])));
                        break;

                    case MINUS:
                        main_display.setText(decimalFormat.format(expressionsCalculator.minus(numbers[0], numbers[1])));
                        break;

                    case MULTIPLY:
                        main_display.setText(decimalFormat.format(expressionsCalculator.multiply(numbers[0], numbers[1])));
                        break;

                    case DIVIDE:
                        main_display.setText(decimalFormat.format(expressionsCalculator.divide(numbers[0], numbers[1])));
                }

                old_display.setText(numbers[0] + CURRENT_OPERATOR + numbers[1]);

                CURRENT_OPERATOR = "0";
            } catch (Exception e) {
                showError();
            }
        }
    }

    public void onDelAll(View view) {
        main_display.setText("");
        old_display.setText("");

        CURRENT_OPERATOR = "0";
    }

    public void onDel(View view) {
        String dis = main_display.getText().toString();

        if (!dis.equals("")) {
            main_display.setText(dis.substring(0, dis.length() - 1));

            // if the last symbol is an operator
            if (dis.indexOf(CURRENT_OPERATOR) == (dis.length() - 1)) {
                CURRENT_OPERATOR = "0";
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void showError() {
        main_display.setText("Error");
    }
}