package com.github.romankh3.image.comparison.model;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.junit.Assert;
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
    public void testGettersAndSetters() throws IOException, URISyntaxException {
        //when
        ComparisonResult comparisonResult = new ComparisonResult();
        comparisonResult.setComparisonState(ComparisonState.MATCH);
        comparisonResult.setImage1(readImageFromResources("image1.png"));
        comparisonResult.setImage2(readImageFromResources("image2.png"));
        comparisonResult.setResult(readImageFromResources("result1.png"));
        comparisonResult.setRectangles(new ArrayList<>());

        //then
        Assert.assertEquals(ComparisonState.MATCH, comparisonResult.getComparisonState());
        Assert.assertNotNull(comparisonResult.getImage1());
        Assert.assertNotNull(comparisonResult.getImage2());
        Assert.assertNotNull(comparisonResult.getResult());
        Assert.assertTrue(comparisonResult.getRectangles().isEmpty());
    }

}
