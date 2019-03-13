package ua.comparison.image;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import ua.comparison.image.model.Rectangle;

/**
 * Tools for the {@link ImageComparison} object.
 */
public class ImageComparisonTools {

    /**
     * Create GUI for represents the resulting image.
     *
     * @param image resulting image.
     */
    public static Frame createGUI(BufferedImage image) {
        JFrame frame = new JFrame("The result of the comparison");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image, "Result"));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(image.getWidth(), (int) (image.getHeight())));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    /**
     * Make a copy of the {@link BufferedImage} object.
     *
     * @param image the provided image.
     * @return copy of the provided image.
     */
    static BufferedImage deepCopy(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * Check images for equals their widths and heights.
     *
     * @param image1 {@link BufferedImage} object of the first image.
     * @param image2 {@link BufferedImage} object of the second image.
     */
    public static void checkCorrectImageSize(BufferedImage image1, BufferedImage image2) {
        if (image1.getHeight() != image2.getHeight() || image1.getWidth() != image2.getWidth()) {
            throw new IllegalArgumentException("Images dimensions mismatch");
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
    public static boolean isDifferent(int rgb1, int rgb2) {
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
     * Create a {@link Rectangle} object.
     *
     * @param matrix the matrix of the Conformity pixels.
     * @param counter the number from marks regions.
     * @return the {@link Rectangle} object.
     */
    public static Rectangle createRectangle(int[][] matrix, int counter) {
        Rectangle rect = new Rectangle();

        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == counter) {
                    if (x < rect.getMinX()) {
                        rect.setMinX(x);
                    }
                    if (x > rect.getMaxX()) {
                        rect.setMaxX(x);
                    }

                    if (y < rect.getMinY()) {
                        rect.setMinY(y);
                    }
                    if (y > rect.getMaxY()) {
                        rect.setMaxY(y);
                    }
                }
            }
        }
        return rect;
    }

    /**
     * Populate binary matrix by "0" and "1". If the pixels are difference set it as "1", otherwise "0".
     *
     * @param image1 {@link BufferedImage} object of the first image.
     * @param image2 {@link BufferedImage} object of the second image.
     * @return populated binary matrix.
     */
    static int[][] populateTheMatrixOfTheDifferences(BufferedImage image1, BufferedImage image2) {
        int[][] matrix = new int[image1.getWidth()][image1.getHeight()];
        for (int y = 0; y < image1.getHeight(); y++) {
            for (int x = 0; x < image1.getWidth(); x++) {
                matrix[x][y] = isDifferent(image1.getRGB(x, y), image2.getRGB(x, y)) ? 1 : 0;
            }
        }
        return matrix;
    }

    /**
     * Read image from the provided path.
     *
     * @param path the path where contains image.
     * @return the {@link BufferedImage} object of this specific image.
     */
    public static BufferedImage readImageFromResources(String path) throws IOException, URISyntaxException {
        return ImageIO.read(new File(ImageComparison.class.getClassLoader().getResource(path).toURI().getPath()));
    }

    /**
     * Read image from the provided file path.
     *
     * @param path the path where contains image.
     * @return the {@link BufferedImage} object of this specific image.
     */
    public static BufferedImage readImageFromFile(File path) throws IOException {
        return ImageIO.read(path);
    }

    /**
     * Save image to the provided path.
     *
     * @param path the path to the saving image.
     * @param image the {@link BufferedImage} object of this specific image.
     */
    public static void saveImage(File path, BufferedImage image) throws IOException {
        File dir = path.getParentFile();
        // make dir if it's not using from Gradle.
        boolean dirExists = dir == null || dir.isDirectory() || dir.mkdirs();
        if (!dirExists) {
            throw new RuntimeException("Unable to create directory " + dir);
        }
        ImageIO.write(image, "png", path);
    }
}
