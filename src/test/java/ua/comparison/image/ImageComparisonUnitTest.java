package ua.comparison.image;


import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ua.comparison.image.ImageComparison.*;


/**
 * Unit-level testing for {@link ImageComparison} object.
 */
public class ImageComparisonUnitTest {

    @Test
    public void testCorrectWorking() throws IOException, URISyntaxException {

        // get images from Resources
        BufferedImage image1 = readImageFromResources( "image1.png" );
        BufferedImage image2 = readImageFromResources( "image2.png" );

        // Draw rectangles on the image.
        BufferedImage drawnDifferences = drawTheDifference( image1, image2 );

        // Get the expected image.
        BufferedImage expectedResultImage = readImageFromResources( "result.png" );

        // assert height of the images.
        assertEquals( drawnDifferences.getHeight(), expectedResultImage.getHeight() );

        // assert width of the images.
        assertEquals( drawnDifferences.getWidth(), expectedResultImage.getWidth() );

        // assert each pixel.
        for ( int y = 0; y < drawnDifferences.getHeight(); y++ ) {
            for ( int x = 0; x < drawnDifferences.getWidth(); x++ ) {
                assertTrue( !isDifferent( x, y, expectedResultImage, drawnDifferences ) );
            }
        }
    }

    @Test( expected = IllegalArgumentException.class )
    public void testCheckInCorrectImageSize() {
        checkCorrectImageSize( 10, 10, 11, 11 );
    }
}
