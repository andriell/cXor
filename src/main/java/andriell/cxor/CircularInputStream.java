package andriell.cxor;

import java.io.*;

/**
 * Created by Rybalko on 29.08.2016.
 */
public class CircularInputStream {
    private File file;
    private BufferedInputStream bis;
    private long position;
    private long fileSize;

    public CircularInputStream(File file) throws FileNotFoundException {
        this.file = file;
        bis = new BufferedInputStream(new FileInputStream(file));
        position = 0;
        fileSize = file.length();
    }

    public byte read() throws IOException {
        checkPosition();
        position++;
        return (byte) bis.read();
    }

    public void skip(long i) throws IOException {
        for (;i > 0; i--) {
            read();
        }
    }

    private void checkPosition() throws IOException {
        if (position >= fileSize) {
            bis.close();
            bis = new BufferedInputStream(new FileInputStream(file));
            position = 0;
        }
    }
}
