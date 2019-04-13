package com.github.romankh3.image.comparison;

import static java.awt.Color.RED;

import com.github.romankh3.image.comparison.model.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ImageComparison {

    /**
     * Prefix of the name of the result image.
     */
    private static final String NAME_PREFIX = "image-comparison";
    /**
     * Suffix of the name of of the result image.
     */
    private static final String NAME_SUFFIX = ".png";
    /**
     * The threshold which means the max distance between non-equal pixels.
     * Could be changed according size and requirements to the image.
     */
    public static int threshold = 5;
    /**
     * First image for comparing
     */
    private final BufferedImage image1;

    /**
     * Second image for comparing
     */
    private final BufferedImage image2;

    private final /* @Nullable */ File destination;
    /**
     * The number which marks how many rectangles. Beginning from 2.
     */
    private int counter = 2;
    /**
     * The number of the marking specific rectangle.
     */
    private int regionCount = counter;
    private int[][] matrix;

    public ImageComparison(String image1, String image2) throws IOException, URISyntaxException {
        this(ImageComparisonTools.readImageFromResources(image1), ImageComparisonTools.readImageFromResources(image2), null);
    }

    /**
     * Create a new instance of {@link ImageComparison} that can compare the given images.
     *
     * @param image1 first image to be compared
     * @param image2 second image to be compared
     * @param destination destination to save the result. If null, the result is shown in the UI.
     */
    public ImageComparison(BufferedImage image1, BufferedImage image2, File destination) {
        this.image1 = image1;
        this.image2 = image2;
        this.destination = destination;
    }

    public ImageComparison(BufferedImage image1, BufferedImage image2) {
        this(image1, image2, null);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        ImageComparison imgCmp = CommandLineUtil.create(args);
        BufferedImage result = imgCmp.compareImages();
        CommandLineUtil.handleResult(imgCmp, (file) -> ImageComparisonTools.saveImage(file, result), () -> ImageComparisonTools
                .createGUI(result));
    }

    /**
     * Draw rectangles which cover the regions of the difference pixels.
     *
     * @return the result of the drawing.
     */
    public BufferedImage compareImages() throws IOException {
        // check images for valid
        ImageComparisonTools.checkCorrectImageSize(image1, image2);

        matrix = ImageComparisonTools.populateTheMatrixOfTheDifferences(image1, image2);

        BufferedImage outImg = ImageComparisonTools.deepCopy(image2);

        Graphics2D graphics = outImg.createGraphics();
        graphics.setColor(RED);

        groupRegions(matrix, regionCount);

        List<Rectangle> rectangles = populateRectangles();

        drawRectangles(rectangles, graphics);

        //save the image:
        ImageComparisonTools
                .saveImage(this.getDestination().orElse(Files.createTempFile(NAME_PREFIX, NAME_SUFFIX).toFile()), outImg);
        return outImg;
    }

    private List<Rectangle> populateRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        while (counter <= regionCount) {
            Rectangle rectangle = createRectangle(matrix, counter);
            if (!rectangle.equals(Rectangle.createDefault())) {
                rectangles.add(rectangle);
            }
            counter++;
        }

        return mergeRectangles(rectangles);
    }

    /**
     * Create a {@link Rectangle} object.
     *
     * @param matrix the matrix of the Conformity pixels.
     * @param counter the number from marks regions.
     * @return the {@link Rectangle} object.
     */
    private Rectangle createRectangle(int[][] matrix, int counter) {
        Rectangle rectangle = Rectangle.createDefault();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == counter) {
                    updateRectangleCreation(rectangle, x, y);
                }
            }
        }
        return rectangle;
    }

    private void updateRectangleCreation(Rectangle rectangle, int x, int y) {
        if (x < rectangle.getMinX()) {
            rectangle.setMinX(x);
        }
        if (x > rectangle.getMaxX()) {
            rectangle.setMaxX(x);
        }

        if (y < rectangle.getMinY()) {
            rectangle.setMinY(y);
        }
        if (y > rectangle.getMaxY()) {
            rectangle.setMaxY(y);
        }
    }

    private List<Rectangle> mergeRectangles(List<Rectangle> rectangles) {
        int position = 0;
        while (position < rectangles.size()) {
            for (int i = 1 + position; i < rectangles.size(); i++) {
                Rectangle r1 = rectangles.get(position);
                Rectangle r2 = rectangles.get(i);
                if (r1.equals(Rectangle.createZero())) {
                    continue;
                }
                if (r1.isOverlapping(r2)) {
                    rectangles.set(position, r1.merge(r2));
                    r2.makeZeroRectangle();
                    if (position != 0) {
                        position--;
                    }
                }
            }
            position++;
        }

        return rectangles.stream().filter(it -> !it.equals(Rectangle.createZero())).collect(Collectors.toList());
    }

    private void drawRectangles(List<Rectangle> rectangles, Graphics2D graphics) {
        rectangles.forEach(rectangle -> graphics.drawRect(rectangle.getMinY(),
                rectangle.getMinX(),
                rectangle.getWidth(),
                rectangle.getHeight()));
    }

    /**
     * Group rectangle regions in matrix.
     */
    private void groupRegions(int[][] matrix, int regionCount) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == 1) {
                    joinToRegion(row, col);
                    regionCount++;
                }
            }
        }
    }

    /**
     * The recursive method which go to all directions and finds difference
     * in binary matrix using {@code threshold} for setting max distance between values which equal "1".
     * and set the {@code groupCount} to matrix.
     *
     * @param row the value of the row.
     * @param col the value of the column.
     */
    private void joinToRegion(int row, int col) {
        if (row >= matrix.length || col >= matrix[row].length) { return; }

        matrix[row][col] = regionCount;

        for (int i = 0; i < threshold; i++) {
            joinToRegion(row + 1 + i, col);
            joinToRegion(row, col + 1 + i);

            joinToRegion(row + 1 + i, col - 1 - i);
            joinToRegion(row - 1 - i, col + 1 + i);
            joinToRegion(row + 1 + i, col + 1 + i);
        }
    }

    public Optional<File> getDestination() {
        return Optional.ofNullable(destination);
    }

    public BufferedImage getImage1() {
        return image1;
    }

    public BufferedImage getImage2() {
        return image2;
    }
}
