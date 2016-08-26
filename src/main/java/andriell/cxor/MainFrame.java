package andriell.cxor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Rybalko on 26.08.2016.
 */
public class MainFrame {
    private JButton file1Button;
    private JButton file2Button;
    private JButton goButton;
    private JProgressBar progressBar1;
    private JPanel rootPane;
    private JLabel label1;
    private JLabel label2;

    private JFileChooser fileOpen1;
    private JFileChooser fileOpen2;
    private File file1;
    private File file2;

    public void show() {
        JFrame frame = new JFrame("Crypto xor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(rootPane);

        frame.setSize(300, 150);
        frame.setResizable(false);
        frame.setVisible(true);

        fileOpen1 = new JFileChooser();
        fileOpen2 = new JFileChooser();

        file1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = fileOpen1.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    file1 = fileOpen1.getSelectedFile();
                    label1.setText(file1.getName());
                }
            }
        });

        file2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = fileOpen2.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    file2 = fileOpen2.getSelectedFile();
                    label2.setText(file2.getName());
                }
            }
        });
    }
}
