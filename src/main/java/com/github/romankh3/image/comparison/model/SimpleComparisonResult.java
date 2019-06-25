package com.github.romankh3.image.comparison.model;

import com.sun.org.apache.bcel.internal.generic.BIPUSH;
import java.awt.image.BufferedImage;

/**
 * Result class for simple comparison result.
 */
public class SimpleComparisonResult {

    private ComparisonState comparisonState;

    private BufferedImage expected;

    private BufferedImage actual;

    private BufferedImage result;


    public static SimpleComparisonResult defaultSizeMisMatchResult(BufferedImage expected, BufferedImage actual) {
        SimpleComparisonResult simpleComparisonResult = new SimpleComparisonResult();
        simpleComparisonResult.setComparisonState(ComparisonState.SIZE_MISMATCH);
        simpleComparisonResult.setActual(actual);
        simpleComparisonResult.setExpected(expected);
        return simpleComparisonResult;
    }

    public static SimpleComparisonResult defaultMisMatchResult(BufferedImage expected, BufferedImage actual) {
        SimpleComparisonResult simpleComparisonResult = new SimpleComparisonResult();
        simpleComparisonResult.setComparisonState(ComparisonState.MISMATCH);
        simpleComparisonResult.setActual(actual);
        simpleComparisonResult.setExpected(expected);
        return simpleComparisonResult;
    }

    public static SimpleComparisonResult defaultMatchResult(BufferedImage expected, BufferedImage actual) {
        SimpleComparisonResult simpleComparisonResult = new SimpleComparisonResult();
        simpleComparisonResult.setComparisonState(ComparisonState.MATCH);
        simpleComparisonResult.setActual(actual);
        simpleComparisonResult.setExpected(expected);
        return simpleComparisonResult;
    }

    public SimpleComparisonResult setComparisonState(ComparisonState comparisonState) {
        this.comparisonState = comparisonState;
        return this;
    }

    public ComparisonState getComparisonState() {
        return comparisonState;
    }

    public SimpleComparisonResult setActual(BufferedImage actual) {
        this.actual = actual;
        return this;
    }

    public BufferedImage getActual() {
        return actual;
    }

    public SimpleComparisonResult setExpected(BufferedImage expected) {
        this.expected = expected;
        return this;
    }

    public BufferedImage getExpected() {
        return expected;
    }

    public SimpleComparisonResult setResult(BufferedImage result) {
        this.result = result;
        return this;
    }

    public BufferedImage getResult() {
        return result;
    }
}
