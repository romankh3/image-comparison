package com.github.romankh3.image.comparison;

import static java.awt.Color.RED;

import com.github.romankh3.image.comparison.model.ComparisonResult;
import com.github.romankh3.image.comparison.model.ComparisonState;
import com.github.romankh3.image.comparison.model.ExcludedAreas;
import com.github.romankh3.image.comparison.model.Point;
import com.github.romankh3.image.comparison.model.Rectangle;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Main class for comparison images.
 */
public class ImageComparison {

    /**
     * The threshold which means the max distance between non-equal pixels.
     * Could be changed according size and requirements to the image.
     */
    private int threshold = 5;

    /**
     * First image for comparing
     */
    private final BufferedImage image1;

    /**
     * Second image for comparing
     */
    private final BufferedImage image2;

    /**
     * Width of the line that is drawn in the rectangle
     */
    private int rectangleLineWidth = 1;

    /**
     * {@link File} of the result destination.
     */
    private /* @Nullable */ File destination;

    /**
     * The number which marks how many rectangles. Beginning from 2.
     */
    private int counter = 2;

    /**
     * The number of the marking specific rectangle.
     */
    private int regionCount = counter;

    /**
     * The number of the minimal rectangle size. Count as (width x height).
     */
    private Integer minimalRectangleSize = 1;

    /**
     * Maximal count of the {@link Rectangle}s.
     * It means that would get first x biggest rectangles.
     * Default value is -1, that means that all the rectagles would be drawn.
     */
    private Integer maximalRectangleCount = -1;

    /**
     * Matrix MxN.
     */
    private int[][] matrix;

    /**
     * ExcludedAreas contains a List of {@link Rectangle}s to be ignored when comparing images
     */
    private ExcludedAreas excludedAreas = new ExcludedAreas();

