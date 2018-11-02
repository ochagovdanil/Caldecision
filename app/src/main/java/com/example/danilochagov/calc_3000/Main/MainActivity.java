package com.example.danilochagov.calc_3000.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.danilochagov.calc_3000.R;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements ICalculator {
    private TextView display, old_display;
    private DecimalFormat decimalFormatShort, decimalFormatLong;
    private StringBuilder stringBuilder;
    private static String operator = "";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.main_display);
        old_display = findViewById(R.id.old_display);

        decimalFormatShort = new DecimalFormat("#.#####");
        decimalFormatLong = new DecimalFormat("#.###########");
    }

    public String makeResult () {
        double a;
        double b;
        double result;

        String one, two;
        String dis = display.getText().toString();

        one = dis.substring(0, dis.indexOf(" "));
        two = dis.substring(dis.indexOf(" ") + 3, dis.length());

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

        String c = decimalFormatLong.format(result);
        if (c.contains(",")) {
            c = c.replace(',', '.');
        }

        return c;
    }

    public void onAddNumber (View v) {
        Button button = (Button) v;

        display.append(button.getText());
    }

    public void onAddOperator (View v) {
        Button button = (Button) v;

        stringBuilder = new StringBuilder(" "); // add spaces between operators

        String dis = display.getText().toString();

        if (dis.equals("-")) return; // user can't set another operator after an operator

        // add operator after the first number
        if (!dis.equals("") && operator.equals("")) {
            stringBuilder.append(button.getText()).append(" ");
            display.append(stringBuilder);

            operator = button.getText().toString();
            return;
        }

        // do result of two numbers
        if (!dis.equals("") && (!operator.equals("")) && (!dis.substring(dis.length()-1, dis.length()).equals(" "))) {
            old_display.setText(display.getText());
            display.setText(makeResult());

            Button b = (Button) v;
            stringBuilder.append(" ").append(b.getText()).append(" ");
            operator = b.getText().toString();

            display.append(stringBuilder);
            return;
        }

        // change operator after the first number if user enter an operator again
        if (!dis.equals("") && (!operator.equals("")) && (dis.substring(dis.length()-1, dis.length()).equals(" "))) {
            Button b = (Button) v;
            stringBuilder.append(" ").append(b.getText()).append(" ");
            operator = b.getText().toString();

            display.setText(dis.substring(0, dis.length()-2));
            display.append(operator+" ");
            return;
        }

        // add operator before the first number
        if (dis.equals("") && button.getText().toString().equals("-") && (!button.getText().toString().equals("x") && !button.getText().toString().equals("/") && !button.getText().toString().equals("+"))) {
            display.setText(button.getText().toString());
        }
    }

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

    public void onDelAll (View v) {
        if (!display.getText().toString().equals("")) {
            display.setText("");
            old_display.setText("");

            operator = "";
        }
    }

    public void onEqual (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (!operator.equals("")) && (!dis.substring(dis.length()-1, dis.length()).equals(" "))) {
            old_display.setText(display.getText());
            display.setText(makeResult());

            return;
        }

        // make Math.pow of the first number
        if (!dis.equals("") && operator.equals("") && dis.contains("^") && !dis.contains(" ") && (dis.charAt(dis.length() - 1) != '^')) {
            double number = Double.parseDouble(dis.substring(0, dis.indexOf("^")));
            int pow = Integer.parseInt(dis.substring(dis.indexOf("^") + 1, dis.length()));

            display.setText(decimalFormatShort.format(Math.pow(number, pow)));

            String a = decimalFormatShort.format(number)+"^"+pow;
            old_display.setText(a);
        }
    }

    public void onSqrt (View v) {
        String dis = display.getText().toString();

        // square from the first number at display
        if (!dis.equals("") && operator.equals("")) {
            double number = Double.parseDouble(dis);
            double numberSqrt = Math.sqrt(number);

            String c = decimalFormatShort.format(number);
            if (c.contains(",")) {
                c = c.replace(',', '.');
            }


            String f = decimalFormatShort.format(numberSqrt);
            if (f.contains(",")) {
                f = f.replace(',', '.');
            }

            display.setText(f);

            String a = "Square("+c+")";
            old_display.setText(a);

            return;
        }

        // square from second number at display
        if (!dis.equals("") && !operator.equals("")) {
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 1, dis.length()));
            double numberSqrt = Math.sqrt(number);

            String f = decimalFormatShort.format(numberSqrt);
            if (f.contains(",")) {
                f = f.replace(',', '.');
            }

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += f;

            display.setText(dis);
        }
    }

    public void onPercent (View v) {
        String dis = display.getText().toString();

        // percent of the first number
        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '%') && operator.equals("")) {
            double number = Double.parseDouble(display.getText().toString());

            String c = decimalFormatShort.format(number);
            if (c.contains(",")) {
                c = c.replace(',', '.');
            }

            String a = "Percent("+c+")";
            old_display.setText(a);

            number = number / 100;

            String f = decimalFormatShort.format(number);
            if (f.contains(",")) {
                f = f.replace(',', '.');
            }
            display.setText(f);

            return;
        }

        // percent of the second number
        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '%') && !operator.equals("")) {
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = number / 100;

            String c = decimalFormatShort.format(number);
            if (c.contains(",")) {
                c = c.replace(",", ".");
            }

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += c;

            display.setText(dis);
        }
    }

    public void onNumberPI (View v) {
        String c = decimalFormatShort.format(Math.PI);
        if (c.contains(",")) {
            c = c.replace(",", ".");
        }

        display.append(c);
    }

    public void onPow (View v) {
        String dis = display.getText().toString();

        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '^') && (dis.charAt(dis.length() - 1) != ' ') && (dis.charAt(dis.length() - 1) != '-')) {
            display.append("^");
        }
    }

    public void onSin (View v) {
        String dis = display.getText().toString();

        // sin from the first number
        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && operator.equals("")) {
            double number = Double.parseDouble(dis);

            String c = decimalFormatShort.format(number);
            if (c.contains(",")) {
                c = c.replace(",", ".");
            }

            String a = "Sin("+c+")";
            old_display.setText(a);

            number = Math.sin(number);

            String f = decimalFormatShort.format(number);
            if (f.contains(",")) {
                f = f.replace(",", ".");
            }

            display.setText(f);

            return;
        }

        // sin from the second number
        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) {
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.sin(number);

            String c = decimalFormatShort.format(number);
            if (c.contains(",")) {
                c = c.replace(",", ".");
            }

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += c;

            display.setText(dis);
        }
    }

    public void onCos (View v) {
        String dis = display.getText().toString();

        // cos from the first number
        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && operator.equals("")) {
            double number = Double.parseDouble(dis);

            String c = decimalFormatShort.format(number);
            if (c.contains(",")) {
                c = c.replace(",", ".");
            }

            String a = "Cos("+c+")";
            old_display.setText(a);

            number = Math.cos(number);

            String f = decimalFormatShort.format(number);
            if (f.contains(",")) {
                f = f.replace(",", ".");
            }

            display.setText(f);

            return;
        }

        // cos from the second number
        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) {
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.cos(number);

            String c = decimalFormatShort.format(number);
            if (c.contains(",")) {
                c = c.replace(",", ".");
            }

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += c;

            display.setText(dis);
        }
    }

    public void onTan (View v) {
        String dis = display.getText().toString();

        // tan from the first number
        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && operator.equals("")) {
            double number = Double.parseDouble(dis);

            String c = decimalFormatShort.format(number);
            if (c.contains(",")) {
                c = c.replace(",", ".");
            }

            String a = "Tan("+c+")";
            old_display.setText(a);

            number = Math.tan(number);

            String f = decimalFormatShort.format(number);
            if (f.contains(",")) {
                f = f.replace(",", ".");
            }

            display.setText(f);

            return;
        }

        // tan from the second number
        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) {
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.tan(number);

            String c = decimalFormatShort.format(number);
            if (c.contains(",")) {
                c = c.replace(",", ".");
            }

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += c;

            display.setText(dis);
        }
    }

    public void onLog (View v) {
        String dis = display.getText().toString();

        // log from the first number
        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && operator.equals("")) {
            double number = Double.parseDouble(dis);

            String c = decimalFormatShort.format(number);
            if (c.contains(",")) {
                c = c.replace(",", ".");
            }

            String a = "Log("+c+")";
            old_display.setText(a);

            number = Math.log(number);

            String f = decimalFormatShort.format(number);
            if (f.contains(",")) {
                f = f.replace(",", ".");
            }

            display.setText(f);

            return;
        }

        // log from the second number
        if (!dis.equals("") && (dis.charAt(dis.length() - 1) != '-') && !operator.equals("") && (dis.charAt(dis.length() - 1) != ' ')) {
            double number = Double.parseDouble(dis.substring(dis.indexOf(operator) + 2, dis.length()));
            number = Math.log(number);

            String c = decimalFormatShort.format(number);
            if (c.contains(",")) {
                c = c.replace(",", ".");
            }

            dis = dis.substring(0, dis.indexOf(operator) + 2);
            dis += c;

            display.setText(dis);
        }
    }

    public void onE (View v) {
        String c = decimalFormatShort.format(Math.E);
        if (c.contains(",")) {
            c = c.replace(",", ".");
        }

        display.append(c);
    }
}