package com.github.romankh3.image.comparison;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.createGUI;
import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit-level testing for {@link ImageComparisonUtil} object.
 */
public class ImageComparisonUtilUnitTest {

    @Test
    public void testFrameMethod() throws IOException, URISyntaxException {
        BufferedImage image = readImageFromResources("result1.png");
        Frame resultFrame = createGUI(image);
        assertEquals(image.getHeight(), resultFrame.getHeight());
        assertEquals(image.getWidth(), resultFrame.getWidth());
    }

    @Test
    public void testSaveImage() throws IOException, URISyntaxException {
        BufferedImage image = readImageFromResources("result1.png");
        String path = "build/test/correct/save/image.png";
        ImageComparisonUtil.saveImage(new File(path), image);
        Assert.assertTrue(new File(path).exists());
    }

    @Test(expected = FileNotFoundException.class)
    public void testNullParent() throws IOException {
        //given
        File path = mock(File.class);
        File parent = mock(File.class);
        when(path.isDirectory()).thenReturn(false);
        when(path.mkdirs()).thenReturn(false);
        when(path.getParentFile()).thenReturn(parent);

        //when-then
        ImageComparisonUtil.saveImage(path, null);
    }

    @Test
    public void testCreation() {
        //when
        ImageComparisonUtil imageComparisonUtil = new ImageComparisonUtil();

        //then
        assertNotNull(imageComparisonUtil);
    }

    @Test
    public void testCommanlineUtilCreation() {
        //when
        CommandLineUtil commandLineUtil = new CommandLineUtil();

        //then
        assertNotNull(commandLineUtil);
    }
}
