package com.github.romankh3.image.comparison.model;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import org.junit.Test;

/**
 * Unit-level testing for {@link ComparisonResult} object.
 */
public class ComparisonResultUnitTest {

    @Test
    public void testObjectCreation() {
        //when
        ComparisonResult comparisonResult = new ComparisonResult();

        //then
        assertNotNull("ComparisonResult object is not null", comparisonResult);
    }

    @Test
    public void testGettersAndSetters() throws IOException {
        //when
        ComparisonResult comparisonResult = new ComparisonResult().setComparisonState(ComparisonState.MATCH)
                .setExpected(readImageFromResources("expected.png"))
                .setActual(readImageFromResources("actual.png"))
                .setResult(readImageFromResources("result.png"));

        //then
        assertEquals(ComparisonState.MATCH, comparisonResult.getComparisonState());
        assertNotNull(comparisonResult.getExpected());
        assertNotNull(comparisonResult.getActual());
        assertNotNull(comparisonResult.getResult());
    }

}
