package com.github.romankh3.image.comparison;

/**
 * A {@link RuntimeException} that is thrown in case of an image comparison failures.
 */
public final class ImageComparisonException  extends RuntimeException {

    public ImageComparisonException(String message) {
        super(message);
    }

    public ImageComparisonException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
