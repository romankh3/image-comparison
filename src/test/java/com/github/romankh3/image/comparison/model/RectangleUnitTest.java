package com.github.romankh3.image.comparison.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit-level testing for {@link Rectangle} object.")
public class RectangleUnitTest {

    @DisplayName("Should properly create default rectangle")
    @Test
    public void shouldProperlyCreateDefaultRectangle() {
        Rectangle rectangle = Rectangle.createDefault();

        assertEquals(rectangle.getMinPoint().x, Integer.MAX_VALUE);
        assertEquals(rectangle.getMinPoint().y, Integer.MAX_VALUE);
        assertEquals(rectangle.getMaxPoint().x, Integer.MIN_VALUE);
        assertEquals(rectangle.getMaxPoint().y, Integer.MIN_VALUE);
    }

    @DisplayName("Should properly work getters and setters")
    @Test
    public void shouldProperlyWorkGetterSetter() {
        Rectangle rectangle = Rectangle.createDefault();

        rectangle.setMinPoint(new Point(10, 20));
        rectangle.setMaxPoint(new Point(30, 40));

        assertEquals(rectangle.getMinPoint().x, 10);
        assertEquals(rectangle.getMinPoint().y, 20);
        assertEquals(rectangle.getMaxPoint().x, 30);
        assertEquals(rectangle.getMaxPoint().y, 40);
    }

    /**
     * Cover overlapping case, drawing below:
     * ............
     * . R1       .
     * .    ...........
     * .    . R2  .   .
     * ............   .
     * ...........
     */
    @DisplayName("Should properly find overlapping")
    @Test
    public void shouldProperlyFindOverlapping() {
        //given
        Rectangle rectangleOne = new Rectangle(2, 2, 8, 6);

        Rectangle rectangleTwo = new Rectangle(4, 4, 10, 10);

        //when-then
        assertTrue(rectangleOne.isOverlapping(rectangleTwo));
    }

