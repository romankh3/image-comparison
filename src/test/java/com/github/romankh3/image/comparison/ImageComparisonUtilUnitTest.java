package com.github.romankh3.image.comparison;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.junit.Test;

/**
 * Unit-level testing for {@link ImageComparisonUtil} object.
 */
public class ImageComparisonUtilUnitTest extends BaseTest {

    @Test
    public void testWrongPath() {
        //when-then
        Throwable ex = getException(() -> readImageFromResources("wrong-file-name.png"));
        assertTrue(ex.getMessage().startsWith("Image with path = wrong-file-name.png not found"));
    }

    @Test
    public void testNullParent() {
        //given
        File path = mock(File.class);
        File parent = mock(File.class);
        when(path.isDirectory()).thenReturn(false);
        when(path.mkdirs()).thenReturn(false);
        when(path.getParentFile()).thenReturn(parent);

        //when-then
        Throwable ex = getException(() -> ImageComparisonUtil.saveImage(path, null));
        assertTrue(ex.getMessage().startsWith("Unable to create directory "));
    }

    @Test
    public void testSaveImage() {
        BufferedImage image = readImageFromResources("result.png");
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
    public void testResize() {
        //given
        BufferedImage actual = readImageFromResources("actualDifferentSize.png");

        //when
        BufferedImage resizedActual = ImageComparisonUtil.resize(actual, 200, 200);

        //then
        assertEquals(200, resizedActual.getHeight());
        assertEquals(200, resizedActual.getWidth());

    }

    @Test
    public void testToBufferedImage() {
        //given
        Image imageInstance = readImageFromResources("actualDifferentSize.png");
        BufferedImage bufferedImageInstance = readImageFromResources("actualDifferentSize.png");

        //when
        BufferedImage bufferedImage = ImageComparisonUtil.toBufferedImage(imageInstance);
        BufferedImage bufferedImage1 = ImageComparisonUtil.toBufferedImage(bufferedImageInstance);

        //then
        assertTrue(bufferedImage instanceof BufferedImage);
        assertTrue(bufferedImage1 instanceof BufferedImage);
    }

    @Test
    public void testGetDifferencePercent() throws IOException {
        //given
        BufferedImage bufferedImage = readImageFromResources("actualDifferentSize.png");

        //when
        float differentPercent = ImageComparisonUtil.getDifferencePercent(bufferedImage, bufferedImage);

        //then
        assertEquals(0, (int) differentPercent);

    }

    @Test
    public void testPixelDiff() {
        //given
        int Pixel1 = 2;
        int Pixel2 = 2;

        //when
        int pixelDiff = ImageComparisonUtil.pixelDiff(Pixel1, Pixel2);

        //then
        assertEquals(0, pixelDiff);
    }

    /**
     * Test issue #136 IllegalArgumentException on deepCopy.
     */
    @Test
    public void testIssue136() {
        //given
        BufferedImage image = readImageFromResources("actual#136.png");
        BufferedImage subimage = image.getSubimage(1, 1, image.getWidth() - 2, image.getHeight() - 2);

        //when
        Throwable ex = getException(() -> ImageComparisonUtil.deepCopy(subimage));
        assertNull("There is no exception:", ex);
    }
}
