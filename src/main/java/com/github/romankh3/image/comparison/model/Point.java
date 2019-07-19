package com.github.romankh3.image.comparison.model;

/**
 * Object contains x and y coordinates for point;
 */
public class Point {

    /**
     * X-coordinate.
     */
    private int x;

    /**
     * Y-coordinate.
     */
    private int y;

    /**
     * Create empty instance of the {@link Point}.
     */
    public Point() {
    }

    /**
     * Create instance of the {@link Point} with defined {@link Point#x} and {@link Point#y}.
     *
     * @param x provided X-coordinate.
     * @param y provided Y-coordinate.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set x = 0 and y = 0.
     */
    public void makeZeroPoint() {
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point point = (Point) o;

        if (x != point.x) {
            return false;
        }
        return y == point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
