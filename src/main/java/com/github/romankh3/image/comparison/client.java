package com.github.romankh3.image.comparison;

import com.github.romankh3.image.comparison.model.*;
import org.apache.commons.cli.*;

import java.awt.image.BufferedImage;
import java.io.File;

public class client {
    static String helpMessage = "Usage: java -jar image-comparison-4.4.0.jar [-e] <image_path1> [-a] <image_path2> [-o] <image_path3>\n"+
            "Options:\n" +
            "\n" +
            "  -h                         Offer this help\n" +
            "  -e                         Path of the expected image\n" +
            "  -a                         Path of the expected image\n" +
            "  -o                         Path of the result of image comparison\n";
    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("h", false, "Offer this help");
        options.addOption("e", true, "Path of the expected image");
        options.addOption("a", true, "Path of the expected image");
        options.addOption("o", true, "Path of the result of image comparison");
        try {
            CommandLineParser parser = new BasicParser();
            CommandLine cli = parser.parse(options, args);
            HelpFormatter hf = new HelpFormatter();
            if(cli.hasOption("h")){
                hf.printHelp("Usage: java -jar image-comparison-4.4.0.jar [-e] <image_path1> [-a] <image_path2> [-o] <image_path3>\n" + "Options:\n", options);
            }else {
                if(cli.hasOption("e") && cli.hasOption("a") ){
                    String expected = cli.getOptionValue("e");
                    String actual = cli.getOptionValue("a");
                    if(cli.hasOption('o')){
                        String result = cli.getOptionValue("o");
                        ImageComparisonResult imageComparisonResult = wholeImageComparison(expected, actual, result);
                    }else {
                        ImageComparisonResult imageComparisonResult = defaultImageComparison(expected, actual);
                    }
                }else {
                    System.out.println("Invalid or Wrong Arguments, Please try -h to get help");
                }
            }
        }
        catch (Exception e){
            System.out.println("Invalid or Wrong Arguments, Please try -h to get help");
        }
    }

    /**
     * Compare images without output
     *
     * @param expected Path of the expected image
     * @param actual Path of the actual image
     * @return the result of image comparison
     */
    public static ImageComparisonResult defaultImageComparison(String expected, String actual){
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expected);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actual);

        //Create ImageComparison object and compare the images.
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();

        //Check the result
        System.out.println(imageComparisonResult.getImageComparisonState().toString());

        return imageComparisonResult;
    }

    /**
     * Compare images without output
     *
     * @param expected Path of the expected image
     * @param actual Path of the actual image
     * @param result Path of the result of image comparison
     * @return the result of image comparison
     */
    public static ImageComparisonResult wholeImageComparison(String expected, String actual, String result){
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expected);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actual);

        // where to save the result (leave null if you want to see the result in the UI)
        File resultDestination = new File(result);

        //Create ImageComparison object with result destination and compare the images.
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage, resultDestination).compareImages();

        return imageComparisonResult;
    }
}
