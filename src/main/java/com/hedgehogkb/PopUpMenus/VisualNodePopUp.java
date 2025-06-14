package com.hedgehogkb.PopUpMenus;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.hedgehogkb.DialogNodeComponents.GroupNodeShell;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;
import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayFrame;

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
            visualNodeDisplay.setProjectUnsaved();
            if (visualNodeShell instanceof GroupNodeShell) {
                visualNodeDisplay.removeVisualNode(visualNodeShell);
            } else {
                visualNodeDisplay.getGroup().getProjectInfo().removeVisualNode(this.visualNodeShell.getDialogId());
            }
        });
    }

    public void setVisualNodeShell(VisualNodeShell visualNodeShell) {
        this.visualNodeShell = visualNodeShell;
    }
}
