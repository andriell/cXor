package andriell.cxor;

import java.io.UnsupportedEncodingException;

public class HiddenString {
    static final String CHARSET = "UTF-8";

    byte[] data = null;
    byte[] dataHidden = null;

    public HiddenString() {
    }

    public HiddenString(String s) {
        byte[] bytes = new byte[0];
        try {
            bytes = s.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setData(bytes);
    }

    public HiddenString(byte[] data) {
        setData(data);
    }

    public void setData(String s) {
        byte[] bytes = new byte[0];
        try {
            bytes = s.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setData(bytes);
    }

    public void setData(byte[] data) {
        this.data = data;
        this.dataHidden = new byte[data.length];
        boolean isPass = false;
        boolean slash = false;
        for (int i = 0; i < data.length; i++) {
            if (slash) {
                slash = false;
                dataHidden[i] = isPass ? 0x2a : data[i];
                continue;
            }
            if (data[i] == 0x3c) { // <
                isPass = true;
                dataHidden[i] = 0x2a;
                continue;
            } else if (data[i] == 0x3e) { // >
                isPass = false;
                dataHidden[i] = 0x2a;
                continue;
            }

            if (data[i] == 0x5c) { // \
                slash = true;
            }
            if (isPass) {
                dataHidden[i] = 0x2a;
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
        if (start < 0 || l <= 0 || start >= data.length) {
            return null;
        }

        if (start + l > data.length) {
            l = data.length - start;
        }
        byte[] bytes = new byte[l];
        boolean slash = false;
        int j = 0;
        for (int i = 0; i < start + l; i++) {
            if (slash) {
                slash = false;
                if (i >= start) {
                    bytes[j++] = data[i];
                }
                continue;
            }
            if (data[i] == 0x3c || data[i] == 0x3e) { // < >
                continue;
            }
            if (data[i] == 0x5c) { // \
                slash = true;
                continue;
            }
            if (i >= start) {
                bytes[j++] = data[i];
            }
        }
        try {
            return new String(bytes, 0, j, CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getStringHidden() {
        if (dataHidden == null) {
            return "";
        }
        try {
            return new String(dataHidden, CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getString() {
        if (data == null) {
            return "";
        }
        try {
            return new String(data, CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
