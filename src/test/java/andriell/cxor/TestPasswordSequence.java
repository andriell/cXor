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
                (byte) 0x09,
                (byte) 0x8f,
                (byte) 0x6b,
                (byte) 0xcd,
                (byte) 0x46,
                (byte) 0x21,
                (byte) 0xd3,
                (byte) 0x73,
                (byte) 0xca,
                (byte) 0xde,
                (byte) 0x4e,
                (byte) 0x83,
                (byte) 0x26,
                (byte) 0x27,
                (byte) 0xb4,
                (byte) 0xf6,

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
