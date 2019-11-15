package com.github.romankh3.image.comparison;

/**
 * {@link RuntimeException} exception, created due to reason to avoid rethrowing checked exceptions.
 */
public class ImageComparisonException  extends RuntimeException {

    public ImageComparisonException(String message) {
        super(message);
    }

    public ImageComparisonException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
