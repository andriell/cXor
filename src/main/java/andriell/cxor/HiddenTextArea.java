package andriell.cxor;

import javax.swing.*;


public class HiddenTextArea extends JTextArea {
    private HiddenString hiddenString = new HiddenString();

    public void setTextAndHide(byte[] t) {
        hiddenString.setData(t);
        super.setText(hiddenString.getStringHidden());
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
        super.setEditable(b);
    }
}
