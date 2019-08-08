package andriell.cxor;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class BinFile {
    public static final String ZIP_FORMAT = "cxorz";
    public static final String CHARSET = "UTF-8";
    public static final int MAX_SIZE = 1048576;

    private static Random random;
    private File file;
    private boolean isZipped = false;
    private char[] password;

    static {
        random = new Random();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) throws IOException {
        if (file == null || !file.isFile()) {
            throw new IOException("The file is not loaded");
        }
        if (file.length() > MAX_SIZE) {
            throw new IOException("The file is too large");
        }
        this.file = file;
        isZipped = file.getName().endsWith(ZIP_FORMAT);
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void save(byte[] data) throws IOException, NoSuchAlgorithmException {
        if (data.length > MAX_SIZE) {
            throw new IOException("The data is too large");
        }
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        if (password == null || password.length < 1) {
            for (byte b : data) {
                os.write(b);
            }
            os.flush();
            os.close();
            return;
        }
        PasswordSequence sequence = new PasswordSequence(password);
        if (!isZipped) {
            for (byte b : data) {
                os.write(sequence.read() ^ b);
            }
            os.flush();
            os.close();
            return;
        }
        data = Gzip.compress(data);

        byte shift = randomByte();
        os.write(sequence.read() ^ shift);
        for (int i = 0; i <= shift + 128; i++) {
            sequence.read();
        }
        for (byte b : data) {
            os.write(sequence.read() ^ b);
        }
        os.flush();
        os.close();
    }

    public byte[] read() throws IOException, NoSuchAlgorithmException {
        BufferedInputStream dataIs = new BufferedInputStream(new FileInputStream(file));
        int fileSize = (int) file.length();
        byte[] r;
        if (password == null || password.length < 1) {
            r = new byte[fileSize];
            for (int i = 0; i < r.length; i++) {
                r[i] = (byte) dataIs.read();
            }
            return r;
        }
        PasswordSequence sequence = new PasswordSequence(password);
        if (!isZipped) {
            r = new byte[fileSize];
            for (int i = 0; i < r.length; i++) {
                r[i] = (byte) (sequence.read() ^ dataIs.read());
            }
            dataIs.close();
            return r;
        }

        r = new byte[fileSize - 1];
        byte shift = (byte) (sequence.read() ^ dataIs.read());
        for (int i = 0; i <= shift + 128; i++) {
            sequence.read();
        }
        for (int i = 0; i < r.length; i++) {
            r[i] = (byte) (sequence.read() ^ dataIs.read());
        }
        dataIs.close();
        try {
            r = Gzip.decompress(r, MAX_SIZE);
        } catch (IOException e) {
            throw new IOException("Incorrect password", e);
        }
        return r;
    }

    static byte randomByte() {
        return (byte) (random.nextInt() & 0xFF);
    }
}
