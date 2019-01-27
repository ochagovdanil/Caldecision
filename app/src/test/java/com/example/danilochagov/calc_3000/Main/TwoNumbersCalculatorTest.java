package com.example.danilochagov.calc_3000.Main;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TwoNumbersCalculatorTest {
    private static ExpressionsCalculator sExpressionsCalculator;
    private double[] mExpected;
    private String mExpression;
    private String mOperator;

    public TwoNumbersCalculatorTest(String expression, String operator, double[] expected) {
        this.mExpression = expression;
        this.mOperator = operator;
        this.mExpected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"2+6", "+", new double[]{2.0, 6.0}},
                {"-2/6", "/", new double[]{-2.0, 6.0}},
                {"-2*-6", "*", new double[]{-2.0, -6.0}},
                {"-2-6", "-", new double[]{-2.0, 6.0}},
                {"+2+6", "+", new double[]{2.0, 6.0}},
                {"-2.9-6.7", "-", new double[]{-2.9, 6.7}},
                {"2+2^6", "+", new double[]{2.0, 64.0}},
                {"3^2+1", "+", new double[]{9.0, 1.0}},
                {"3^2+4^2", "+", new double[]{9.0, 16.0}},
                {"-3^3+4^2", "+", new double[]{-27.0, 16.0}},
                {"-3^3-4^2", "-", new double[]{-27.0, 16.0}}
        });
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        sExpressionsCalculator = new ExpressionsCalculator();
    }


    @Test
    public void getTwoNumbersTest() {
        Assert.assertTrue(Arrays.equals(
                mExpected,
                sExpressionsCalculator.getTwoNumbers(mExpression, mOperator)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTwoNumbersExceptionTest() {
        sExpressionsCalculator.getTwoNumbers("2+6", "");
    }

}