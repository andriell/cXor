package andriell.cxor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Rybalko on 30.08.2016.
 */
public class GuiFilePassword {
    private JButton fileButton;
    private JButton showButton;
    private JPasswordField passwordField;
    private JLabel fileLabel;
    private JTextArea textArea;
    private JButton saveButton;
    private JPanel rootPane;
    private JButton loadButton;

    private JFileChooser dataFileChooser;
    private File dataFile;

    public JPanel getRootPane() {
        return rootPane;
    }

    public void init() {
        dataFileChooser = new JFileChooser();

        fileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = dataFileChooser.showOpenDialog(rootPane);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    dataFile = dataFileChooser.getSelectedFile();
                    update();
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (dataFile == null || !dataFile.isFile()) {
                    return;
                }
                try {
                    PasswordSequence sequence = new PasswordSequence(passwordField.getPassword());
                    CircularInputStream dataIs = new CircularInputStream(dataFile);
                    int fileSize = (int) dataFile.length();
                    byte[] data = new byte[fileSize];
                    for (int i = 0; i < fileSize; i++) {
                        data[i] = (byte) (sequence.read() ^ dataIs.read());
                    }
                    dataIs.close();
                    textArea.setText(new String(data, "UTF-8"));
                    update();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void update() {
        saveButton.setEnabled(dataFile != null);

    }
}
