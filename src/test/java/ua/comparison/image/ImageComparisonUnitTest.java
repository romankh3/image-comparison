package ua.comparison.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static ua.comparison.image.ImageComparisonTools.isDifferent;
import static ua.comparison.image.ImageComparisonTools.readImageFromResources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Test;


/**
 * Unit-level testing for {@link ImageComparison} object.
 */
public class ImageComparisonUnitTest {
    
    private static final String IMAGE_1_NAME = "image1.png";
    private static final String IMAGE_2_NAME = "image2.png";
    private static final String RESULT_1_NAME = "result1.png";
    
    private static final String BUG_17_1_NAME = "b1#17.png";
    private static final String BUG_17_2_NAME = "b2#17.png";
    private static final String BUG_21_1_NAME = "b1#21.png";
    private static final String BUG_21_2_NAME = "b2#21.png";

    @Test
    public void shouldCorrectWorkingCommonCase() throws IOException, URISyntaxException {
        //given
        BufferedImage expectedResultImage = readImageFromResources( RESULT_1_NAME );

        //when
        BufferedImage drawnDifferences = new ImageComparison( IMAGE_1_NAME, IMAGE_2_NAME ).compareImages();


        //then
        assertImagesEqual(expectedResultImage, drawnDifferences);
    }

    /**
     * Test issue #17. It was StackOverFlowError.
     */
    @Test
    public void testIssue17() throws IOException, URISyntaxException {
        BufferedImage bufferedImage = new ImageComparison(BUG_17_1_NAME, BUG_17_2_NAME).compareImages();
        assertNotNull(bufferedImage);
    }

    /**
     * Test issue #21. It was StackOverFlowError.
     */
    @Test
    public void testIssue21() throws IOException, URISyntaxException {
        BufferedImage bufferedImage = new ImageComparison(BUG_21_1_NAME, BUG_21_2_NAME).compareImages();
        assertNotNull(bufferedImage);
    }

    @Test
    public void testCreateDefault() throws IOException, URISyntaxException {
        ImageComparison comparison = ImageComparison.createDefault();
        assertImagesEqual(readImageFromResources(IMAGE_1_NAME ), comparison.getImage1());
        assertImagesEqual(readImageFromResources(IMAGE_2_NAME ), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoArgs() throws IOException, URISyntaxException {
        File image1 = new File( ImageComparison.class.getClassLoader().getResource ( IMAGE_1_NAME ).toURI().getPath() );
        File image2 = new File( ImageComparison.class.getClassLoader().getResource ( IMAGE_2_NAME ).toURI().getPath() );
        ImageComparison comparison = ImageComparison.create(image1.getAbsolutePath(), image2.getAbsolutePath());

        assertImagesEqual(readImageFromResources(IMAGE_1_NAME ), comparison.getImage1());
        assertImagesEqual(readImageFromResources(IMAGE_2_NAME ), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoImagesAsArgs() throws IOException, URISyntaxException {
        File image1 = new File( ImageComparison.class.getClassLoader().getResource ( IMAGE_1_NAME ).toURI().getPath() );
        File image2 = new File( ImageComparison.class.getClassLoader().getResource ( IMAGE_2_NAME ).toURI().getPath() );
        ImageComparison comparison = ImageComparison.create(new ArgsParser.Arguments(image1, image2, null));

        assertImagesEqual(readImageFromResources(IMAGE_1_NAME ), comparison.getImage1());
        assertImagesEqual(readImageFromResources(IMAGE_2_NAME ), comparison.getImage2());
        assertFalse(comparison.getDestination().isPresent());
    }

    @Test
    public void testCreateWithTwoImagesAndDestinationFileAsArgs() throws IOException, URISyntaxException {
        File image1 = new File( ImageComparison.class.getClassLoader().getResource ( IMAGE_1_NAME ).toURI().getPath() );
        File image2 = new File( ImageComparison.class.getClassLoader().getResource ( IMAGE_2_NAME ).toURI().getPath() );
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        ImageComparison comparison = ImageComparison.create(new ArgsParser.Arguments(image1, image2, destination));

        assertImagesEqual(readImageFromResources(IMAGE_1_NAME ), comparison.getImage1());
        assertImagesEqual(readImageFromResources(IMAGE_2_NAME ), comparison.getImage2());
        assertTrue(comparison.getDestination().isPresent());
        assertEquals(destination, comparison.getDestination().get());
    }

    @Test
    public void resultIsHandledCorrectlyWhenItShouldShowUI() throws IOException, URISyntaxException {
        ImageComparison comparison = new ImageComparison(IMAGE_1_NAME, IMAGE_2_NAME);
        AtomicBoolean savedToFile = new AtomicBoolean(false);
        AtomicBoolean showUI = new AtomicBoolean(false);

        ImageComparison.handleResult(comparison, file -> savedToFile.set(true), () -> showUI.set(true));

        assertFalse(savedToFile.get());
        assertTrue(showUI.get());
    }

    @Test
    public void resultIsHandledCorrectlyWhenItShouldSaveToFile() throws IOException, URISyntaxException {
        File image1 = new File( ImageComparison.class.getClassLoader().getResource ( IMAGE_1_NAME ).toURI().getPath() );
        File image2 = new File( ImageComparison.class.getClassLoader().getResource ( IMAGE_2_NAME ).toURI().getPath() );
        File destination = Files.createTempFile("image-comparison-test", ".png").toFile();
        ImageComparison comparison = ImageComparison.create(new ArgsParser.Arguments(image1, image2, destination));

        AtomicBoolean savedToFile = new AtomicBoolean(false);
        AtomicBoolean showUI = new AtomicBoolean(false);

        ImageComparison.handleResult(comparison, file -> savedToFile.set(true), () -> showUI.set(true));

        assertTrue(savedToFile.get());
        assertFalse(showUI.get());
    }

    private static void assertImagesEqual(BufferedImage expectedImage, BufferedImage drawnImage) {
        if (expectedImage.getWidth() != drawnImage.getWidth() || expectedImage.getHeight() != drawnImage.getHeight()) {
            fail("Images have different dimensions");
        }
        for ( int y = 0; y < drawnImage.getHeight(); y++ ) {
            for ( int x = 0; x < drawnImage.getWidth(); x++ ) {
                assertFalse( isDifferent( expectedImage.getRGB( x, y ), drawnImage.getRGB( x, y ) ) );
            }
        }
    }

}
