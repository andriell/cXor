package andriell.cxor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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

    private static final String CHARSET = "UTF-8";

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
                    fileLabel.setText("The file is not loaded");
                    return;
                }
                try {
                    CircularInputStream dataIs = new CircularInputStream(dataFile);
                    int fileSize = (int) dataFile.length();
                    byte[] data = new byte[fileSize];
                    if (passwordField.getPassword() == null || passwordField.getPassword().length < 1) {
                        for (int i = 0; i < fileSize; i++) {
                            data[i] = dataIs.read();
                        }
                    } else {
                        PasswordSequence sequence = new PasswordSequence(passwordField.getPassword());
                        for (int i = 0; i < fileSize; i++) {
                            data[i] = (byte) (sequence.read() ^ dataIs.read());
                        }
                    }
                    dataIs.close();
                    textArea.setText(new String(data, CHARSET));
                    update();
                } catch (Exception e1) {
                    fileLabel.setText("Error");
                    e1.printStackTrace();
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (dataFile == null) {
                    fileLabel.setText("The file is not loaded");
                    return;
                }
                try {
                    FileOutputStream os = new FileOutputStream(dataFile);
                    byte[] data = textArea.getText().getBytes(CHARSET);
                    PasswordSequence sequence = new PasswordSequence(passwordField.getPassword());
                    for (byte b: data) {
                        os.write(sequence.read() ^ b);
                    }
                    os.flush();
                    os.close();
                } catch (Exception e1) {
                    fileLabel.setText("Error");
                    e1.printStackTrace();
                }
            }
        });
    }

    private void update() {
        saveButton.setEnabled(dataFile != null);
        loadButton.setEnabled(dataFile != null);
        if (dataFile == null) {
            fileLabel.setText("");
        } else {
            fileLabel.setText(dataFile.getName());
        }
    }
}