package andriell.cxor;

import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rybalko on 30.08.2016.
 */
public class TestPasswordSequence {

    @Test
    public void test1() throws IOException, NoSuchAlgorithmException {
        PasswordSequence sequence = new PasswordSequence("test");
        byte[] bytes = {
                (byte) 0xb6,
                (byte) 0xf1,
                (byte) 0x20,
                (byte) 0x8d,
                (byte) 0xe7,
                (byte) 0xa0,
                (byte) 0x6b,
                (byte) 0x65,
                (byte) 0x0e,
                (byte) 0x0e,
                (byte) 0x50,
                (byte) 0x2d,
                (byte) 0xec,
                (byte) 0x1a,
                (byte) 0x04,
                (byte) 0x21,

                (byte) 0xda,
                (byte) 0xac,
                (byte) 0xda,
                (byte) 0x74,
                (byte) 0xbc,
                (byte) 0xba,
                (byte) 0xe9,
                (byte) 0xf6,
                (byte) 0x3a,
                (byte) 0x9c,
                (byte) 0x29,
                (byte) 0xa8,
                (byte) 0xc9,
                (byte) 0xff,
                (byte) 0x3f,
                (byte) 0x9a
        };
        for (byte b: bytes) {
            assertEquals(sequence.read(), b);
        }

    }
}
