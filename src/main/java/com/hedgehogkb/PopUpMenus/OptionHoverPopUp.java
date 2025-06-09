package com.hedgehogkb.PopUpMenus;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.hedgehogkb.DialogNodeComponents.DialogNode;

public class OptionHoverPopUp {
    private JFrame frame;

    private JPopupMenu popupMenu;
    private JLabel text;
    private boolean popupActive;

    public OptionHoverPopUp(JFrame frame) {
        this.frame = frame;
        this.popupMenu = new JPopupMenu();

        this.text = new JLabel();
        JPanel panel = new JPanel();
        panel.add(text);
        popupMenu.add(panel);
        this.popupActive = false;
    }

    public void setDialogOption(DialogNode node, int optionId) {
        text.setText("" + optionId + " - " + trim(node.getOption(optionId).getTitle(), 100));
    }

    public void show(int posX, int posY) {
        popupMenu.show(frame, posX, posY+20);
        //popupMenu.setVisible(true); I doubt that I need this
        popupActive = true;
    }

    public void hide() {
        popupMenu.setVisible(false);
        popupActive = false;
    }

    public String trim(String text, int length) {
        return (text.length() > length) ? text.substring(0, length)+"..." : text;
    }

    public Boolean isVisible() {
        return popupActive;
    }
}
