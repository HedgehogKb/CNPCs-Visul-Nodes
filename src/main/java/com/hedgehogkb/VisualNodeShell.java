package com.hedgehogkb;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class VisualNodeShell {
    private DialogNode dialogNode;

    private int posX;
    private int posY;

    private int offsetX;
    private int offsetY;

    private int width;
    private int height;

    private boolean isBeingDragged;
    public VisualNodeShell() {
        this.dialogNode = new DialogNode();
        this.width = 100;
        this.height = 75;
        this.offsetX = 0;
        this.offsetY = 0;
        this.posX = 0;
        this.posY = 0;
        this.isBeingDragged = false;
    }

    public VisualNodeShell(int posX, int posY) {
        this.dialogNode = new DialogNode();
        this.width = 100;
        this.height = 75;
        this.offsetX = 0;
        this.offsetY = 0;
        this.posX = posX;
        this.posY = posY;
        this.isBeingDragged = false;

    }

    public VisualNodeShell(int posX, int posY, int offsetX, int offsetY) {
        this.dialogNode = new DialogNode();
        this.width = 100;
        this.height = 75;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.posX = posX;
        this.posY = posY;
        this.isBeingDragged = false;

    }

    public VisualNodeShell(int posX, int posY, int offsetX, int offsetY, DialogNode dialogNode) {
        this.dialogNode = dialogNode;
        this.width = 100;
        this.height = 75;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.posX = posX;
        this.posY = posY;
        this.isBeingDragged = false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(posX+offsetX, posY+offsetY, width, height);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(posX+offsetX, posY+offsetY, width, height);
    }

    public boolean isTouchingMouse(int mouseX, int mouseY) {
        boolean isTouching = (posX + offsetX <= mouseX && posX +offsetX + width >= mouseX) &&
                             (posY + offsetY <= mouseY && posY + offsetY + height >= mouseY);
        return isTouching;
    }

    /**
     * Sets the offset X and Y variables.
     * @param offsetX
     * @param offsetY
     */
    public void setOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * adds to the offset X and Y variables.
     * @param offsetX
     * @param offsetY
     */
    public void changeOffset(int offsetX, int offsetY) {
        this.offsetX += offsetX;
        this.offsetY += offsetY;
    }

    public void setPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void changePosition(int posX, int posY) {
        this.posX += posX;
        this.posY += posY;
    }

    public boolean getIsBeingDragged() {
        return this.isBeingDragged;
    }

    public void setIsBeingDragged(boolean isBeingDragged) {
        this.isBeingDragged = isBeingDragged;
    }


    /**
     * Returns the dialog node that is associated with the visual node shell.
     * This allows access to the change the values of the inner dialog node.
     * @return DialogNode
     */
    public DialogNode getDialogNode() {
        return dialogNode;
    }
}
