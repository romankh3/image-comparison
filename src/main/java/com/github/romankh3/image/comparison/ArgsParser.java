package com.github.romankh3.image.comparison;

import java.io.File;
import java.util.Optional;

/**
 * Arguments parser for commandline usage.
 */
final class ArgsParser {

    /**
     * {@link Runnable} for success exit.
     */
    private final Runnable successExit;

    /**
     * {@link Runnable} for error exit.
     */
    private final Runnable errorExit;

    /**
     * Create new instance of the {@link ArgsParser} with default {@link ArgsParser#successExit}
     * and {@link ArgsParser#errorExit}.
     */
    ArgsParser() {
        this(() -> System.exit(0), () -> System.exit(1));
    }

    /**
     * Create new instance of the {@link ArgsParser} with provided {@link ArgsParser#successExit}
     * and {@link ArgsParser#errorExit}.
     *
     * @param successExit {@link Runnable} for success exit.
     * @param errorExit {@link Runnable} for error exit.
     */
    ArgsParser(Runnable successExit, Runnable errorExit) {
        this.successExit = successExit;
        this.errorExit = errorExit;
    }

    /**
     * Parse arguments provided by args.
     *
     * @param args arguments for parsing.
     * @return {@link Optional<Arguments>} object.
     */
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

    /**
     * Execute help operation.
     *
     * @param args arguments for parsing.
     * @return {@link Optional<Arguments>} object.
     */
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

    /**
     * Execute main action, where all the needed data are provided.
     *
     * @param args arguments for parsing.
     * @return {@link Optional<Arguments>} object.
     */
    private Optional<Arguments> execute(String... args) {
        File expected = new File(args[0]);
        File actual = new File(args[1]);
        File result = null;
        if (args.length == 3) {
            result = new File(args[2]);
        }
        return Optional.of(new Arguments(expected, actual, result));
    }

    /**
     * Execute error action.
     *
     * @param errorMessage message of the error.
     * @return {@link Optional#empty()} object.
     */
    private Optional<Arguments> error(String errorMessage) {
        System.err.println(errorMessage);
        errorExit.run();
        return Optional.empty();
    }

    /**
     * Inner static final class for result of the parsing.
     */
    public static final class Arguments {

        /**
         * Expected image for comparing
         */
        private final File expected;

        /**
         * Actual image for comparing
         */
        private final File actual;

        /**
         * Destination file for the result of the comparing.
         */
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
