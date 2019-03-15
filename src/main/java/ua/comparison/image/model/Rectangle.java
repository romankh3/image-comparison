package ua.comparison.image.model;

/**
 * Object contained data for a rectangle.
 */
public class Rectangle {

    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    //todo Move all code to use {@link Point}.
    private Point bottomLeft = new Point(maxX, maxY);
    private Point topRight = new Point(minX, minY);

    //todo write unit test for this logic.
    public void merge(Rectangle other) {
        if(isInnerRectangle(this, other)) {
            //todo To be Implemented
        } else if (isFirstUpper(this, other)) {
            //todo To Be implemented
        } else {
            //todo to be implemented
        }
    }

    private boolean isInnerRectangle(Rectangle one, Rectangle two) {
        return false;
    }

    private boolean isFirstUpper(Rectangle one, Rectangle two) {
        return false;
    }

    //todo write unit test for this logic.
    public boolean isOverlapping(Rectangle other) {
        if (this.topRight.getY() < other.bottomLeft.getY() || this.bottomLeft.getY() > other.topRight.getY()) {
            return false;
        }
        if (this.topRight.getX() < other.bottomLeft.getX() || this.bottomLeft.getX() > other.topRight.getX()) {
            return false;
        }
        return true;
    }

    /**
     * Module of the vector from the start of the matrix to point of the
     * beginning {@link Rectangle} object.
     */
    public int calculateMinVectorModule() {
        return minX*minX + minY*minY;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() { return minY; }

    public int getMaxX() { return maxX; }

    public int getMaxY() { return maxY; }

    public int getWidth() { return maxY - minY; }

    public int getHeight() { return maxX - minX; }

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
