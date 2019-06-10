package com.github.romankh3.image.comparison;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static com.github.romankh3.image.comparison.model.ComparisonState.MATCH;
import static com.github.romankh3.image.comparison.model.ComparisonState.MISMATCH;
import static com.github.romankh3.image.comparison.model.ComparisonState.SIZE_MISMATCH;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.github.romankh3.image.comparison.model.ComparisonResult;
import com.github.romankh3.image.comparison.model.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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
    public void testCorrectWorkingExpectedActual() throws IOException, URISyntaxException {
        //given
        BufferedImage expectedResultImage = readImageFromResources("result.png");

        File file = new File("build/test-images/result.png");

        //when
        ComparisonResult comparisonResult = new ImageComparison("expected.png", "actual.png")
                .compareImages()
                .writeResultTo(file);

        //then
        assertEquals(MISMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedResultImage, comparisonResult.getResult());
    }

    @Test
    public void testMaximalRectangleCount() throws IOException, URISyntaxException {
        //given
        ImageComparison imageComparison = new ImageComparison("expected.png", "actual.png");
        imageComparison.setMaximalRectangleCount(3);
        BufferedImage expectedImage = readImageFromResources("maximalRectangleCountResult.png");

        //when
        ComparisonResult comparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedImage, comparisonResult.getResult());
    }

    @Test
    public void testImagesWithTotallyDifferentImages() throws IOException, URISyntaxException {
        //when
        BufferedImage expectedResult = readImageFromResources("totallyDifferentImageResult.png");

        //when
        ComparisonResult comparisonResult =
                new ImageComparison("expected.png", "actualTotallyDifferent.png").compareImages();

        //then
        assertEquals(MISMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedResult, comparisonResult.getResult());
    }

    @Test
    public void testDestinationGetting() throws IOException, URISyntaxException {
        //given
        BufferedImage expected = readImageFromResources("expected.png");
        BufferedImage actual = readImageFromResources("actual.png");

        //when
        ImageComparison imageComparison = new ImageComparison(expected, actual)
                .setDestination(new File("result.png"));

        //then
        assertTrue(imageComparison.getDestination().isPresent());
    }

    @Test
    public void testMinimalRectangleSize() throws IOException, URISyntaxException {
        //given
        ImageComparison imageComparison = new ImageComparison("expected.png", "actual.png");
        imageComparison.setMinimalRectangleSize(10);
        BufferedImage expectedImage = readImageFromResources("minimalRectangleSizeResult.png");

        //when
        ComparisonResult comparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedImage, comparisonResult.getResult());
    }

    /**
     * Test issue #17. It was StackOverFlowError.
     */
    @Test
    public void testIssue17() throws IOException, URISyntaxException {
        //when
        ComparisonResult comparisonResult = new ImageComparison("expected#17.png", "actual#17.png").compareImages();

        //then
        assertEquals(MISMATCH, comparisonResult.getComparisonState());
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
        ComparisonResult comparisonResult = new ImageComparison("expected#21.png", "actual#21.png").compareImages();

        //then
        assertEquals(MISMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedResultImage, comparisonResult.getResult());
    }

    /**
     * Test issue #11.
     */
    @Test
    public void testIssue11() throws IOException, URISyntaxException {
        //given
        BufferedImage expectedResultImage = readImageFromResources("result#11.png");

        BufferedImage expected = readImageFromResources("expected#11.png");
        BufferedImage actual = readImageFromResources("actual#11.png");

        //when
        ComparisonResult comparisonResult = new ImageComparison(expected, actual).compareImages();

        //then
        assertEquals(MISMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedResultImage, comparisonResult.getResult());
    }

    /**
     * Verify that it is possible to use a thick line in the rectangle
     */
    @Test
    public void testRectangleWithLineWidth10() throws IOException, URISyntaxException {
        //given
        BufferedImage expectedResultImage = readImageFromResources("resultThickRectangle.png");

        BufferedImage expected = readImageFromResources("expected#11.png");
        BufferedImage actual = readImageFromResources("actual#11.png");

        ImageComparison imageComparison = new ImageComparison(expected, actual,
                new File("build/test-images/result.png"))
                .setRectangleLineWidth(10);

        //when
        ComparisonResult comparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedResultImage, comparisonResult.getResult());
        assertEquals(10, imageComparison.getRectangleLineWidth());
    }

    @Test
    public void testShouldReturnARectangleList() throws IOException, URISyntaxException {
        //given
        BufferedImage original = readImageFromResources("expected#17.png");
        BufferedImage masked = readImageFromResources("actualMasked#58.png");
        List<Rectangle> expectedRectangleList = new ArrayList<>();
        expectedRectangleList.add(new Rectangle(131, 0, 224, 224));
        ImageComparison imageComparison = new ImageComparison(original, masked);

        //when
        List<Rectangle> actualRectangleList = imageComparison.createMask();

        //then
        assertEquals(1, actualRectangleList.size());
        assertEquals(expectedRectangleList.get(0), actualRectangleList.get(0));
    }

    @Test
    public void testSizeMissMatch() throws IOException {
        //given
        BufferedImage expected = new BufferedImage(10, 10, 10);
        BufferedImage actual = new BufferedImage(12, 12, 10);

        //when
        ComparisonResult comparisonResult = new ImageComparison(expected, actual).compareImages();

        //then
        assertEquals(SIZE_MISMATCH, comparisonResult.getComparisonState());
    }

    @Test
    public void testShouldIgnoreExcludedArea() throws IOException, URISyntaxException {
        //given
        BufferedImage expected = readImageFromResources("expected#17.png");
        BufferedImage actual = readImageFromResources("actualMaskedComparison#58.png");
        List<Rectangle> excludedAreas = new ArrayList<>();
        excludedAreas.add(new Rectangle(131, 0, 224, 224));
        ImageComparison imageComparison = new ImageComparison(expected, actual).setExcludedAreas(excludedAreas);

        //when
        ComparisonResult result = imageComparison.compareImages();

        //then
        assertEquals(result.getComparisonState(), MATCH);
    }

    /**
     * Test issue #98 and #97 to see the drawn excluded areas.
     */
    @Test
    public void testIssue98() throws IOException, URISyntaxException {
        //given
        BufferedImage expected = readImageFromResources("expected#98.png");
        BufferedImage actual = readImageFromResources("actual#98.png");

        List<Rectangle> excludedAreas = asList(
                new Rectangle(80, 388, 900, 514),
                new Rectangle(410, 514, 900, 565),
                new Rectangle(410, 636, 900, 754)
        );

        BufferedImage expectedImage = readImageFromResources("result#98WithExcludedAreas.png");

        ImageComparison imageComparison = new ImageComparison(expected, actual)
                .setExcludedAreas(excludedAreas)
                .setRectangleLineWidth(5)
                .setDrawExcludedRectangles(true);

        //when
        ComparisonResult comparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedImage, comparisonResult.getResult());
    }

    /**
     * Test issue #113 to draw red and green rectangles.
     */
    @Test
    public void testIssue113() throws IOException, URISyntaxException {
        //given
        BufferedImage expected = readImageFromResources("expected#98.png");
        BufferedImage actual = readImageFromResources("actual#98.png");

        List<Rectangle> excludedAreas = asList(
                new Rectangle(410, 514, 900, 565),
                new Rectangle(410, 636, 900, 754)
        );

        BufferedImage expectedImage = readImageFromResources("result#113.png");

        ImageComparison imageComparison = new ImageComparison(expected, actual)
                .setExcludedAreas(excludedAreas)
                .setRectangleLineWidth(5)
                .setDrawExcludedRectangles(true);

        //when
        ComparisonResult comparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedImage, comparisonResult.getResult());
    }

    @Test
    public void testMatchSize() throws IOException, URISyntaxException {
        //when
        ComparisonResult comparisonResult = new ImageComparison("expected.png", "expected.png").compareImages();

        //then
        assertEquals(MATCH, comparisonResult.getComparisonState());
    }

    @Test
    public void testGettersAnsSetters() throws IOException, URISyntaxException {
        //when
        ImageComparison imageComparison = new ImageComparison("expected.png", "actual.png")
                .setMinimalRectangleSize(100)
                .setMaximalRectangleCount(200)
                .setRectangleLineWidth(300)
                .setExcludedAreas(asList(Rectangle.createZero(), Rectangle.createDefault()))
                .setDrawExcludedRectangles(true)
                .setThreshold(400);

        //then
        assertEquals(String.valueOf(100), String.valueOf(imageComparison.getMinimalRectangleSize()));
        assertEquals(String.valueOf(200), String.valueOf(imageComparison.getMaximalRectangleCount()));
        assertEquals(String.valueOf(300), String.valueOf(imageComparison.getRectangleLineWidth()));
        assertEquals(String.valueOf(400), String.valueOf(imageComparison.getThreshold()));
        assertTrue(imageComparison.isDrawExcludedRectangles());
    }

    @Test
    public void testResearchJpegImages() throws IOException, URISyntaxException {
        //given
        BufferedImage expected = readImageFromResources("expected.jpg");
        BufferedImage actual = readImageFromResources("actual.jpg");

        BufferedImage expectedResult = readImageFromResources("result.jpg");

        //when
        ComparisonResult comparisonResult = new ImageComparison(expected, actual).compareImages();

        //then
        assertEquals(MISMATCH, comparisonResult.getComparisonState());
        assertImagesEqual(expectedResult, comparisonResult.getResult());
    }
}
