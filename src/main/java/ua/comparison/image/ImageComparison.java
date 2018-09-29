package ua.comparison.image;

import ua.comparison.image.model.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Optional;

import static java.awt.Color.RED;
import static ua.comparison.image.ImageComparisonTools.*;

public class ImageComparison {

    /**
     * The threshold which means the max distance between non-equal pixels.
     * Could be changed according size and requirements to the image.
     */
    public static int threshold = 5;

    /**
     * The number which marks how many rectangles. Beginning from 2.
     */
    private int counter = 2;

    /**
     * The number of the marking specific rectangle.
     */
    private int regionCount = counter;

    private final BufferedImage image1;
    private final BufferedImage image2;
    private int[][] matrix;

    ImageComparison( String image1, String image2 ) throws IOException, URISyntaxException {
        this( readImageFromResources(image1), readImageFromResources(image2) );
    }

    /**
     * Create a new instance of {@link ImageComparison} that can compare the given images.
     *
     * @param image1 first image to be compared
     * @param image2 second image to be compared
     */
    public ImageComparison( BufferedImage image1, BufferedImage image2 ) {
        this.image1 = image1;
        this.image2 = image2;
        matrix = populateTheMatrixOfTheDifferences( image1, image2 );
    }

    public static void main( String[] args ) throws IOException, URISyntaxException {
        ArgsParser parser = new ArgsParser();
        Optional<ArgsParser.Arguments> arguments = parser.parseArgs(args);
        ImageComparison imgCmp;
        if (arguments.isPresent()) {
            imgCmp = new ImageComparison(
                    readImageFromFile(arguments.get().getImage1()),
                    readImageFromFile(arguments.get().getImage2()));
            Optional<File> destination = arguments.get().getDestinationImage();
            if (destination.isPresent()) {
                BufferedImage result = imgCmp.compareImages();
                saveImage(destination.get(), result);
            } else {
                createGUI( imgCmp.compareImages() );
            }
        } else {
            imgCmp = new ImageComparison(
                    readImageFromResources("image1.png" ),
                    readImageFromResources("image2.png" ));
            createGUI( imgCmp.compareImages() );
        }
    }

    /**
     * Draw rectangles which cover the regions of the difference pixels.
     * @return the result of the drawing.
     */
    BufferedImage compareImages() throws IOException {
        // check images for valid
        checkCorrectImageSize( image1, image2 );

        BufferedImage outImg = deepCopy( image2 );

        Graphics2D graphics = outImg.createGraphics();
        graphics.setColor( RED );

        groupRegions();
        drawRectangles( graphics );

        //save the image:
        saveImage(Files.createTempFile("image-comparison", ".png").toFile(), outImg );

        return outImg;
    }

    /**
     * Draw rectangles with the differences pixels.
     * @param graphics the Graphics2D object for drawing rectangles.
     */
    private void drawRectangles( Graphics2D graphics ) {
        if( counter > regionCount ) return;

        Rectangle rectangle = createRectangle( matrix, counter );

        graphics.drawRect( rectangle.getMinY(), rectangle.getMinX(), rectangle.getWidth(), rectangle.getHeight() );
        counter++;
        drawRectangles( graphics );
    }

    /**
     * Group rectangle regions in binary matrix.
     */
    private void groupRegions() {
        for ( int row = 0; row < matrix.length; row++ ) {
            for ( int col = 0; col < matrix[row].length; col++ ) {
                if ( matrix[row][col] == 1 ) {
                    joinToRegion( row, col );
                    regionCount++;
                }
            }
        }
    }

    /**
     * The recursive method which go to all directions and finds difference
     * in binary matrix using {@code threshold} for setting max distance between values which equal "1".
     * and set the {@code groupCount} to matrix.
     * @param row the value of the row.
     * @param col the value of the column.
     */
    private void joinToRegion( int row, int col ) {
        if ( row < 0 || row >= matrix.length || col < 0 || col >= matrix[row].length || matrix[row][col] != 1 ) return;

        matrix[row][col] = regionCount;

        for ( int i = 0; i < threshold; i++ ) {
            // goes to all directions.
            joinToRegion( row - 1 - i, col );
            joinToRegion( row + 1 + i, col );
            joinToRegion( row, col - 1 - i );
            joinToRegion( row, col + 1 + i );

            joinToRegion( row - 1 - i, col - 1 - i );
            joinToRegion( row + 1 + i, col - 1 - i );
            joinToRegion( row - 1 - i, col + 1 + i );
            joinToRegion( row + 1 + i, col + 1 + i );
        }
    }
}
