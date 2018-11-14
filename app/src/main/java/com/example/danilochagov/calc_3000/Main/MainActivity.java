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

        if (CURRENT_OPERATOR.equals("0")) {
            if (dis.equals("") && operator.equals(MINUS)) { // add or '-' in the start of the expression
                main_display.append(operator);

                CURRENT_OPERATOR = "0";
                return;
            }

            if (!dis.equals("") && (dis.length() > 1) && (dis.indexOf(MINUS) == 0)) { // add the operator after the first number with an operator before the first number
                main_display.append(operator);

                CURRENT_OPERATOR = operator;
                return;
            }

            if (!dis.equals("") && (dis.indexOf(MINUS) != 0)) { // add the operator after the first number without an operator before the first number
                main_display.append(operator);

                CURRENT_OPERATOR = operator;
            }
        } else {
            if (dis.indexOf(CURRENT_OPERATOR) == (dis.length() - 1)) { // replace the operator after the first number
                dis = dis.substring(0, dis.length() - 1);
                dis += operator;

                main_display.setText(dis);

                CURRENT_OPERATOR = operator;
                return;
            }

            // do result of two numbers
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
    }

    public void onNumberPI(View view) {
        String dis = main_display.getText().toString();

        if (dis.equals("")) { // just add PI number
            main_display.setText(String.valueOf(decimalFormat.format(Math.PI)));
        } else {
            if (!dis.contains(".")) { // just add PI number
                main_display.append(String.valueOf(decimalFormat.format(Math.PI)));
                return;
            }

            if (dis.contains(".")) { // the second number must not have a dot
                boolean second_number_with_dot = false;

                for (int i = 0; i < dis.length(); i++) {
                    if (dis.charAt(i) == '.' && (dis.indexOf(CURRENT_OPERATOR) < i)) {
                        second_number_with_dot = true;
                    }
                }

                if (!second_number_with_dot) {
                    main_display.append(String.valueOf(decimalFormat.format(Math.PI)));
                }
            }
        }
    }

    public void onNumberE(View view) {
        String dis = main_display.getText().toString();

        if (dis.equals("")) { // just add E number
            main_display.setText(String.valueOf(decimalFormat.format(Math.E)));
        } else {
            if (!dis.contains(".")) { // just add E number
                main_display.append(String.valueOf(decimalFormat.format(Math.E)));
                return;
            }

            if (dis.contains(".")) { // the second number must not have a dot
                boolean second_number_with_dot = false;

                for (int i = 0; i < dis.length(); i++) {
                    if (dis.charAt(i) == '.' && (dis.indexOf(CURRENT_OPERATOR) < i)) {
                        second_number_with_dot = true;
                    }
                }

                if (!second_number_with_dot) {
                    main_display.append(String.valueOf(decimalFormat.format(Math.E)));
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onSin(View view) {
        String dis = main_display.getText().toString();

        // sin from the first number
        if (!dis.equals("") && CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Math.sin(Double.parseDouble(dis))));

            old_display.setText("SIN(" + dis + ")");
            main_display.setText(String.valueOf(number));

            return;
        }

        // sin from the second number
        if (!dis.equals("") && !CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Math.sin(Double.parseDouble(dis.substring(dis.indexOf(CURRENT_OPERATOR) + 1)))));

            dis = dis.substring(0, dis.indexOf(CURRENT_OPERATOR) + 1);
            dis += number;

            main_display.setText(dis);
        }
    }

    @SuppressLint("SetTextI18n")
    public void onCos(View view) {
        String dis = main_display.getText().toString();

        // cos from the first number
        if (!dis.equals("") && CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Math.cos(Double.parseDouble(dis))));

            old_display.setText("COS(" + dis + ")");
            main_display.setText(String.valueOf(number));

            return;
        }

        // cos from the second number
        if (!dis.equals("") && !CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Math.cos(Double.parseDouble(dis.substring(dis.indexOf(CURRENT_OPERATOR) + 1)))));

            dis = dis.substring(0, dis.indexOf(CURRENT_OPERATOR) + 1);
            dis += number;

            main_display.setText(dis);
        }
    }

    @SuppressLint("SetTextI18n")
    public void onTan(View view) {
        String dis = main_display.getText().toString();

        // tan from the first number
        if (!dis.equals("") && CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Math.tan(Double.parseDouble(dis))));

            old_display.setText("TAN(" + dis + ")");
            main_display.setText(String.valueOf(number));

            return;
        }

        // tan from the second number
        if (!dis.equals("") && !CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Math.tan(Double.parseDouble(dis.substring(dis.indexOf(CURRENT_OPERATOR) + 1)))));

            dis = dis.substring(0, dis.indexOf(CURRENT_OPERATOR) + 1);
            dis += number;

            main_display.setText(dis);
        }
    }

    @SuppressLint("SetTextI18n")
    public void onLog(View view) {
        String dis = main_display.getText().toString();

        // log from the first number
        if (!dis.equals("") && CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Math.log(Double.parseDouble(dis))));

            old_display.setText("LOG(" + dis + ")");
            main_display.setText(String.valueOf(number));

            return;
        }

        // log from the second number
        if (!dis.equals("") && !CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Math.log(Double.parseDouble(dis.substring(dis.indexOf(CURRENT_OPERATOR) + 1)))));

            dis = dis.substring(0, dis.indexOf(CURRENT_OPERATOR) + 1);
            dis += number;

            main_display.setText(dis);
        }
    }

    @SuppressLint("SetTextI18n")
    public void onSqrt(View view) {
        String dis = main_display.getText().toString();

        // square from the first number
        if (!dis.equals("") && CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Math.sqrt(Double.parseDouble(dis))));

            old_display.setText("Square(" + dis + ")");
            main_display.setText(String.valueOf(number));

            return;
        }

        // square from the second number
        if (!dis.equals("") && !CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Math.sqrt(Double.parseDouble(dis.substring(dis.indexOf(CURRENT_OPERATOR) + 1)))));

            dis = dis.substring(0, dis.indexOf(CURRENT_OPERATOR) + 1);
            dis += number;

            main_display.setText(dis);
        }
    }

    @SuppressLint("SetTextI18n")
    public void onPercent(View view) {
        String dis = main_display.getText().toString();

        // percent of the first number
        if (!dis.equals("") && CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Double.parseDouble(dis) / 100));

            old_display.setText("Percent(" + dis + ")");
            main_display.setText(String.valueOf(number));

            return;
        }

        // percent of the second number
        if (!dis.equals("") && !CURRENT_OPERATOR.equals("0")) {
            double number = Double.parseDouble(decimalFormat.format(Double.parseDouble(dis.substring(dis.indexOf(CURRENT_OPERATOR) + 1)) / 100));

            dis = dis.substring(0, dis.indexOf(CURRENT_OPERATOR) + 1);
            dis += number;

            main_display.setText(dis);
        }
    }

    public void onPow(View view) {
        String dis = main_display.getText().toString();

        if (CURRENT_OPERATOR.equals("0")) {
            if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '^')) {
                main_display.append("^");
            }
        } else {
            if ((dis.charAt(dis.length() - 1) != '^') && (dis.charAt(dis.length() - 1) != '+') && (dis.charAt(dis.length() - 1) != '-') && (dis.charAt(dis.length() - 1) != 'x') && (dis.charAt(dis.length() - 1) != '/')) {
                main_display.append("^");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onEqual(View view) {
        String dis = main_display.getText().toString();

        if (CURRENT_OPERATOR.equals("0") && dis.contains("^")) {
            old_display.setText(dis);

            double one = Double.parseDouble(dis.substring(0, dis.indexOf("^")));
            double two = Double.parseDouble(dis.substring(dis.indexOf("^") + 1));

            main_display.setText(decimalFormat.format(Math.pow(one, two)));
            return;
        }

        if (!CURRENT_OPERATOR.equals("0") && dis.contains("^")) {
            // pow
        }

        if (!CURRENT_OPERATOR.equals("0") && dis.indexOf(CURRENT_OPERATOR) != (dis.length() - 1)) { // after the operator must be numbers
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
}