package com.github.romankh3.image.comparison;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.createGUI;
import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
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
        assertTrue(new File(path).exists());
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
