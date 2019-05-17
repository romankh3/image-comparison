package com.github.romankh3.image.comparison;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Class for the presentation of the program.
 */
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        ImageComparison imgCmp = CommandLineUtil.create(args);
        BufferedImage result = imgCmp.compareImages().getResult();
        CommandLineUtil.handleResult(imgCmp, (file) -> ImageComparisonUtil.saveImage(file, result), () -> ImageComparisonUtil
                .createGUI(result));
    }
}
