package com.github.romankh3.image.comparison;

import java.io.File;
import java.util.Optional;

/**
 * Argument parser for commandline usage.
 */
final class ArgsParser {

    private final Runnable successExit;
    private final Runnable errorExit;

    ArgsParser() {
        this(() -> System.exit(0), () -> System.exit(1));
    }

    ArgsParser(Runnable successExit, Runnable errorExit) {
        this.successExit = successExit;
        this.errorExit = errorExit;
    }

    public Optional<Arguments> parseArgs(String... args) {
        if (args.length == 0) {
            return Optional.empty();
        }
        if (args.length == 1) {
            if (args[0].equals("-h") || args[0].equals("--help")) {
                return help();
            } else {
                return error("Unrecognized option: '" + args[0] + "'. To see usage, use the '-h' option");
            }
        } else if (args.length == 2 || args.length == 3) {
            File image1 = new File(args[0]);
            File image2 = new File(args[1]);
            File result = null;
            if (args.length == 3) {
                result = new File(args[2]);
            }
            return Optional.of(new Arguments(image1, image2, result));
        } else {
            return error("Too many arguments. To see usage, use the '-h' option");
        }
    }

    private Optional<Arguments> help() {
        System.out.println("Java ImageComparison Tool\n" +
                "\n" +
                "Usage:\n" +
                "  java -jar image-comparison.jar <options> [image1 image2 [result]]\n" +
                "where:\n" +
                "    image1     the first image to compare.\n" +
                "    image2     the second image to compare.\n" +
                "    result     the comparison result image. If not provided, the image is shown in a UI.\n" +
                "\n" +
                "Options:\n" +
                "    -h --help  print this help message\n\n" +
                "If no arguments are provided, two demo images are compared and the difference displayed in the UI.\n");
        successExit.run();
        return Optional.empty();
    }

    private Optional<Arguments> error(String errorMessage) {
        System.err.println(errorMessage);
        errorExit.run();
        return Optional.empty();
    }

    public static final class Arguments {

        private final File image1;
        private final File image2;
        private final File destinationImage;

        Arguments(File image1, File image2, File destinationImage) {
            this.image1 = image1;
            this.image2 = image2;
            this.destinationImage = destinationImage;
        }

        public File getImage1() {
            return image1;
        }

        public File getImage2() {
            return image2;
        }

        public Optional<File> getDestinationImage() {
            return Optional.ofNullable(destinationImage);
        }
    }

}
