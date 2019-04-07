package ua.comparison.image.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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

    /**
     * Cover overlapping case, drawing below:
     * ............
     * . R1       .
     * .    ...........
     * .    . R2  .   .
     * ............   .
     *      ...........
     */
    @Test
    public void testIsOverlap() {
        //given
        Rectangle rectangleOne = new Rectangle(2, 2, 8, 6);

        Rectangle rectangleTwo = new Rectangle(4, 4, 10, 10);

        //when-then
        assertTrue(rectangleOne.isOverlapping(rectangleTwo));
    }

    /**
     * Cover non overlapping case, drawing below:
     *    .............
     *    . R1        .
     *    .           .
     *    .............
     * ------------------------
     *      ..............
     *      . R2         .
     *      .            .
     *      ..............
     */
    @Test
    public void testNonOverlappingUpDown() {
        //given
        Rectangle rectangleOne = new Rectangle(1, 1, 4, 4);
        Rectangle rectangleTwo = new Rectangle(4, 6, 6, 8);

        //when-then
        assertFalse(rectangleOne.isOverlapping(rectangleTwo));
    }

    /**
     * Cover non overlapping case, drawing below:
     * ........... |
     * . R1      . |  ...........
     * .         . |  . R2      .
     * ........... |  .         .
     *             |  .         .
     *             |  ...........
     *             |
     */
    @Test
    public void testNonOverlappingLeftRight() {
        //given
        Rectangle rectangleOne = new Rectangle(2, 2, 4, 4);
        Rectangle rectangleTwo = new Rectangle(6, 2, 10, 4);

        //when-then
        assertFalse(rectangleOne.isOverlapping(rectangleTwo));
    }

    @Test
    public void testEqual() {
        //given
        Rectangle rectangleOne = new Rectangle(1, 1, 2, 2);
        Rectangle rectangleTwo = new Rectangle(1, 1, 2, 2);

        //when-then
        assertEquals(rectangleOne, rectangleTwo);
    }

    @Test
    public void testEqualTheSame() {
        //given
        Rectangle rectangleOne = new Rectangle(1, 1, 2, 2);

        //when-then
        assertEquals(rectangleOne, rectangleOne);
    }

    @Test
    public void testEqualNull() {
        //given
        Rectangle rectangle = new Rectangle(1, 1, 2, 2);

        //when-then
        assertNotEquals(rectangle, null);
    }

    @Test
    public void testNonEqual() {
        //given
        Rectangle rectangle = new Rectangle(1, 1, 2, 2);
        Rectangle rectangleMinX = new Rectangle(2, 1, 2, 2);
        Rectangle rectangleMinY = new Rectangle(1, 2, 2, 2);
        Rectangle rectangleMaxX = new Rectangle(1, 1, 4, 2);
        Rectangle rectangleMaxY = new Rectangle(1, 1, 2, 5);

        //when-then
        assertNotEquals(rectangle, rectangleMinX);
        assertNotEquals(rectangle, rectangleMinY);
        assertNotEquals(rectangle, rectangleMaxX);
        assertNotEquals(rectangle, rectangleMaxY);

    }


    @Test
    public void testTheSameHashCode() {
        //given
        Rectangle rectangleOne = new Rectangle(1, 1, 2, 2);
        Rectangle rectangleTwo = new Rectangle(1, 1, 2, 2);

        //when
        int hashCodeOne = rectangleOne.hashCode();
        int hashCodeTwo = rectangleTwo.hashCode();

        //then
        assertEquals(hashCodeOne, hashCodeTwo);
    }

    @Test
    public void testNonTheSameHashCode() {
        //given
        Rectangle rectangleOne = new Rectangle(1, 1, 2, 2);
        Rectangle rectangleTwo = new Rectangle(1, 3, 2, 2);

        //when
        int hashCodeOne = rectangleOne.hashCode();
        int hashCodeTwo = rectangleTwo.hashCode();

        //then
        assertNotEquals(hashCodeOne, hashCodeTwo);
    }

}
