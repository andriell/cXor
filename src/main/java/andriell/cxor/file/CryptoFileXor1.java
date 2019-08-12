package andriell.cxor.file;

import andriell.cxor.crypto.PasswordSequence;

import java.io.*;

public class CryptoFileXor1 extends CryptoFileXorN {
    private String[] extensions = {"xor"};

    @Override
    int getN() {
        return 0;
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
