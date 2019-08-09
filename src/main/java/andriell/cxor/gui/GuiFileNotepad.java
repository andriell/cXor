package andriell.cxor.gui;

import andriell.cxor.Constants;
import andriell.cxor.file.CryptoFileInterface;
import andriell.cxor.file.CryptoFiles;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

/**
 * Created by Rybalko on 30.08.2016.
 */
public class GuiFileNotepad {
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
    private JComboBox formatComboBox;

    private JFileChooser dataFileChooser;
    private File dataFile;
    private char echoChar;
    private Color backgroundColor;
    private boolean loaded = false;
    private String error;

    public JPanel getRootPane() {
        return rootPane;
    }

    public void init() {
        String defaultPath = new File(".").getAbsolutePath();
        backgroundColor = rootPane.getBackground();

        //<editor-fold desc="textArea">
        DefaultContextMenu.addContextMenu(textArea);
        ((HiddenTextArea) textArea).setScrollPane(textAreaSp);
        //</editor-fold>

        //<editor-fold desc="dataFileChooser">
        dataFileChooser = new JFileChooser(Preferences.get(Preferences.LAST_USED_FOLDER_DATA_PASS, defaultPath));
        FileNameExtensionFilter ff = CryptoFiles.getInstance().getExtensionFilterAll();
        dataFileChooser.addChoosableFileFilter(ff);
        dataFileChooser.setFileFilter(ff);
        FileNameExtensionFilter[] filters = CryptoFiles.getInstance().getExtensionFilters();
        for (FileNameExtensionFilter filter : filters) {
            dataFileChooser.addChoosableFileFilter(filter);
        }
        Dimension dimension = (Dimension) Preferences.getSerializable(Preferences.LAST_USED_DIMENSION, dataFileChooser.getPreferredSize());
        dataFileChooser.setPreferredSize(dimension);
        echoChar = passwordField.getEchoChar();
        if (dataFile != null && dataFile.isFile()) {
            dataFileChooser.setSelectedFile(dataFile);
        }
        //</editor-fold>

        //<editor-fold desc="formatComboBox">
        String[] descriptions = CryptoFiles.getInstance().getDescriptions();
        DefaultComboBoxModel model = new DefaultComboBoxModel(descriptions);
        formatComboBox.setModel(model);
        //</editor-fold>

        //<editor-fold desc="fileButton">
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
        //</editor-fold>

        //<editor-fold desc="loadButton">
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    loaded = false;
                    int i = formatComboBox.getSelectedIndex();
                    CryptoFileInterface binFile = CryptoFiles.getInstance().getCryptoFile(i);
                    binFile.setFile(dataFile);
                    binFile.setPassword(new String(passwordField.getPassword()).getBytes(Constants.CHARSET));
                    byte[] data = binFile.read();
                    textArea.setEditable(false);
                    HiddenTextArea hiddenTextArea = (HiddenTextArea) textArea;
                    hiddenTextArea.setTextAndHide(new String(data, Constants.CHARSET));
                    loaded = true;
                    error = null;
                } catch (Exception e1) {
                    error = e1.getMessage();
                }
                update();
            }
        });
        //</editor-fold>

        //<editor-fold desc="clearButton">
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passwordField.setText("");
                loaded = false;
                dataFile = null;
                update();
            }
        });
        //</editor-fold>

        //<editor-fold desc="saveButton">
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int i = formatComboBox.getSelectedIndex();
                    CryptoFileInterface binFile = CryptoFiles.getInstance().getCryptoFile(i);
                    binFile.setFile(dataFile);
                    binFile.setPassword(new String(passwordField.getPassword()).getBytes(Constants.CHARSET));
                    byte[] data = textArea.getText().getBytes(Constants.CHARSET);
                    binFile.save(data);
                    error = null;
                } catch (Exception e1) {
                    error = e1.getMessage();
                }
                update();
            }
        });
        //</editor-fold>

        //<editor-fold desc="showButton">
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
        //</editor-fold>

        //<editor-fold desc="editDataButton">
        editDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setEditable(!textArea.isEditable());
                update();
            }
        });
        //</editor-fold>

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
