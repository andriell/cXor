package andriell.cxor.file;

import andriell.cxor.crypto.CircularBytes;
import andriell.cxor.crypto.PasswordSequence;

import java.io.*;

public class CryptoFileXor2 extends AbstractCryptoFile {
    private String[] extensions = {"bin", "xor2"};

    public void save(byte[] data) throws IOException {
        checkDataSize(data);
        BufferedOutputStream os = getBufferedOutputStream();
        PasswordSequence sequence = getPasswordSequence();
        CircularBytes cb = randomCircularBytes(4);
        for (int i = 0; i < 4; i++) {
            os.write(sequence.read() ^ cb.read());
        }
        for (byte b : data) {
            os.write(sequence.read() ^ b ^ cb.read());
        }
        os.flush();
        os.close();
    }

    public byte[] read() throws IOException {
        BufferedInputStream is = getBufferedInputStream();
        int fileSize = getFileSizeInt();

        PasswordSequence sequence = getPasswordSequence();
        byte[] bytes = new byte[4];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (sequence.read() ^ is.read());
        }
        CircularBytes cb = new CircularBytes(bytes);
        byte[] r = new byte[fileSize - 4];
        for (int i = 0; i < r.length; i++) {
            r[i] = (byte) (sequence.read() ^ is.read() ^ cb.read());
        }
        is.close();
        return r;
    }

    @Override
    public String getDescription() {
        return "Xor2";
    }

    @Override
    public String[] getExtensions() {
        return extensions;
    }
}
