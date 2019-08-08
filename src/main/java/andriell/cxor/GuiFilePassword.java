package andriell.cxor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
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
    private JButton clearButton;
    private JButton editDataButton;
    private JScrollPane textAreaSp;

    private JFileChooser dataFileChooser;
    private File dataFile;
    private char echoChar;
    private Color backgroundColor;
    private BinFile binFile;
    private boolean loaded = false;
    private String error;

    public JPanel getRootPane() {
        return rootPane;
    }

    public void init() {
        String defaultPath = new File(".").getAbsolutePath();
        backgroundColor = rootPane.getBackground();
        binFile = new BinFile();

        dataFileChooser = new JFileChooser(Preferences.get(Preferences.LAST_USED_FOLDER_DATA_PASS, defaultPath));
        FileNameExtensionFilter ff = new FileNameExtensionFilter("All formats (*.cxorz, *.cxor, *.bin)", "cxor", "cxorz", "bin");
        dataFileChooser.addChoosableFileFilter(ff);
        dataFileChooser.setFileFilter(ff);
        dataFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Zipped format (*.cxorz)", "cxorz"));
        dataFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Regular format (*.cxor, *.bin)", "cxor", "bin"));

        Dimension dimension = (Dimension) Preferences.getSerializable(Preferences.LAST_USED_DIMENSION, dataFileChooser.getPreferredSize());
        dataFileChooser.setPreferredSize(dimension);
        echoChar = passwordField.getEchoChar();

        if (dataFile != null && dataFile.isFile()) {
            dataFileChooser.setSelectedFile(dataFile);
        }

        fileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = dataFileChooser.showOpenDialog(rootPane);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    dataFile = dataFileChooser.getSelectedFile();
                    Preferences.put(Preferences.LAST_USED_FOLDER_DATA_PASS, dataFileChooser.getSelectedFile().getParent());
                    update();
                }
                Preferences.putSerializable(Preferences.LAST_USED_DIMENSION, dataFileChooser.getSize());
                dataFileChooser.setPreferredSize(dataFileChooser.getSize());
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    loaded = false;
                    binFile.setFile(dataFile);
                    binFile.setPassword(passwordField.getPassword());
                    byte[] data = binFile.read();
                    textArea.setEditable(false);
                    HiddenTextArea hiddenTextArea = (HiddenTextArea) textArea;
                    hiddenTextArea.setTextAndHide(new String(data, BinFile.CHARSET));
                    loaded = true;
                    error = null;
                } catch (Exception e1) {
                    error = e1.getMessage();
                }
                update();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passwordField.setText("");
                loaded = false;
                dataFile = null;
                update();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    binFile.setFile(dataFile);
                    binFile.setPassword(passwordField.getPassword());
                    byte[] data = textArea.getText().getBytes(BinFile.CHARSET);
                    binFile.save(data);
                    error = null;
                } catch (Exception e1) {
                    error = e1.getMessage();
                }
                update();
                saveButton.setEnabled(false);
            }
        });

        DefaultContextMenu.addContextMenu(textArea);
        ((HiddenTextArea) textArea).setScrollPane(textAreaSp);

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
        editDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setEditable(!textArea.isEditable());
                update();
            }
        });
        update();
    }

    private void update() {
        saveButton.setEnabled(dataFile != null && textArea.isEditable());
        loadButton.setEnabled(dataFile != null);
        clearButton.setEnabled(dataFile != null);
        editDataButton.setEnabled(dataFile != null && loaded);
        if (!loaded) {
            textArea.setText("");
        }
        if (textArea.isEditable()) {
            editDataButton.setText("Hide");
            textArea.setBackground(Color.WHITE);
        } else {
            editDataButton.setText("Edit");
            textArea.setBackground(backgroundColor);
        }
        if (this.error != null) {
            fileLabel.setText(this.error);
            fileLabel.setForeground(Color.RED);
        } else if (dataFile == null) {
            fileLabel.setText("Not set");
            fileLabel.setForeground(Color.BLACK);
        } else {
            fileLabel.setText(dataFile.getName());
            fileLabel.setForeground(Color.BLACK);
        }
    }

    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }

    public File getDataFile() {
        return dataFile;
    }

    private void createUIComponents() {
        textArea = new HiddenTextArea();
    }
}
