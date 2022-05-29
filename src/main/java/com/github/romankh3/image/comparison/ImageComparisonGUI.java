package com.github.romankh3.image.comparison;

import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageComparisonGUI {
    public static void main(String[] args) {
        // [frame]
        JFrame f = new JFrame("Image Comparison");
        f.setSize(900,600);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // [Configuration]
        // 1.threshold
        JLabel thresholdLabel = new JLabel("threshold");
        thresholdLabel.setFont(new Font("Calibri",Font.BOLD,15));
        thresholdLabel.setBounds(50,500,100,30);
        f.add(thresholdLabel);
        JTextField thresholdText = new JTextField("5");
        thresholdText.setFont(new Font("Calibri",Font.BOLD,15));
        thresholdText.setBounds(150,500,100,30);
        f.add(thresholdText);
        // 2.rectangleLineWidth
        JLabel rectangleLineWidthLabel = new JLabel("rectangleLineWidth");
        rectangleLineWidthLabel.setFont(new Font("Calibri",Font.BOLD,15));
        rectangleLineWidthLabel.setBounds(300,500,150,30);
        f.add(rectangleLineWidthLabel);
        JTextField rectangleLineWidthText = new JTextField("1");
        rectangleLineWidthText.setFont(new Font("Calibri",Font.BOLD,15));
        rectangleLineWidthText.setBounds(450,500,100,30);
        f.add(rectangleLineWidthText);
        // 3.pixelToleranceLevel
        JLabel pixelToleranceLevelLabel = new JLabel("pixelToleranceLevel");
        pixelToleranceLevelLabel.setFont(new Font("Calibri",Font.BOLD,15));
        pixelToleranceLevelLabel.setBounds(600,500,150,30);
        f.add(pixelToleranceLevelLabel);
        JTextField pixelToleranceLevelText = new JTextField("0.1");
        pixelToleranceLevelText.setFont(new Font("Calibri",Font.BOLD,15));
        pixelToleranceLevelText.setBounds(750,500,100,30);
        f.add(pixelToleranceLevelText);
        // --- select expected image
        // image 1
        JLabel expectedLabel = new JLabel();
        JPanel expectedPanel = new JPanel();
        expectedPanel.setBounds(0,0,300,400);
        expectedPanel.add(expectedLabel);
        f.add(expectedPanel);
        // text 1
        JTextField expectedPath = new JTextField("empty");
        expectedPath.setFont(new Font("Calibri",Font.BOLD,15));
        expectedPath.setBounds(50,430,200,30);
        expectedPath.setEditable(false);
        f.add(expectedPath);
        // button 1
        JButton selectExpectedButton = new JButton("select expected image");
        selectExpectedButton.setFont(new Font("Calibri",Font.BOLD,15));
        selectExpectedButton.setBounds(50,400,200,30);
        selectExpectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int i = fc.showOpenDialog(f);
                if(i == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    String path = file.getPath();
                    expectedLabel.setIcon(new ImageIcon(path));
                    expectedPath.setText(path);
                }
            }
        });
        f.add(selectExpectedButton);
        // --- select actual image
        // image 2
        JLabel actualLabel = new JLabel();
        JPanel actualPanel = new JPanel();
        actualPanel.setBounds(300,0,300,400);
        actualPanel.add(actualLabel);
        f.add(actualPanel);
        // text 2
        JTextField actualPath = new JTextField("empty");
        actualPath.setFont(new Font("Calibri",Font.BOLD,15));
        actualPath.setBounds(350,430,200,30);
        f.add(actualPath);
        actualPath.setEditable(false);
        // button 2
        JButton selectActualButton = new JButton("select actual image");
        selectActualButton.setFont(new Font("Calibri",Font.BOLD,15));
        selectActualButton.setBounds(350,400,200,30);
        selectActualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int i = fc.showOpenDialog(f);
                if(i == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    String path = file.getPath();
                    actualLabel.setIcon(new ImageIcon(path));
                    actualPath.setText(path);
                }
            }
        });
        f.add(selectActualButton);
        // --- show result image
        // image 3
        JLabel resultLabel = new JLabel();
        JPanel resultPanel = new JPanel();
        resultPanel.setBounds(600,0,300,400);
        resultPanel.add(resultLabel);
        f.add(resultPanel);
        // text 3
        JTextField resultPath = new JTextField("empty");
        resultPath.setFont(new Font("Calibri",Font.BOLD,15));
        resultPath.setBounds(650,430,200,30);
        resultPath.setEditable(false);
        f.add(resultPath);
        // info 3
        JButton resultImageLabel = new JButton("Result Image");
        resultImageLabel.setFont(new Font("Calibri",Font.BOLD,15));
        resultImageLabel.setBounds(650,400,200,30);
        f.add(resultImageLabel);
        // result judge label-text
        JLabel resultJudgeLabel = new JLabel("Comparison Result");
        resultJudgeLabel.setFont(new Font("Calibri",Font.BOLD,15));
        resultJudgeLabel.setBounds(275,465,200,30);
        f.add(resultJudgeLabel);
        JTextField resultJudgeText = new JTextField();
        resultJudgeText.setFont(new Font("Calibri",Font.BOLD,15));
        resultJudgeText.setBounds(400,465,150,30);
        resultJudgeText.setEditable(false);
        f.add(resultJudgeText);
        // result image path
        JLabel resultPathLabel = new JLabel("Result Path");
        resultPathLabel.setFont(new Font("Calibri",Font.BOLD,15));
        resultPathLabel.setBounds(600,465,150,30);
        f.add(resultPathLabel);
        JTextField resultImagePath = new JTextField("result.png");
        resultImagePath.setFont(new Font("Calibri",Font.BOLD,15));
        resultImagePath.setBounds(700,465,150,30);
        f.add(resultImagePath);
        // [Execute Comparison]
        JButton execute = new JButton("Execute Comparison");
        execute.setFont(new Font("Calibri",Font.BOLD,15));
        execute.setForeground(Color.magenta);
        execute.setBounds(50,465,200,30);
        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // load images to be compared
                BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedPath.getText());
                BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualPath.getText());
                // where to save the result (leave null if you want to see the result in the UI)
                File resultDestination = new File(resultImagePath.getText());
                // Create ImageComparison object with result destination and compare the images.
                ImageComparison ic = new ImageComparison(expectedImage, actualImage, resultDestination);
                String path = resultDestination.getPath();
                resultLabel.setIcon(new ImageIcon(path));
                resultPath.setText(path);
                // configuration
                ic.setThreshold(Integer.parseInt(thresholdText.getText()));
                ic.setRectangleLineWidth(Integer.parseInt(rectangleLineWidthText.getText()));
                ic.setPixelToleranceLevel(Double.parseDouble(pixelToleranceLevelText.getText()));
                // get the comparison result
                ImageComparisonResult imageComparisonResult = ic.compareImages();
                if( imageComparisonResult.getImageComparisonState() == ImageComparisonState.MATCH ) {
                    resultJudgeText.setForeground(Color.green);
                    resultJudgeText.setText("MATCH");
                }
                else if ( imageComparisonResult.getImageComparisonState() == ImageComparisonState.MISMATCH ) {
                    resultJudgeText.setForeground(Color.red);
                    resultJudgeText.setText("MISMATCH");
                }
                else if ( imageComparisonResult.getImageComparisonState() == ImageComparisonState.SIZE_MISMATCH ) {
                    resultJudgeText.setForeground(Color.yellow);
                    resultJudgeText.setText("SIZE_MISMATCH");
                }
            }
        });
        f.add(execute);
        // [start]
        f.setVisible(true);
    }
}
