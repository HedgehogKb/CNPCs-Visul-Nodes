package com.hedgehogkb;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MouseInputDetector implements MouseMotionListener, MouseListener {
    private int mouseX;
    private int mouseY;
    private int offsetX;
    private int offsetY;
    private ArrayList<VisualNodeShell> visualNodeShells;

    public MouseInputDetector(ArrayList<VisualNodeShell> visualNodeShells) {
        this.mouseX = 0;
        this.mouseY = 0;
        this.visualNodeShells = visualNodeShells;
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
        for (int i = 0; i < visualNodeShells.size(); i++) {
            VisualNodeShell curVisualNode = visualNodeShells.get(i);
            if (curVisualNode.isTouchingMouse(e.getX(), e.getY()) || curVisualNode.getIsBeingDragged()) {
                curVisualNode.setIsBeingDragged(true);
                curVisualNode.changePosition(e.getX() - this.mouseX, e.getY() - this.mouseY);

                this.mouseX = e.getX();
                this.mouseY = e.getY();
                return;
            }
        }

        this.offsetX += e.getX() - this.mouseX;
        this.offsetY += e.getY() - this.mouseY;
        
        this.mouseX = e.getX();
        this.mouseY = e.getY();
        //System.out.println("Mouse Offset: " + offsetX + ", " + offsetY);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < visualNodeShells.size(); i++) {
            VisualNodeShell curVisualNode = visualNodeShells.get(i);
            System.err.println("is touching" + curVisualNode.isTouchingMouse(e.getX(), e.getY()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < visualNodeShells.size(); i++) {
            visualNodeShells.get(i).setIsBeingDragged(false);
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getMouseOffsetX() {
        return this.offsetX;
    }

    public int getMouseOffsetY() {
        return this.offsetY;
    }
    
    
}
