package andriell.cxor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Rybalko on 30.08.2016.
 */
public class MainFrame {
    private JTabbedPane rootTabbedPane;
    private JPanel rootPane;

    private JFrame frame;

    public void init() {
        frame = new JFrame("Crypto XOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(rootPane);

        GuiFilePassword guiFilePassword = new GuiFilePassword();
        guiFilePassword.init();
        rootTabbedPane.addTab("Password", guiFilePassword.getRootPane());

        GuiFileFile guiFileFile = new GuiFileFile();
        guiFileFile.init();
        rootTabbedPane.addTab("File", guiFileFile.getRootPane());

        rootTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int i = rootTabbedPane.getSelectedIndex();
                if (i == 0) {
                    frame.setSize(600, 400);
                } else if (i == 1) {
                    frame.setSize(400, 210);
                }
            }
        });

        frame.setSize(600, 400);
        //frame.setResizable(false);
        frame.setVisible(true);
    }
}
