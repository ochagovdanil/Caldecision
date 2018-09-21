package com.example.danilochagov.calc_3000;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextView display, old_display; // the main display numbers and the old display numbers
    private LinearLayout main_curt, addition_curt; // curtains of buttons

    private DecimalFormat decimalFormatShort, decimalFormatLong; // use to convert decimal numbers

    private StringBuilder stringBuilder;
    private static String operator = ""; // current operator (+, -, /, x)
    private static final String TAG = "Calc"; // Log.d()

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.main_display);
        old_display = findViewById(R.id.old_display);

        main_curt = findViewById(R.id.main_curtain);
        addition_curt = findViewById(R.id.addition_curtain);

        decimalFormatShort = new DecimalFormat("#.#####");
        decimalFormatLong = new DecimalFormat("#.###########");
    }

    // make result of two numbers
    private String makeResult () {
        double a; // number one
        double b; // number two
        double result; // result of numbers

        String one, two; // numbers for manipulation
        String dis = display.getText().toString();

        // search for numbers on the main display
        one = dis.substring(0, dis.indexOf(" "));
        two = dis.substring(dis.indexOf(" ") + 3, dis.length());

        // ------------------ pow or clean number ( 2^5  or  2 ) -----------------------
        if (one.contains("^")) {
            a = Double.parseDouble(one.substring(0, one.indexOf("^")));
            int pow = Integer.parseInt(one.substring(one.indexOf("^") + 1, one.length()));

            a = Math.pow(a, pow);
        } else {
            a = Double.parseDouble(one);
        }

        if (two.contains("^")) {
            b = Double.parseDouble(two.substring(0, two.indexOf("^")));
            int pow = Integer.parseInt(two.substring(two.indexOf("^") + 1, two.length()));

            b = Math.pow(b, pow);
        } else {
            b = Double.parseDouble(two);
        }
        // -----------------------------------------------------------------------------

        switch (operator) {
            case "+":
                result = a + b;
                break;

            case "-":
                result = a - b;
                break;

            case "/":
                result = a / b;
                break;

            case "x":
                result = a * b;
                break;

            default:
                result = 0;
        }

        operator = "";
        stringBuilder.setLength(0);

        return decimalFormatLong.format(result);
    }

    // add numbers (0, 1, 2...)
    public void onAddNumber (View v) {
        Button button = (Button) v;

        display.append(button.getText());
    }

    // add operator (+, -, /...)
    public void onAddOperator (View v) {
        Button button = (Button) v;
        stringBuilder = new StringBuilder(" "); // add operators with spaces
        String dis = display.getText().toString();



        if (dis.equals("-")) { // user can't set another operator after an operator
            // nothing
            return;
        }

        if (!dis.equals("") && operator.equals("")) { // just add operator after the first number
            stringBuilder.append(button.getText()).append(" ");
            display.append(stringBuilder);

            operator = button.getText().toString();
            return;
        }

        if (!dis.equals("") && (!operator.equals("")) && (!dis.substring(dis.length()-1, dis.length()).equals(" "))) { // do result of two numbers
            old_display.setText(display.getText());
            display.setText(makeResult());

            Button b = (Button) v;
            stringBuilder.append(" ").append(b.getText()).append(" ");
            operator = b.getText().toString();

            display.append(stringBuilder);
            return;
        }

        if (!dis.equals("") && (!operator.equals("")) && (dis.substring(dis.length()-1, dis.length()).equals(" "))) { // change operator after the first number
            Button b = (Button) v;
            stringBuilder.append(" ").append(b.getText()).append(" ");
            operator = b.getText().toString();

            display.setText(dis.substring(0, dis.length()-2));
            display.append(operator+" ");
            return;
        }

        if (dis.equals("") && button.getText().toString().equals("-") && (!button.getText().toString().equals("x") && !button.getText().toString().equals("/") && !button.getText().toString().equals("+"))) { // add operator before the first number
            display.setText(button.getText().toString());
        }
    }

    // clear one symbol
    public void onDel (View v) {
        if (!display.getText().toString().equals("")) {
            String dis = display.getText().toString();

            if (dis.substring(dis.length() - 1, dis.length()).equals(" ")) {
                display.setText(dis.substring(0, dis.length() - 3));
                operator = "";
            } else {
                display.setText(dis.substring(0, dis.length() - 1));
            }
        }
    }

    // clear all displays
    public void onDelAll (View v) {
        if (!display.getText().toString().equals("")) {
            display.setText("");
            old_display.setText("");

            operator = "";
        }
    }

    // do result of two numbers
    public void onEqual (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (!operator.equals("")) && (!dis.substring(dis.length()-1, dis.length()).equals(" "))) { // make result of two numbers
            old_display.setText(display.getText());
            display.setText(makeResult());

            return;
        }

        if (!dis.equals("") && operator.equals("") && dis.contains("^") && !dis.contains(" ") && (dis.charAt(dis.length() - 1) != '^')) { // make power of the first number
            double number = Double.parseDouble(dis.substring(0, dis.indexOf("^")));
            int pow = Integer.parseInt(dis.substring(dis.indexOf("^") + 1, dis.length()));

            display.setText(decimalFormatShort.format(Math.pow(number, pow)));

            String a = decimalFormatShort.format(number)+"^"+pow;
            old_display.setText(a);
        }
    }

    // show the curtain
    public void onShowCurtain (View v) {
        main_curt.setVisibility(View.GONE);
        addition_curt.setVisibility(View.VISIBLE);
    }

    // close the curtain
    public void onCloseCurtain (View v) {
        main_curt.setVisibility(View.VISIBLE);
        addition_curt.setVisibility(View.GONE);
    }

    // make square
    public void onSqrt (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && operator.equals("")) { // square from the first number at display
            double number = Double.parseDouble(dis); // take number
            double numberSqrt = Math.sqrt(number); // sqrt of number

            display.setText(decimalFormatShort.format(numberSqrt));

            String a = "Square("+decimalFormatShort.format(number)+")";
            old_display.setText(a);

            return;
        }

        if (!dis.equals("") && !operator.equals("")) { // square from second number at display
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 1, dis.length())); // take number
            double numberSqrt = Math.sqrt(number); // sqrt of number

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += decimalFormatShort.format(numberSqrt);

            display.setText(dis);
        }
    }

    // math_percent of number ( 9 -> 0.09 )
    public void onPercent (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '%') && operator.equals("")) { // percent of the first number
            double number = Double.parseDouble(display.getText().toString()); // take number

            String a = "Percent("+decimalFormatShort.format(number)+")";
            old_display.setText(a);

            number = number / 100; // percent of number
            display.setText(decimalFormatShort.format(number));

            return;
        }

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '%') && !operator.equals("")) { // percent of the second number
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = number / 100;

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += decimalFormatShort.format(number);

            display.setText(dis);
        }
    }

    // make number PI ( 3.14.... )
    public void onNumberPI (View v) {
        display.append(String.valueOf(decimalFormatShort.format(Math.PI)));
    }

    // make pow of number ( 2^4 -> 16 )
    public void onPow (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '^') && (dis.charAt(dis.length() - 1) != ' ') && (dis.charAt(dis.length() - 1) != '-')) {
            display.append("^");
        }
    }

    // make sin
    public void onSin (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && operator.equals("")) { // sin from the first number
            double number = Double.parseDouble(dis.substring(0, dis.length())); // take number

            String a = "Sin("+ decimalFormatShort.format(number)+")";
            old_display.setText(a);

            number = Math.sin(number); // sin of number
            display.setText(decimalFormatShort.format(number));

            return;
        }

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) { // sin from the second number
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.sin(number);

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += decimalFormatShort.format(number);

            display.setText(dis);
        }
    }

    // make cos
    public void onCos (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && operator.equals("")) { // cos from the first number
            double number = Double.parseDouble(dis.substring(0, dis.length())); // take number

            String a = "Cos("+ decimalFormatShort.format(number)+")";
            old_display.setText(a);

            number = Math.cos(number); // cos of number
            display.setText(decimalFormatShort.format(number));

            return;
        }

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) { // cos from the second number
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.cos(number);

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += decimalFormatShort.format(number);

            display.setText(dis);
        }
    }

    // make tan
    public void onTan (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && operator.equals("")) { // tan from the first number
            double number = Double.parseDouble(dis.substring(0, dis.length())); // take number

            String a = "Tan("+ decimalFormatShort.format(number)+")";
            old_display.setText(a);

            number = Math.tan(number); // tan of number
            display.setText(decimalFormatShort.format(number));

            return;
        }

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) { // tan from the second number
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.tan(number);

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += decimalFormatShort.format(number);

            display.setText(dis);
        }
    }

    // make log
    public void onLog (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && operator.equals("")) { // log from the first number
            double number = Double.parseDouble(dis.substring(0, dis.length())); // take number

            String a = "Log("+ decimalFormatShort.format(number)+")";
            old_display.setText(a);

            number = Math.log(number); // log of number
            display.setText(decimalFormatShort.format(number));

            return;
        }

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) { // log from the second number
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.log(number);

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += decimalFormatShort.format(number);

            display.setText(dis);
        }
    }

    // make number e ( 2.781 )
    public void onE (View v) {
        display.append(decimalFormatShort.format(Math.E));
    }
}