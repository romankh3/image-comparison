package com.github.romankh3.image.comparison;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.romankh3.image.comparison.exception.ImageComparisonException;
import com.github.romankh3.image.comparison.exception.ImageNotFoundException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import org.junit.jupiter.api.Test;

/**
 * Unit-level testing for {@link ImageComparisonUtil} object.
 */
public class ImageComparisonUtilUnitTest extends BaseTest {

    @Test
    public void testWrongPath() {
        //when-then
        ImageNotFoundException ex = assertThrows(ImageNotFoundException.class,
                () -> readImageFromResources("wrong-file-name.png"));
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
        ImageComparisonException ex = assertThrows(ImageComparisonException.class,
                () -> ImageComparisonUtil.saveImage(path, null));
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
        assertNotNull(bufferedImage);
        assertNotNull(bufferedImage1);
    }

    @Test
    public void testGetDifferencePercent() {
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
        int pixel1 = 2;
        int pixel2 = 2;

        //when
        int pixelDiff = ImageComparisonUtil.pixelDiff(pixel1, pixel2);

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
        assertDoesNotThrow(() -> ImageComparisonUtil.deepCopy(subimage));
    }

    /**
     * Test issue #180. It was a problem with {@link ImageComparisonUtil#readImageFromResources(String)}
     * from non test/resources.
     */
    @Test
    public void shouldProperlyHandleBug180FromRoot() {
        //given
        String imagePath = "build/resources/test/result.png";

        //when
        BufferedImage result = ImageComparisonUtil.readImageFromResources(imagePath);

        //then
        assertNotNull(result);
    }

    /**
     * Test issue #180. It was a problem with {@link ImageComparisonUtil#readImageFromResources(String)}
     * from non test/resources.
     */
    @Test
    public void shouldProperlyHandleBug180FromTestResources() {
        //given
        String imagePath = "actual.png";

        //when
        BufferedImage result = ImageComparisonUtil.readImageFromResources(imagePath);

        //then
        assertNotNull(result);
    }
}
