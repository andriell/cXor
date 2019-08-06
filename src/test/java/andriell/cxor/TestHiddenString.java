package andriell.cxor;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestHiddenString {
    @Test
    public void test1() throws IOException {
        String s = "1 \\< 2 && 3 \\> 2 text: <pass\\>word> text\\\\html";
        String sh = "1 \\< 2 && 3 \\> 2 text: ************ text\\\\html";

        HiddenString hiddenString = new HiddenString(s);

        assertEquals(hiddenString.getString(), s);

        assertEquals(hiddenString.getStringHidden(), sh);

        assertNull(hiddenString.copy(-1, -1));
        assertNull(hiddenString.copy(-1, 0));
        assertNull(hiddenString.copy(0, -1));
        assertNull(hiddenString.copy(0, 0));
        assertNull(hiddenString.copy(-1, -1));
        assertNull(hiddenString.copy(-1, 1));
        assertNull(hiddenString.copy(1, -1));
        String[] strings = {
                null,
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
                "1 < 2 && 3 > 2 text: pass>word t",
                "1 < 2 && 3 > 2 text: pass>word te",
                "1 < 2 && 3 > 2 text: pass>word tex",
                "1 < 2 && 3 > 2 text: pass>word text",
                "1 < 2 && 3 > 2 text: pass>word text",
                "1 < 2 && 3 > 2 text: pass>word text\\",
                "1 < 2 && 3 > 2 text: pass>word text\\h",
                "1 < 2 && 3 > 2 text: pass>word text\\ht",
                "1 < 2 && 3 > 2 text: pass>word text\\htm",
                "1 < 2 && 3 > 2 text: pass>word text\\html",
                "1 < 2 && 3 > 2 text: pass>word text\\html",
                "1 < 2 && 3 > 2 text: pass>word text\\html",
        };
        for (int i = 0; i <= 48; i++) {
            assertEquals(hiddenString.copy(0, i), strings[i]);
        }

        String[] strings2 = {
                null,
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
                "< 2 && 3 > 2 text: pass>word t",
                "< 2 && 3 > 2 text: pass>word te",
                "< 2 && 3 > 2 text: pass>word tex",
                "< 2 && 3 > 2 text: pass>word text",
                "< 2 && 3 > 2 text: pass>word text",
                "< 2 && 3 > 2 text: pass>word text\\",
                "< 2 && 3 > 2 text: pass>word text\\h",
                "< 2 && 3 > 2 text: pass>word text\\ht",
                "< 2 && 3 > 2 text: pass>word text\\htm",
                "< 2 && 3 > 2 text: pass>word text\\html",
                "< 2 && 3 > 2 text: pass>word text\\html",
                "< 2 && 3 > 2 text: pass>word text\\html",
        };
        for (int i = 0; i <= 46; i++) {
            assertEquals(hiddenString.copy(2, i), strings2[i]);
        }


        String[] strings3 = {
                null,
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
                "< 2 && 3 > 2 text: pass>word t",
                "< 2 && 3 > 2 text: pass>word te",
                "< 2 && 3 > 2 text: pass>word tex",
                "< 2 && 3 > 2 text: pass>word text",
                "< 2 && 3 > 2 text: pass>word text",
                "< 2 && 3 > 2 text: pass>word text\\",
                "< 2 && 3 > 2 text: pass>word text\\h",
                "< 2 && 3 > 2 text: pass>word text\\ht",
                "< 2 && 3 > 2 text: pass>word text\\htm",
                "< 2 && 3 > 2 text: pass>word text\\html",
                "< 2 && 3 > 2 text: pass>word text\\html",
        };

        for (int i = 0; i <= 44; i++) {
            assertEquals(hiddenString.copy(3, i), strings3[i]);
        }
    }
}
