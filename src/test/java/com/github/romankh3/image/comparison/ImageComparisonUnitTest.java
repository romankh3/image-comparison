package com.github.romankh3.image.comparison;

import static com.github.romankh3.image.comparison.ImageComparisonTools.readImageFromResources;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Test;

/**
 * Unit-level testing for {@link ImageComparison} object.
 */
public class ImageComparisonUnitTest {

    private static void assertImagesEqual(BufferedImage imgA, BufferedImage imgB) {
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            fail("Images have different dimensions");
        }

        int width = imgA.getWidth();
        int height = imgA.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    fail("Images are different, found different pixel at: x = " + x + ", y = " + y);
                }
            }
        }
    }

    /**
     * The most important test. Shown, that the changes in algorithm,
     * don't break the main behaviour and result as expected.
     */
    @Test
    public void testCorrectWorkingImage1Image2() throws IOException, URISyntaxException {
        //given
        BufferedImage expectedResultImage = readImageFromResources("result1.png");

        //when
        BufferedImage drawnDifferences = new ImageComparison("image1.png", "image2.png").compareImages();

        //then
        assertImagesEqual(expectedResultImage, drawnDifferences);
    }

    /**
     * Test issue #17. It was StackOverFlowError.
     */
    @Test
    public void testIssue17() throws IOException, URISyntaxException {
        BufferedImage bufferedImage = new ImageComparison("b1#17.png", "b2#17.png").compareImages();
        assertNotNull(bufferedImage);
    }

    /**
     * Test issue #21. It was StackOverFlowError.
     */
    @Test
    public void testIssue21() throws IOException, URISyntaxException {
        //given
        BufferedImage expectedResultImage = readImageFromResources("result#21.png");

        //when
        BufferedImage comparisonResult = new ImageComparison("b1#21.png", "b2#21.png").compareImages();

        //then
        assertImagesEqual(expectedResultImage, comparisonResult);
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
        BufferedImage comparisonResult = new ImageComparison(image1, image2).compareImages();

        //then
        assertImagesEqual(expectedResultImage, comparisonResult);
    }

    /**
     * Verify that it is possible to use a thick line in the rectangle
     */
    @Test
    public void testRectangleWithLineWidth10() throws IOException, URISyntaxException {
        BufferedImage expectedResultImage = readImageFromResources("resultThickRectangle.png");

        ImageComparison imageComparison = new ImageComparison("b1#11.png", "b2#11.png");
        imageComparison.setRectangleLineWidth(10);
        BufferedImage comparisonResult = imageComparison.compareImages();

        assertImagesEqual(expectedResultImage, comparisonResult);
    }

    @Test
    public void testCreateDefault() throws IOException, URISyntaxException {
        ImageComparison comparison = CommandLineUtil.createDefault();
        assertImagesEqual(readImageFromResources("image1.png"), comparison.getImage1());
        assertImagesEqual(readImageFromResources("image2.png"), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoArgs() throws IOException, URISyntaxException {
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        ImageComparison comparison = CommandLineUtil.create(image1.getAbsolutePath(), image2.getAbsolutePath());

        assertImagesEqual(readImageFromResources("image1.png"), comparison.getImage1());
        assertImagesEqual(readImageFromResources("image2.png"), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoImagesAsArgs() throws IOException, URISyntaxException {
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        ImageComparison comparison = CommandLineUtil.create(new ArgsParser.Arguments(image1, image2, null));

        assertImagesEqual(readImageFromResources("image1.png"), comparison.getImage1());
        assertImagesEqual(readImageFromResources("image2.png"), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoImagesAndDestinationFileAsArgs() throws IOException, URISyntaxException {
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        ImageComparison comparison = CommandLineUtil.create(new ArgsParser.Arguments(image1, image2, destination));

        assertImagesEqual(readImageFromResources("image1.png"), comparison.getImage1());
        assertImagesEqual(readImageFromResources("image2.png"), comparison.getImage2());
        assertTrue(comparison.getDestination().isPresent());
        assertEquals(destination, comparison.getDestination().get());
    }

    @Test
    public void resultIsHandledCorrectlyWhenItShouldShowUI() throws IOException, URISyntaxException {
        ImageComparison comparison = new ImageComparison("image1.png", "image2.png");
        AtomicBoolean savedToFile = new AtomicBoolean(false);
        AtomicBoolean showUI = new AtomicBoolean(false);

        CommandLineUtil.handleResult(comparison, file -> savedToFile.set(true), () -> showUI.set(true));

        assertFalse(savedToFile.get());
        assertTrue(showUI.get());
    }

    @Test
    public void resultIsHandledCorrectlyWhenItShouldSaveToFile() throws IOException, URISyntaxException {
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        ImageComparison comparison = CommandLineUtil.create(new ArgsParser.Arguments(image1, image2, destination));

        AtomicBoolean savedToFile = new AtomicBoolean(false);
        AtomicBoolean showUI = new AtomicBoolean(false);

        CommandLineUtil.handleResult(comparison, file -> savedToFile.set(true), () -> showUI.set(true));

        assertTrue(savedToFile.get());
        assertFalse(showUI.get());
    }

    @Test
    public void testSetterAndGettersThresholdWhenItShouldSetsProperly() throws IOException, URISyntaxException {
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        ImageComparison comparison = CommandLineUtil.create(new ArgsParser.Arguments(image1, image2, destination));
        int setValue = 10;
        comparison.setThreshold(setValue);
        assertEquals(setValue, comparison.getThreshold());
    }
}
