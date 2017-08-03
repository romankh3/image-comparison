package ua.comparison.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
public class ImageComparison {

    /**
     * The threshold which means the max distance between non-equal pixels.
     */
    public static int THRESHOLD = 5;

    /**
     * The first number which marks first rectangle.
     */
    private static final int COUNTER = 2;

    public static void main( String[] args ) throws IOException, URISyntaxException {
        // get images from Resources
        BufferedImage image1 = readImageFromResources( "image1.png" );
        BufferedImage image2 = readImageFromResources( "image2.png" );

        // Draw rectangles on the image.
        BufferedImage drawnDifferences = drawTheDifference( image1, image2 );

        // make dir if it's not using from Gradle.
        new File( "build" );
        ImageIO.write( drawnDifferences,
                "png",
                new File( "build/result.png" ) );

        ImageResultFrame.createGUI( drawnDifferences );
    }

    /**
     * Draw rectangles which cover the regions of the difference pixels.
     * @param image1 {@code BufferedImage} object of the first image.
     * @param image2 {@code BufferedImage} object of the second image.
     * @return the result of the drawing.
     */
    public static BufferedImage drawTheDifference(BufferedImage image1, BufferedImage image2 ) {
        // check images for valid
        checkingImages(image1,image2);

        int width = image1.getWidth();
        int height = image1.getHeight();

        int[][] matrix = new int[width][height];

        BufferedImage outImg = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ( isDifferent( x, y, image1, image2 ) ) {
                    matrix[x][y] = 1;
                } else {
                    matrix[x][y] = 0;
                }
                outImg.setRGB(x, y, image2.getRGB(x, y) );
            }
        }

        Graphics2D graphics = outImg.createGraphics();
        graphics.setColor( Color.RED );

        int lastNumberCount = groupRegions(matrix, THRESHOLD);
        drawRectangles(matrix, graphics, COUNTER, lastNumberCount );
        return outImg;
    }

    /**
     * Group rectangle regions in binary matrix.
     * @param matrix The binary matrix.
     * @param threshold The threshold which means the max distance between non-equal pixels.
     * @return the last number which marks the lat region.
     */
    private static int groupRegions(int[][] matrix, int threshold) {
        int regionCount = 2;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == 1) {
                    joinToRegion(matrix, row, col, regionCount, threshold);
                    regionCount++;
                }
            }
        }
        return regionCount;
    }

    /**
     * Draw rectangles with the differences pixels.
     * @param matrix the matrix of the Conformity pixels.
     * @param graphics the Graphics2D object for drawing rectangles.
     * @param counter the number from marks regions.
     * @param lastNumberCount the last number which marks region.
     */
    private static void drawRectangles(int[][] matrix, Graphics2D graphics, int counter, int lastNumberCount ) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        if( counter > lastNumberCount ) return;

        boolean find = false;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == counter) {
                    find = true;

                    if (x < minX) minX = x;
                    if (x > maxX) maxX = x;

                    if (y < minY) minY = y;
                    if (y > maxY) maxY = y;
                }
            }
        }
        if (find) {
            graphics.drawRect( minY, minX, maxY - minY, maxX - minX);
            drawRectangles(matrix, graphics, ++counter, lastNumberCount );
        }
    }

    /**
     * The recursive method which go to all directions and finds difference
     * in binary matrix using {@code threshold} for set max distance between values which equal "1".
     * and set the {@code groupCount} to matrix.
     * @param matrix the binary matrix.
     * @param row the value of the row.
     * @param col the value of the column.
     * @param regionCount the number which marks caught values that are "1".
     * @param threshold the max distance between two values which equal "1".
     */
    private static void joinToRegion( int[][] matrix, int row, int col, int regionCount, int threshold ) {
        if (row < 0 || row >= matrix.length) return;
        if (col < 0 || col >= matrix[row].length) return;
        if (matrix[row][col] != 1) return;

        matrix[row][col] = regionCount;

        for (int i = 0; i < threshold; i++) {
            // goes to all directions.
            joinToRegion(matrix, row - 1 - i, col, regionCount, threshold);
            joinToRegion(matrix, row + 1 + i, col, regionCount, threshold);
            joinToRegion(matrix, row, col - 1 - i, regionCount, threshold);
            joinToRegion(matrix, row, col + 1 + i, regionCount, threshold);

            joinToRegion(matrix, row - 1 - i, col - 1 - i, regionCount, threshold);
            joinToRegion(matrix, row + 1 + i, col - 1 - i, regionCount, threshold);
            joinToRegion(matrix, row - 1 - i, col + 1 + i, regionCount, threshold);
            joinToRegion(matrix, row + 1 + i, col + 1 + i, regionCount, threshold);
        }

    }

    /**
     * Says if the two pixels equal or not. The rule is the difference between two pixels
     * need to be more then 10%.
     * @param x the X value of the binary matrix.
     * @param y the Y value of the binary matrix.
     * @param image1 {@code BufferedImage} object of the first image.
     * @param image2 {@code BufferedImage} object of the second image.
     * @return {@code true} if they' are difference, {@code false} otherwise.
     */
    private static boolean isDifferent(int x, int y, BufferedImage image1, BufferedImage image2){
        boolean result = false;
        int[] im1= image1.getRaster().getPixel(x,y,new int[3]);
        int[] im2= image2.getRaster().getPixel(x,y,new int[3]);
        //gets modules of the images:
        double mod1 = Math.sqrt(im1[0]*im1[0]+im1[1]*im1[1]+im1[2]*im1[2]);
        double mod2 = Math.sqrt(im2[0]*im2[0]+im2[1]*im2[1]+im2[2]*im2[2]);
        // gets module of the difference of images.
        double mod3 = Math.sqrt( Math.abs(im1[0]-im2[0]) * Math.abs(im1[0]-im2[0]) +
                Math.abs(im1[1]-im2[1]) * Math.abs(im1[1]-im2[1]) +
                Math.abs(im1[2]-im2[2])*Math.abs(im1[2]-im2[2]));
        double imageChanges1 = mod3/mod1;
        double imageChanges2 = mod3/mod2;
        if( imageChanges1 > 0.1 && imageChanges2 > 0.1 ) result = true;
        return result;
    }

    /**
     * Reads image from the provided path.
     * @param path the path where contains image.
     * @return the {@code BufferedImage} object of this specific image.
     * @throws IOException
     * @throws URISyntaxException
     */
    static BufferedImage readImageFromResources(String path) throws IOException, URISyntaxException {
        return ImageIO.read( new File( ImageComparison.class.getClassLoader().getResource (path ).toURI().getPath()) );
    }

    /**
     * Checks images for equals their widths and heights.
     * @param image1 {@code BufferedImage} object of the first image.
     * @param image2 {@code BufferedImage} object of the second image.
     */
    private static void checkingImages( BufferedImage image1, BufferedImage image2 ) {
        if( image1.getWidth() != image2.getWidth() || image2.getHeight() != image2.getHeight())
            throw new IllegalArgumentException("Images dimensions mismatch");
    }
}
