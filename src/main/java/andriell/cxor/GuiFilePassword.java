package andriell.cxor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.prefs.Preferences;

/**
 * Created by Rybalko on 30.08.2016.
 */
public class GuiFilePassword {
    private static String LAST_USED_FOLDER_DATA = "last_used_folder_data";

    private JButton fileButton;
    private JButton showButton;
    private JPasswordField passwordField;
    private JLabel fileLabel;
    private JTextArea textArea;
    private JButton saveButton;
    private JPanel rootPane;
    private JButton loadButton;
    private JButton clearButton;

    private Preferences prefs;
    private JFileChooser dataFileChooser;
    private File dataFile;
    private char echoChar;

    private static final String CHARSET = "UTF-8";

    public JPanel getRootPane() {
        return rootPane;
    }

    public void init() {
        prefs = Preferences.userRoot().node(getClass().getName());
        String defaultPath = new File(".").getAbsolutePath();
        dataFileChooser = new JFileChooser(prefs.get(LAST_USED_FOLDER_DATA, defaultPath));
        echoChar = passwordField.getEchoChar();

        fileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = dataFileChooser.showOpenDialog(rootPane);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    dataFile = dataFileChooser.getSelectedFile();
                    prefs.put(LAST_USED_FOLDER_DATA, dataFileChooser.getSelectedFile().getParent());
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
                long fileSizeL = dataFile.length();
                if (fileSizeL > 1048576) {
                    fileLabel.setText("The file is too large");
                    return;
                }
                try {
                    CircularInputStream dataIs = new CircularInputStream(dataFile);
                    int fileSize = (int) fileSizeL;
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
                } catch (Exception e1) {
                    fileLabel.setText("Error");
                    e1.printStackTrace();
                }
                update();
                saveButton.setEnabled(false);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passwordField.setText("");
                textArea.setText("");
                dataFile = null;
                update();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (dataFile == null) {
                    fileLabel.setText("The file is not loaded");
                    return;
                }
                if (passwordField.getPassword() == null || passwordField.getPassword().length < 1) {
                    fileLabel.setText("Empty password");
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
                update();
                saveButton.setEnabled(false);
            }
        });

        DocumentListener documentListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                update();
            }
            public void removeUpdate(DocumentEvent e) {
                update();
            }
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        };
        passwordField.getDocument().addDocumentListener(documentListener);
        textArea.getDocument().addDocumentListener(documentListener);

        showButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {
                passwordField.setEchoChar((char) 0);
                showButton.setText("Hide");
            }

            public void mouseReleased(MouseEvent e) {
                passwordField.setEchoChar(echoChar);
                showButton.setText("Show");
            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        });
        update();
    }

    private void update() {
        saveButton.setEnabled(dataFile != null);
        loadButton.setEnabled(dataFile != null);
        clearButton.setEnabled(dataFile != null);
        if (dataFile == null) {
            fileLabel.setText("Not set");
        } else {
            fileLabel.setText(dataFile.getName());
        }
    }
}
