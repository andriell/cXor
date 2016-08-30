package andriell.cxor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Rybalko on 26.08.2016.
 */
public class GuiFileFile {
    private JButton dataButton;
    private JButton keyButton;
    private JButton saveButton;
    private JProgressBar saveProgressBar;
    private JPanel rootPane;
    private JLabel dataLabel;
    private JLabel keyLabel;
    private JTextField keyShiftTextField;


    private JFileChooser dataFileChooser;
    private JFileChooser keyFileChooser;
    private JFileChooser saveFileChooser;
    private File dataFile;
    private File keyFile;
    private File saveFile;
    private Encrypt encrypt = new Encrypt();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private boolean isRun = false;

    private static final String TEXT_SAVE = "Save";
    private static final String TEXT_STOP = "Stop";

    public void init() {
        dataFileChooser = new JFileChooser();
        keyFileChooser = new JFileChooser();
        saveFileChooser = new JFileChooser();

        dataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = dataFileChooser.showOpenDialog(rootPane);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    dataFile = dataFileChooser.getSelectedFile();
                    update();
                }
            }
        });

        keyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = keyFileChooser.showOpenDialog(rootPane);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    keyFile = keyFileChooser.getSelectedFile();
                    update();
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isRun) {
                    isRun = false;
                } else {
                    int ret = saveFileChooser.showSaveDialog(rootPane);
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        saveFile = saveFileChooser.getSelectedFile();
                        executor.execute(encrypt);
                    }
                }
                update();
            }
        });
        update();
    }

    private void update() {
        if (dataFile == null) {
            dataLabel.setText("Not set");
        } else {
            dataLabel.setText(dataFile.getName());
        }
        if (keyFile == null) {
            keyLabel.setText("Not set");
        } else {
            keyLabel.setText(keyFile.getName());
        }
        saveButton.setEnabled(dataFile != null && keyFile != null);
        if (isRun) {
            saveButton.setText(TEXT_STOP);
        } else {
            saveButton.setText(TEXT_SAVE);
            saveProgressBar.setValue(0);
            saveProgressBar.setString("0%");
        }
        dataButton.setEnabled(!isRun);
        keyButton.setEnabled(!isRun);
        keyShiftTextField.setEnabled(!isRun);
    }

    private class Encrypt implements Runnable {
        public void run() {
            if (!(dataFile != null && keyFile != null && dataFile.isFile() && keyFile.isFile())) {
                return;
            }
            isRun = true;
            update();
            try {
                CircularInputStream dataIs = new CircularInputStream(dataFile);
                CircularInputStream keyIs = new CircularInputStream(keyFile);
                BufferedOutputStream saveOs = new BufferedOutputStream(new FileOutputStream(saveFile));
                int dataSize = (int) dataFile.length();
                int keyShift = Integer.parseInt(keyShiftTextField.getText());
                if (keyShift > 0) {
                    keyIs.skip(keyShift);
                }
                saveProgressBar.setMinimum(0);
                saveProgressBar.setMaximum(dataSize);
                saveProgressBar.setValue(0);
                for (int i = 0; i < dataSize; i++) {
                    if (!isRun) {
                        break;
                    }
                    saveOs.write(dataIs.read() ^ keyIs.read());
                    saveProgressBar.setValue(i);
                    saveProgressBar.setString((Math.round(i * 1000.0f / dataSize) / 10.0f) + "%");
                }
                saveOs.flush();
                saveOs.close();
                dataIs.close();
                keyIs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isRun = false;
            update();
        }
    }

    public JPanel getRootPane() {
        return rootPane;
    }
}