    public ImageComparison(String image1, String image2) throws IOException, URISyntaxException {
        this(ImageComparisonUtil.readImageFromResources(image1), ImageComparisonUtil.readImageFromResources(image2),
                null);
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

    /**
     * Draw rectangles which cover the regions of the difference pixels.
     *
     * @return the result of the drawing.
     * @throws IOException due to saving result image.
     */
    public ComparisonResult compareImages() throws IOException {

        // check images for valid
        if (isImageSizesNotEqual(image1, image2)) {
            return ComparisonResult.sizeMissMatchResult();
        }

        List<Rectangle> rectangles = populateRectangles();

        ComparisonResult comparisonResult = new ComparisonResult();
        comparisonResult.setImage1(image1);
        comparisonResult.setImage2(image2);

        if (rectangles.isEmpty()) {
            comparisonResult.setComparisonState(ComparisonState.MATCH);
            return comparisonResult;
        } else {
            comparisonResult.setComparisonState(ComparisonState.MISMATCH);
        }

        BufferedImage resultImage = ImageComparisonUtil.deepCopy(image2);
        drawRectangles(rectangles, resultImage);

        comparisonResult.setResult(resultImage);

        if (Objects.nonNull(destination)) {
            ImageComparisonUtil.saveImage(destination, resultImage);
        }

        return comparisonResult;
    }

    /**
     * Check images for equals their widths and heights.
     *
     * @param image1 {@link BufferedImage} object of the first image.
     * @param image2 {@link BufferedImage} object of the second image.
     *
     * @return true if image size are not equal, false otherwise.
     */
    private boolean isImageSizesNotEqual(BufferedImage image1, BufferedImage image2) {
        return image1.getHeight() != image2.getHeight() || image1.getWidth() != image2.getWidth();
    }

    /**
     * Populate binary matrix by "0" and "1". If the pixels are difference set it as "1", otherwise "0".
     */
    private void populateTheMatrixOfTheDifferences() {
        matrix = new int[image1.getWidth()][image1.getHeight()];
        for (int y = 0; y < image1.getHeight(); y++) {
            for (int x = 0; x < image1.getWidth(); x++) {
                Point point = new Point(x, y);
                if (!excludedAreas.contains(point)) {
                    matrix[x][y] = isDifferentPixels(image1.getRGB(x, y), image2.getRGB(x, y)) ? 1 : 0;
                }
            }
        }
    }

    /**
     * Say if the two pixels equal or not. The rule is the difference between two pixels
     * need to be more then 10%.
     *
     * @param rgb1 the RGB value of the Pixel of the Image1.
     * @param rgb2 the RGB value of the Pixel of the Image2.
     * @return {@code true} if they' are difference, {@code false} otherwise.
     */
    private boolean isDifferentPixels(int rgb1, int rgb2) {
        int red1 = (rgb1 >> 16) & 0xff;
        int green1 = (rgb1 >> 8) & 0xff;
        int blue1 = (rgb1) & 0xff;
        int red2 = (rgb2 >> 16) & 0xff;
        int green2 = (rgb2 >> 8) & 0xff;
        int blue2 = (rgb2) & 0xff;
        double result = Math.sqrt(Math.pow(red2 - red1, 2) +
                Math.pow(green2 - green1, 2) +
                Math.pow(blue2 - blue1, 2))
                /
                Math.sqrt(Math.pow(255, 2) * 3);
        return result > 0.1;
    }

    /**
     * Populate rectangles of the differences
     *
     * @return the collection of the populated {@link Rectangle} objects.
     */
    private List<Rectangle> populateRectangles() {
        populateTheMatrixOfTheDifferences();
        groupRegions();
        List<Rectangle> rectangles = new ArrayList<>();
        while (counter <= regionCount) {
            Rectangle rectangle = createRectangle();
            if (!rectangle.equals(Rectangle.createDefault()) && rectangle.size() >= minimalRectangleSize) {
                rectangles.add(rectangle);
            }
            counter++;
        }

        return mergeRectangles(rectangles);
    }

    /**
     * Create a {@link Rectangle} object.
     *
     * @return the {@link Rectangle} object.
     */
    private Rectangle createRectangle() {
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

    /**
     * Update {@link Point} of the rectangle based on x and y coordinates.
     */
    private void updateRectangleCreation(Rectangle rectangle, int x, int y) {
        if (x < rectangle.getMinPoint().getX()) {
            rectangle.getMinPoint().setX(x);
        }
        if (x > rectangle.getMaxPoint().getX()) {
            rectangle.getMaxPoint().setX(x);
        }

        if (y < rectangle.getMinPoint().getY()) {
            rectangle.getMinPoint().setY(y);
        }
        if (y > rectangle.getMaxPoint().getY()) {
            rectangle.getMaxPoint().setY(y);
        }
    }

    /**
     * Find overlapping rectangles and rmerge them.
     */
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

    /**
     * Draw the rectangles based on collection of the rectangles and result image.
     *
     * @param rectangles the collection of the {@link Rectangle} objects.
     * @param resultImage result image, which will being drawn.
     */
    private void drawRectangles(List<Rectangle> rectangles, BufferedImage resultImage) {
        Graphics2D graphics = resultImage.createGraphics();
        graphics.setColor(RED);

        BasicStroke stroke = new BasicStroke(rectangleLineWidth);
        graphics.setStroke(stroke);

        List<Rectangle> rectanglesForDraw;

        if (maximalRectangleCount > 0) {
            rectanglesForDraw = rectangles.stream()
                    .sorted(Comparator.comparing(Rectangle::size))
                    .skip(rectangles.size() - maximalRectangleCount)
                    .collect(Collectors.toList());
        } else {
            rectanglesForDraw = new ArrayList<>(rectangles);
        }

        rectanglesForDraw.forEach(rectangle -> graphics.drawRect(rectangle.getMinPoint().getY(),
                rectangle.getMinPoint().getX(),
                rectangle.getWidth(),
                rectangle.getHeight()));
    }

    /**
     * Group rectangle regions in matrix.
     */
    private void groupRegions() {
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
        if (isJumpRejected(row, col)) {
            return;
        }

        matrix[row][col] = regionCount;

        for (int i = 0; i < threshold; i++) {
            joinToRegion(row + 1 + i, col);
            joinToRegion(row, col + 1 + i);

            joinToRegion(row + 1 + i, col - 1 - i);
            joinToRegion(row - 1 - i, col + 1 + i);
            joinToRegion(row + 1 + i, col + 1 + i);
        }
    }

    /**
     * Returns the list of rectangles that would be drawn as a diff image.
     * If you submit two images that are the same barring the parts you want to excludedAreas you get a list of
     * rectangles that can be used as said excludedAreas
     *
     * @return List of {@link Rectangle}
     */
    public List<Rectangle> createMask() {
        return populateRectangles();
    }

    /**
     * Check next step valid or not.
     */
    private boolean isJumpRejected(int row, int col) {
        return row < 0 || row >= matrix.length || col < 0 || col >= matrix[row].length || matrix[row][col] != 1;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public Optional<File> getDestination() {
        return Optional.ofNullable(destination);
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }

    public BufferedImage getImage1() {
        return image1;
    }

    public BufferedImage getImage2() {
        return image2;
    }

    public int getRectangleLineWidth() {
        return rectangleLineWidth;
    }

    public void setRectangleLineWidth(int rectangleLineWidth) {
        this.rectangleLineWidth = rectangleLineWidth;
    }

    public Integer getMinimalRectangleSize() {
        return minimalRectangleSize;
    }

    public void setMinimalRectangleSize(Integer minimalRectangleSize) {
        this.minimalRectangleSize = minimalRectangleSize;
    }

    public Integer getMaximalRectangleCount() {
        return maximalRectangleCount;
    }

    public void setMaximalRectangleCount(Integer maximalRectangleCount) {
        this.maximalRectangleCount = maximalRectangleCount;
    }

    public void setExcludedAreas(List<Rectangle> excludedAreas) {
        this.excludedAreas = new ExcludedAreas(excludedAreas);
    }
}
