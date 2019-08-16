package com.github.romankh3.image.comparison;

import com.github.romankh3.image.comparison.ArgsParser.Arguments;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Class for static method related to command line.
 */
class CommandLineUsage {

    public ImageComparison create(String... args) throws IOException {
        Optional<Arguments> arguments = new ArgsParser().parseArgs(args);
        return arguments.isPresent() ? create(arguments.get()) : createDefault();
    }

    public ImageComparison create(ArgsParser.Arguments args) throws IOException {
        return new ImageComparison(
                ImageComparisonUtil.readImageFromFile(args.getExpected()),
                ImageComparisonUtil.readImageFromFile(args.getActual()),
                args.getDestinationImage().orElse(null));
    }

    private ImageComparison createDefault() throws IOException {
        return new ImageComparison(
                ImageComparisonUtil.readImageFromResources("expected.png"),
                ImageComparisonUtil.readImageFromResources("actual.png"),
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
