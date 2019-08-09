package andriell.cxor.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpectrumPanel extends JPanel {
    private long spectrum[] = null;
    private int sizeH = 2;
    private int sizeV = 2;
    private JPanel rootPanel;
    public static Color COLOR_BLACK;
    public static Color COLOR_GRAY;
    public static Color COLOR_RED;
    public static Color COLOR_GREEN;
    public static Font FONT;

    static {
        COLOR_BLACK = new Color(0,0,0);
        COLOR_GRAY = new Color(128,128,128);
        COLOR_RED = new Color(234,153,153);
        COLOR_GREEN = new Color(182,215,168);
        FONT = new Font("Dialog", Font.PLAIN, 11);
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

    public boolean isEmpty()
    {
        return spectrum == null || spectrum.length != 256;
    }

    public void clear() {
        this.spectrum = null;
        repaint();
    }

    public Dimension getPreferredSize() {
        int maxWidth = rootPanel.getWidth() - this.getX() - 7;
        int maxHeight = rootPanel.getHeight() - this.getY() - 7;
        int sW = maxWidth / 256;
        int sH = maxHeight / 100;
        sizeH = Math.max(1, sH);
        sizeV = Math.max(1, sW);
        return new Dimension(256 * sizeV + 2, 100 * sizeH + 2);
    }

    public void saveToFile(File file) throws IOException {
        Dimension d = getPreferredSize();
        BufferedImage bufferedImage = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        paintSpectrum(bufferedImage.createGraphics());
        ImageIO.write(bufferedImage, "png", file);
    }

    public void paintSpectrum(Graphics g) {
        g.setColor(COLOR_GRAY);
        g.fillRect(0, 0, 256 * sizeV + 2, 100 * sizeH + 2);
        if (isEmpty()) {
            g.setColor(rootPanel.getBackground());
            g.fillRect(1, 1, 256 * sizeV, 100 * sizeH);
            return;
        }
        g.setColor(COLOR_GREEN);
        g.fillRect(1, 1, 256 * sizeV, 100 * sizeH);
        g.setColor(COLOR_RED);
        long spectrumMax = 0;
        for (int i = 0; i < 256; i++) {
            spectrumMax = Math.max(spectrumMax, spectrum[i]);
        }
        int spectrumInt[] = new int[256];
        for (int i = 0; i < 256; i++) {
            spectrumInt[i] = (int) Math.round(((double) spectrum[i] / (double) spectrumMax) * 1000d);
            g.fillRect (i * sizeV + 1, 1, sizeV, (1000 - spectrumInt[i]) / 10 * sizeH);
        }
        if (sizeH < 3 || sizeV < 3) {
            return;
        }
        g.setColor(COLOR_BLACK);
        g.setFont(FONT);
        for (int i = 0; i < 16; i++) {
            g.drawString(Integer.toString(i), (i + 2) * 40, 16);
        }
        for (int i = 0; i < 16; i++) {
            g.drawString(Integer.toString(i), 40, (i + 2) * 16);
        }
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                int i = y * 16 + x;
                g.drawString(Float.toString(spectrumInt[i] / 10f), (x + 2) * 40, (y + 2) * 16);
            }
        }
    }

    protected void paintComponent(Graphics g) {
        paintSpectrum(g);
    }
}
