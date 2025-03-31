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
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Unit-level testing for {@link ImageComparisonUtil} object.")
public class ImageComparisonUtilUnitTest {

    @DisplayName("Should properly perform wrong path")
    @Test
    public void shouldReturnWrongPath() {
        //when-then
        ImageNotFoundException ex = assertThrows(ImageNotFoundException.class,
                () -> readImageFromResources("wrong-file-name.png"));
        assertTrue(ex.getMessage().startsWith("Image with path = wrong-file-name.png not found"));
    }

    @DisplayName("Should return null parent")
    @Test
    public void shouldReturnNullParent() {
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

    @DisplayName("Should throw an exception when image can't be saved")
    @Test
    public void shouldThrowAnExceptionWhenImageCantBeSaved() {
        //given
        BufferedImage image = readImageFromResources("result.png");
        File path = mock(File.class);
        when(path.getAbsolutePath()).thenReturn("build/resources/test");
        when(path.delete()).thenAnswer(invocationOnMock -> {
            throw new IOException();
        });

        //when-then
        ImageComparisonException ex = assertThrows(ImageComparisonException.class,
                () -> ImageComparisonUtil.saveImage(path, image));
        assertEquals("Cannot save image to path=build/resources/test", ex.getMessage());
    }

    @DisplayName("Should properly save image")
    @Test
    public void shouldProperlySaveImage() {
        BufferedImage image = readImageFromResources("result.png");
        String path = "build/test/correct/save/image.png";
        ImageComparisonUtil.saveImage(new File(path), image);
        assertTrue(new File(path).exists());
    }

    @DisplayName("Should create ImageComparisonUtil")
    @Test
    public void shouldCreate() {
        //when
        ImageComparisonUtil imageComparisonUtil = new ImageComparisonUtil();

        //then
        assertNotNull(imageComparisonUtil);
    }

    @DisplayName("Should properly resize image")
    @Test
    public void shouldProperlyResize() {
        //given
        BufferedImage actual = readImageFromResources("actualDifferentSize.png");

        //when
        BufferedImage resizedActual = ImageComparisonUtil.resize(actual, 200, 200);

        //then
        assertEquals(200, resizedActual.getHeight());
        assertEquals(200, resizedActual.getWidth());

    }


    @DisplayName("Should properly map Image to BufferedImage")
    @Test
    public void shouldProperlyWorkToBufferedImage() {
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

    @DisplayName("Should properly get percent difference")
    @Test
    public void shouldProperlyGetDifferencePercent() {
        //given
        BufferedImage bufferedImage = readImageFromResources("actualDifferentSize.png");

        //when
        float differentPercent = ImageComparisonUtil.getDifferencePercent(bufferedImage, bufferedImage);

        //then
        assertEquals(0, (int) differentPercent);

    }

    @DisplayName("Should properly get pixel diff")
    @Test
    public void shouldProperlyWorkPixelDiff() {
        //given
        int pixel1 = 2;
        int pixel2 = 2;

        //when
        int pixelDiff = ImageComparisonUtil.pixelDiff(pixel1, pixel2);

        //then
        assertEquals(0, pixelDiff);
    }

    @DisplayName("Should properly work in bug #136")
    @Test
    public void shouldProperlyWorkInBug136() {
        //given
        BufferedImage image = readImageFromResources("actual#136.png");
        BufferedImage subimage = image.getSubimage(1, 1, image.getWidth() - 2, image.getHeight() - 2);

        //when
        assertDoesNotThrow(() -> ImageComparisonUtil.deepCopy(subimage));
    }

    @DisplayName("Should properly handle bug #180 from test resources")
    @Test
    public void shouldProperlyHandleBug180FromTestResources() {
        //given
        String imagePath = "actual.png";

        //when
        BufferedImage result = ImageComparisonUtil.readImageFromResources(imagePath);

        //then
        assertNotNull(result);
    }

    @DisplayName("Should throw an exception when trying to read image from directory")
    @Test
    public void shouldThrowAnExceptionWhenTryingToReadImageFromDirectory(@TempDir Path tempDir) {
        //when-then
        ImageComparisonException ex = assertThrows(ImageComparisonException.class,
                () -> ImageComparisonUtil.readImageFromResources(tempDir.toAbsolutePath().toString()));
        assertTrue(ex.getMessage().startsWith("Cannot read image from the file, path="),
                   "Expected exception message to start with \"Cannot read image from the file, path=\", but is \"" + ex.getMessage() + "\"");
    }
}
