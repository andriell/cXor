package andriell.cxor;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestHiddenString {
    @Test
    public void test1() {
        String s = "\"\"\"\"\"\"text\"\"\"\"\"\": \"\"\"\"\"pass\"\"word\"\"\"\"\" текст text\\html\nПользователь \"па\nро\"\"ль\"";
        String sh = "\"\"\"text\"\"\": xxxxxxxxxxxxx текст text\\html\nПользователь xx\nxxxxx";
        String sc = "\"\"\"text\"\"\": \"\"pass\"word\"\" текст text\\html\nПользователь па\nро\"ль";

        assertEquals(sh.length(), sc.length());

        HiddenString hiddenString = new HiddenString(s);

        assertEquals(hiddenString.getString(), s);
        assertEquals(hiddenString.getStringHidden(), sh);

        assertEquals("", hiddenString.copy(-1, -1));
        assertEquals("", hiddenString.copy(-1, 0));
        assertEquals("", hiddenString.copy(0, -1));
        assertEquals("", hiddenString.copy(0, 0));
        assertEquals("", hiddenString.copy(-1, -1));
        assertEquals("", hiddenString.copy(-1, 1));
        assertEquals("", hiddenString.copy(1, -1));

        for (int i = 0; i < 10; i++) {
            assertEquals("", hiddenString.copy(sc.length() + i, 1));
        }

        for (int i = 0; i < 10; i++) {
            assertEquals(sc, hiddenString.copy(0, sc.length() + i));
        }

        for (int i = 0; i < sh.length(); i++) {
            for (int l = 0; l < sh.length() - i + 1; l++) {
                //System.out.println(i + " " + l + " " + hiddenString.copy(i, l));
                assertEquals(hiddenString.copy(i, l), sc.substring(i, i + l));
            }
        }
    }

    public void test2() {
        byte b = 0;
        do {
            String s2 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            System.out.println(s2 + " " + b);
            b++;
        } while (b != 0);
    }

    public void test3() {

        String s = "А";
        byte[] bytes = s.getBytes();
        for (byte b : bytes) {
            String s2 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            System.out.println(s2 + " " + b);
        }

        char[] chars = s.toCharArray();
        for (char c : chars) {
            String s2 = String.format("%16s", Integer.toBinaryString(c & 0xFFFF)).replace(' ', '0');
            System.out.println(s2 + " " + c);
        }
    }
}
