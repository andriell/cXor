package andriell.cxor.file;


import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class CryptoFiles {
    private static CryptoFiles instance = null;

    public static CryptoFiles getInstance() {
        if (instance == null) {
            instance = new CryptoFiles();
        }
        return instance;
    }

    private CryptoFileInterface[] cryptoFiles;
    private FileNameExtensionFilter filterAll;

    private CryptoFiles() {
        cryptoFiles = new CryptoFileInterface[5];
        cryptoFiles[0] = new CryptoFileText();
        cryptoFiles[1] = new CryptoFileXor1();
        cryptoFiles[2] = new CryptoFileXor1Zipped();
        cryptoFiles[3] = new CryptoFileXor2();
        cryptoFiles[4] = new CryptoFileXor2Zipped();
    }

    public String[] getDescriptions()
    {
        String[] r = new String[cryptoFiles.length];
        for (int i = 0; i < cryptoFiles.length; i++) {
            r[i] = cryptoFiles[i].getDescription();
        }
        return r;
    }

    public File renameFile(File file, CryptoFileInterface cryptoFile) {
        int dotIndex = file.getAbsolutePath().lastIndexOf('.');
        String extension = file.getAbsolutePath().substring(dotIndex + 1);
        String[] extensions = cryptoFile.getExtensions();
        for (String e : extensions) {
            if (e.equals(extension)) {
                return file;
            }
        }
        return new File(file.getAbsolutePath().substring(0, dotIndex + 1) + extensions[0]);
    }

    /**
     * Return index crypto file to open a file by the file parameter
     * @param file File
     * @return int
     */
    public int getCryptoFileIndex(File file) {
        for (int i = 0; i < cryptoFiles.length; i++) {
            FileNameExtensionFilter filter = cryptoFiles[i].getExtensionFilter();
            if (filter.accept(file)) {
                return i;
            }
        }
        return 3;
    }

    public CryptoFileInterface getCryptoFile(int i) {
        if (i < 0 || i >= cryptoFiles.length) {
            return null;
        }
        return cryptoFiles[i];
    }

    public FileNameExtensionFilter[] getExtensionFilters() {
        FileNameExtensionFilter[] r = new FileNameExtensionFilter[cryptoFiles.length];
        for (int i = 0; i < cryptoFiles.length; i++) {
            r[i] = cryptoFiles[i].getExtensionFilter();
        }
        return r;
    }

    public FileNameExtensionFilter getExtensionFilterAll() {
        if (filterAll == null) {
            int count = 0;
            for (CryptoFileInterface f : cryptoFiles) {
                count += f.getExtensions().length;
            }
            String[] extensions = new String[count];
            int i = 0;
            for (CryptoFileInterface f : cryptoFiles) {
                String[] extensions1 = f.getExtensions();
                for (String e : extensions1) {
                    extensions[i++] = e;
                }
            }
            filterAll = buildFilter("All formats", extensions);
        }

        return filterAll;
    }

    public static FileNameExtensionFilter buildFilter(String description, String[] extensions)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(description);
        builder.append(" (");
        String prefix = "";
        for (String extension :
                extensions) {
            builder.append(prefix);
            builder.append("*.");
            builder.append(extension);
            prefix = ", ";
        }
        builder.append(")");
        return new FileNameExtensionFilter(builder.toString(), extensions);
    }
}
