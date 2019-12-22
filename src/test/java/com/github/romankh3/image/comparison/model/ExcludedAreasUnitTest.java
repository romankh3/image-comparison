package com.github.romankh3.image.comparison.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Unit-level testing for {@link ExcludedAreas} object.
 */
public class ExcludedAreasUnitTest {

    @Test
    public void testBeanCreation() {
        //when
        ExcludedAreas excludedAreas = new ExcludedAreas();

        //then
        assertNotNull(excludedAreas);
    }

    @Test
    public void testGettingRectangleList() {
        //given
        List<Rectangle> rectangles = Arrays.asList(Rectangle.createDefault(), Rectangle.createZero());

        //when
        ExcludedAreas excludedAreas = new ExcludedAreas(rectangles);

        //then
        assertEquals(rectangles, excludedAreas.getExcluded());
    }

}
