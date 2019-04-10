package ua.comparison.image.model;

import java.util.Arrays;
import java.util.List;

/**
 * Object contained data for a rectangle.
 */
public class Rectangle {

    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    private Rectangle() {
    }

    public Rectangle(Rectangle rectangle) {
        this.minX = rectangle.getMinX();
        this.minY = rectangle.getMinY();
        this.maxX = rectangle.getMaxX();
        this.maxY = rectangle.getMaxY();
    }

    public Rectangle(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Create default {@link Rectangle} object.
     */
    public static Rectangle createDefault() {
        Rectangle defaultRectangle = new Rectangle();

        defaultRectangle.setDefaultValues();

        return defaultRectangle;
    }

    public static Rectangle createZero() {
        Rectangle rectangle = new Rectangle();
        rectangle.makeZeroRectangle();
        return rectangle;
    }

    /**
     * Create new {@link Rectangle} via merging this and that.
     *
     * @param that {@link Rectangle} for merging with this.
     * @return new merged {@link Rectangle}.
     */
    public Rectangle merge(Rectangle that) {
        List<Integer> xValues = Arrays.asList(this.getMinX(), this.getMaxX(), that.getMinX(), that.getMaxX());
        List<Integer> yValues = Arrays.asList(this.getMinY(), this.getMaxY(), that.getMinY(), that.getMaxY());

        int minX = xValues.stream().reduce(Integer::min).get();
        int maxX = xValues.stream().reduce(Integer::max).get();
        int minY = yValues.stream().reduce(Integer::min).get();
        int maxY = yValues.stream().reduce(Integer::max).get();

        return new Rectangle(minX, minY, maxX, maxY);
    }

    private boolean isR1LeftwardR2(Rectangle r1, Rectangle r2) {
        return r1.getMinX() <= r2.getMinX() && r1.getMaxY() >= r2.getMaxY() &&
                r1.getMaxX() <= r2.getMaxX() && r1.getMinY() >= r1.getMinY();
    }

    private boolean isR2UnderR1(Rectangle r1, Rectangle r2) {
        return r1.getMinX() <= r2.getMinX() && r1.getMinY() <= r2.getMinY() &&
                r1.getMaxX() <= r2.getMaxX() && r1.getMaxY() <= r2.getMaxY();
    }

    /**
     * Tell if r2 inside r1.
     *
     * @param r1 first rectangle
     * @param r2 second rectangle
     * @return true if r2 inside r1, otherwise false.
     */
    private boolean isR2InsideR1(Rectangle r1, Rectangle r2) {
        return r1.getMinX() <= r2.getMinX() && r1.getMinY() <= r2.getMinY() &&
                r1.getMaxX() >= r2.getMaxX() && r1.getMaxY() >= r2.getMaxY();
    }

    public boolean isOverlapping(Rectangle that) {
        if (this.maxY < that.minY || that.maxY < this.minY) {
            return false;
        }
        return this.maxX >= that.minX && that.maxX >= this.minX;
    }

    public void setDefaultValues() {
        this.maxX = Integer.MIN_VALUE;
        this.maxY = Integer.MIN_VALUE;

        this.minY = Integer.MAX_VALUE;
        this.minX = Integer.MAX_VALUE;
    }

    public void makeZeroRectangle() {
        this.minX = 0;
        this.minY = 0;
        this.maxX = 0;
        this.maxY = 0;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getWidth() {
        return maxY - minY;
    }

    public int getHeight() {
        return maxX - minX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Rectangle rectangle = (Rectangle) o;

        if (minX != rectangle.minX) {
            return false;
        }
        if (minY != rectangle.minY) {
            return false;
        }
        if (maxX != rectangle.maxX) {
            return false;
        }
        return maxY == rectangle.maxY;
    }

    @Override
    public int hashCode() {
        int result = minX;
        result = 31 * result + minY;
        result = 31 * result + maxX;
        result = 31 * result + maxY;
        return result;
    }
}
