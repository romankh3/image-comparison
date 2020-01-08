package com.github.romankh3.image.comparison;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.image.BufferedImage;

/**
 * Base test for all the tests. Contains common methods, fields for almost all the tests.
 */
class BaseTest {

    public void assertImagesEqual(BufferedImage expected, BufferedImage actual) {
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
