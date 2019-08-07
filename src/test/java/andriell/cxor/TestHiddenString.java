package andriell.cxor;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestHiddenString {
    @Test
    public void test1() {
        String s = "\"\"text\"\": \"pass\"\"word\" текст text\\html\nПользователь \"па\nро\"\"ль\"";
        String sh = "\"text\": ********* текст text\\html\nПользователь **\n*****";
        String sc = "\"text\": pass\"word текст text\\html\nПользователь па\nро\"ль";

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

        for (int i = 0; i < sh.length(); i++) {
            for (int l = i; l < sh.length() - i + 1; l++) {
                assertEquals(hiddenString.copy(i, l), sc.substring(i, i + l));
            }
        }

        String[] strings = {
                "",
                "\"",
                "\"t",
                "\"te",
                "\"tex",
                "\"text",
                "\"text\"",
                "\"text\":",
                "\"text\": ",
                "\"text\": p",
                "\"text\": pa",
                "\"text\": pas",
                "\"text\": pass",
                "\"text\": pass\"",
                "\"text\": pass\"w",
                "\"text\": pass\"wo",
                "\"text\": pass\"wor",
                "\"text\": pass\"word",
                "\"text\": pass\"word ",
                "\"text\": pass\"word т",
                "\"text\": pass\"word те",
                "\"text\": pass\"word тек",
                "\"text\": pass\"word текс",
                "\"text\": pass\"word текст",
                "\"text\": pass\"word текст ",
                "\"text\": pass\"word текст t",
                "\"text\": pass\"word текст te",
                "\"text\": pass\"word текст tex",
                "\"text\": pass\"word текст text",
                "\"text\": pass\"word текст text\\",
                "\"text\": pass\"word текст text\\h",
                "\"text\": pass\"word текст text\\ht",
                "\"text\": pass\"word текст text\\htm",
                "\"text\": pass\"word текст text\\html",
                "\"text\": pass\"word текст text\\html\n",
                "\"text\": pass\"word текст text\\html\nП",
                "\"text\": pass\"word текст text\\html\nПо",
                "\"text\": pass\"word текст text\\html\nПол",
                "\"text\": pass\"word текст text\\html\nПоль",
                "\"text\": pass\"word текст text\\html\nПольз",
                "\"text\": pass\"word текст text\\html\nПользо",
                "\"text\": pass\"word текст text\\html\nПользов",
                "\"text\": pass\"word текст text\\html\nПользова",
                "\"text\": pass\"word текст text\\html\nПользоват",
                "\"text\": pass\"word текст text\\html\nПользовате",
                "\"text\": pass\"word текст text\\html\nПользовател",
                "\"text\": pass\"word текст text\\html\nПользователь",
                "\"text\": pass\"word текст text\\html\nПользователь ",
                "\"text\": pass\"word текст text\\html\nПользователь п",
                "\"text\": pass\"word текст text\\html\nПользователь па",
                "\"text\": pass\"word текст text\\html\nПользователь па\n",
                "\"text\": pass\"word текст text\\html\nПользователь па\nр",
                "\"text\": pass\"word текст text\\html\nПользователь па\nро",
                "\"text\": pass\"word текст text\\html\nПользователь па\nро\"",
                "\"text\": pass\"word текст text\\html\nПользователь па\nро\"л",
                "\"text\": pass\"word текст text\\html\nПользователь па\nро\"ль",
                "\"text\": pass\"word текст text\\html\nПользователь па\nро\"ль",
                "\"text\": pass\"word текст text\\html\nПользователь па\nро\"ль",

        };
        for (int i = 0; i < strings.length; i++) {
            assertEquals(hiddenString.copy(0, i), strings[i]);
        }

        String[] strings2 = {
                "",
                "t",
                "te",
                "tex",
                "text",
                "text\"",
                "text\":",
                "text\": ",
                "text\": p",
                "text\": pa",
                "text\": pas",
                "text\": pass",
                "text\": pass\"",
                "text\": pass\"w",
                "text\": pass\"wo",
                "text\": pass\"wor",
                "text\": pass\"word",
                "text\": pass\"word ",
                "text\": pass\"word т",
                "text\": pass\"word те",
                "text\": pass\"word тек",
                "text\": pass\"word текс",
                "text\": pass\"word текст",
                "text\": pass\"word текст ",
                "text\": pass\"word текст t",
                "text\": pass\"word текст te",
                "text\": pass\"word текст tex",
                "text\": pass\"word текст text",
                "text\": pass\"word текст text\\",
                "text\": pass\"word текст text\\h",
                "text\": pass\"word текст text\\ht",
                "text\": pass\"word текст text\\htm",
                "text\": pass\"word текст text\\html",
                "text\": pass\"word текст text\\html\n",
                "text\": pass\"word текст text\\html\nП",
                "text\": pass\"word текст text\\html\nПо",
                "text\": pass\"word текст text\\html\nПол",
                "text\": pass\"word текст text\\html\nПоль",
                "text\": pass\"word текст text\\html\nПольз",
                "text\": pass\"word текст text\\html\nПользо",
                "text\": pass\"word текст text\\html\nПользов",
                "text\": pass\"word текст text\\html\nПользова",
                "text\": pass\"word текст text\\html\nПользоват",
                "text\": pass\"word текст text\\html\nПользовате",
                "text\": pass\"word текст text\\html\nПользовател",
                "text\": pass\"word текст text\\html\nПользователь",
                "text\": pass\"word текст text\\html\nПользователь ",
                "text\": pass\"word текст text\\html\nПользователь п",
                "text\": pass\"word текст text\\html\nПользователь па",
                "text\": pass\"word текст text\\html\nПользователь па\n",
                "text\": pass\"word текст text\\html\nПользователь па\nр",
                "text\": pass\"word текст text\\html\nПользователь па\nро",
                "text\": pass\"word текст text\\html\nПользователь па\nро\"",
                "text\": pass\"word текст text\\html\nПользователь па\nро\"л",
                "text\": pass\"word текст text\\html\nПользователь па\nро\"ль",
                "text\": pass\"word текст text\\html\nПользователь па\nро\"ль",
                "text\": pass\"word текст text\\html\nПользователь па\nро\"ль",
        };
        for (int i = 0; i < strings2.length; i++) {
            assertEquals(hiddenString.copy(1, i), strings2[i]);
        }


        String[] strings3 = {
                "",
                "\"",
                "\"w",
                "\"wo",
                "\"wor",
                "\"word",
                "\"word ",
                "\"word т",
                "\"word те",
                "\"word тек",
                "\"word текс",
                "\"word текст",
                "\"word текст ",
                "\"word текст t",
                "\"word текст te",
                "\"word текст tex",
                "\"word текст text",
                "\"word текст text\\",
                "\"word текст text\\h",
                "\"word текст text\\ht",
                "\"word текст text\\htm",
                "\"word текст text\\html",
                "\"word текст text\\html\n",
                "\"word текст text\\html\nП",
                "\"word текст text\\html\nПо",
                "\"word текст text\\html\nПол",
                "\"word текст text\\html\nПоль",
                "\"word текст text\\html\nПольз",
                "\"word текст text\\html\nПользо",
                "\"word текст text\\html\nПользов",
                "\"word текст text\\html\nПользова",
                "\"word текст text\\html\nПользоват",
                "\"word текст text\\html\nПользовате",
                "\"word текст text\\html\nПользовател",
                "\"word текст text\\html\nПользователь",
                "\"word текст text\\html\nПользователь ",
                "\"word текст text\\html\nПользователь п",
                "\"word текст text\\html\nПользователь па",
                "\"word текст text\\html\nПользователь па\n",
                "\"word текст text\\html\nПользователь па\nр",
                "\"word текст text\\html\nПользователь па\nро",
                "\"word текст text\\html\nПользователь па\nро\"",
                "\"word текст text\\html\nПользователь па\nро\"л",
                "\"word текст text\\html\nПользователь па\nро\"ль",
                "\"word текст text\\html\nПользователь па\nро\"ль",
                "\"word текст text\\html\nПользователь па\nро\"ль",
        };

        for (int i = 0; i < strings3.length; i++) {
            assertEquals(hiddenString.copy(12, i), strings3[i]);
        }

        String[] strings4 = {
                "",
                "w",
                "wo",
                "wor",
                "word",
                "word ",
                "word т",
                "word те",
                "word тек",
                "word текс",
                "word текст",
                "word текст ",
                "word текст t",
                "word текст te",
                "word текст tex",
                "word текст text",
                "word текст text\\",
                "word текст text\\h",
                "word текст text\\ht",
                "word текст text\\htm",
                "word текст text\\html",
                "word текст text\\html\n",
                "word текст text\\html\nП",
                "word текст text\\html\nПо",
                "word текст text\\html\nПол",
                "word текст text\\html\nПоль",
                "word текст text\\html\nПольз",
                "word текст text\\html\nПользо",
                "word текст text\\html\nПользов",
                "word текст text\\html\nПользова",
                "word текст text\\html\nПользоват",
                "word текст text\\html\nПользовате",
                "word текст text\\html\nПользовател",
                "word текст text\\html\nПользователь",
                "word текст text\\html\nПользователь ",
                "word текст text\\html\nПользователь п",
                "word текст text\\html\nПользователь па",
                "word текст text\\html\nПользователь па\n",
                "word текст text\\html\nПользователь па\nр",
                "word текст text\\html\nПользователь па\nро",
                "word текст text\\html\nПользователь па\nро\"",
                "word текст text\\html\nПользователь па\nро\"л",
                "word текст text\\html\nПользователь па\nро\"ль",
                "word текст text\\html\nПользователь па\nро\"ль",
                "word текст text\\html\nПользователь па\nро\"ль",
        };

        for (int i = 0; i < strings4.length; i++) {
            assertEquals(hiddenString.copy(13, i), strings4[i]);
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
