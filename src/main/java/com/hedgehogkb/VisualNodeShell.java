package com.hedgehogkb;

import java.awt.Color;
import java.awt.Dialog;
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

    public VisualNodeShell() {
        this.dialogNode = new DialogNode();
        this.width = 100;
        this.height = 75;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(posX+offsetX, posY+offsetY, width, height);
    }
}
