package com.hedgehogkb;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
        this.width = 150;
        this.height = 100;
        this.offsetX = 0;
        this.offsetY = 0;
        this.posX = 0;
        this.posY = 0;
        this.isBeingDragged = false;
    }

    public VisualNodeShell(int posX, int posY) {
        this.dialogNode = new DialogNode();
        this.width = 150;
        this.height = 100;
        this.offsetX = 0;
        this.offsetY = 0;
        this.posX = posX;
        this.posY = posY;
        this.isBeingDragged = false;

    }

    public VisualNodeShell(int posX, int posY, int offsetX, int offsetY) {
        this.dialogNode = new DialogNode();
        this.width = 150;
        this.height = 100;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.posX = posX;
        this.posY = posY;
        this.isBeingDragged = false;

    }

    public VisualNodeShell(int posX, int posY, int offsetX, int offsetY, DialogNode dialogNode) {
        this.dialogNode = dialogNode;
        this.width = 150;
        this.height = 100;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.posX = posX;
        this.posY = posY;
        this.isBeingDragged = false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int xScreenPos = posX + offsetX;;
        int yScreenPos = posY + offsetY;

        int colorValue = 100;
        g2d.setColor(new Color(colorValue, colorValue, colorValue));
        g2d.fillRect(xScreenPos, yScreenPos, width, height);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(xScreenPos+3, yScreenPos+3, width-6, height-6);

        g2d.setStroke(new BasicStroke(2));

        for(int i = 0 ; i < 6; i++) {
            if (dialogNode.getOptions().get(i).getOptionType() == 1) {
                g2d.fillRect(xScreenPos + 133, yScreenPos + 5 + i*15, 11, 11);
            } else {
                g2d.drawRect(xScreenPos + 133, yScreenPos + 5 + i*15, 11, 11);

            }
        }
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        g2d.drawString(dialogNode.getDialogTitle(), xScreenPos+10, yScreenPos+22);
        g2d.drawString("Id: " + dialogNode.getDialogId(), xScreenPos+10, yScreenPos+44);
    }

    public boolean isTouchingMouse(int mouseX, int mouseY) {
        boolean isTouching = (posX + offsetX <= mouseX && posX +offsetX + width >= mouseX) &&
                             (posY + offsetY <= mouseY && posY + offsetY + height >= mouseY);
        return isTouching;
    }

    public int isOptionTouchingMouse(int mouseX, int mouseY) {
        for (int i = 0; i < 6; i++) {
            int minX = posX + offsetX + 133;
            int maxX = posX + offsetX + 144;
            int minY = posY + offsetY + 5 + i * 15;
            int maxY = posY + offsetY + 16 + i * 15;
            if (minX <= mouseX && maxX >= mouseX && minY <= mouseY && maxY >= mouseY) {
                return i;
            }
        }
        return -1;
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
     * adds to the parameters to the X offset and Y offset variables.
     * @param offsetX
     * @param offsetY
     */
    public void changeOffset(int offsetX, int offsetY) {
        this.offsetX += offsetX;
        this.offsetY += offsetY;
    }

    /**
     * sets position of the X position and Y position variables.
     * @param posX
     * @param posY
     */
    public void setPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * add the parameters to the X position and Y position variables.
     * @param posX
     * @param posY
     */
    public void changePosition(int posX, int posY) {
        this.posX += posX;
        this.posY += posY;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public boolean getIsBeingDragged() {
        return this.isBeingDragged;
    }

    public void setIsBeingDragged(boolean isBeingDragged) {
        this.isBeingDragged = isBeingDragged;
    }

    public int getDialogId() {
        return dialogNode.getDialogId();
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
