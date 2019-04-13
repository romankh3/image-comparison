package com.github.romankh3.image.comparison;

import com.github.romankh3.image.comparison.ArgsParser.Arguments;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Class for static method related to command line.
 */
public class CommandLineUtil {

    public static ImageComparison create(String... args) throws IOException, URISyntaxException {
        Optional<Arguments> arguments = new ArgsParser().parseArgs(args);
        return arguments.isPresent() ? create(arguments.get()) : createDefault();
    }

    public static ImageComparison createDefault() throws IOException, URISyntaxException {
        return new ImageComparison(
                ImageComparisonTools.readImageFromResources("image1.png"),
                ImageComparisonTools.readImageFromResources("image2.png"),
                null);
    }

    public static ImageComparison create(ArgsParser.Arguments args) throws IOException {
        return new ImageComparison(
                ImageComparisonTools.readImageFromFile(args.getImage1()),
                ImageComparisonTools.readImageFromFile(args.getImage2()),
                args.getDestinationImage().orElse(null));
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
