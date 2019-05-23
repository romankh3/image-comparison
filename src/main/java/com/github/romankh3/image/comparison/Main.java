package com.github.romankh3.image.comparison;

import static com.github.romankh3.image.comparison.CommandLineUsage.handleResult;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Main class for running image-comparison from commandline.
 */
public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        CommandLineUsage commandLineUsage = new CommandLineUsage();
        ImageComparison imgCmp = commandLineUsage.create(args);
        BufferedImage result = imgCmp.compareImages().getResult();
        handleResult(imgCmp, (file) -> ImageComparisonUtil.saveImage(file, result),
                () -> ImageComparisonUtil.createGUI(result));
    }
}
