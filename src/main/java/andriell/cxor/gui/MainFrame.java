package andriell.cxor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

/**
 * Created by Rybalko on 30.08.2016.
 */
public class MainFrame {
    private JTabbedPane rootTabbedPane;
    private JPanel rootPane;

    private JFrame frame;
    private File dataFile;

    public void init() {
        frame = new JFrame("Crypto XOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/keys-access.png"));
        frame.setIconImage(icon.getImage());

        frame.setContentPane(rootPane);

        GuiFileNotepad guiFilePassword = new GuiFileNotepad();
        guiFilePassword.setDataFile(dataFile);
        guiFilePassword.init();
        rootTabbedPane.addTab("Notepad", guiFilePassword.getRootPane());

        GuiFileFile guiFileFile = new GuiFileFile();
        guiFileFile.init();
        rootTabbedPane.addTab("File", guiFileFile.getRootPane());

        Rectangle bounds = (Rectangle) Preferences.getSerializable(Preferences.LAST_USED_BOUNDS, new Rectangle(0, 0, 600, 400));
        frame.setBounds(bounds);
        frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                Preferences.putSerializable(Preferences.LAST_USED_BOUNDS, frame.getBounds());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                Preferences.putSerializable(Preferences.LAST_USED_BOUNDS, frame.getBounds());
            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        frame.setVisible(true);
    }

    public File getDataFile() {
        return dataFile;
    }

    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }
}
