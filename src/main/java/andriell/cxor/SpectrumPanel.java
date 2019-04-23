package andriell.cxor;

import javax.swing.*;
import java.awt.*;

public class SpectrumPanel extends JPanel {
    private long spectrum[] = null;
    private int size = 2;
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
        int maxWidth = rootPanel.getWidth() - this.getX() - 10;
        int maxHeight = rootPanel.getHeight() - this.getY() - 10;
        int sW = maxWidth / 258;
        int sH = maxHeight / 102;
        size = Math.max(1, Math.min(sW, sH));
        return new Dimension(258 * size, 102 * size);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(COLOR_BLACK);
        g.fillRect(0, 0, 258 * size, 102 * size);
        if (spectrum == null || spectrum.length != 256) {
            g.setColor(Color.white);
            g.fillRect(size, size, 256 * size, 100 * size);
            return;
        }
        g.setColor(COLOR_GREEN);
        g.fillRect(size, size, 256 * size, 100 * size);
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
            g.fillRect ((i + 1) * size, size, size, (100 - p) * size);
        }
    }
}
