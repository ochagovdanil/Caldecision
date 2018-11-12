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
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_display = findViewById(R.id.main_display);
        old_display = findViewById(R.id.old_display);

        expressionsCalculator = new ExpressionsCalculator();
        decimalFormat = new DecimalFormat("#.########");
    }

    public void onAddNumber (View view) {
        Button button = (Button) view;
        main_display.append(button.getText().toString());
    }

    @SuppressLint("SetTextI18n")
    public void onAddOperator (View view) {
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

    @SuppressLint("SetTextI18n")
    public void onEqual (View view) {
        String dis = main_display.getText().toString();

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

    public void onDelAll (View view) {
        main_display.setText("");
        old_display.setText("");

        CURRENT_OPERATOR = "0";
    }

    public void onDel (View view) {
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