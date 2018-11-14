package com.example.danilochagov.calc_3000.Main;

public class ExpressionsCalculator implements ICalculator {
    @Override
    public double[] getTwoNumbers(String text, String current_operator) {
        double one, two;

        // if the expression has two minus
        if (text.charAt(0) == '-' && current_operator.equals("-")) {
            int index_operator = 0;

            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '-' && i > 0) {
                    index_operator = i;
                }
            }

            one = Double.parseDouble(text.substring(0, index_operator));
            two = Double.parseDouble(text.substring(index_operator + 1, text.length()));

            return new double[]{one, two};
        }

        // if the expression has two plus
        if (text.charAt(0) == '+' && current_operator.equals("+")) {
            int index_operator = 0;

            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '+' && i > 0) {
                    index_operator = i;
                }
            }

            one = Double.parseDouble(text.substring(0, index_operator));
            two = Double.parseDouble(text.substring(index_operator + 1, text.length()));

            return new double[]{one, two};
        }

        one = Double.parseDouble(text.substring(0, text.indexOf(current_operator)));
        two = Double.parseDouble(text.substring(text.indexOf(current_operator) + 1, text.length()));

        return new double[]{one, two};
    }

    @Override
    public double addition(double one, double two) {
        return one + two;
    }

    @Override
    public double minus(double one, double two) {
        return one - two;
    }

    @Override
    public double multiply(double one, double two) {
        return one * two;
    }

    @Override
    public double divide(double one, double two) {
        return one / two;
    }

    @Override
    public double getNumberPI() {
        return Math.PI;
    }

    @Override
    public double getNumberE() {
        return Math.E;
    }

    @Override
    public double sinus(double number) {
        return Math.sin(number);
    }

    @Override
    public double cosinus(double number) {
        return Math.cos(number);
    }

    @Override
    public double tangent(double number) {
        return Math.tan(number);
    }

    @Override
    public double logarithm(double number) {
        return Math.log(number);
    }

    @Override
    public double square(double number) {
        return Math.sqrt(number);
    }

    @Override
    public double percent(double number) {
        return number / 100;
    }
}
