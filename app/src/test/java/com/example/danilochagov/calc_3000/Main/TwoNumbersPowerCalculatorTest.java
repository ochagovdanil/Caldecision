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
    private static ExpressionsCalculator sExpressionsCalculator;
    private String mExpression;
    private double[] mExpected;

    public TwoNumbersPowerCalculatorTest(String expression, double[] expected) {
        this.mExpression = expression;
        this.mExpected = expected;
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

    @BeforeClass
    public static void setUpBeforeClass() {
        sExpressionsCalculator = new ExpressionsCalculator();
    }

    @Test
    public void getTwoNumbersPowerTest() {
        Assert.assertTrue(Arrays.equals(
                mExpected,
                sExpressionsCalculator.getTwoNumbersPower(mExpression)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTwoNumbersPowerExceptionTest() {
        sExpressionsCalculator.getTwoNumbersPower("26");
    }

}
