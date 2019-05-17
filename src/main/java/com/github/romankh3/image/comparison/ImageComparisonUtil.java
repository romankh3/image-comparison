package com.github.romankh3.image.comparison;

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

/**
 * Tools for the {@link ImageComparison} object.
 */
public class ImageComparisonUtil {

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
        ImageIO.write(image, "png", path);
    }
}
