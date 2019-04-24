package andriell.cxor;

import java.text.DecimalFormat;

public class StringHelper {
    private static long K = 1024;
    private static long M = K * K;
    private static long G = M * K;
    private static long T = G * K;
    private static String[] units = new String[]{"TB", "GB", "MB", "KB", "B"};
    private static long[] dividers = new long[]{T, G, M, K, 1};

    public static String fileSize(final long value) {
        if (value < 1) {
            return null;
        }
        for (int i = 0; i < dividers.length; i++) {
            final long divider = dividers[i];
            if (value >= divider) {
                final double result = divider > 1 ? (double) value / (double) divider : (double) value;
                return new DecimalFormat("#,##0.#").format(result) + " " + units[i];
            }
        }
        return null;
    }
}
