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
        //when
        ImageComparison comparison = commandLineUsage.create();

        //then
        assertImagesEqual(readImageFromResources("expected.png"), comparison.getExpected());
        assertImagesEqual(readImageFromResources("actual.png"), comparison.getActual());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoArgs() throws IOException, URISyntaxException {
        //given
        File expected = new File(ImageComparison.class.getClassLoader().getResource("expected.png").toURI().getPath());
        File actual = new File(ImageComparison.class.getClassLoader().getResource("actual.png").toURI().getPath());

        //when
        ImageComparison comparison = commandLineUsage.create(expected.getAbsolutePath(), actual.getAbsolutePath());

        //then
        assertImagesEqual(readImageFromResources("expected.png"), comparison.getExpected());
        assertImagesEqual(readImageFromResources("actual.png"), comparison.getActual());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoImagesAsArgs() throws IOException, URISyntaxException {
        //given
        File expected = new File(ImageComparison.class.getClassLoader().getResource("expected.png").toURI().getPath());
        File actual = new File(ImageComparison.class.getClassLoader().getResource("actual.png").toURI().getPath());

        //when
        ImageComparison comparison = commandLineUsage.create(new ArgsParser.Arguments(expected, actual, null));

        //then
        assertImagesEqual(readImageFromResources("expected.png"), comparison.getExpected());
        assertImagesEqual(readImageFromResources("actual.png"), comparison.getActual());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoImagesAndDestinationFileAsArgs() throws IOException, URISyntaxException {
        //given
        File expected = new File(ImageComparison.class.getClassLoader().getResource("expected.png").toURI().getPath());
        File actual = new File(ImageComparison.class.getClassLoader().getResource("actual.png").toURI().getPath());
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();

        //when
        ImageComparison comparison = commandLineUsage.create(new ArgsParser.Arguments(expected, actual, destination));

        //then
        assertImagesEqual(readImageFromResources("expected.png"), comparison.getExpected());
        assertImagesEqual(readImageFromResources("actual.png"), comparison.getActual());
        assertTrue(comparison.getDestination().isPresent());
        assertEquals(destination, comparison.getDestination().get());
    }

    @Test
    public void resultIsHandledCorrectlyWhenItShouldShowUI() throws IOException, URISyntaxException {
        //given
        ImageComparison comparison = new ImageComparison("expected.png", "actual.png");
        AtomicBoolean savedToFile = new AtomicBoolean(false);
        AtomicBoolean showUI = new AtomicBoolean(false);

        //when
        CommandLineUsage.handleResult(comparison, file -> savedToFile.set(true), () -> showUI.set(true));

        //then
        assertFalse(savedToFile.get());
        assertTrue(showUI.get());
    }

    @Test
    public void resultIsHandledCorrectlyWhenItShouldSaveToFile() throws IOException, URISyntaxException {
        //given
        File expected = new File(ImageComparison.class.getClassLoader().getResource("expected.png").toURI().getPath());
        File actual = new File(ImageComparison.class.getClassLoader().getResource("actual.png").toURI().getPath());
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        ImageComparison comparison = commandLineUsage.create(new ArgsParser.Arguments(expected, actual, destination));

        AtomicBoolean savedToFile = new AtomicBoolean(false);
        AtomicBoolean showUI = new AtomicBoolean(false);

        //when
        CommandLineUsage.handleResult(comparison, file -> savedToFile.set(true), () -> showUI.set(true));

        //then
        assertTrue(savedToFile.get());
        assertFalse(showUI.get());
    }

    @Test
    public void testSetterAndGettersThresholdWhenItShouldSetsProperly() throws IOException, URISyntaxException {
        //given
        File expected = new File(ImageComparison.class.getClassLoader().getResource("expected.png").toURI().getPath());
        File actual = new File(ImageComparison.class.getClassLoader().getResource("actual.png").toURI().getPath());
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        int setValue = 10;

        //when
        ImageComparison comparison = commandLineUsage.create(new ArgsParser.Arguments(expected, actual, destination))
                .setThreshold(setValue);

        //then
        assertEquals(setValue, comparison.getThreshold());
    }
}
