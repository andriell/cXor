package andriell.cxor;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BinFileTest {
    @Test
    public void test1() {
        int[] counter = new int[256];
        for (int i = 0; i < counter.length; i++) {
            counter[i] = 0;
        }
        for (int i = 0; i < 1048576; i++){
            byte b = BinFile.randomByte();
            counter[b + 128]++;
        }
        for (int value : counter) {
            assertTrue(value > 100);
        }
    }
}
