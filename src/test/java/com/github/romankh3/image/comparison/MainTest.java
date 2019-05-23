package com.github.romankh3.image.comparison;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.Test;

/**
 * Unit-level testing for {@link Main}.
 */
public class MainTest {

    @Test
    public void testMainClass() throws IOException, URISyntaxException {
        //given
        Main main = new Main();
        String[] args = new String[2];
        File image1 = new File(ImageComparison.class.getClassLoader().getResource("image1.png").toURI().getPath());
        File image2 = new File(ImageComparison.class.getClassLoader().getResource("image2.png").toURI().getPath());
        args[0] = image1.getAbsolutePath();
        args[1] = image2.getAbsolutePath();

        //when
        Main.main(args);
        //then
        assertNotNull(main);
        assertNotNull(image1);
        assertNotNull(image2);
    }
}
