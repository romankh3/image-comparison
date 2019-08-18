package com.github.romankh3.image.comparison.model;

import com.github.romankh3.image.comparison.ImageComparisonUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Data transfer objects which contains all the needed data for result of the comparison.
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
     * The difference percentage between two images.
     */
    private float differencePercent;

    /**
     * Create default instance of the {@link ComparisonResult} with {@link ComparisonState#SIZE_MISMATCH}.
     *
     * @param expected expected {@link BufferedImage} object.
     * @param actual actual {@link BufferedImage} object.
     *
     * @return instance of the {@link ComparisonResult} object.
     */
    public static ComparisonResult defaultSizeMisMatchResult(BufferedImage expected, BufferedImage actual,float differencePercent) {
        return new ComparisonResult()
                .setComparisonState(ComparisonState.SIZE_MISMATCH)
                .setDifferencePercent(differencePercent)
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

    /**
     * Save the image to the provided {@link File} object.
     *
     * @param file the provided {@link File} object.
     * @return this {@link ComparisonResult} object.
     * @throws IOException due to save image.
     */
    public ComparisonResult writeResultTo(File file) throws IOException {
        ImageComparisonUtil.saveImage(file, result);
        return this;
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

    public float getDifferencePercent() {
        return differencePercent;
    }

    public ComparisonResult setDifferencePercent(float differencePercent) {
        this.differencePercent = differencePercent;
        return this;
    }


}
