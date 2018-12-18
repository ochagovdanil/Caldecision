package com.example.danilochagov.calc_3000.Main;

public class ExpressionsCalculator implements ICalculator {

    // calculate the power number statement if need
    private String checkForPowerStatement(String number) {
        if (number.contains("^")) {
            if (number.contains("^")) {
                double[] pow_statement = getTwoNumbersPower(number);
                number = String.valueOf(pow(pow_statement[0], pow_statement[1]));

                return number;
            }
        }

        return number;
    }

    // get one number and number of power from the expression ('2^6' -> [2, 6])
    double[] getTwoNumbersPower (String text) {
        if (text.contains("^")) {
            double number = Double.parseDouble(text.substring(0, text.indexOf("^")));
            double pow = Double.parseDouble(text.substring(text.indexOf("^") + 1));

            return new double[]{number, pow};
        }

        throw new IllegalArgumentException("The expression has not got the next symbol - '^'");
    }

    double[] getTwoNumbers(String text, String current_operator) {
        if (!current_operator.equals("")) {
            String one, two;

            // if the expression has two minus
            if (text.charAt(0) == '-' && current_operator.equals("-")) {
                int index_operator = 0;

                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == '-' && i > 0) {
                        index_operator = i;
                    }
                }

                one = text.substring(0, index_operator);
                two = text.substring(index_operator + 1, text.length());

                one = checkForPowerStatement(one);
                two = checkForPowerStatement(two);

                return new double[]{Double.parseDouble(one), Double.parseDouble(two)};
            }

            // if the expression has two plus
            if (text.charAt(0) == '+' && current_operator.equals("+")) {
                int index_operator = 0;

                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == '+' && i > 0) {
                        index_operator = i;
                    }
                }

                one = text.substring(0, index_operator);
                two = text.substring(index_operator + 1, text.length());

                one = checkForPowerStatement(one);
                two = checkForPowerStatement(two);

                return new double[]{Double.parseDouble(one), Double.parseDouble(two)};
            }

            one = text.substring(0, text.indexOf(current_operator));
            two = text.substring(text.indexOf(current_operator) + 1, text.length());

            one = checkForPowerStatement(one);
            two = checkForPowerStatement(two);

            return new double[]{Double.parseDouble(one), Double.parseDouble(two)};
        }

        throw new IllegalArgumentException("The expression has not got a operator!");
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
        if (two == 0) throw new ArithmeticException("Division by zero!");

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

    @Override
    public double pow(double one, double two) {
        return Math.pow(one, two);
    }

}
