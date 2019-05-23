package com.github.romankh3.image.comparison;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Test;

/**
 * Unit-level testing for {@link CommandLineUsage} object.
 */
public class CommandLineUsageTest extends BaseTest {

    private CommandLineUsage commandLineUsage = new CommandLineUsage();

    @Test
    public void testCreateDefault() throws IOException, URISyntaxException {
        ImageComparison comparison = commandLineUsage.create();
        assertImagesEqual(readImageFromResources("image1.png"), comparison.getImage1());
        assertImagesEqual(readImageFromResources("image2.png"), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoArgs() throws IOException, URISyntaxException {
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        ImageComparison comparison = commandLineUsage.create(image1.getAbsolutePath(), image2.getAbsolutePath());

        assertImagesEqual(readImageFromResources("image1.png"), comparison.getImage1());
        assertImagesEqual(readImageFromResources("image2.png"), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoImagesAsArgs() throws IOException, URISyntaxException {
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        ImageComparison comparison = commandLineUsage.create(new ArgsParser.Arguments(image1, image2, null));

        assertImagesEqual(readImageFromResources("image1.png"), comparison.getImage1());
        assertImagesEqual(readImageFromResources("image2.png"), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoImagesAndDestinationFileAsArgs() throws IOException, URISyntaxException {
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        ImageComparison comparison = commandLineUsage.create(new ArgsParser.Arguments(image1, image2, destination));

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

        CommandLineUsage.handleResult(comparison, file -> savedToFile.set(true), () -> showUI.set(true));

        assertFalse(savedToFile.get());
        assertTrue(showUI.get());
    }

    @Test
    public void resultIsHandledCorrectlyWhenItShouldSaveToFile() throws IOException, URISyntaxException {
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        ImageComparison comparison = commandLineUsage.create(new ArgsParser.Arguments(image1, image2, destination));

        AtomicBoolean savedToFile = new AtomicBoolean(false);
        AtomicBoolean showUI = new AtomicBoolean(false);

        CommandLineUsage.handleResult(comparison, file -> savedToFile.set(true), () -> showUI.set(true));

        assertTrue(savedToFile.get());
        assertFalse(showUI.get());
    }

    @Test
    public void testSetterAndGettersThresholdWhenItShouldSetsProperly() throws IOException, URISyntaxException {
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        ImageComparison comparison = commandLineUsage.create(new ArgsParser.Arguments(image1, image2, destination));
        int setValue = 10;
        comparison.setThreshold(setValue);
        assertEquals(setValue, comparison.getThreshold());
    }
}
