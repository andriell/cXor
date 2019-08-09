package andriell.cxor;

import andriell.cxor.crypto.Gzip;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGzip {

    @Test
    public void test1() throws IOException {
        byte[] bytes0 = new byte[1024111];
        for (int i = 0; i < bytes0.length; i++) {
            bytes0[i] = (byte) (i & 0xFF);
        }
        byte[] bytes1 = Gzip.compress(bytes0);
        assertTrue(bytes1.length < bytes0.length / 10);
        byte[] bytes2 = Gzip.decompress(bytes1, 1024 * 1024);
        assertEquals(bytes0.length, bytes2.length);
        for (int i = 0; i < bytes0.length; i++) {
            assertEquals(bytes2[i], bytes0[i]);
        }
    }

    public void test2() throws IOException {
        byte[] chars1 = new byte[40 * 1024];
        byte[] chars2 = new byte[chars1.length];
        for (int i = 0; i < chars1.length; i++) {
            chars1[i] = (byte) (i & 0xFF);
            if (i > chars1.length / 2) {
                chars2[i] = (byte) ((i + 10) & 0xFF);
            } else {
                chars2[i] = (byte) (i & 0xFF);
            }
        }

        byte[] bytes1 = Gzip.compress(chars1);
        System.out.println(bytes1.length);
        byte[] bytes2 = Gzip.compress(chars2);
        System.out.println(bytes2.length);

        int minL = Math.min(bytes1.length, bytes2.length);
        int trueCount = 0;
        for (int i = 0; i < minL; i++) {
            if (bytes1[i] == bytes2[i]) {
                String sb = String.format("%8s", Integer.toBinaryString(bytes1[i] & 0xFF)).replace(' ', '0');
                System.out.println(sb + " " + i);
                trueCount++;
            }
        }
        System.out.println(trueCount);
    }
}
