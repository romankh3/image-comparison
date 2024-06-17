package com.github.romankh3.image.comparison;

import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Load tests for {@link simpleComparison} method")
public class LoadTest {
    @Test
    @DisplayName("should result MisMatch for two images")
    public void simpleComparisonTest(){
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expected.png");
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("actual.png");

        //Create ImageComparison object and compare the images.
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).simpleComparison();

        //Check the result
        assertEquals(ImageComparisonState.MISMATCH, imageComparisonResult.getImageComparisonState());
    }


    @Test
    @DisplayName("compare load differences between {@link simpleComparison} method and {@link compareImage} method")
    public void loadTest1(){
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expected.png");
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("actual.png");

        long time1 = System.currentTimeMillis();
        //Create ImageComparison object and compare the images.
        ImageComparisonResult imageComparisonResult1 = new ImageComparison(expectedImage, actualImage).compareImages();
        long etime1 = System.currentTimeMillis();
        //Time for compareImage method
        long compareImageTime = etime1 - time1;

        //Check the result
        assertEquals(ImageComparisonState.MISMATCH, imageComparisonResult1.getImageComparisonState());

        long time2 = System.currentTimeMillis();
        //Create ImageComparison2 object and compare the images.
        ImageComparisonResult imageComparisonResult2 = new ImageComparison(expectedImage, actualImage).simpleComparison();
        long etime2 = System.currentTimeMillis();
        //Time for simpleComparison method
        long simpleComparisonTime = etime2 - time2;

        //Check the result
        assertEquals(ImageComparisonState.MISMATCH, imageComparisonResult2.getImageComparisonState());
        //check the load differences between simpleComparison and compareImage pattern
        assertTrue(simpleComparisonTime < compareImageTime);
    }

    @Test
    @DisplayName("compare load differences between {@link simpleComparison} method and {@link compareImage} method")
    public void loadTest2(){
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expected#201.png");
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("actual#201.png");

        long time1 = System.currentTimeMillis();
        //Create ImageComparison object and compare the images.
        ImageComparisonResult imageComparisonResult1 = new ImageComparison(expectedImage, actualImage).compareImages();
        long etime1 = System.currentTimeMillis();
        //Time for compareImage method
        long compareImageTime = etime1 - time1;

        //Check the result
        assertEquals(ImageComparisonState.MISMATCH, imageComparisonResult1.getImageComparisonState());

        long time2 = System.currentTimeMillis();
        //Create ImageComparison2 object and compare the images.
        ImageComparisonResult imageComparisonResult2 = new ImageComparison(expectedImage, actualImage).simpleComparison();
        long etime2 = System.currentTimeMillis();
        //Time for simpleComparison method
        long simpleComparisonTime = etime2 - time2;

        //Check the result
        assertEquals(ImageComparisonState.MISMATCH, imageComparisonResult2.getImageComparisonState());
        //check the load differences between simpleComparison and compareImage pattern
        assertTrue(simpleComparisonTime < compareImageTime);
    }

    @Test
    @DisplayName("compare load differences between {@link simpleComparison} method and {@link compareImage} method")
    public void loadTest3(){
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expected#98.png");
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("actual#98.png");

        long time1 = System.currentTimeMillis();
        //Create ImageComparison object and compare the images.
        ImageComparisonResult imageComparisonResult1 = new ImageComparison(expectedImage, actualImage).compareImages();
        long etime1 = System.currentTimeMillis();
        //Time for compareImage method
        long compareImageTime = etime1 - time1;

        //Check the result
        assertEquals(ImageComparisonState.MISMATCH, imageComparisonResult1.getImageComparisonState());

        long time2 = System.currentTimeMillis();
        //Create ImageComparison2 object and compare the images.
        ImageComparisonResult imageComparisonResult2 = new ImageComparison(expectedImage, actualImage).simpleComparison();
        long etime2 = System.currentTimeMillis();
        //Time for simpleComparison method
        long simpleComparisonTime = etime2 - time2;

        //Check the result
        assertEquals(ImageComparisonState.MISMATCH, imageComparisonResult2.getImageComparisonState());
        //check the load differences between simpleComparison and compareImage pattern
        assertTrue(simpleComparisonTime < compareImageTime);
    }

}
