package andriell.cxor.file;

import andriell.cxor.crypto.PasswordSequence;

import java.io.*;

public class CryptoFileXor1 extends AbstractCryptoFile {
    private String[] extensions = {"xor"};

    public void save(byte[] data) throws IOException {
        checkDataSize(data);
        BufferedOutputStream os = getBufferedOutputStream();
        PasswordSequence sequence = getPasswordSequence();
        for (byte b : data) {
            os.write(sequence.read() ^ b);
        }
        os.flush();
        os.close();
    }

    public byte[] read() throws IOException {
        BufferedInputStream is = getBufferedInputStream();
        int fileSize = getFileSizeInt();
        byte[] r = new byte[fileSize];
        PasswordSequence sequence = getPasswordSequence();
        for (int i = 0; i < r.length; i++) {
            r[i] = (byte) (sequence.read() ^ is.read());
        }
        is.close();
        return r;
    }

    @Override
    public String getDescription() {
        return "Xor";
    }

    @Override
    public String[] getExtensions() {
        return extensions;
    }
}
