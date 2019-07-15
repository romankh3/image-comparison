package com.github.romankh3.image.comparison.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The area that will be excluded, masked, in the image.
 */
public class ExcludedAreas {

    private final List<Rectangle> excluded;

    public ExcludedAreas() {
        excluded = new ArrayList<>();
    }

    public ExcludedAreas(List<Rectangle> excluded) {
        this.excluded = excluded;
    }

    public boolean contains(Point point) {
        return excluded.stream().anyMatch(rectangle -> rectangle.containsPoint(point));
    }

    public List<Rectangle> getExcluded() {
        return excluded;
    }
}
