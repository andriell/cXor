package andriell.cxor.file;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public interface CryptoFileInterface {
    public File getFile();

    public void setFile(File file) throws IOException;

    public byte[] getPassword();

    public void setPassword(byte[] password);

    public void save(byte[] data) throws IOException;

    public byte[] read() throws IOException;

    public FileNameExtensionFilter getExtensionFilter();

    public String getDescription();
    public String[] getExtensions();
}
