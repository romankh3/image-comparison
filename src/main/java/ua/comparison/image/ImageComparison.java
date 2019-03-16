package ua.comparison.image;

import static java.awt.Color.RED;
import static ua.comparison.image.CommandLineUtil.create;
import static ua.comparison.image.CommandLineUtil.handleResult;
import static ua.comparison.image.ImageComparisonTools.checkCorrectImageSize;
import static ua.comparison.image.ImageComparisonTools.createGUI;
import static ua.comparison.image.ImageComparisonTools.createRectangle;
import static ua.comparison.image.ImageComparisonTools.deepCopy;
import static ua.comparison.image.ImageComparisonTools.populateTheMatrixOfTheDifferences;
import static ua.comparison.image.ImageComparisonTools.readImageFromResources;
import static ua.comparison.image.ImageComparisonTools.saveImage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import ua.comparison.image.model.Rectangle;

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

    /**
     * First image for comparing
     */
    private final BufferedImage image1;

    /**
     * Second image for comparing
     */
    private final BufferedImage image2;

    private final /* @Nullable */ File destination;

    private int[][] matrix;

    /**
     * Prefix of the name of the result image.
     */
    private static final String NAME_PREFIX = "image-comparison";

    /**
     * Suffix of the name of of the result image.
     */
    private static final String NAME_SUFFIX = ".png";

    ImageComparison(String image1, String image2) throws IOException, URISyntaxException {
        this(readImageFromResources(image1), readImageFromResources(image2), null);
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

    public static void main(String[] args) throws IOException, URISyntaxException {
        ImageComparison imgCmp = create(args);
        BufferedImage result = imgCmp.compareImages();
        handleResult(imgCmp, (file) -> saveImage(file, result), () -> createGUI(result));
    }

    /**
     * Draw rectangles which cover the regions of the difference pixels.
     *
     * @return the result of the drawing.
     */
    public BufferedImage compareImages() throws IOException {
        // check images for valid
        checkCorrectImageSize(image1, image2);

        matrix = populateTheMatrixOfTheDifferences(image1, image2);

        BufferedImage outImg = deepCopy(image2);

        Graphics2D graphics = outImg.createGraphics();
        graphics.setColor(RED);

        groupRegions();

        List<Rectangle> rectangles = new ArrayList<>();
        while (counter <= regionCount) {
            rectangles.add(createRectangle(matrix, counter));
            counter++;
        }


        avoidOverlapping(rectangles).forEach(rectangle -> graphics.drawRect(rectangle.getMinY(),
                                                                            rectangle.getMinX(),
                                                                            rectangle.getWidth(),
                                                                            rectangle.getHeight()));

        //save the image:
        saveImage(this.getDestination().orElse(Files.createTempFile(NAME_PREFIX, NAME_SUFFIX).toFile()), outImg);
        return outImg;
    }

    //todo implement logic for overlapping.
    private List<Rectangle> avoidOverlapping(List<Rectangle> rectangles) {
        rectangles.sort(Comparator.comparing(it -> it.getTopLeft().calculateModule()));
        //todo removed this hotfix and investigate it in #32 issue.
        return rectangles.stream().filter(it -> !it.equals(new Rectangle())).collect(Collectors.toList());
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
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[row].length || matrix[row][col] != 1) {
            return;
        }

        matrix[row][col] = regionCount;

        //todo refactor it to make it faster.
        for (int i = 0; i < threshold; i++) {
            joinToRegion(row + 1 + i, col);
            joinToRegion(row, col + 1 + i);

            joinToRegion(row + 1 + i, col - 1 - i);
            joinToRegion(row - 1 - i, col + 1 + i);
            joinToRegion(row + 1 + i, col + 1 + i);
        }
    }

    Optional<File> getDestination() {
        return Optional.ofNullable(destination);
    }

    public BufferedImage getImage1() {
        return image1;
    }

    public BufferedImage getImage2() {
        return image2;
    }
}
