package com.github.romankh3.image.comparison.model;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

/**
 * Object contained data for a rectangle.
 */
public class Rectangle {

    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    private Point minPoint;
    private Point maxPoint;

    private Rectangle() {
    }

    public Rectangle(Rectangle rectangle) {
        this.minX = rectangle.getMinX();
        this.minY = rectangle.getMinY();
        this.maxX = rectangle.getMaxX();
        this.maxY = rectangle.getMaxY();

        this.minPoint = new Point(rectangle.getMinPoint().getX(), rectangle.getMinPoint().getY());
        this.maxPoint = new Point(rectangle.getMaxPoint().getX(), rectangle.getMaxPoint().getY());
    }

    public Rectangle(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

        this.minPoint = new Point(minX, minY);
        this.maxPoint = new Point(maxX, maxY);
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
        return new Rectangle(min(this.getMinX(), that.getMinX()),
                min(this.getMinY(), that.getMinY()),
                max(this.getMaxX(), that.getMaxX()),
                max(this.getMaxY(), that.getMaxY()));
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
        this.maxPoint = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

        this.minY = Integer.MAX_VALUE;
        this.minX = Integer.MAX_VALUE;
        this.minPoint = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public void makeZeroRectangle() {
        this.minPoint.makeZeroPoint();
        this.maxPoint.makeZeroPoint();
        this.minX = 0;
        this.minY = 0;
        this.maxX = 0;
        this.maxY = 0;
    }

    public Point getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(Point minPoint) {
        this.minPoint = minPoint;
    }

    public Point getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(Point maxPoint) {
        this.maxPoint = maxPoint;
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

        return !hasDifferentPoints(rectangle);
    }

    private boolean hasDifferentPoints(Rectangle rectangle) {
        return minX != rectangle.minX || minY != rectangle.minY || maxX != rectangle.maxX || maxY != rectangle.maxY;
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
