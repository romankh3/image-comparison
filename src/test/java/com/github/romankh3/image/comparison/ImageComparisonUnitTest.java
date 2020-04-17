package com.github.romankh3.image.comparison;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static com.github.romankh3.image.comparison.model.ImageComparisonState.MATCH;
import static com.github.romankh3.image.comparison.model.ImageComparisonState.MISMATCH;
import static com.github.romankh3.image.comparison.model.ImageComparisonState.SIZE_MISMATCH;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Unit-level testing for {@link ImageComparison} object.
 */
public class ImageComparisonUnitTest extends BaseTest {

    /**
     * The most important test. Shown, that the changes in algorithm,
     * don't break the main behaviour and result as expected.
     */
    @Test
    public void testCorrectWorkingExpectedActual() {
        //given
        BufferedImage expectedResultImage = readImageFromResources("result.png");

        File file = new File("build/test-images/result.png");

        //when
        ImageComparison imageComparison = new ImageComparison("expected.png", "actual.png");
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages().writeResultTo(file);

        //then
        assertNotNull(imageComparison.getActual());
        assertNotNull(imageComparison.getExpected());
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedResultImage, imageComparisonResult.getResult());
    }

    @Test
    public void shouldAllowLessThanOnePercentDifference() {
        //given
        ImageComparison imageComparison = new ImageComparison("expected.png", "actual.png")
                .setAllowingPercentOfDifferentPixels(1);

        //when
        ImageComparisonResult result = imageComparison.compareImages();

        //then
        assertEquals(MATCH, result.getImageComparisonState());

        //when
        result = imageComparison.setAllowingPercentOfDifferentPixels(0).compareImages();

        //then
        assertEquals(MISMATCH, result.getImageComparisonState());
    }

    @Test
    public void testMaximalRectangleCount() {
        //given
        ImageComparison imageComparison = new ImageComparison("expected.png", "actual.png");
        imageComparison.setMaximalRectangleCount(3);
        BufferedImage expectedImage = readImageFromResources("maximalRectangleCountResult.png");

        //when
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedImage, imageComparisonResult.getResult());
    }

    @Test
    public void testIssue165() {
        //given
        ImageComparison imageComparison = new ImageComparison("expected.png", "actual.png");
        imageComparison.setMaximalRectangleCount(5);
        BufferedImage expectedImage = readImageFromResources("result.png");

        //when
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedImage, imageComparisonResult.getResult());
    }

    @Test
    public void testImagesWithTotallyDifferentImages() {
        //when
        BufferedImage expectedResult = readImageFromResources("totallyDifferentImageResult.png");

        //when
        ImageComparisonResult imageComparisonResult =
                new ImageComparison("expected.png", "actualTotallyDifferent.png").compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedResult, imageComparisonResult.getResult());
    }

    @Test
    public void testDestinationGetting() {
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
    public void testMinimalRectangleSize() {
        //given
        ImageComparison imageComparison = new ImageComparison("expected.png", "actual.png");
        imageComparison.setMinimalRectangleSize(4 * 4 + 1);
        BufferedImage expectedImage = readImageFromResources("minimalRectangleSizeResult.png");

        //when
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedImage, imageComparisonResult.getResult());
    }

    /**
     * Test issue #17. It was StackOverFlowError.
     */
    @Test
    public void testIssue17() {
        //when
        ImageComparisonResult imageComparisonResult = new ImageComparison("expected#17.png", "actual#17.png")
                .compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertNotNull(imageComparisonResult.getResult());
    }

    /**
     * Test issue #21. It was StackOverFlowError.
     */
    @Test
    public void testIssue21() {
        //given
        BufferedImage expectedResultImage = readImageFromResources("result#21.png");

        //when
        ImageComparisonResult imageComparisonResult =
                new ImageComparison("expected#21.png", "actual#21.png").compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedResultImage, imageComparisonResult.getResult());
    }

    /**
     * Test issue #11.
     */
    @Test
    public void testIssue11() {
        //given
        BufferedImage expectedResultImage = readImageFromResources("result#11.png");

        BufferedImage expected = readImageFromResources("expected#11.png");
        BufferedImage actual = readImageFromResources("actual#11.png");

        //when
        ImageComparisonResult imageComparisonResult = new ImageComparison(expected, actual).compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedResultImage, imageComparisonResult.getResult());
    }

    /**
     * Verify that it is possible to use a thick line in the rectangle
     */
    @Test
    public void testRectangleWithLineWidth10() {
        //given
        BufferedImage expectedResultImage = readImageFromResources("resultThickRectangle.png");

        BufferedImage expected = readImageFromResources("expected#11.png");
        BufferedImage actual = readImageFromResources("actual#11.png");

        ImageComparison imageComparison = new ImageComparison(expected, actual,
                new File("build/test-images/result.png"))
                .setRectangleLineWidth(10);

        //when
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedResultImage, imageComparisonResult.getResult());
        assertEquals(10, imageComparison.getRectangleLineWidth());
    }

    @Test
    public void testShouldReturnARectangleList() {
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
    public void testSizeMissMatch() {
        //given
        BufferedImage expected = new BufferedImage(10, 10, 10);
        BufferedImage actual = new BufferedImage(12, 12, 10);

        //when
        ImageComparisonResult imageComparisonResult = new ImageComparison(expected, actual).compareImages();

        //then
        assertEquals(SIZE_MISMATCH, imageComparisonResult.getImageComparisonState());
    }

    @Test
    public void testShouldIgnoreExcludedArea() {
        //given
        BufferedImage expected = readImageFromResources("expected#17.png");
        BufferedImage actual = readImageFromResources("actualMaskedComparison#58.png");
        List<Rectangle> excludedAreas = new ArrayList<>();
        excludedAreas.add(new Rectangle(131, 0, 224, 224));
        ImageComparison imageComparison = new ImageComparison(expected, actual).setExcludedAreas(excludedAreas);

        //when
        ImageComparisonResult result = imageComparison.compareImages();

        //then
        assertEquals(result.getImageComparisonState(), MATCH);
    }

    /**
     * Test issue #98 and #97 to see the drawn excluded areas.
     */
    @Test
    public void testIssue98() {
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
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedImage, imageComparisonResult.getResult());
    }

    @Test
    public void shouldProperlyCompare171issue() {
        //given
        BufferedImage actual = readImageFromResources("actual#171.png");
        BufferedImage expected = readImageFromResources("expected#171.png");

        BufferedImage expectedResultImage = readImageFromResources("result#171.png");

        ImageComparison imageComparison = new ImageComparison(expected, actual)
                .setExcludedAreas(singletonList(new Rectangle(325, 50, 650, 80)))
                .setDrawExcludedRectangles(true)
                .setRectangleLineWidth(3);

        //when
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedResultImage, imageComparisonResult.getResult());
    }

    /**
     * Test issue #113 to draw red and green rectangles.
     */
    @Test
    public void testIssue113() {
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
                .setPixelToleranceLevel(0.0)
                .setDrawExcludedRectangles(true);

        //when
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedImage, imageComparisonResult.getResult());
        assertEquals(0.0, imageComparison.getPixelToleranceLevel(), 0.0);
    }

    /**
     * Test issue #167(a). Fill difference rectangles in transparent red.
     */
    @Test
    public void testIssue167a() {
        //given
        BufferedImage expected = readImageFromResources("expected#98.png");
        BufferedImage actual = readImageFromResources("actual#98.png");

        List<Rectangle> excludedAreas = asList(
                new Rectangle(410, 514, 900, 565),
                new Rectangle(410, 636, 900, 754)
        );

        BufferedImage expectedImage = readImageFromResources("result#167a.png");

        ImageComparison imageComparison = new ImageComparison(expected, actual)
                .setExcludedAreas(excludedAreas)
                .setRectangleLineWidth(5)
                .setPixelToleranceLevel(0.0)
                .setDrawExcludedRectangles(true)
                .setDifferenceRectangleFilling(true, 30.0);

        //when
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedImage, imageComparisonResult.getResult());
    }

    /**
     * Test issue #167(b). Fill excluded rectangles in transparent green.
     */
    @Test
    public void testIssue167b() {
        //given
        BufferedImage expected = readImageFromResources("expected#98.png");
        BufferedImage actual = readImageFromResources("actual#98.png");

        List<Rectangle> excludedAreas = asList(
                new Rectangle(410, 514, 900, 565),
                new Rectangle(410, 636, 900, 754)
        );

        BufferedImage expectedImage = readImageFromResources("result#167b.png");

        ImageComparison imageComparison = new ImageComparison(expected, actual)
                .setExcludedAreas(excludedAreas)
                .setRectangleLineWidth(5)
                .setPixelToleranceLevel(0.0)
                .setDrawExcludedRectangles(true)
                .setExcludedRectangleFilling(true, 30.0);

        //when
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedImage, imageComparisonResult.getResult());
    }

    /**
     * Test issue #134 If image is different in a line in 1 px, ComparisonState is always MATCH.
     */
    @Test
    public void testIssue134() {
        //given
        ImageComparison imageComparison = new ImageComparison("expected#134.png", "actual#134.png");
        BufferedImage expectedImage = readImageFromResources("result#134.png");

        //when
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedImage, imageComparisonResult.getResult());
    }

    @Test
    public void testMatchSize() {
        //when
        ImageComparisonResult imageComparisonResult = new ImageComparison("expected.png", "expected.png")
                .compareImages();

        //then
        assertEquals(MATCH, imageComparisonResult.getImageComparisonState());
    }

    @Test
    public void testGettersAndSetters() {
        //when
        ImageComparison imageComparison = new ImageComparison("expected.png", "actual.png")
                .setMinimalRectangleSize(100)
                .setMaximalRectangleCount(200)
                .setRectangleLineWidth(300)
                .setExcludedAreas(asList(Rectangle.createZero(), Rectangle.createDefault()))
                .setDrawExcludedRectangles(true)
                .setPixelToleranceLevel(0.6)
                .setThreshold(400)
                .setDifferenceRectangleFilling(true, 35.1)
                .setExcludedRectangleFilling(true, 45.1);

        //then
        assertEquals(String.valueOf(100), String.valueOf(imageComparison.getMinimalRectangleSize()));
        assertEquals(String.valueOf(200), String.valueOf(imageComparison.getMaximalRectangleCount()));
        assertEquals(String.valueOf(300), String.valueOf(imageComparison.getRectangleLineWidth()));
        assertEquals(String.valueOf(400), String.valueOf(imageComparison.getThreshold()));
        assertEquals(0.6, imageComparison.getPixelToleranceLevel(), 0.0);
        assertTrue(imageComparison.isDrawExcludedRectangles());
        assertEquals(35.1, imageComparison.getPercentOpacityDifferenceRectangles(), 0.0);
        assertEquals(45.1, imageComparison.getPercentOpacityExcludedRectangles(), 0.0);
        assertTrue(imageComparison.isFillDifferenceRectangles());
        assertTrue(imageComparison.isFillExcludedRectangles());
    }

    @Test
    public void testResearchJpegImages() {
        //given
        BufferedImage expected = readImageFromResources("expected.jpg");
        BufferedImage actual = readImageFromResources("actual.jpg");

        BufferedImage expectedResult = readImageFromResources("result.jpg");

        //when
        ImageComparisonResult imageComparisonResult = new ImageComparison(expected, actual)
                .setMinimalRectangleSize(4)
                .compareImages();

        //then
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(expectedResult, imageComparisonResult.getResult());
    }

    @Test
    public void testCompareMisSizedImages() {
        //given
        BufferedImage expected = readImageFromResources("expected.png");
        BufferedImage actual = readImageFromResources("actualDifferentSize.png");

        //when
        ImageComparisonResult imageComparisonResult = new ImageComparison(expected, actual).compareImages();

        //then
        assertEquals(SIZE_MISMATCH, imageComparisonResult.getImageComparisonState());
        boolean differenceLessThan2 = imageComparisonResult.getDifferencePercent() < 2;
        assertTrue(differenceLessThan2);
    }
}
