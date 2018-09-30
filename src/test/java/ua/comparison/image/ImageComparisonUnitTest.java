package ua.comparison.image;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static ua.comparison.image.ImageComparisonTools.*;


/**
 * Unit-level testing for {@link ImageComparison} object.
 */
public class ImageComparisonUnitTest {

    @Test
    public void testCorrectWorking() throws IOException, URISyntaxException {
        // Draw rectangles on the image.
        BufferedImage drawnDifferences = new ImageComparison( "image1.png", "image2.png" ).compareImages();

        // Get the expected image.
        BufferedImage expectedResultImage = readImageFromResources( "result1.png" );

        // assert height of the images.
        assertEquals( drawnDifferences.getHeight(), expectedResultImage.getHeight() );

        // assert width of the images.
        assertEquals( drawnDifferences.getWidth(), expectedResultImage.getWidth() );

        // assert each pixel.
        for ( int y = 0; y < drawnDifferences.getHeight(); y++ ) {
            for ( int x = 0; x < drawnDifferences.getWidth(); x++ ) {
                assertTrue( !isDifferent( expectedResultImage.getRGB( x, y ), drawnDifferences.getRGB( x, y ) ) );
            }
        }
    }

    /**
     * Test issue #17. It was StackOverFlowError.
     */
    @Test
    public void testIssue17() throws IOException, URISyntaxException {
        new ImageComparison( "b1#17.png", "b2#17.png" ).compareImages();
    }

    @Test
    public void testCreateDefault() throws IOException, URISyntaxException {
        ImageComparison comparison = ImageComparison.createDefault();
        assertImagesEqual(readImageFromResources("image1.png" ), comparison.getImage1());
        assertImagesEqual(readImageFromResources("image2.png" ), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoArgs() throws IOException, URISyntaxException {
        File image1 = new File( ImageComparison.class.getClassLoader().getResource ( "image1.png" ).toURI().getPath() );
        File image2 = new File( ImageComparison.class.getClassLoader().getResource ( "image2.png" ).toURI().getPath() );
        ImageComparison comparison = ImageComparison.create(image1.getAbsolutePath(), image2.getAbsolutePath());

        assertImagesEqual(readImageFromResources("image1.png" ), comparison.getImage1());
        assertImagesEqual(readImageFromResources("image2.png" ), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoImagesAsArgs() throws IOException, URISyntaxException {
        File image1 = new File( ImageComparison.class.getClassLoader().getResource ( "image1.png" ).toURI().getPath() );
        File image2 = new File( ImageComparison.class.getClassLoader().getResource ( "image2.png" ).toURI().getPath() );
        ImageComparison comparison = ImageComparison.create(new ArgsParser.Arguments(image1, image2, null));

        assertImagesEqual(readImageFromResources("image1.png" ), comparison.getImage1());
        assertImagesEqual(readImageFromResources("image2.png" ), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoImagesAndDestinationFileAsArgs() throws IOException, URISyntaxException {
        File image1 = new File( ImageComparison.class.getClassLoader().getResource ( "image1.png" ).toURI().getPath() );
        File image2 = new File( ImageComparison.class.getClassLoader().getResource ( "image2.png" ).toURI().getPath() );
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        ImageComparison comparison = ImageComparison.create(new ArgsParser.Arguments(image1, image2, destination));

        assertImagesEqual(readImageFromResources("image1.png" ), comparison.getImage1());
        assertImagesEqual(readImageFromResources("image2.png" ), comparison.getImage2());
        assertTrue(comparison.getDestination().isPresent());
        assertEquals(destination, comparison.getDestination().get());
    }

    @Test
    public void resultIsHandledCorrectlyWhenItShouldShowUI() throws IOException, URISyntaxException {
        ImageComparison comparison = new ImageComparison("image1.png", "image2.png");
        AtomicBoolean savedToFile = new AtomicBoolean(false);
        AtomicBoolean showUI = new AtomicBoolean(false);

        ImageComparison.handleResult(comparison, file -> savedToFile.set(true), () -> showUI.set(true));

        assertFalse(savedToFile.get());
        assertTrue(showUI.get());
    }

    @Test
    public void resultIsHandledCorrectlyWhenItShouldSaveToFile() throws IOException, URISyntaxException {
        File image1 = new File( ImageComparison.class.getClassLoader().getResource ( "image1.png" ).toURI().getPath() );
        File image2 = new File( ImageComparison.class.getClassLoader().getResource ( "image2.png" ).toURI().getPath() );
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        ImageComparison comparison = ImageComparison.create(new ArgsParser.Arguments(image1, image2, destination));

        AtomicBoolean savedToFile = new AtomicBoolean(false);
        AtomicBoolean showUI = new AtomicBoolean(false);

        ImageComparison.handleResult(comparison, file -> savedToFile.set(true), () -> showUI.set(true));

        assertTrue(savedToFile.get());
        assertFalse(showUI.get());
    }

    private static void assertImagesEqual(BufferedImage imgA, BufferedImage imgB) {
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            fail("Images have different dimensions");
        }

        int width  = imgA.getWidth();
        int height = imgA.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    fail("Images are different, found different pixel at: x = " + x + ", y = " + y);
                }
            }
        }
    }

}
