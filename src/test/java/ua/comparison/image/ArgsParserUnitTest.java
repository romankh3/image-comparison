package ua.comparison.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Optional;
import org.junit.Test;

public class ArgsParserUnitTest {

    private final KnowsIfRan successExitMock = new KnowsIfRan();
    private final KnowsIfRan errorExitMock = new KnowsIfRan();

    private final ArgsParser parser = new ArgsParser(successExitMock, errorExitMock);

    @Test
    public void noOptions() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs();
        assertFalse(result.isPresent());
        assertFalse(successExitMock.hasRun);
        assertFalse(errorExitMock.hasRun);
    }

    @Test
    public void shortHelpOption() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("-h");
        assertFalse(result.isPresent());
        assertTrue(successExitMock.hasRun);
        assertFalse(errorExitMock.hasRun);
    }

    @Test
    public void longHelpOption() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("--help");
        assertFalse(result.isPresent());
        assertTrue(successExitMock.hasRun);
        assertFalse(errorExitMock.hasRun);
    }

    @Test
    public void compareTwoImagesAndShowInUI() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("img1", "img2");
        assertTrue(result.isPresent());

        ArgsParser.Arguments args = result.get();
        assertEquals("img1", args.getImage1().getPath());
        assertEquals("img2", args.getImage2().getPath());
        assertFalse(args.getDestinationImage().isPresent());

        assertFalse(successExitMock.hasRun);
        assertFalse(errorExitMock.hasRun);
    }

    @Test
    public void compareTwoImagesAndSaveResultInFile() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("first", "second", "res");
        assertTrue(result.isPresent());

        ArgsParser.Arguments args = result.get();
        assertEquals("first", args.getImage1().getPath());
        assertEquals("second", args.getImage2().getPath());
        assertTrue(args.getDestinationImage().isPresent());

        File destinationImage = args.getDestinationImage().get();
        assertEquals("res", destinationImage.getPath());

        assertFalse(successExitMock.hasRun);
        assertFalse(errorExitMock.hasRun);
    }

    @Test
    public void unrecognizedOption() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("wrong");
        assertFalse(result.isPresent());
        assertFalse(successExitMock.hasRun);
        assertTrue(errorExitMock.hasRun);
    }

    @Test
    public void tooManyArguments() {
        Optional<ArgsParser.Arguments> result = parser.parseArgs("a", "b", "c", "d");
        assertFalse(result.isPresent());
        assertFalse(successExitMock.hasRun);
        assertTrue(errorExitMock.hasRun);
    }

    private static class KnowsIfRan implements Runnable {
        boolean hasRun = false;

        @Override
        public void run() {
            hasRun = true;
        }
    }

}
