package com.github.romankh3.image.comparison;

import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class LogTest {
    @Test
    public void logTest1(){
        //load images to be compared:
        try{
            BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expcted.png");
            fail();
        }
        catch (Exception e){
            assertEquals(e.getMessage(), "Image with path = expcted.png not found");
        }
    }

    @Test
    public void logTest2(){
        //load images to be compared:
        try{
            BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expected.png");
            BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("actul.jpg");
            ImageComparisonResult imageComparisonResult1 = new ImageComparison(expectedImage, actualImage).compareImages();
            fail();
        }
        catch (Exception e){
            assertEquals(e.getMessage(), "Image with path = actul.jpg not found");
        }
    }

}
