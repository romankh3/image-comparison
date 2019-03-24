package ua.comparison.image.model;

/**
 * Object contained data for a rectangle.
 */
public class Rectangle {

    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    /**
     * Create default {@link Rectangle} object.
     */
    public static Rectangle createDefault() {
        Rectangle defaultRectangle = new Rectangle();

        defaultRectangle.setMaxX(Integer.MIN_VALUE);
        defaultRectangle.setMaxY(Integer.MIN_VALUE);
        defaultRectangle.setMinX(Integer.MAX_VALUE);
        defaultRectangle.setMinY(Integer.MAX_VALUE);

        return defaultRectangle;
    }

    public void merge(Rectangle that) {
        if(this.minX <= that.minX && this.minY <= that.minY && this.maxY >= that.maxY && this.maxX >= that.maxX) {
            that.setDefaultValues();
        }
        if(that.minX <= this.minX && that.minY <= this.minY && that.maxY >= this.maxY && that.maxX >= this.maxX) {
            this.setDefaultValues();
        }
    }

    private boolean isFirstUpper(Rectangle that) {
        return false;
    }

    public boolean isOverlapping(Rectangle that) {
        if (this.maxY < that.minY || that.maxY < this.minY) {
            return false;
        }
        if (this.maxX < that.minX || that.maxX < this.minX) {
            return false;
        }
        return true;
    }

    public void setDefaultValues() {
        this.maxX = Integer.MIN_VALUE;
        this.maxY = Integer.MIN_VALUE;

        this.minY = Integer.MAX_VALUE;
        this.minX = Integer.MAX_VALUE;
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
