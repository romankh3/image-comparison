package com.github.romankh3.image.comparison.model;

import java.awt.image.BufferedImage;

/**
 * Result of the comparison.
 */
public class ComparisonResult {

    /**
     * {@link BufferedImage} object of the image1.
     */
    private BufferedImage image1;

    /**
     * {@link BufferedImage} object of the image2.
     */
    private BufferedImage image2;

    /**
     * {@link BufferedImage} object of the comparison result.
     */
    private BufferedImage result;

    /**
     * State of the comparison.
     */
    private ComparisonState comparisonState;

    /**
     * Create instance of the {@link ComparisonResult} with {@link ComparisonState#SIZE_MISMATCH}.
     *
     * @return instance of the {@link ComparisonResult} object.
     */
    public static ComparisonResult sizeMissMatchResult(BufferedImage image1, BufferedImage image2) {
        return new ComparisonResult()
                .setComparisonState(ComparisonState.SIZE_MISMATCH)
                .setImage1(image1)
                .setImage2(image2)
                .setResult(image1);
    }

    public BufferedImage getImage1() {
        return image1;
    }

    public ComparisonResult setImage1(BufferedImage image1) {
        this.image1 = image1;
        return this;
    }

    public BufferedImage getImage2() {
        return image2;
    }

    public ComparisonResult setImage2(BufferedImage image2) {
        this.image2 = image2;
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
