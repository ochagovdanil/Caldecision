package com.example.danilochagov.calc_3000.Main;

public class ExpressionsCalculator implements ICalculator {
    @Override
    public double[] getTwoNumbers(String text, String current_operator) {
        double one, two;

        if (text.charAt(0) == '-' && current_operator.equals("-")) { // if the expression has two minus
            int index_operator = 0;

            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '-' && i > 0) {
                    index_operator = i;
                }
            }

            one = Double.parseDouble(text.substring(0, index_operator));
            two = Double.parseDouble(text.substring(index_operator + 1, text.length()));
        } else {
            one = Double.parseDouble(text.substring(0, text.indexOf(current_operator)));
            two = Double.parseDouble(text.substring(text.indexOf(current_operator) + 1, text.length()));
        }

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
}
