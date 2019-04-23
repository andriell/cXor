package andriell.cxor;

import javax.swing.*;
import java.awt.*;

public class SpectrumPanel extends JPanel {
    private long spectrum[] = null;
    private int size = 2;

    public long[] getSpectrum() {
        return spectrum;
    }

    public void setSpectrum(long[] spectrum) {
        this.spectrum = spectrum;
    }

    public Dimension getPreferredSize() {
        return new Dimension(258 * size, 102 * size);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 258 * size, 102 * size);
        if (spectrum == null || spectrum.length != 256) {
            g.setColor(Color.white);
            g.fillRect(size, size, 256 * size, 100 * size);
            return;
        }
        g.setColor(Color.green);
        g.fillRect(size, size, 256 * size, 100 * size);
        g.setColor(Color.red);
        long spectrumMax = 0;
        for (int i = 0; i < 256; i++) {
            spectrumMax = Math.max(spectrumMax, spectrum[i]);
        }
        for (int i = 0; i < 256; i++) {
            int p = (int) (((double) spectrum[i] / (double) spectrumMax) * 100);
            System.out.print(spectrum[i]);
            System.out.print(" / ");
            System.out.print(spectrum[i]);
            System.out.print(" = ");
            System.out.println(p);
            g.fillRect ((i + 1) * size, size, size, (100 - p) * size);
        }
    }
}
