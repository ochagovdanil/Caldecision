package com.example.danilochagov.calc_3000.Main;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ExpressionsCalculatorTest {
    private ExpressionsCalculator expressionsCalculator;

    @Before
    public void setUp() {
        expressionsCalculator = new ExpressionsCalculator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTwoNumbersPower() {
        double[] expected = new double[] {2.0, 6.0};
        double[] actual = expressionsCalculator.getTwoNumbersPower("2^6");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {2.0, 6.9};
        actual = expressionsCalculator.getTwoNumbersPower("2^6.9");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {2.2, 6.9};
        actual = expressionsCalculator.getTwoNumbersPower("2.2^6.9");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {2.0, 6.9};
        actual = expressionsCalculator.getTwoNumbersPower("2^6.9");
        assertTrue(Arrays.equals(expected, actual));

        expressionsCalculator.getTwoNumbersPower("26");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTwoNumbers() {
        double[] expected = new double[] {2.0, 6.0};
        double[] actual = expressionsCalculator.getTwoNumbers("2+6", "+");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {-2.0, 6.0};
        actual = expressionsCalculator.getTwoNumbers("-2/6", "/");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {-2.0, -6.0};
        actual = expressionsCalculator.getTwoNumbers("-2*-6", "*");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {-2.0, 6.0};
        actual = expressionsCalculator.getTwoNumbers("-2-6", "-");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {2.0, 6.0};
        actual = expressionsCalculator.getTwoNumbers("+2+6", "+");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {-2.9, 6.7};
        actual = expressionsCalculator.getTwoNumbers("-2.9-6.7", "-");
        assertTrue(Arrays.equals(expected, actual));

        expressionsCalculator.getTwoNumbers("2+6", "");

        expected = new double[] {2.0, 64.0};
        actual = expressionsCalculator.getTwoNumbers("2+2^6", "+");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {9.0, 1.0};
        actual = expressionsCalculator.getTwoNumbers("3^2+1", "+");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {9.0, 16.0};
        actual = expressionsCalculator.getTwoNumbers("3^2+4^2", "+");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {-27.0, 16.0};
        actual = expressionsCalculator.getTwoNumbers("-3^3+4^2", "+");
        assertTrue(Arrays.equals(expected, actual));

        expected = new double[] {-27.0, 16.0};
        actual = expressionsCalculator.getTwoNumbers("-3^3-4^2", "-");
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void addition() {
        assertThat(6.0, is(expressionsCalculator.addition(3, 3)));
    }

    @Test
    public void minus() {
        assertThat(6.0, is(expressionsCalculator.minus(15, 9)));
    }

    @Test
    public void multiply() {
        assertThat(9.0, is(expressionsCalculator.multiply(3, 3)));
    }

    @Test(expected = ArithmeticException.class)
    public void divide() {
        assertThat(2.0, is(expressionsCalculator.divide(18, 9)));
        expressionsCalculator.divide(18, 0);
    }

    @Test
    public void getNumberPI() {
        double expected = Math.PI;
        double actual = expressionsCalculator.getNumberPI();
        assertThat(expected, is(actual));
    }

    @Test
    public void getNumberE() {
        double expected = Math.E;
        double actual = expressionsCalculator.getNumberE();
        assertThat(expected, is(actual));
    }

    @Test
    public void sinus() {
        double expected = -0.5365729180004349;
        double actual = expressionsCalculator.sinus(12.0);
        assertThat(expected, is(actual));
    }

    @Test
    public void cosinus() {
        double expected = 0.8438539587324921;
        double actual = expressionsCalculator.cosinus(12.0);
        assertThat(expected, is(actual));
    }

    @Test
    public void tangent() {
        double expected = -0.6358599286615808;
        double actual = expressionsCalculator.tangent(12.0);
        assertThat(expected, is(actual));
    }

    @Test
    public void logarithm() {
        double expected = 2.4849066497880004;
        double actual = expressionsCalculator.logarithm(12.0);
        assertThat(expected, is(actual));
    }

    @Test
    public void square() {
        double expected = 9;
        double actual = expressionsCalculator.square(81.0);
        assertThat(expected, is(actual));
    }

    @Test
    public void percent() {
        double expected = 0.05;
        double actual = expressionsCalculator.percent(5.0);
        assertThat(expected, is(actual));

        expected = -0.05;
        actual = expressionsCalculator.percent(-5.0);
        assertThat(expected, is(actual));
    }

    @Test
    public void pow() {
        double expected = 9;
        double actual = expressionsCalculator.pow(3, 2);
        assertThat(expected, is(actual));

        expected = 9;
        actual = expressionsCalculator.pow(-3, 2);
        assertThat(expected, is(actual));
    }

}