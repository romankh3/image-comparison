package com.github.romankh3.image.comparison;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Optional;
import org.junit.Test;

/**
 * Unit-level testing for {@link ArgsParser} object.
 */
public class ArgsParserUnitTest {

    private final KnowsIfRan successExitMock = new KnowsIfRan();
    private final KnowsIfRan errorExitMock = new KnowsIfRan();

    private final ArgsParser parser = new ArgsParser(successExitMock, errorExitMock);

    @Test
    public void noOptions() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs();
        assertFalse(result.isPresent());
        assertFalse(successExitMock.isHasRun());
        assertFalse(errorExitMock.isHasRun());
    }

    @Test
    public void shortHelpOption() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("-h");
        assertFalse(result.isPresent());
        assertTrue(successExitMock.isHasRun());
        assertFalse(errorExitMock.isHasRun());
    }

    @Test
    public void longHelpOption() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("--help");
        assertFalse(result.isPresent());
        assertTrue(successExitMock.isHasRun());
        assertFalse(errorExitMock.isHasRun());
    }

    @Test
    public void compareTwoImagesAndShowInUI() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("img1", "img2");
        assertTrue(result.isPresent());

        ArgsParser.Arguments args = result.get();
        assertEquals("img1", args.getExpected().getPath());
        assertEquals("img2", args.getActual().getPath());
        assertFalse(args.getDestinationImage().isPresent());

        assertFalse(successExitMock.isHasRun());
        assertFalse(errorExitMock.isHasRun());
    }

    @Test
    public void compareTwoImagesAndSaveResultInFile() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("first", "second", "res");
        assertTrue(result.isPresent());

        ArgsParser.Arguments args = result.get();
        assertEquals("first", args.getExpected().getPath());
        assertEquals("second", args.getActual().getPath());
        assertTrue(args.getDestinationImage().isPresent());

        File destinationImage = args.getDestinationImage().get();
        assertEquals("res", destinationImage.getPath());

        assertFalse(successExitMock.isHasRun());
        assertFalse(errorExitMock.isHasRun());
    }

    @Test
    public void unrecognizedOption() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("wrong");
        assertFalse(result.isPresent());
        assertFalse(successExitMock.isHasRun());
        assertTrue(errorExitMock.isHasRun());
    }

    @Test
    public void tooManyArguments() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("a", "b", "c", "d");
        assertFalse(result.isPresent());
        assertFalse(successExitMock.isHasRun());
        assertTrue(errorExitMock.isHasRun());
    }

    private static class KnowsIfRan implements Runnable {

        private boolean hasRun = false;

        @Override
        public void run() {
            hasRun = true;
        }

        public boolean isHasRun() {
            return hasRun;
        }
    }

}
