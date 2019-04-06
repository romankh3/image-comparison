package ua.comparison.image.model;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Ignore;
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
        Assert.assertTrue(rectangleOne.isOverlapping(rectangleTwo));
    }

    /**
     * Cover non overlapping case:
     * .............
     * . R1        .
     * .           .
     * .............
     *     ..............
     *     . R2         .
     *     .            .
     *     ..............
     */
    @Test
    public void testNonOverlappingUpDown() {
        //given
        Rectangle rectangleOne = new Rectangle(1, 1, 4, 4);
        Rectangle rectangleTwo = new Rectangle(4, 6, 6, 8);

        //when-then
        Assert.assertFalse(rectangleOne.isOverlapping(rectangleTwo));
    }

    /**
     * Cover non overlapping case:
     * ...........
     * . R1      .  ...........
     * .         .  . R2      .
     * ...........  .         .
     *              .         .
     *              ...........
     */
    @Test
    public void testNonOverlappingLeftRight() {
        //given
        Rectangle rectangleOne = new Rectangle(2, 2, 4, 4);
        Rectangle rectangleTwo = new Rectangle(6, 8, 10, 12);

        //when-then
        Assert.assertFalse(rectangleOne.isOverlapping(rectangleTwo));
    }

    @Test
    @Ignore
    //todo should be written 3 tests for it.
    public void testMergeRectangles() {
        //given
        Rectangle rectangleOne = new Rectangle(2, 2, 8, 6);

        Rectangle rectangleTwo = new Rectangle(4, 4, 10, 10);

        Rectangle expected = new Rectangle(2, 2, 10, 10);

        //when
        rectangleOne.merge(rectangleTwo);

        //then
        Assert.assertEquals(expected, rectangleOne);
    }
}
