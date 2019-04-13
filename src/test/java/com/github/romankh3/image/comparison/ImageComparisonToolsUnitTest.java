package com.github.romankh3.image.comparison;

import static com.github.romankh3.image.comparison.ImageComparisonTools.createGUI;
import static com.github.romankh3.image.comparison.ImageComparisonTools.readImageFromResources;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit-level testing for {@link ImageComparisonTools} object.
 */
public class ImageComparisonToolsUnitTest {

    @Test
    public void testFrameMethod() throws IOException, URISyntaxException {
        BufferedImage image = readImageFromResources("result1.png");
        Frame resultFrame = createGUI(image);
        assertEquals(image.getHeight(), resultFrame.getHeight());
        assertEquals(image.getWidth(), resultFrame.getWidth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckCorrectImageSize() {
        BufferedImage image1 = new BufferedImage(10, 10, 10);
        BufferedImage image2 = new BufferedImage(12, 12, 10);

        ImageComparisonTools.checkCorrectImageSize(image1, image2);
    }

    @Test
    public void testSaveImage() throws IOException, URISyntaxException {
        BufferedImage image = readImageFromResources("result1.png");
        String path = "build/test/correct/save/image.png";
        ImageComparisonTools.saveImage(new File(path), image);
        Assert.assertTrue(new File(path).exists());
    }

    @Test(expected = RuntimeException.class)
    public void testNullParent() throws IOException {
        //given
        File path = mock(File.class);
        File parent = mock(File.class);
        when(path.isDirectory()).thenReturn(false);
        when(path.mkdirs()).thenReturn(false);
        when(path.getParentFile()).thenReturn(parent);

        //when-then
        ImageComparisonTools.saveImage(path, null);
    }

    @Test
    public void testCreation() {
        //when
        ImageComparisonTools imageComparisonTools = new ImageComparisonTools();

        //then
        assertNotNull(imageComparisonTools);
    }

    @Test
    public void testCommanlineUtilCreation() {
        //when
        CommandLineUtil commandLineUtil = new CommandLineUtil();

        //then
        assertNotNull(commandLineUtil);
    }
}
