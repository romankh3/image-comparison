package com.github.romankh3.image.comparison.model;

import java.awt.image.BufferedImage;

/**
 * Result of the comparison.
 */
public class ComparisonResult {

    /**
     * {@link BufferedImage} object of the expected.
     */
    private BufferedImage expected;

    /**
     * {@link BufferedImage} object of the actual.
     */
    private BufferedImage actual;

    /**
     * {@link BufferedImage} object of the comparison result.
     */
    private BufferedImage result;

    /**
     * State of the comparison.
     */
    private ComparisonState comparisonState;

    /**
     * Create default instance of the {@link ComparisonResult} with {@link ComparisonState#SIZE_MISMATCH}.
     *
     * @param expected expected {@link BufferedImage} object.
     * @param actual actual {@link BufferedImage} object.
     *
     * @return instance of the {@link ComparisonResult} object.
     */
    public static ComparisonResult defaultSizeMissMatchResult(BufferedImage expected, BufferedImage actual) {
        return new ComparisonResult()
                .setComparisonState(ComparisonState.SIZE_MISMATCH)
                .setExpected(expected)
                .setActual(actual)
                .setResult(actual);
    }

    /**
     * Create default instance of the {@link ComparisonResult} with {@link ComparisonState#MISMATCH}.
     *
     * @param expected expected {@link BufferedImage} object.
     * @param actual actual {@link BufferedImage} object.
     * @return instance of the {@link ComparisonResult} object.
     */
    public static ComparisonResult defaultMisMatchResult(BufferedImage expected, BufferedImage actual) {
        return new ComparisonResult()
                .setComparisonState(ComparisonState.MISMATCH)
                .setExpected(expected)
                .setActual(actual)
                .setResult(actual);
    }

    /**
     * Create default instance of the {@link ComparisonResult} with {@link ComparisonState#MATCH}.
     *
     * @param expected expected {@link BufferedImage} object.
     * @param actual actual {@link BufferedImage} object.
     *
     * @return instance of the {@link ComparisonResult} object.
     */
    public static ComparisonResult defaultMatchResult(BufferedImage expected, BufferedImage actual) {
        return new ComparisonResult()
                .setComparisonState(ComparisonState.MATCH)
                .setExpected(expected)
                .setActual(actual)
                .setResult(actual);
    }

    public BufferedImage getExpected() {
        return expected;
    }

    public ComparisonResult setExpected(BufferedImage expected) {
        this.expected = expected;
        return this;
    }

    public BufferedImage getActual() {
        return actual;
    }

    public ComparisonResult setActual(BufferedImage actual) {
        this.actual = actual;
        return this;
    }

    public BufferedImage getResult() {
        return result;
    }

    public ComparisonResult setResult(BufferedImage result) {
        this.result = result;
        return this;
    }

    public ComparisonState getComparisonState() {
        return comparisonState;
    }

    public ComparisonResult setComparisonState(ComparisonState comparisonState) {
        this.comparisonState = comparisonState;
        return this;
    }


}
