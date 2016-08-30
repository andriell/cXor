package andriell.cxor;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Rybalko on 30.08.2016.
 */
public class PasswordSequence {
    private byte[] password;
    private byte[] bytes;
    private int position = 0;
    private MessageDigest md;

    public PasswordSequence(char[] password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] p = new byte[password.length];
        for (int i = 0; i < password.length; i++) {
            p[i] = (byte) password[i];
        }
        setPassword(p);
    }

    public PasswordSequence(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        setPassword(password.getBytes());
    }

    private void setPassword(byte[] password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.password = password;
        md = MessageDigest.getInstance("MD5");
        bytes = md.digest(mergeBytes(this.password, md.digest(this.password)));
    }

    public byte read() {
        if (position >= bytes.length) {
            bytes = md.digest(mergeBytes(password, bytes));
            position = 0;
        }
        return bytes[position++];
    }

    public static byte[] mergeBytes(byte[] b1, byte[] b2) {
        if (b1 == null || b2 == null) {
            return null;
        }
        byte[] r = new byte[b1.length + b2.length];
        int p = 0;
        for (byte b:b1) {
            r[p++] = b;
        }
        for (byte b:b2) {
            r[p++] = b;
        }
        return r;
    }
}
