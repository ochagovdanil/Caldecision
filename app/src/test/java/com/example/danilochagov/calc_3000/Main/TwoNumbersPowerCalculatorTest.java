package com.example.danilochagov.calc_3000.Main;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TwoNumbersPowerCalculatorTest {
    private static ExpressionsCalculator expressionsCalculator;
    private String expression;
    private double[] expected;

    @BeforeClass
    public static void setUpBeforeClass() {
        expressionsCalculator = new ExpressionsCalculator();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"2^6", new double[]{2.0, 6.0}},
                {"2^6.9", new double[]{2.0, 6.9}},
                {"2.2^6.9", new double[]{2.2, 6.9}},
                {"2^6.9", new double[]{2.0, 6.9}}
        });
    }

    public TwoNumbersPowerCalculatorTest(String expression, double[] expected) {
        this.expression = expression;
        this.expected = expected;
    }

    @Test
    public void getTwoNumbersPowerTest() {
        Assert.assertTrue(Arrays.equals(expected, expressionsCalculator.getTwoNumbersPower(expression)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTwoNumbersPowerExceptionTest() {
        expressionsCalculator.getTwoNumbersPower("26");
    }

}
