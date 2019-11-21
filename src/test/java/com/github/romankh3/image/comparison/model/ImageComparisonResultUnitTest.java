package com.github.romankh3.image.comparison.model;

import org.junit.Test;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit-level testing for {@link ImageComparisonResult} object.
 */
public class ImageComparisonResultUnitTest {

    @Test
    public void testObjectCreation() {
        //when
        ImageComparisonResult imageComparisonResult = new ImageComparisonResult();

        //then
        assertNotNull("ComparisonResult object is not null", imageComparisonResult);
    }

    @Test
    public void testGettersAndSetters() {
        //when
        ImageComparisonResult imageComparisonResult = new ImageComparisonResult().setImageComparisonState(ImageComparisonState.MATCH)
                .setExpected(readImageFromResources("expected.png"))
                .setActual(readImageFromResources("actual.png"))
                .setResult(readImageFromResources("result.png"));

        //then
        assertEquals(ImageComparisonState.MATCH, imageComparisonResult.getImageComparisonState());
        assertNotNull(imageComparisonResult.getExpected());
        assertNotNull(imageComparisonResult.getActual());
        assertNotNull(imageComparisonResult.getResult());
    }

}
