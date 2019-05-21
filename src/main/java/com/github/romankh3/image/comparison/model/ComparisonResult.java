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
     * Create instance of the {@link ComparisonResult} with {@link ComparisonState#SIZE_MISSMATCH}.
     *
     * @return instance of the {@link ComparisonResult} object.
     */
    public static ComparisonResult sizeMissMatchResult() {
        ComparisonResult comparisonResult = new ComparisonResult();
        comparisonResult.setComparisonState(ComparisonState.SIZE_MISSMATCH);
        return comparisonResult;
    }

    public BufferedImage getImage1() {
        return image1;
    }

    public void setImage1(BufferedImage image1) {
        this.image1 = image1;
    }

    public BufferedImage getImage2() {
        return image2;
    }

    public void setImage2(BufferedImage image2) {
        this.image2 = image2;
    }

    public BufferedImage getResult() {
        return result;
    }

    public void setResult(BufferedImage result) {
        this.result = result;
    }

    public ComparisonState getComparisonState() {
        return comparisonState;
    }

    public void setComparisonState(ComparisonState comparisonState) {
        this.comparisonState = comparisonState;
    }


}
