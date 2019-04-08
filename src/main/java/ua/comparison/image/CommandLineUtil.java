package ua.comparison.image;

import static ua.comparison.image.ImageComparisonTools.readImageFromFile;
import static ua.comparison.image.ImageComparisonTools.readImageFromResources;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import ua.comparison.image.ArgsParser.Arguments;

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
                readImageFromResources("image1.png"),
                readImageFromResources("image2.png"),
                null);
    }

    public static ImageComparison create(ArgsParser.Arguments args) throws IOException {
        return new ImageComparison(
                readImageFromFile(args.getImage1()),
                readImageFromFile(args.getImage2()),
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
