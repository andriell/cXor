package andriell.cxor;

import javax.swing.*;
import java.awt.*;

public class SpectrumPanel extends JPanel {
    private long spectrum[] = null;
    private int sizeH = 2;
    private int sizeV = 2;
    private JPanel rootPanel;
    public static Color COLOR_BLACK;
    public static Color COLOR_RED;
    public static Color COLOR_GREEN;

    static {
        COLOR_BLACK = new Color(128,128,128);
        COLOR_RED = new Color(234,153,153);
        COLOR_GREEN = new Color(182,215,168);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void setRootPanel(JPanel rootPanel) {
        this.rootPanel = rootPanel;
    }

    public long[] getSpectrum() {
        return spectrum;
    }

    public void setSpectrum(long[] spectrum) {
        this.spectrum = spectrum;
    }

    public Dimension getPreferredSize() {
        int maxWidth = rootPanel.getWidth() - this.getX() - 5;
        int maxHeight = rootPanel.getHeight() - this.getY() - 5;
        int sW = maxWidth / 258;
        int sH = maxHeight / 102;
        sizeH = Math.max(1, sH);
        sizeV = Math.max(1, sW);
        return new Dimension(258 * sizeV, 102 * sizeH);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(COLOR_BLACK);
        g.fillRect(0, 0, 258 * sizeV, 102 * sizeH);
        if (spectrum == null || spectrum.length != 256) {
            g.setColor(Color.white);
            g.fillRect(sizeV, sizeH, 256 * sizeV, 100 * sizeH);
            return;
        }
        g.setColor(COLOR_GREEN);
        g.fillRect(sizeV, sizeH, 256 * sizeV, 100 * sizeH);
        g.setColor(COLOR_RED);
        long spectrumMax = 0;
        for (int i = 0; i < 256; i++) {
            spectrumMax = Math.max(spectrumMax, spectrum[i]);
        }
        for (int i = 0; i < 256; i++) {
            int p = (int) (((double) spectrum[i] / (double) spectrumMax) * 100);
            // System.out.print(spectrum[i]);
            // System.out.print(" / ");
            // System.out.print(spectrum[i]);
            // System.out.print(" = ");
            // System.out.println(p);
            g.fillRect ((i + 1) * sizeV, sizeH, sizeV, (100 - p) * sizeH);
        }
    }
}
