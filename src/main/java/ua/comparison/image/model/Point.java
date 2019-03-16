package ua.comparison.image.model;

//todo add unit tests.
/**
 * Object contained data of the (x,y) point.
 */
public class Point {

    /**
     * X coordinate for point.
     */
    private int x;

    /**
     * Y coordinate for point.
     */
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //todo add test for it.
    public int calculateModule() {
        return x*x + y*y;
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
