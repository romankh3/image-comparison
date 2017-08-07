package ua.comparison.image;

import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static ua.comparison.image.ImageComparisonTools.checkCorrectImageSize;
import static ua.comparison.image.ImageComparisonTools.createGUI;
import static ua.comparison.image.ImageComparisonTools.readImageFromResources;

/**
 * Unit-level testing for {@link ImageComparisonTools} object.
 */
public class ImageComparisonToolsUnitTest {

    @Test
    public void testFrameMethod() throws IOException, URISyntaxException {
        BufferedImage image = readImageFromResources( "result.png" );
        Frame resultFrame = createGUI( image );
        assertEquals( resultFrame.getHeight(), image.getHeight() );
        assertEquals( resultFrame.getWidth(), image.getWidth() );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testCheckInCorrectImageSize() {
        checkCorrectImageSize( 10, 10, 11, 11 );
    }
}
