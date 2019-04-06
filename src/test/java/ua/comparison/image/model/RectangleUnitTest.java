package ua.comparison.image.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit-level testing for {@link Rectangle} object.
 */
public class RectangleUnitTest {

    @Test
    public void testDefaultRectangle() {
        Rectangle rectangle = Rectangle.createDefault();

        assertEquals(rectangle.getMinX(), Integer.MAX_VALUE);
        assertEquals(rectangle.getMinY(), Integer.MAX_VALUE);
        assertEquals(rectangle.getMaxX(), Integer.MIN_VALUE);
        assertEquals(rectangle.getMaxY(), Integer.MIN_VALUE);
    }

    @Test
    public void testGetterSetter() {
        Rectangle rectangle = Rectangle.createDefault();

        rectangle.setMinX(10);
        rectangle.setMinY(20);
        rectangle.setMaxX(30);
        rectangle.setMaxY(40);

        assertEquals(rectangle.getMinX(), 10);
        assertEquals(rectangle.getMinY(), 20);
        assertEquals(rectangle.getMaxX(), 30);
        assertEquals(rectangle.getMaxY(), 40);
    }
}
