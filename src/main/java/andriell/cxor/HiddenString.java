package andriell.cxor;

import java.io.UnsupportedEncodingException;

public class HiddenString {
    char[] data = null;
    char[] dataHidden = null;
    int dataHiddenLength = 0;

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
        dataHiddenLength = 0;
        boolean isPass = false;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == '"') {
                if (i != data.length - 1 && data[i + 1] == '"') {
                    i++;
                } else {
                    isPass = !isPass;
                    continue;
                }
            }
            if (isPass) {
                dataHidden[dataHiddenLength++] = data[i] == '\n' ? '\n' : '*';
            } else {
                dataHidden[dataHiddenLength++] = data[i];
            }
        }
    }

    public void clear() {
        this.data = null;
        this.dataHidden = null;
    }

    public String copy(int start, int l) {
        if (start < 0 || l <= 0) {
            return "";
        }
        char[] r = new char[data.length];
        int chr = 0;
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == '"') {
                if (i != data.length - 1 && data[i + 1] == '"') {
                    i++;
                } else {
                    // password begin or end
                    continue;
                }
            }
            chr++;
            if (start < chr && chr <= start + l) {
                r[j++] = data[i];
            }
        }
        return new String(r, 0, j);
    }


    public String getStringHidden() {
        if (dataHidden == null) {
            return "";
        }
        return new String(dataHidden, 0, dataHiddenLength);
    }

    public String getString() {
        if (data == null) {
            return "";
        }
        return new String(data);
    }
}
