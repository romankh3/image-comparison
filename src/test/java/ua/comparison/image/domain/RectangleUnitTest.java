package ua.comparison.image.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit-level testing for {@link Rectangle} object.
 */
public class RectangleUnitTest {

    @Test
    public void testGetterSetter() {
        Rectangle rectangle = new Rectangle(1, 2, 3, 4 );

        assertEquals( rectangle.getMinX(), 1 );
        assertEquals( rectangle.getMinY(), 2 );
        assertEquals( rectangle.getMaxX(), 3 );
        assertEquals( rectangle.getMaxY(), 4 );

        rectangle.setMinX( 10 );
        rectangle.setMinY( 20 );
        rectangle.setMaxX( 30 );
        rectangle.setMaxY( 40 );

        assertEquals( rectangle.getMinX(), 10 );
        assertEquals( rectangle.getMinY(), 20 );
        assertEquals( rectangle.getMaxX(), 30 );
        assertEquals( rectangle.getMaxY(), 40 );
    }

    @Test
    public void testDefaultRectangle() {
        Rectangle rectangle = Rectangle.createDefaultRectangle();

        assertEquals( rectangle.getMinX(), Integer.MAX_VALUE );
        assertEquals( rectangle.getMinY(), Integer.MAX_VALUE );
        assertEquals( rectangle.getMaxX(), Integer.MIN_VALUE );
        assertEquals( rectangle.getMaxY(), Integer.MIN_VALUE );
    }

}
