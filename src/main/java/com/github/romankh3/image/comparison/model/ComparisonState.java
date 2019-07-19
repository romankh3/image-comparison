package com.github.romankh3.image.comparison.model;

/**
 * Enum for telling the result of the Comparison.
 * For example {@link ComparisonState#MATCH} - means that the images are equal.
 */
public enum ComparisonState {
    SIZE_MISMATCH,
    MISMATCH,
    MATCH
}
