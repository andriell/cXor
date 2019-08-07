package andriell.cxor;

import java.io.UnsupportedEncodingException;

public class HiddenString {
    char[] data = null;
    char[] dataHidden = null;

    public HiddenString() {
    }

    public HiddenString(String s) {
        setData(s.toCharArray());
    }

    public HiddenString(char[] data) {
        setData(data);
    }

    public void setData(String s) {
        setData(s.toCharArray());
    }

    public void setData(char[] data) {
        this.data = data;
        this.dataHidden = new char[data.length];
        boolean isPass = false;
        boolean slash = false;
        for (int i = 0; i < data.length; i++) {
            if (slash) {
                slash = false;
                dataHidden[i] = isPass ? '*' : data[i];
                continue;
            }
            if (data[i] == '<') {
                isPass = true;
                dataHidden[i] = '*';
                continue;
            } else if (data[i] == '>') {
                isPass = false;
                dataHidden[i] = '*';
                continue;
            }

            if (data[i] == '\\') {
                slash = true;
            }
            if (isPass) {
                dataHidden[i] = data[i] == '\n' ? '\n' : '*';
            } else {
                dataHidden[i] = data[i];
            }
        }
    }

    public void clear()
    {
        this.data = null;
        this.dataHidden = null;
    }

    public String copy(int start, int l) {
        if (start < 0 || l <= 0) {
            return "";
        }

        char[] bytes = new char[data.length];
        boolean slash = false;
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            if (slash) {
                slash = false;
                if (start <= i && i < start + l) {
                    bytes[j++] = data[i];
                }
                continue;
            }
            if (data[i] == '<' || data[i] == '>') {
                continue;
            }
            if (data[i] == '\\') {
                slash = true;
                continue;
            }
            if (start <= i && i < start + l) {
                bytes[j++] = data[i];
            }
        }
        return new String(bytes, 0, j);
    }


    public String getStringHidden() {
        if (dataHidden == null) {
            return "";
        }
        return new String(dataHidden);
    }

    public String getString() {
        if (data == null) {
            return "";
        }
        return new String(data);
    }
}
