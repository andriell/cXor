package andriell.cxor;

import javax.swing.*;
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

        GuiFileFile guiFileFile = new GuiFileFile();
        guiFileFile.init();
        rootTabbedPane.addTab("File", guiFileFile.getRootPane());

        frame.setSize(400, 210);
        //frame.setResizable(false);
        frame.setVisible(true);
    }
}
