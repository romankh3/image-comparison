package com.github.romankh3.image.comparison.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit-level testing for {@link ExcludedAreas} object.")
public class ExcludedAreasUnitTest {

    @DisplayName("Should properly create bean")
    @Test
    public void shouldProperlyCreateBean() {
        //when
        ExcludedAreas excludedAreas = new ExcludedAreas();

        //then
        assertNotNull(excludedAreas);
    }

    @DisplayName("Should properly get rectangle list")
    @Test
    public void shouldProperlyWorkGettingRectangleList() {
        //given
        List<Rectangle> rectangles = Arrays.asList(Rectangle.createDefault(), Rectangle.createZero());

        //when
        ExcludedAreas excludedAreas = new ExcludedAreas(rectangles);

        //then
        assertEquals(rectangles, excludedAreas.getExcluded());
    }

    @DisplayName("Should check coordinates without creating Point objects")
    @Test
    public void shouldCheckCoordinates() {
        //given
        ExcludedAreas excludedAreas = new ExcludedAreas(Arrays.asList(new Rectangle(1, 1, 3, 3)));

        //then
        assertTrue(excludedAreas.contains(2, 2));
        assertFalse(excludedAreas.contains(4, 4));
    }

}
