package ua.comparison.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageResultFrame extends JFrame {

    public static void createGUI( BufferedImage image ) {
        JFrame frame = new JFrame("The result of the comparison");
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        JLabel label = new JLabel();
        label.setBackground(Color.BLUE);
        label.setIcon(new ImageIcon(image, "Result"));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension( (image.getWidth() ), image.getHeight() ) );
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}