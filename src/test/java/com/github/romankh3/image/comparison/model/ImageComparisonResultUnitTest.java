package com.github.romankh3.image.comparison.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit-level testing for {@link ImageComparisonResult} object")
public class ImageComparisonResultUnitTest {

    @DisplayName("Should properly work object creation")
    @Test
    public void shouldProperlyWorkObjectCreation() {
        //when
        ImageComparisonResult imageComparisonResult = new ImageComparisonResult();

        //then
        assertNotNull(imageComparisonResult);
    }

    @DisplayName("Should properly work getters and setters")
    @Test
    public void shouldProperlyWorkGettersAndSetters() {
        //when
        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(Rectangle.createDefault());
        ImageComparisonResult imageComparisonResult = new ImageComparisonResult()
                .setImageComparisonState(ImageComparisonState.MATCH)
                .setExpected(readImageFromResources("expected.png"))
                .setActual(readImageFromResources("actual.png"))
                .setResult(readImageFromResources("result.png"))
                .setRectangles(rectangles);

        //then
        assertEquals(ImageComparisonState.MATCH, imageComparisonResult.getImageComparisonState());
        assertNotNull(imageComparisonResult.getExpected());
        assertNotNull(imageComparisonResult.getActual());
        assertNotNull(imageComparisonResult.getResult());
        assertEquals(imageComparisonResult.getRectangles(), rectangles);
    }

}
