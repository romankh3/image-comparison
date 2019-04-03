package ua.comparison.image;

import static org.junit.Assert.assertEquals;
import static ua.comparison.image.ImageComparisonTools.createGUI;
import static ua.comparison.image.ImageComparisonTools.readImageFromResources;

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
}
