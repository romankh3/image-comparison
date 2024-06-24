package com.github.romankh3.image.comparison;

import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static com.github.romankh3.image.comparison.model.ImageComparisonState.MISMATCH;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class BMPTest {
    @Test
    public void Test1a(){
        BufferedImage result = readImageFromResources("result#21.png");
        BufferedImage expected = readImageFromResources("expected#21.png");
        BufferedImage actual = readImageFromResources("actual#21.png");

        ImageComparison imageComparison = new ImageComparison(expected,actual);
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        assertNotNull(imageComparison.getActual());
        assertNotNull(imageComparison.getExpected());
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(result, imageComparisonResult.getResult());
    }

    @Test
    public void Test1b(){
        BufferedImage result = readImageFromResources("result#21.png");
        BufferedImage expected = readImageFromResources("expected#21.png");
        BufferedImage actual = readImageFromResources("actual#21.png");

        ImageComparison imageComparison = new ImageComparison(expected,actual);
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages_BMP();

        assertNotNull(imageComparison.getActual());
        assertNotNull(imageComparison.getExpected());
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(result, imageComparisonResult.getResult());
    }

    @Test
    public void Test2a(){
        BufferedImage result = readImageFromResources("test_result_98.png");
        BufferedImage expected = readImageFromResources("expected#98.png");
        BufferedImage actual = readImageFromResources("actual#98.png");

        ImageComparison imageComparison = new ImageComparison(expected,actual);
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        assertNotNull(imageComparison.getActual());
        assertNotNull(imageComparison.getExpected());
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(result, imageComparisonResult.getResult());
    }

    @Test
    public void Test2b(){
        BufferedImage result = readImageFromResources("test_result_98.png");
        BufferedImage expected = readImageFromResources("expected#98.png");
        BufferedImage actual = readImageFromResources("actual#98.png");

        ImageComparison imageComparison = new ImageComparison(expected,actual);
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages_BMP();

        assertNotNull(imageComparison.getActual());
        assertNotNull(imageComparison.getExpected());
        assertEquals(MISMATCH, imageComparisonResult.getImageComparisonState());
        assertImagesEqual(result, imageComparisonResult.getResult());
    }

    private void assertImagesEqual(BufferedImage expected, BufferedImage actual) {
        if (expected.getWidth() != actual.getWidth() || expected.getHeight() != actual.getHeight()) {
            fail("Images have different dimensions");
        }

        int width = expected.getWidth();
        int height = expected.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (expected.getRGB(x, y) != actual.getRGB(x, y)) {
                    fail("Images are different, found a different pixel at: x = " + x + ", y = " + y);
                }
            }
        }
    }
}
