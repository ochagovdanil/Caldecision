package com.example.danilochagov.calc_3000;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextView display, old_display; // the main display numbers and the old display numbers

    private DecimalFormat decimalFormatShort, decimalFormatLong; // use to convert decimal numbers
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private FragmentMain fragmentMain;
    private FragmentAddition fragmentAddition;

    private StringBuilder stringBuilder;
    private static String operator = ""; // current operator (+, -, /, x)
    private static final String TAG = "Calc"; // Log.d()

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.main_display);
        old_display = findViewById(R.id.old_display);

        decimalFormatShort = new DecimalFormat("#.#####");
        decimalFormatLong = new DecimalFormat("#.###########");

        fragmentMain = new FragmentMain();
        fragmentAddition = new FragmentAddition();
        fragmentManager = getSupportFragmentManager();

        setDefaultKeyboard();
    }

    // set main buttons
    private void setDefaultKeyboard () {
        fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentByTag(FragmentMain.TAG) == null) {
            fragmentTransaction.replace(R.id.place, fragmentMain);
        }

        fragmentTransaction.commit();
    }

    // set addition buttons
    private void setAdditionKeyboard () {
        fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentByTag(FragmentAddition.TAG) == null) {
            fragmentTransaction.replace(R.id.place, fragmentAddition);
        }

        fragmentTransaction.commit();
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

        // ------------- ( 0,2 -> 0.2 ) -------------
        String c = decimalFormatLong.format(result);

        if (c.contains(",")) {
            c = c.replace(',', '.');
        }
        // --------------------------------------------

        return c;
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

        // correct input and handler of actions
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

    // clear all
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
        setAdditionKeyboard();
    }

    // close the curtain
    public void onCloseCurtain (View v) {
        setDefaultKeyboard();
    }

    // make square
    public void onSqrt (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && operator.equals("")) { // square from the first number at display
            double number = Double.parseDouble(dis); // take number
            double numberSqrt = Math.sqrt(number); // sqrt of number

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String c = decimalFormatShort.format(number);

            if (c.contains(",")) {
                c = c.replace(',', '.');
            }


            String f = decimalFormatShort.format(numberSqrt);

            if (f.contains(",")) {
                f = f.replace(',', '.');
            }
            // ----------------------------------------------

            display.setText(f);

            String a = "Square("+c+")";
            old_display.setText(a);

            return;
        }

        if (!dis.equals("") && !operator.equals("")) { // square from second number at display
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 1, dis.length())); // take number
            double numberSqrt = Math.sqrt(number); // sqrt of number

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String f = decimalFormatShort.format(numberSqrt);

            if (f.contains(",")) {
                f = f.replace(',', '.');
            }
            // ----------------------------------------------

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += f;

            display.setText(dis);
        }
    }

    // math_percent of number ( 9 -> 0.09 )
    public void onPercent (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '%') && operator.equals("")) { // percent of the first number
            double number = Double.parseDouble(display.getText().toString()); // take number

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String c = decimalFormatShort.format(number);

            if (c.contains(",")) {
                c = c.replace(',', '.');
            }
            // ----------------------------------------------

            String a = "Percent("+c+")";
            old_display.setText(a);

            number = number / 100; // percent of number

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String f = decimalFormatShort.format(number);

            if (f.contains(",")) {
                f = f.replace(',', '.');
            }
            // ---------------------------
            display.setText(f);

            return;
        }

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '%') && !operator.equals("")) { // percent of the second number
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = number / 100;

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String c = decimalFormatShort.format(number);

            if (c.contains(",")) {
                c = c.replace(",", ".");
            }
            // -----------------------------------------------

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += c;

            display.setText(dis);
        }
    }

    // make number PI ( 3.14.... )
    public void onNumberPI (View v) {
        // ---------------- ( 0,2 -> 0.2 ) --------------------
        String c = decimalFormatShort.format(Math.PI);

        if (c.contains(",")) {
            c = c.replace(",", ".");
        }
        // -----------------------------------------------

        display.append(c);
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

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String c = decimalFormatShort.format(number);

            if (c.contains(",")) {
                c = c.replace(",", ".");
            }
            // -----------------------------------------------

            String a = "Sin("+c+")";
            old_display.setText(a);

            number = Math.sin(number); // sin of number

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String f = decimalFormatShort.format(number);

            if (f.contains(",")) {
                f = f.replace(",", ".");
            }
            // -----------------------------------------------

            display.setText(f);

            return;
        }

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) { // sin from the second number
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.sin(number);

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String c = decimalFormatShort.format(number);

            if (c.contains(",")) {
                c = c.replace(",", ".");
            }
            // -----------------------------------------------

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += c;

            display.setText(dis);
        }
    }

    // make cos
    public void onCos (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && operator.equals("")) { // cos from the first number
            double number = Double.parseDouble(dis.substring(0, dis.length())); // take number

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String c = decimalFormatShort.format(number);

            if (c.contains(",")) {
                c = c.replace(",", ".");
            }
            // -----------------------------------------------

            String a = "Cos("+c+")";
            old_display.setText(a);

            number = Math.cos(number); // cos of number

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String f = decimalFormatShort.format(number);

            if (f.contains(",")) {
                f = f.replace(",", ".");
            }
            // -----------------------------------------------

            display.setText(f);

            return;
        }

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) { // cos from the second number
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.cos(number);

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String c = decimalFormatShort.format(number);

            if (c.contains(",")) {
                c = c.replace(",", ".");
            }
            // -----------------------------------------------

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += c;

            display.setText(dis);
        }
    }

    // make tan
    public void onTan (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && operator.equals("")) { // tan from the first number
            double number = Double.parseDouble(dis.substring(0, dis.length())); // take number

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String c = decimalFormatShort.format(number);

            if (c.contains(",")) {
                c = c.replace(",", ".");
            }
            // -----------------------------------------------

            String a = "Tan("+c+")";
            old_display.setText(a);

            number = Math.tan(number); // tan of number

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String f = decimalFormatShort.format(number);

            if (f.contains(",")) {
                f = f.replace(",", ".");
            }
            // -----------------------------------------------

            display.setText(f);

            return;
        }

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) { // tan from the second number
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.tan(number);

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String c = decimalFormatShort.format(number);

            if (c.contains(",")) {
                c = c.replace(",", ".");
            }
            // -----------------------------------------------

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += c;

            display.setText(dis);
        }
    }

    // make log
    public void onLog (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && operator.equals("")) { // log from the first number
            double number = Double.parseDouble(dis.substring(0, dis.length())); // take number

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String c = decimalFormatShort.format(number);

            if (c.contains(",")) {
                c = c.replace(",", ".");
            }
            // -----------------------------------------------

            String a = "Log("+c+")";
            old_display.setText(a);

            number = Math.log(number); // log of number

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String f = decimalFormatShort.format(number);

            if (f.contains(",")) {
                f = f.replace(",", ".");
            }
            // -----------------------------------------------

            display.setText(f);

            return;
        }

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) { // log from the second number
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.log(number);

            // ---------------- ( 0,2 -> 0.2 ) --------------------
            String c = decimalFormatShort.format(number);

            if (c.contains(",")) {
                c = c.replace(",", ".");
            }
            // -----------------------------------------------

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += c;

            display.setText(dis);
        }
    }

    // make number e ( 2.781 )
    public void onE (View v) {
        // ---------------- ( 0,2 -> 0.2 ) --------------------
        String c = decimalFormatShort.format(Math.E);

        if (c.contains(",")) {
            c = c.replace(",", ".");
        }
        // -----------------------------------------------

        display.append(c);
    }
}