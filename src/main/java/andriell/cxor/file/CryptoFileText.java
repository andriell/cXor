package andriell.cxor.file;

import andriell.cxor.Constants;

import java.io.*;

public class CryptoFileText extends AbstractCryptoFile {
    private String[] extensions = {"txt"};

    public void save(byte[] data) throws IOException {
        if (data.length > Constants.MAX_SIZE) {
            throw new IOException("The data is too large");
        }
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(getFile()));
        for (byte b : data) {
            os.write(b);
        }
        os.flush();
        os.close();
    }

    public byte[] read() throws IOException {
        BufferedInputStream dataIs = new BufferedInputStream(new FileInputStream(getFile()));
        int fileSize = (int) getFile().length();
        byte[] r;
        r = new byte[fileSize];
        for (int i = 0; i < r.length; i++) {
            r[i] = (byte) dataIs.read();
        }
        return r;
    }

    @Override
    public String getDescription() {
        return "Text";
    }

    @Override
    public String[] getExtensions() {
        return extensions;
    }
}
