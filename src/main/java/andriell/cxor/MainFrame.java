package andriell.cxor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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


    public void show() {
        frame = new JFrame("Crypto xor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(rootPane);

        frame.setSize(300, 180);
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
        saveProgressBar.setMinimum(0);
        saveProgressBar.setMaximum(10);
        saveProgressBar.setValue(0);
        saveProgressBar.setString("0 %");

        saveButton.setEnabled(dataFile != null && keyFile != null);
    }
}
