package com.github.romankh3.image.comparison;

import com.github.romankh3.image.comparison.ArgsParser.Arguments;
import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Class for static method related to command line.
 */
final class CommandLineUsage {

    ImageComparison create(String... args) {
        Optional<Arguments> arguments = new ArgsParser().parseArgs(args);
        return arguments.map(this::create).orElseGet(this::createDefault);
    }

    ImageComparison create(ArgsParser.Arguments args) {
        return new ImageComparison(
                ImageComparisonUtil.readImageFromFile(args.getExpected()),
                ImageComparisonUtil.readImageFromFile(args.getActual()),
                args.getDestinationImage().orElse(null));
    }

    ImageComparison createDefault() {
        return new ImageComparison(
                ImageComparisonUtil.readImageFromResources("expected.png"),
                ImageComparisonUtil.readImageFromResources("actual.png"),
                null);
    }

    static void handleResult(ImageComparison instance, Consumer<File> saveToFile, Runnable showUI) {
        if (instance.getDestination().isPresent()) {
            saveToFile.accept(instance.getDestination().get());
        } else {
            showUI.run();
        }
    }
}
