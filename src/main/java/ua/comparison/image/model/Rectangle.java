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
    private Point topLeft = new Point(maxX, maxY);
    private Point bottomRight = new Point(minX, minY);

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

    // TODO: 2019-03-15 to be implemented
    private boolean isInnerRectangle(Rectangle one, Rectangle two) {
        return false;
    }

    // TODO: 2019-03-15 to be implemented
    private boolean isFirstUpper(Rectangle one, Rectangle two) {
        return false;
    }

    //todo write unit test for this logic.
    public boolean isOverlapping(Rectangle other) {
        if (this.topLeft.getY() < other.bottomRight.getY() || this.bottomRight.getY() > other.topLeft.getY()) {
            return false;
        }
        if (this.topLeft.getX() < other.bottomRight.getX() || this.bottomRight.getX() > other.topLeft.getX()) {
            return false;
        }
        return true;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
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