    /**
     * Cover non overlapping case, drawing below:
     * .............
     * . R1        .
     * .           .
     * .............
     * ------------------------
     * ..............
     * . R2         .
     * .            .
     * ..............
     */
    @DisplayName("Should properly find non-overlapping")
    @Test
    public void shouldProperlyFindNonOverlappingUpDown() {
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
     * |  .         .
     * |  ...........
     * |
     */
    @DisplayName("Should properly find non-overlapping for left right")
    @Test
    public void shouldFindNonOverlappingLeftRight() {
        //given
        Rectangle rectangleOne = new Rectangle(2, 2, 4, 4);
        Rectangle rectangleTwo = new Rectangle(6, 2, 10, 4);

        //when-then
        assertFalse(rectangleOne.isOverlapping(rectangleTwo));
    }

    @DisplayName("Should properly work equal rectangles")
    @Test
    public void shouldProperlyEqual() {
        //given
        Rectangle rectangleOne = new Rectangle(1, 1, 2, 2);
        Rectangle rectangleTwo = new Rectangle(1, 1, 2, 2);

        //when-then
        assertEquals(rectangleOne, rectangleTwo);
    }

    @DisplayName("Should properly equal tow the same rectangles")
    @Test
    public void shouldEqualTheSame() {
        //given
        Rectangle rectangleOne = new Rectangle(1, 1, 2, 2);

        //when-then
        assertEquals(rectangleOne, rectangleOne);
    }

    @DisplayName("Should properly equal with null")
    @Test
    public void shouldEqualNull() {
        //given
        Rectangle rectangle = new Rectangle(1, 1, 2, 2);

        //when-then
        assertNotEquals(rectangle, null);
    }

    @DisplayName("Should properly non equal")
    @Test
    public void shouldNonEqual() {
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


    @DisplayName("Should work the same work")
    @Test
    public void shouldProperlyWorkTheSameHashCode() {
        //given
        Rectangle rectangleOne = new Rectangle(1, 1, 2, 2);
        Rectangle rectangleTwo = new Rectangle(1, 1, 2, 2);

        //when
        int hashCodeOne = rectangleOne.hashCode();
        int hashCodeTwo = rectangleTwo.hashCode();

        //then
        assertEquals(hashCodeOne, hashCodeTwo);
    }

    @DisplayName("Should properly find non-equal hash codes")
    @Test
    public void shouldWorkNonTheSameHashCode() {
        //given
        Rectangle rectangleOne = new Rectangle(1, 1, 2, 2);
        Rectangle rectangleTwo = new Rectangle(1, 3, 2, 2);

        //when
        int hashCodeOne = rectangleOne.hashCode();
        int hashCodeTwo = rectangleTwo.hashCode();

        //then
        assertNotEquals(hashCodeOne, hashCodeTwo);
    }

    @DisplayName("Should properly clone by rectangle")
    @Test
    public void shouldProperlyCloneRectangleByConstructor() {
        //given
        Rectangle rectangle = new Rectangle(1, 1, 4, 4);

        //when
        Rectangle clonedRectangle = new Rectangle(rectangle);

        //then
        assertNotSame(rectangle, clonedRectangle);
        assertEquals(rectangle, clonedRectangle);
    }

    /**
     * Coven test with merging rectangles one into other.
     *
     * ................................................
     * . R1                                           .
     * .     ..............................           .
     * .     . R2                         .           .
     * .     .                            .           .
     * .     .                            .           .
     * .     ..............................           .
     * .                                              .
     * ................................................
     */
    @DisplayName("Should properly merge R1 inside R2")
    @Test
    public void shouldProperlyMergeR1InsideR2() {
        //given
        Rectangle r1 = new Rectangle(3, 3, 9, 5);
        Rectangle r2 = new Rectangle(1, 1, 14, 7);

        //when
        Rectangle mergedRectangleR1intoR2 = r1.merge(r2);
        Rectangle mergedRectangleR2intoR1 = r2.merge(r1);

        //then
        assertEquals(r2, mergedRectangleR1intoR2);
        assertEquals(r2, mergedRectangleR2intoR1);
    }

    /**
     * Cover test case with merging rectangles when R2 under R1.
     *
     * ....................
     * . R1               .
     * .      ....................
     * .      . R2        .      .
     * .      .           .      .
     * .      .           .      .
     * ....................      .
     * .                  .
     * ....................
     */
    @DisplayName("Should properly merge R2 under R1")
    @Test
    public void shouldProperlyMergeR2UnderR1() {
        //given
        Rectangle r1 = new Rectangle(4, 3, 8, 7);
        Rectangle r2 = new Rectangle(6, 5, 11, 10);

        Rectangle expectedMergedRectangle = new Rectangle(4, 3, 11, 10);

        //when
        Rectangle mergedRectangle1 = r1.merge(r2);
        Rectangle mergedRectangle2 = r2.merge(r1);

        //then
        assertEquals(expectedMergedRectangle, mergedRectangle1);
        assertEquals(expectedMergedRectangle, mergedRectangle2);
    }

    /**
     * Cover test case with merging rectangles when R1 leftward R1.
     *
     * ..........................
     * . R2                     .
     * .                        .
     * .........................        .
     * . R1    .               .        .
     * .       .               .        .
     * .       ..........................
     * .                       .
     * .                       .
     * .                       .
     * .                       .
     * .........................
     */
    @DisplayName("Should properly merge R1 leftward R2")
    @Test
    public void shouldProperlyMergeR1leftwardR2() {
        //given
        Rectangle r1 = new Rectangle(2, 4, 6, 7);
        Rectangle r2 = new Rectangle(4, 2, 10, 6);

        Rectangle expectedMergedRectangle = new Rectangle(2, 2, 10, 7);

        //when
        Rectangle mergedRectangle1 = r1.merge(r2);
        Rectangle mergedRectangle2 = r2.merge(r1);

        //then
        assertEquals(expectedMergedRectangle, mergedRectangle1);
        assertEquals(expectedMergedRectangle, mergedRectangle2);
    }

    @DisplayName("Should properly set Zero rectangle")
    @Test
    public void shouldProperlySetZeroRectangle() {
        //given
        Rectangle rectangle = new Rectangle(1, 2, 4, 5);

        Rectangle expectedZero = new Rectangle(0, 0, 0, 0);

        //when
        rectangle.makeZeroRectangle();

        //then
        assertEquals(expectedZero, rectangle);
    }
}
