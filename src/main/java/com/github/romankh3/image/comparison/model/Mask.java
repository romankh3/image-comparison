package com.github.romankh3.image.comparison.model;

import java.util.ArrayList;
import java.util.List;

public class Mask {
    private List<Rectangle> mask;

    public Mask() {
        mask = new ArrayList<>();
    }

    public Mask(List<Rectangle> mask) {
        this.mask = mask;
    }

    public boolean contains(Point point) {
        for (Rectangle rectangle : mask) {
            if (rectangle.containsPoint(point)) {
                return true;
            }
        }

        return false;
    }
}
