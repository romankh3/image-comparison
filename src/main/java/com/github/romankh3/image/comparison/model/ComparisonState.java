package com.github.romankh3.image.comparison.model;

/**
 * Enum for telling the result of the Comparison.
 * For example {@link ComparisonState#MATCH} - means that the images are equal.
 */
public enum ComparisonState {

    /**
     * Result state of the comparison, where mismatch of the image sizes.
     */
    SIZE_MISMATCH,

    /**
     * Result state of the comparison, where mismatch of the images.
     */
    MISMATCH,

    /**
     * Result state of the images, where images are equal, e.g. match.
     */
    MATCH
}
