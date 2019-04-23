package andriell.cxor;

import javax.swing.*;
import java.awt.*;

public class SpectrumPanel extends JPanel {
    private long spectrum[] = null;

    public long[] getSpectrum() {
        return spectrum;
    }

    public void setSpectrum(long[] spectrum) {
        this.spectrum = spectrum;
    }

    public Dimension getPreferredSize() {
        return new Dimension(258, 101);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(0, 0, 257, 101);
        if (spectrum == null || spectrum.length != 256) {
            g.setColor(Color.white);
            g.fillRect(1, 1, 256, 100);
            return;
        }
        g.setColor(Color.red);
        g.fillRect(1, 1, 256, 100);
        g.setColor(Color.green);
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
            g.drawLine (i + 1, 100, i + 1, 101 - p);
        }
    }
}
