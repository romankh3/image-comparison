package ua.comparison.image.model;

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

    /**
     * Create new {@link Rectangle} via merging this and that.
     *
     * @param that {@link Rectangle} for merging with this.
     *
     * @return new merged {@link Rectangle}.
     */
    public Rectangle merge(Rectangle that) {
        Rectangle mergedRectangle = null;
        if (isR2InsideR1(this, that)) {
            mergedRectangle = new Rectangle(this);
        } else if (isR2InsideR1(that, this)) {
            mergedRectangle = new Rectangle(that);
        } else if(isR2UnderR1(that, this)) {
            mergedRectangle = new Rectangle(that.getMinX(), that.getMinY(), this.getMaxX(), this.getMaxY());
        } else if(isR2UnderR1(this, that)) {
            mergedRectangle = new Rectangle(this.getMinX(), this.getMinY(), that.getMaxX(), that.getMaxY());
        } else if(isR1LeftwardR2(this, that)) {
            mergedRectangle = new Rectangle(this.getMinX(), that.getMinY(), that.getMaxX(), this.getMaxY());
        } else if(isR1LeftwardR2(that, this)) {
            mergedRectangle = new Rectangle(that.getMinX(), this.getMinY(), this.getMaxX(), that.getMaxY());
        }
        return mergedRectangle;
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
