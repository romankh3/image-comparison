package ua.comparison.image;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
}
