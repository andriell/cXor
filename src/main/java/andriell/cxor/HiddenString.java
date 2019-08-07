package andriell.cxor;

import java.io.UnsupportedEncodingException;

public class HiddenString {
    static final String CHARSET = "UTF-8";
    private static final byte CHR_ASTERISK = 0x2a; // *
    private static final byte CHR_LESS = 0x3c; // <
    private static final byte CHR_GREATER = 0x3e; // >
    private static final byte CHR_REVERSE_SOLIDUS = 0x5c; // >
    private static final byte ONE_OCTET = -64; // >

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
                dataHidden[i] = isPass ? CHR_ASTERISK : data[i];
                continue;
            }
            if (data[i] == CHR_LESS) { // <
                isPass = true;
                dataHidden[i] = CHR_ASTERISK;
                continue;
            } else if (data[i] == CHR_GREATER) { // >
                isPass = false;
                dataHidden[i] = CHR_ASTERISK;
                continue;
            }

            if (data[i] == CHR_REVERSE_SOLIDUS) { // \
                slash = true;
            }
            if (isPass) {
                dataHidden[i] = CHR_ASTERISK;
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

        byte[] bytes = new byte[data.length];
        boolean slash = false;
        int chr = 0;
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] >= ONE_OCTET) {
                chr++;
            }
            if (slash) {
                slash = false;
                if (start < chr && chr <= start + l) {
                    bytes[j++] = data[i];
                }
                continue;
            }
            if (data[i] == CHR_LESS || data[i] == CHR_GREATER) { // < >
                continue;
            }
            if (data[i] == CHR_REVERSE_SOLIDUS) { // \
                slash = true;
                continue;
            }
            if (start < chr && chr <= start + l) {
                bytes[j++] = data[i];
            }
        }
        try {
            return new String(bytes, 0, j, CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
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
