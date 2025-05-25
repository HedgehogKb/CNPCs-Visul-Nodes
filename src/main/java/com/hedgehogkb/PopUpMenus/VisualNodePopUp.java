package com.hedgehogkb.PopUpMenus;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.hedgehogkb.VisualNodeDisplayFrame;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;

public class VisualNodePopUp {
    private JPopupMenu visualNodePopUp;
    private JMenuItem deleteNodeButton;    
    private VisualNodeShell visualNodeShell;
    private VisualNodeDisplayFrame visualNodeDisplay;

    public VisualNodePopUp() {
        this.visualNodePopUp = new JPopupMenu();
        initializePopUp();
        handleMenuInputs();
    }

    public VisualNodePopUp(VisualNodeDisplayFrame visualNodeDisplay) {
        this();
        this.visualNodeDisplay = visualNodeDisplay;
    }

    private void initializePopUp() {
        this.deleteNodeButton = new JMenuItem("Delete Node");
        this.visualNodePopUp.add(deleteNodeButton);
    }

    public void showPopUp(JFrame invoker, int x, int y) {
        this.visualNodePopUp.show(invoker, x+10, y+35);
    }

    public void handleMenuInputs() {
        deleteNodeButton.addActionListener(e -> {
            visualNodeDisplay.removeVisualNode(this.visualNodeShell);
        });
    }

    public void setVisualNodeShell(VisualNodeShell visualNodeShell) {
        this.visualNodeShell = visualNodeShell;
    }
}
