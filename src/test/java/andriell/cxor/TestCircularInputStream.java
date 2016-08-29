package andriell.cxor;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rybalko on 29.08.2016.
 */
public class TestCircularInputStream {
    @Test
    public void test1() throws IOException {
        File file = new File("src/test/resources/testData.txt");
        assertEquals(file.isFile(), true);
        CircularInputStream stream = new CircularInputStream(file);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);

        stream.skip(2);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);

        stream.skip(2);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);

        stream.skip(2);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);

        stream.skip(1);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);

        stream.skip(1);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);

        stream.skip(1);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
        assertEquals(stream.read(), 49);
        assertEquals(stream.read(), 50);
        assertEquals(stream.read(), 51);
    }
}
