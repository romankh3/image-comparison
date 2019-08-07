package com.github.romankh3.image.comparison;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
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
     *
     * @return {@link Frame} for running GUI.
     */
    public static Frame createGUI(BufferedImage image) {
        JFrame frame = new JFrame("The result of the comparison");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image, "Result"));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
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
     * Read image from the provided path.
     *
     * @param path the path where contains image.
     * @return the {@link BufferedImage} object of this specific image.
     *
     * @throws IOException due to the saving result image
     * @throws URISyntaxException due to the creating file based on simple string.
     */
    public static BufferedImage readImageFromResources(String path) throws IOException, URISyntaxException {
        return ImageIO.read(new File(ImageComparisonUtil.class.getClassLoader().getResource(path).toURI().getPath()));
    }

    /**
     * Read image from the provided file path.
     *
     * @param path the path where contains image.
     * @return the {@link BufferedImage} object of this specific image.
     *
     * @throws IOException due to read the image from FS.
     */
    public static BufferedImage readImageFromFile(File path) throws IOException {
        return ImageIO.read(path);
    }

    /**
     * Save image to the provided path.
     *
     * @param pathFile the path to the saving image.
     * @param image the {@link BufferedImage} object of this specific image.
     * @throws IOException due to save image.
     */
    public static void saveImage(File pathFile, BufferedImage image) throws IOException {
        File dir = pathFile.getParentFile();
        // make dir if it's not using from Gradle.
        boolean dirExists = dir == null || dir.isDirectory() || dir.mkdirs();
        if (!dirExists) {
            throw new FileNotFoundException("Unable to create directory " + dir);
        }
        ImageIO.write(image, "png", pathFile);
    }

    /**
     * Resize image to new dimensions and return new image
     *
     * @param img the object of the image to be resized.
     * @param newW the new width.
     * @param newH the new height.
     */

    public static BufferedImage resize(BufferedImage img, int newW, int newH)  {
        Image imgtmp = img;


        BufferedImage newImage = toBufferedImage( imgtmp.getScaledInstance(newW, newH, Image.SCALE_SMOOTH));
        return newImage;
    }

    /**
     * convert image to Buffered Image.
     *
     * @param img the object of Image to be converted.
     */

    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }
}
