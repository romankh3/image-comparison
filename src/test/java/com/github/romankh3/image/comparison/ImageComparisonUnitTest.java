package com.github.romankh3.image.comparison;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static com.github.romankh3.image.comparison.model.ComparisonState.MATCH;
import static com.github.romankh3.image.comparison.model.ComparisonState.MISSMATCH;
import static com.github.romankh3.image.comparison.model.ComparisonState.SIZE_MISSMATCH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.github.romankh3.image.comparison.model.ComparisonResult;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.github.romankh3.image.comparison.model.Rectangle;
import org.junit.Test;

/**
 * Unit-level testing for {@link ImageComparison} object.
 */
public class ImageComparisonUnitTest extends BaseTest {

    /**
     * The most important test. Shown, that the changes in algorithm,
     * don't break the main behaviour and result as expected.
     */
    @Test
    public void testCorrectWorkingImage1Image2() throws IOException, URISyntaxException {
        //given
        BufferedImage expectedResultImage = readImageFromResources("result1.png");

        //when
        ComparisonResult comparisonResult = new ImageComparison("image1.png", "image2.png").compareImages();

        //then
        assertEquals(MISSMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedResultImage, comparisonResult.getResult());
    }

    @Test
    public void testMaximalRectangleCount() throws IOException, URISyntaxException {
        //given
        ImageComparison imageComparison = new ImageComparison("image1.png", "image2.png");
        imageComparison.setMaximalRectangleCount(3);
        BufferedImage expectedImage = readImageFromResources("maximalReqtangleCountResult.png");

        //when
        ComparisonResult comparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISSMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedImage, comparisonResult.getResult());
    }

    @Test
    public void testImagesWithTotallyDifferentImages() throws IOException, URISyntaxException {
        //when
        BufferedImage expectedResult = readImageFromResources("totallyDifferentImageResult.png");

        //when
        ComparisonResult comparisonResult =
                new ImageComparison("image1.png", "image1TotallyDifferent.png").compareImages();

        //then
        assertEquals(MISSMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedResult, comparisonResult.getResult());
    }

    @Test
    public void testMinimalRectangleSize() throws IOException, URISyntaxException {
        //given
        ImageComparison imageComparison = new ImageComparison("image1.png", "image2.png");
        imageComparison.setMinimalRectangleSize(10);
        BufferedImage expectedImage = readImageFromResources("minimalRectangleSizeResult.png");

        //when
        ComparisonResult comparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISSMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedImage, comparisonResult.getResult());
    }

    /**
     * Test issue #17. It was StackOverFlowError.
     */
    @Test
    public void testIssue17() throws IOException, URISyntaxException {
        //when
        ComparisonResult comparisonResult = new ImageComparison("b1#17.png", "b2#17.png").compareImages();

        //then
        assertEquals(MISSMATCH, comparisonResult.getComparisonState());
        assertNotNull(comparisonResult.getResult());
    }

    /**
     * Test issue #21. It was StackOverFlowError.
     */
    @Test
    public void testIssue21() throws IOException, URISyntaxException {
        //given
        BufferedImage expectedResultImage = readImageFromResources("result#21.png");

        //when
        ComparisonResult comparisonResult = new ImageComparison("b1#21.png", "b2#21.png").compareImages();

        //then
        assertEquals(MISSMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedResultImage, comparisonResult.getResult());
    }

    /**
     * Test issue #11.
     */
    @Test
    public void testIssue11() throws IOException, URISyntaxException {
        //given
        BufferedImage expectedResultImage = readImageFromResources("result#11.png");

        BufferedImage image1 = readImageFromResources("b1#11.png");
        BufferedImage image2 = readImageFromResources("b2#11.png");

        //when
        ComparisonResult comparisonResult = new ImageComparison(image1, image2).compareImages();

        //then
        assertEquals(MISSMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedResultImage, comparisonResult.getResult());
    }

    /**
     * Verify that it is possible to use a thick line in the rectangle
     */
    @Test
    public void testRectangleWithLineWidth10() throws IOException, URISyntaxException {
        //given
        BufferedImage expectedResultImage = readImageFromResources("resultThickRectangle.png");

        //when
        ImageComparison imageComparison = new ImageComparison("b1#11.png", "b2#11.png");
        imageComparison.setRectangleLineWidth(10);
        ComparisonResult comparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISSMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedResultImage, comparisonResult.getResult());
        assertEquals(10, imageComparison.getRectangleLineWidth());
    }

    @Test
    public void testShouldReturnARectangleList() throws IOException, URISyntaxException {
        //Arrange
        BufferedImage original = readImageFromResources("b1#17.png");
        BufferedImage masked = readImageFromResources("Masked#58.png");
        List<Rectangle> expectedRectangleList = new ArrayList<>();
        expectedRectangleList.add(new Rectangle(0, 131, 224, 224));
        ImageComparison imageComparison = new ImageComparison(original, masked);

        //Act
        List<Rectangle> actualRectangleList = imageComparison.createMask();

        //Assert
        assertEquals(1, actualRectangleList.size());
        assertEquals(expectedRectangleList.get(0), actualRectangleList.get(0));
    }

    @Test
    public void testSizeMissMatch() {
        //given
        BufferedImage image1 = new BufferedImage(10, 10, 10);
        BufferedImage image2 = new BufferedImage(12, 12, 10);

        //when
        ComparisonResult comparisonResult = new ImageComparison(image1, image2).compareImages();

        //then
        assertEquals(SIZE_MISSMATCH, comparisonResult.getComparisonState());
    }

    @Test
    public void testShouldIgnoreExcludedArea() throws IOException, URISyntaxException {
        //Arrange
        BufferedImage image1 = readImageFromResources("b1#17.png");
        BufferedImage image2 = readImageFromResources("MaskedComparison#58.png");
        List<Rectangle> excludedAreas = new ArrayList<>();
        excludedAreas.add(new Rectangle(0, 131, 224, 224));
        ImageComparison imageComparison = new ImageComparison(image1, image2);
        imageComparison.setExcludedAreas(excludedAreas);

        //Act
        ComparisonResult result = imageComparison.compareImages();

        //Assert
        assertEquals(result.getComparisonState(), MATCH);
    }

    @Test
    public void testMatchSize() throws IOException, URISyntaxException {
        //when
        ComparisonResult comparisonResult = new ImageComparison("image1.png", "image1.png").compareImages();

        //then
        assertEquals(MATCH, comparisonResult.getComparisonState());
    }

    @Test
    public void testGettersAnsSetters() throws IOException, URISyntaxException {
        //given
        ImageComparison imageComparison = new ImageComparison("image1.png", "image2.png");

        //when
        imageComparison.setMinimalRectangleSize(100);
        imageComparison.setMaximalRectangleCount(200);
        imageComparison.setRectangleLineWidth(300);
        imageComparison.setThreshold(400);

        //then
        assertEquals(String.valueOf(100), String.valueOf(imageComparison.getMinimalRectangleSize()));
        assertEquals(String.valueOf(200), String.valueOf(imageComparison.getMaximalRectangleCount()));
        assertEquals(String.valueOf(300), String.valueOf(imageComparison.getRectangleLineWidth()));
        assertEquals(String.valueOf(400), String.valueOf(imageComparison.getThreshold()));
    }

    @Test(expected = SizeMissMatchException.class)
    public void testShouldTriggerExceptionForSizeMissMatch() throws IOException, URISyntaxException {
        //Arrange
        BufferedImage image1 = readImageFromResources("b1#17.png");
        BufferedImage image2 = readImageFromResources("b1#21.png");
        ImageComparison imageComparison = new ImageComparison(image1, image2);

        //Act
        imageComparison.createMask();
    }
}
