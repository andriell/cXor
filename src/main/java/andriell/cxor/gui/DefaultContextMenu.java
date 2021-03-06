package andriell.cxor.gui;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.*;

public class DefaultContextMenu extends JPopupMenu
{
    private JTextComponent component;

    private Clipboard clipboard;

    private UndoManager undoManager;

    private JMenuItem undo;
    private JMenuItem redo;
    private JMenuItem cut;
    private JMenuItem copy;
    private JMenuItem paste;
    private JMenuItem delete;
    private JMenuItem selectAll;

    private JTextComponent textComponent;

    public DefaultContextMenu(JTextComponent component)
    {
        this.component = component;
        undoManager = new UndoManager();
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        addPopupMenuItems();
    }

    private void addPopupMenuItems()
    {
        undo = new JMenuItem("Undo");
        Font font = new Font (Font.DIALOG, Font.PLAIN, undo.getFont().getSize());
        undo.setFont(font);
        undo.setEnabled(false);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                undoManager.undo();
            }
        });
        add(undo);

        redo = new JMenuItem("Redo");
        redo.setFont(font);
        redo.setEnabled(false);
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                undoManager.redo();
            }
        });
        add(redo);

        add(new JSeparator());

        cut = new JMenuItem("Cut");
        cut.setFont(font);
        cut.setEnabled(false);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                textComponent.cut();
            }
        });
        add(cut);

        copy = new JMenuItem("Copy");
        copy.setFont(font);
        copy.setEnabled(false);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                textComponent.copy();
            }
        });
        add(copy);

        paste = new JMenuItem("Paste");
        paste.setFont(font);
        paste.setEnabled(false);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                textComponent.paste();
            }
        });
        add(paste);

        delete = new JMenuItem("Delete");
        delete.setFont(font);
        delete.setEnabled(false);
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                textComponent.replaceSelection("");
            }
        });
        add(delete);

        add(new JSeparator());

        selectAll = new JMenuItem("Select All");
        selectAll.setFont(font);
        selectAll.setEnabled(false);
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                textComponent.selectAll();
            }
        });
        add(selectAll);
    }

    private void addTo(JTextComponent textComponent)
    {
        textComponent.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent pressedEvent)
            {
                if ((pressedEvent.getKeyCode() == KeyEvent.VK_Z)
                        && ((pressedEvent.getModifiersEx() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0))
                {
                    if (undoManager.canUndo())
                    {
                        undoManager.undo();
                    }
                }

                if ((pressedEvent.getKeyCode() == KeyEvent.VK_Y)
                        && ((pressedEvent.getModifiersEx() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0))
                {
                    if (undoManager.canRedo())
                    {
                        undoManager.redo();
                    }
                }
            }
        });

        textComponent.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent releasedEvent)
            {
                handleContextMenu(releasedEvent);
            }

            @Override
            public void mouseReleased(MouseEvent releasedEvent)
            {
                handleContextMenu(releasedEvent);
            }
        });

        textComponent.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent event) {
                undoManager.addEdit(event.getEdit());
            }
        });
    }

    private void handleContextMenu(MouseEvent releasedEvent)
    {
        if (releasedEvent.getButton() == MouseEvent.BUTTON3)
        {
            processClick(releasedEvent);
        }
    }

    private void processClick(MouseEvent event)
    {
        textComponent = (JTextComponent) event.getSource();
        textComponent.requestFocus();

        boolean enableUndo = undoManager.canUndo() && component.isEditable();
        boolean enableRedo = undoManager.canRedo() && component.isEditable();
        boolean enableCut = false;
        boolean enableCopy = false;
        boolean enablePaste = false;
        boolean enableDelete = false;
        boolean enableSelectAll = false;

        String selectedText = textComponent.getSelectedText();
        String text = textComponent.getText();

        if (text != null)
        {
            if (text.length() > 0)
            {
                enableSelectAll = true;
            }
        }

        if (selectedText != null)
        {
            if (selectedText.length() > 0)
            {
                enableCut = component.isEditable();
                enableCopy = true;
                enableDelete = component.isEditable();
            }
        }

        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor) && textComponent.isEnabled())
        {
            enablePaste = component.isEditable();
        }

        undo.setEnabled(enableUndo);
        redo.setEnabled(enableRedo);
        cut.setEnabled(enableCut);
        copy.setEnabled(enableCopy);
        paste.setEnabled(enablePaste);
        delete.setEnabled(enableDelete);
        selectAll.setEnabled(enableSelectAll);

        // Shows the popup menu
        show(textComponent, event.getX(), event.getY());
    }

    public static void addContextMenu(JTextComponent component)
    {
        DefaultContextMenu defaultContextMenu = new DefaultContextMenu(component);
        defaultContextMenu.addTo(component);
    }
}
