package com.github.romankh3.image.comparison.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit-level testing for {@link Point}.
 */
public class PointUnitTest {

    @Test
    public void testXandYConstructor() {
        //when
        Point point = new Point(1, 2);

        //then
        assertEquals(1, point.getX());
        assertEquals(2, point.getY());

        //and-when
        point.setX(5);
        point.setY(10);

        //then
        assertEquals(5, point.getX());
        assertEquals(10, point.getY());
    }

    @Test
    public void testEquals() {
        //when
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);

        //then
        assertTrue(p1.equals(p2));

        //and
        assertTrue(p1.equals(p1));

        //and
        assertFalse(p1.equals(null));

        //and-when
        Point p3 = new Point(2, 2);

        //then
        assertFalse(p1.equals(p3));
    }

    @Test
    public void testHashCode() {
        //given
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);

        //when
        int hashCode1 = p1.hashCode();
        int hashCode2 = p2.hashCode();

        //then
        assertEquals(hashCode1, hashCode2);
    }
}
