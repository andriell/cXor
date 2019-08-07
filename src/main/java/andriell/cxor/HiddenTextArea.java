package andriell.cxor;

import javax.swing.*;
import java.awt.*;


public class HiddenTextArea extends JTextArea {
    JScrollPane scrollPane;

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    private HiddenString hiddenString = new HiddenString();

    public void setTextAndHide(String t) {
        hiddenString.setData(t);
        super.setText(hiddenString.getStringHidden());
        setCaretPosition(0);
    }

    @Override
    public String getSelectedText() {
        if (isEditable()) {
            return super.getSelectedText();
        }
        int start = getSelectionStart();
        return hiddenString.copy(start, getSelectionEnd() - start);
    }

    @Override
    public void setEditable(boolean b) {
        Point point = null;
        if (scrollPane != null) {
            point = scrollPane.getViewport().getViewPosition();
        }

        if (b) {
            if (hiddenString != null) {
                setText(hiddenString.getString());
            }
        } else {
            if (hiddenString != null) {
                hiddenString.setData(getText());
                setText(hiddenString.getStringHidden());
            }
        }
        if (point != null) {
            SwingUtilities.invokeLater(new LaterUpdater(point));
        }
        super.setEditable(b);
    }

    class LaterUpdater implements Runnable {
        private Point point;
        LaterUpdater(Point point) {
            this.point = point;
        }
        public void run() {
            scrollPane.getViewport().setViewPosition(point);
        }
    }
}
