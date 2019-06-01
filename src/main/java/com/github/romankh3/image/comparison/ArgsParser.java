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
        switch (args.length) {
            case 0:
                return Optional.empty();
            case 1:
                return help(args);
            case 2:
            case 3:
                return execute(args);
            default:
                return error("Too many arguments. To see usage, use the '-h' option");
        }
    }

    private Optional<Arguments> help(String... args) {
        if (args[0].equals("-h") || args[0].equals("--help")) {
            System.out.println("Java ImageComparison Tool\n" +
                    "\n" +
                    "Usage:\n" +
                    "  java -jar image-comparison.jar <options> [expected actual [result]]\n" +
                    "where:\n" +
                    "    expected     expected image to compare.\n" +
                    "    actual     actual image to compare.\n" +
                    "    result     the comparison result image. If not provided, the image is shown in a UI.\n" +
                    "\n" +
                    "Options:\n" +
                    "    -h --help  print this help message\n\n" +
                    "If no arguments are provided, two demo images are compared and the difference displayed in the UI.\n");
            successExit.run();
            return Optional.empty();
        } else {
            return error("Unrecognized option: '" + args[0] + "'. To see usage, use the '-h' option");
        }
    }

    private Optional<Arguments> execute(String... args) {
        File expected = new File(args[0]);
        File actual = new File(args[1]);
        File result = null;
        if (args.length == 3) {
            result = new File(args[2]);
        }
        return Optional.of(new Arguments(expected, actual, result));
    }

    private Optional<Arguments> error(String errorMessage) {
        System.err.println(errorMessage);
        errorExit.run();
        return Optional.empty();
    }

    public static final class Arguments {

        private final File expected;
        private final File actual;
        private final File destinationImage;

        Arguments(File expected, File actual, File destinationImage) {
            this.expected = expected;
            this.actual = actual;
            this.destinationImage = destinationImage;
        }

        public File getExpected() {
            return expected;
        }

        public File getActual() {
            return actual;
        }

        public Optional<File> getDestinationImage() {
            return Optional.ofNullable(destinationImage);
        }
    }

}
