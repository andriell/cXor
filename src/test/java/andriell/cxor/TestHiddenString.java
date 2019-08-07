package andriell.cxor;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestHiddenString {
    @Test
    public void test1() throws IOException {
        String s = "1 \\< 2 && 3 \\> 2 text: <pass\\>word> текст text\\\\html";
        String sh = "1 \\< 2 && 3 \\> 2 text: ************ текст text\\\\html";

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
        String[] strings = {
                "",
                "1",
                "1 ",
                "1 ",
                "1 <",
                "1 < ",
                "1 < 2",
                "1 < 2 ",
                "1 < 2 &",
                "1 < 2 &&",
                "1 < 2 && ",
                "1 < 2 && 3",
                "1 < 2 && 3 ",
                "1 < 2 && 3 ",
                "1 < 2 && 3 >",
                "1 < 2 && 3 > ",
                "1 < 2 && 3 > 2",
                "1 < 2 && 3 > 2 ",
                "1 < 2 && 3 > 2 t",
                "1 < 2 && 3 > 2 te",
                "1 < 2 && 3 > 2 tex",
                "1 < 2 && 3 > 2 text",
                "1 < 2 && 3 > 2 text:",
                "1 < 2 && 3 > 2 text: ",
                "1 < 2 && 3 > 2 text: ",
                "1 < 2 && 3 > 2 text: p",
                "1 < 2 && 3 > 2 text: pa",
                "1 < 2 && 3 > 2 text: pas",
                "1 < 2 && 3 > 2 text: pass",
                "1 < 2 && 3 > 2 text: pass",
                "1 < 2 && 3 > 2 text: pass>",
                "1 < 2 && 3 > 2 text: pass>w",
                "1 < 2 && 3 > 2 text: pass>wo",
                "1 < 2 && 3 > 2 text: pass>wor",
                "1 < 2 && 3 > 2 text: pass>word",
                "1 < 2 && 3 > 2 text: pass>word",
                "1 < 2 && 3 > 2 text: pass>word ",
                "1 < 2 && 3 > 2 text: pass>word т",
                "1 < 2 && 3 > 2 text: pass>word те",
                "1 < 2 && 3 > 2 text: pass>word тек",
                "1 < 2 && 3 > 2 text: pass>word текс",
                "1 < 2 && 3 > 2 text: pass>word текст",
                "1 < 2 && 3 > 2 text: pass>word текст ",
                "1 < 2 && 3 > 2 text: pass>word текст t",
                "1 < 2 && 3 > 2 text: pass>word текст te",
                "1 < 2 && 3 > 2 text: pass>word текст tex",
                "1 < 2 && 3 > 2 text: pass>word текст text",
                "1 < 2 && 3 > 2 text: pass>word текст text",
                "1 < 2 && 3 > 2 text: pass>word текст text\\",
                "1 < 2 && 3 > 2 text: pass>word текст text\\h",
                "1 < 2 && 3 > 2 text: pass>word текст text\\ht",
                "1 < 2 && 3 > 2 text: pass>word текст text\\htm",
                "1 < 2 && 3 > 2 text: pass>word текст text\\html",
                "1 < 2 && 3 > 2 text: pass>word текст text\\html",
                "1 < 2 && 3 > 2 text: pass>word текст text\\html",

        };
        for (int i = 0; i < strings.length; i++) {
            assertEquals(hiddenString.copy(0, i), strings[i]);
        }

        String[] strings2 = {
                "",
                "",
                "<",
                "< ",
                "< 2",
                "< 2 ",
                "< 2 &",
                "< 2 &&",
                "< 2 && ",
                "< 2 && 3",
                "< 2 && 3 ",
                "< 2 && 3 ",
                "< 2 && 3 >",
                "< 2 && 3 > ",
                "< 2 && 3 > 2",
                "< 2 && 3 > 2 ",
                "< 2 && 3 > 2 t",
                "< 2 && 3 > 2 te",
                "< 2 && 3 > 2 tex",
                "< 2 && 3 > 2 text",
                "< 2 && 3 > 2 text:",
                "< 2 && 3 > 2 text: ",
                "< 2 && 3 > 2 text: ",
                "< 2 && 3 > 2 text: p",
                "< 2 && 3 > 2 text: pa",
                "< 2 && 3 > 2 text: pas",
                "< 2 && 3 > 2 text: pass",
                "< 2 && 3 > 2 text: pass",
                "< 2 && 3 > 2 text: pass>",
                "< 2 && 3 > 2 text: pass>w",
                "< 2 && 3 > 2 text: pass>wo",
                "< 2 && 3 > 2 text: pass>wor",
                "< 2 && 3 > 2 text: pass>word",
                "< 2 && 3 > 2 text: pass>word",
                "< 2 && 3 > 2 text: pass>word ",
                "< 2 && 3 > 2 text: pass>word т",
                "< 2 && 3 > 2 text: pass>word те",
                "< 2 && 3 > 2 text: pass>word тек",
                "< 2 && 3 > 2 text: pass>word текс",
                "< 2 && 3 > 2 text: pass>word текст",
                "< 2 && 3 > 2 text: pass>word текст ",
                "< 2 && 3 > 2 text: pass>word текст t",
                "< 2 && 3 > 2 text: pass>word текст te",
                "< 2 && 3 > 2 text: pass>word текст tex",
                "< 2 && 3 > 2 text: pass>word текст text",
                "< 2 && 3 > 2 text: pass>word текст text",
                "< 2 && 3 > 2 text: pass>word текст text\\",
                "< 2 && 3 > 2 text: pass>word текст text\\h",
                "< 2 && 3 > 2 text: pass>word текст text\\ht",
                "< 2 && 3 > 2 text: pass>word текст text\\htm",
                "< 2 && 3 > 2 text: pass>word текст text\\html",
                "< 2 && 3 > 2 text: pass>word текст text\\html",
                "< 2 && 3 > 2 text: pass>word текст text\\html",
        };
        for (int i = 0; i < strings2.length; i++) {
            assertEquals(hiddenString.copy(2, i), strings2[i]);
        }


        String[] strings3 = {
                "",
                "<",
                "< ",
                "< 2",
                "< 2 ",
                "< 2 &",
                "< 2 &&",
                "< 2 && ",
                "< 2 && 3",
                "< 2 && 3 ",
                "< 2 && 3 ",
                "< 2 && 3 >",
                "< 2 && 3 > ",
                "< 2 && 3 > 2",
                "< 2 && 3 > 2 ",
                "< 2 && 3 > 2 t",
                "< 2 && 3 > 2 te",
                "< 2 && 3 > 2 tex",
                "< 2 && 3 > 2 text",
                "< 2 && 3 > 2 text:",
                "< 2 && 3 > 2 text: ",
                "< 2 && 3 > 2 text: ",
                "< 2 && 3 > 2 text: p",
                "< 2 && 3 > 2 text: pa",
                "< 2 && 3 > 2 text: pas",
                "< 2 && 3 > 2 text: pass",
                "< 2 && 3 > 2 text: pass",
                "< 2 && 3 > 2 text: pass>",
                "< 2 && 3 > 2 text: pass>w",
                "< 2 && 3 > 2 text: pass>wo",
                "< 2 && 3 > 2 text: pass>wor",
                "< 2 && 3 > 2 text: pass>word",
                "< 2 && 3 > 2 text: pass>word",
                "< 2 && 3 > 2 text: pass>word ",
                "< 2 && 3 > 2 text: pass>word т",
                "< 2 && 3 > 2 text: pass>word те",
                "< 2 && 3 > 2 text: pass>word тек",
                "< 2 && 3 > 2 text: pass>word текс",
                "< 2 && 3 > 2 text: pass>word текст",
                "< 2 && 3 > 2 text: pass>word текст ",
                "< 2 && 3 > 2 text: pass>word текст t",
                "< 2 && 3 > 2 text: pass>word текст te",
                "< 2 && 3 > 2 text: pass>word текст tex",
                "< 2 && 3 > 2 text: pass>word текст text",
                "< 2 && 3 > 2 text: pass>word текст text",
                "< 2 && 3 > 2 text: pass>word текст text\\",
                "< 2 && 3 > 2 text: pass>word текст text\\h",
                "< 2 && 3 > 2 text: pass>word текст text\\ht",
                "< 2 && 3 > 2 text: pass>word текст text\\htm",
                "< 2 && 3 > 2 text: pass>word текст text\\html",
                "< 2 && 3 > 2 text: pass>word текст text\\html",
                "< 2 && 3 > 2 text: pass>word текст text\\html",
        };

        for (int i = 0; i < strings3.length; i++) {
            assertEquals(hiddenString.copy(3, i), strings3[i]);
        }
    }

    @Test
    public void test2() {
        byte b = 0;
        do {
            String s2 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            System.out.println(s2 + " " + b);
            b++;
        } while (b != 0);
    }
}
