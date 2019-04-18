package com.github.romankh3.image.comparison.model;

import java.awt.image.BufferedImage;
import java.util.List;

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
     * The collection of the reactangles with the differences.
     */
    private List<Rectangle> rectangles;

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    public void setRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
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
