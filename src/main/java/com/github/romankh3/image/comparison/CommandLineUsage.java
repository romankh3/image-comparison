package com.github.romankh3.image.comparison;

import com.github.romankh3.image.comparison.ArgsParser.Arguments;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Class for static method related to command line.
 */
class CommandLineUsage {

    public static void main(String[] args) throws IOException, URISyntaxException {
        CommandLineUsage commandLineUsage = new CommandLineUsage();
        ImageComparison imgCmp = commandLineUsage.create(args);
        BufferedImage result = imgCmp.compareImages().getResult();
        handleResult(imgCmp,
                (file) -> ImageComparisonUtil.saveImage(file, result),
                () -> ImageComparisonUtil.createGUI(result));
    }

    public ImageComparison create(String... args) throws IOException, URISyntaxException {
        Optional<Arguments> arguments = new ArgsParser().parseArgs(args);
        return arguments.isPresent() ? create(arguments.get()) : createDefault();
    }

    public ImageComparison create(ArgsParser.Arguments args) throws IOException {
        return new ImageComparison(
                ImageComparisonUtil.readImageFromFile(args.getImage1()),
                ImageComparisonUtil.readImageFromFile(args.getImage2()),
                args.getDestinationImage().orElse(null));
    }

    private ImageComparison createDefault() throws IOException, URISyntaxException {
        return new ImageComparison(
                ImageComparisonUtil.readImageFromResources("image1.png"),
                ImageComparisonUtil.readImageFromResources("image2.png"),
                null);
    }

    public static void handleResult(ImageComparison instance, IOConsumer<File> saveToFile, Runnable showUI)
            throws IOException {
        if (instance.getDestination().isPresent()) {
            saveToFile.accept(instance.getDestination().get());
        } else {
            showUI.run();
        }
    }

}
