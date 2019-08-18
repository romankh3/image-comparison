package com.github.romankh3.image.comparison;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
     * @throws IOException due to read the image from resources.
     */
    public static BufferedImage readImageFromResources(String path) throws IOException {
        InputStream inputStream = ImageComparisonUtil.class.getClassLoader().getResourceAsStream(path);
        if (inputStream != null) {
            return ImageIO.read(inputStream);
        } else {
            throw new IOException("Image " + path + " not found");
        }
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

        float softenFactor = 0.05f;
        final Image temp = new ImageIcon(img).getImage();
        final BufferedImage bufferedImage = new BufferedImage(
                temp.getWidth(null),
                temp.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
        final Graphics g = bufferedImage.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        final float[] softenArray = {0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
        final Kernel kernel = new Kernel(3, 3, softenArray);
        final ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        final BufferedImage filteredBufferedImage = cOp.filter(bufferedImage, null);

        return filteredBufferedImage;
    }

    /**
     * Return difference percent between two buffered images.
     * @param img1 the first image.
     * @param img2 the second image.
     * @return difference percent.
     */
    public static float getDifferencePercent(BufferedImage img1, BufferedImage img2) {
        int width = img1.getWidth();
        int height = img1.getHeight();

        long diff = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
            }
        }
        long maxDiff = 3L * 255 * width * height;

        return (float) (100.0 * diff / maxDiff);
    }

    /**
     * Compare to pixels
     * @param rgb1 the first rgb
     * @param rgb2 the second rgn
     * @return the difference.
     */
    public static int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >>  8) & 0xff;
        int b1 =  rgb1        & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >>  8) & 0xff;
        int b2 =  rgb2        & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }
}
