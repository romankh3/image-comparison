package com.github.romankh3.image.comparison;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Artem Kudria
 */
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        ImageComparison imgCmp = CommandLineUtil.create(args);
        BufferedImage result = imgCmp.compareImages();
        CommandLineUtil.handleResult(imgCmp, (file) -> ImageComparisonTools.saveImage(file, result), () -> ImageComparisonTools
                .createGUI(result));
    }
}
