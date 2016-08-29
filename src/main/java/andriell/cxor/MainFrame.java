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
public class MainFrame {
    private JButton dataButton;
    private JButton keyButton;
    private JButton saveButton;
    private JProgressBar saveProgressBar;
    private JPanel rootPane;
    private JLabel dataLabel;
    private JLabel keyLabel;
    private JTextField keyShiftTextField;

    private JFrame frame;
    private JFileChooser dataFileChooser;
    private JFileChooser keyFileChooser;
    private JFileChooser saveFileChooser;
    private File dataFile;
    private File keyFile;
    private File saveFile;
    private Encrypt encrypt = new Encrypt();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public void show() {
        frame = new JFrame("Crypto xor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(rootPane);

        frame.setSize(400, 180);
        frame.setResizable(false);
        frame.setVisible(true);

        dataFileChooser = new JFileChooser();
        keyFileChooser = new JFileChooser();
        saveFileChooser = new JFileChooser();

        dataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = dataFileChooser.showOpenDialog(frame);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    dataFile = dataFileChooser.getSelectedFile();
                    update();
                }
            }
        });

        keyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = keyFileChooser.showOpenDialog(frame);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    keyFile = keyFileChooser.getSelectedFile();
                    update();
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = saveFileChooser.showSaveDialog(frame);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    saveFile = saveFileChooser.getSelectedFile();
                    update();
                    executor.execute(encrypt);
                }
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
    }

    private class Encrypt implements Runnable {

        public void run() {
            if (!(dataFile != null && keyFile != null && dataFile.isFile() && keyFile.isFile())) {
                return;
            }
            dataButton.setEnabled(false);
            keyButton.setEnabled(false);
            keyShiftTextField.setEnabled(false);
            saveButton.setEnabled(false);
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
                    saveOs.write(dataIs.read() ^ keyIs.read());
                    saveProgressBar.setValue(i);
                    ;
                    saveProgressBar.setString((Math.round(i * 1000.0f / dataSize) / 10.0f) + "%");
                }
                saveOs.flush();
                saveOs.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            dataButton.setEnabled(true);
            keyButton.setEnabled(true);
            keyShiftTextField.setEnabled(true);
            saveButton.setEnabled(true);
            saveProgressBar.setValue(0);
            saveProgressBar.setString("0%");
        }
    }
}
