package com.github.romankh3.image.comparison;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNotNull;

public class MainTest {
    @Test
    public void testMainClass() throws IOException, URISyntaxException {
        //given
        String[] args = new String[2];
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        args[0] = image1.getAbsolutePath();
        args[1] = image2.getAbsolutePath();

        //when
        Main.main(args);

        //then
        assertNotNull(image1);
        assertNotNull(image2);
    }
}
