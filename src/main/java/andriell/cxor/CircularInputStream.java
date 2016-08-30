package andriell.cxor;

import java.io.*;

/**
 * Created by Rybalko on 29.08.2016.
 */
public class CircularInputStream {
    private File file;
    private BufferedInputStream is;
    private long position;
    private long fileSize;

    public CircularInputStream(File file) throws FileNotFoundException {
        this.file = file;
        is = new BufferedInputStream(new FileInputStream(file));
        position = 0;
        fileSize = file.length();
    }

    public byte read() throws IOException {
        checkPosition();
        position++;
        return (byte) is.read();
    }

    public void skip(long i) throws IOException {
        for (; i > 0; i--) {
            read();
        }
    }

    private void checkPosition() throws IOException {
        if (position >= fileSize) {
            is.close();
            is = new BufferedInputStream(new FileInputStream(file));
            position = 0;
        }
    }

    public void close() throws IOException {
        is.close();
        file = null;
        is = null;
    }
}
