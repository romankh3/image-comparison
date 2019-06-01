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
        File expected = new File(ImageComparison.class.getClassLoader().getResource("expected.png").toURI().getPath());
        File actual = new File(ImageComparison.class.getClassLoader().getResource("actual.png").toURI().getPath());
        args[0] = expected.getAbsolutePath();
        args[1] = actual.getAbsolutePath();

        //when
        Main.main(args);
        //then
        assertNotNull(main);
        assertNotNull(expected);
        assertNotNull(actual);
    }
}
