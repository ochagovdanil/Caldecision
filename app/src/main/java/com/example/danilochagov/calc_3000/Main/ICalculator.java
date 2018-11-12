package com.example.danilochagov.calc_3000.Main;

interface ICalculator {
    double[] getTwoNumbers (String text, String current_operator);

    double addition (double one, double two);
    double minus (double one, double two);
    double multiply (double one, double two);
    double divide (double one, double two);
}
