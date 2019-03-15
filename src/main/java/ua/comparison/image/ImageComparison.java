package ua.comparison.image;

import static java.awt.Color.RED;
import static ua.comparison.image.ImageComparisonTools.checkCorrectImageSize;
import static ua.comparison.image.ImageComparisonTools.createGUI;
import static ua.comparison.image.ImageComparisonTools.createRectangle;
import static ua.comparison.image.ImageComparisonTools.deepCopy;
import static ua.comparison.image.ImageComparisonTools.populateTheMatrixOfTheDifferences;
import static ua.comparison.image.ImageComparisonTools.readImageFromFile;
import static ua.comparison.image.ImageComparisonTools.readImageFromResources;
import static ua.comparison.image.ImageComparisonTools.saveImage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Optional;
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

    private final BufferedImage image1;
    private final BufferedImage image2;
    private final /* @Nullable */ File destination;
    private int[][] matrix;

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

    public BufferedImage getImage1() {
        return image1;
    }

    public BufferedImage getImage2() {
        return image2;
    }

    Optional<File> getDestination() {
        return Optional.ofNullable(destination);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        ImageComparison imgCmp = create(args);
        BufferedImage result = imgCmp.compareImages();
        handleResult(imgCmp, (file) -> saveImage(file, result), () -> createGUI(result));
    }

    static ImageComparison create(String... args) throws IOException, URISyntaxException {
        Optional<ArgsParser.Arguments> arguments = new ArgsParser().parseArgs(args);
        return arguments.isPresent() ? create(arguments.get()) : createDefault();
    }

    static ImageComparison createDefault() throws IOException, URISyntaxException {
        return new ImageComparison(
                readImageFromResources("image1.png"),
                readImageFromResources("image2.png"),
                null);
    }

    static ImageComparison create(ArgsParser.Arguments args) throws IOException {
        return new ImageComparison(
                readImageFromFile(args.getImage1()),
                readImageFromFile(args.getImage2()),
                args.getDestinationImage().orElse(null));
    }

    static void handleResult(ImageComparison instance, IOConsumer<File> saveToFile, Runnable showUI)
            throws IOException {
        if (instance.getDestination().isPresent()) {
            saveToFile.accept(instance.getDestination().get());
        } else {
            showUI.run();
        }
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
        drawRectangles(graphics);

        //save the image:
        saveImage(this.getDestination().orElse(Files.createTempFile("image-comparison", ".png").toFile()), outImg);
        return outImg;
    }

    /**
     * Draw rectangles with the differences pixels.
     *
     * @param graphics the Graphics2D object for drawing rectangles.
     */
    private void drawRectangles(Graphics2D graphics) {
        if (counter > regionCount) {
            return;
        }

        Rectangle rectangle = createRectangle(matrix, counter);

        graphics.drawRect(rectangle.getMinY(), rectangle.getMinX(), rectangle.getWidth(), rectangle.getHeight());
        counter++;
        drawRectangles(graphics);
    }

    /**
     * Group rectangle regions in binary matrix.
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

        for (int i = 0; i < threshold; i++) {
            joinToRegion(row + 1 + i, col);
            joinToRegion(row, col + 1 + i);

            joinToRegion(row + 1 + i, col - 1 - i);
            joinToRegion(row - 1 - i, col + 1 + i);
            joinToRegion(row + 1 + i, col + 1 + i);
        }
    }
}
