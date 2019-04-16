package com.github.romankh3.image.comparison.model;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

import java.util.Objects;

/**
 * Object contained data for a rectangle.
 */
public class Rectangle {

    private Point minPoint;
    private Point maxPoint;

    private Rectangle() {
        minPoint = new Point();
        maxPoint = new Point();
    }

    public Rectangle(Rectangle rectangle) {
        this.minPoint = new Point(rectangle.getMinPoint().getX(), rectangle.getMinPoint().getY());
        this.maxPoint = new Point(rectangle.getMaxPoint().getX(), rectangle.getMaxPoint().getY());
    }

    public Rectangle(int minX, int minY, int maxX, int maxY) {
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
        return new Rectangle(min(this.getMinPoint().getX(), that.getMinPoint().getX()),
                min(this.getMinPoint().getY(), that.getMinPoint().getY()),
                max(this.getMaxPoint().getX(), that.getMaxPoint().getX()),
                max(this.getMaxPoint().getY(), that.getMaxPoint().getY()));
    }

    public boolean isOverlapping(Rectangle that) {
        if (this.getMaxPoint().getY() < that.getMinPoint().getY() ||
                that.getMaxPoint().getY() < this.getMinPoint().getY()) {
            return false;
        }
        return this.getMaxPoint().getX() >= that.getMinPoint().getX() &&
                that.getMaxPoint().getX() >= this.getMinPoint().getX();
    }

    public void setDefaultValues() {
        this.maxPoint = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.minPoint = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public void makeZeroRectangle() {
        this.minPoint.makeZeroPoint();
        this.maxPoint.makeZeroPoint();
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

    public int getWidth() {
        return maxPoint.getY() - minPoint.getY();
    }

    public int getHeight() {
        return maxPoint.getX() - minPoint.getX();
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
        return minPoint.equals(rectangle.minPoint) &&
                maxPoint.equals(rectangle.maxPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minPoint, maxPoint);
    }
}
