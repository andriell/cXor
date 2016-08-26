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

    private JFrame frame;
    private JFileChooser fileChooser1;
    private JFileChooser fileChooser2;
    private JFileChooser fileChooser3;
    private File file1;
    private File file2;


    public void show() {
        frame = new JFrame("Crypto xor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(rootPane);

        frame.setSize(300, 150);
        frame.setResizable(false);
        frame.setVisible(true);

        fileChooser1 = new JFileChooser();
        fileChooser2 = new JFileChooser();
        fileChooser3 = new JFileChooser();

        file1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = fileChooser1.showOpenDialog(frame);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    file1 = fileChooser1.getSelectedFile();
                    label1.setText(file1.getName());
                }
            }
        });

        file2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = fileChooser2.showOpenDialog(frame);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    file2 = fileChooser2.getSelectedFile();
                    label2.setText(file2.getName());
                }
            }
        });


        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = fileChooser3.showSaveDialog(frame);
                if (ret == JFileChooser.APPROVE_OPTION) {

                }
            }
        });

    }
}
