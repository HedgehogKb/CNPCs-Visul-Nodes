package com.hedgehogkb;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class MouseInputDetector implements MouseMotionListener, MouseListener {
    private int mouseX;
    private int mouseY;
    private int offsetX;
    private int offsetY;

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<VisualNodeShell> visualNodeShells;
    private boolean isDraggingBackground;
    private boolean isDraggingOptionNode;
    private DialogOption draggedOptionNode;
    private int draggedOptionNodeSlot;

    public MouseInputDetector(ArrayList<VisualNodeShell> visualNodeShells) {
        this.mouseX = 0;
        this.mouseY = 0;
        this.visualNodeShells = visualNodeShells;
        this.isDraggingBackground = false;
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
            Boolean touching = curVisualNode.isTouchingMouse(e.getX(), e.getY());
            if ((touching || curVisualNode.getIsBeingDragged()) && !isDraggingBackground) {
                if (!curVisualNode.getIsBeingDragged()) {
                    int curIsDragginOption = curVisualNode.isOptionTouchingMouse(e.getX(), e.getY());
                    if (curIsDragginOption != -1) {
                        this.isDraggingOptionNode = true;
                        this.draggedOptionNode = curVisualNode.getDialogNode().getOptions().get(curIsDragginOption);
                        this.draggedOptionNodeSlot = curIsDragginOption;
                        curVisualNode.setIsBeingDragged(false);
                    } else {
                        curVisualNode.setIsBeingDragged(true);
                        curVisualNode.changePosition(e.getX() - this.mouseX, e.getY() - this.mouseY);

                    }
                }
                this.isDraggingBackground = false;
                visualNodeShells.add(0, visualNodeShells.remove(i)); // Move the dragged node to the front of the list
                this.mouseX = e.getX();
                this.mouseY = e.getY();
                return;
            }
        }

        this.isDraggingBackground = true;

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
            if (curVisualNode.isTouchingMouse(e.getX(), e.getY()) && !curVisualNode.getIsBeingDragged() && !isDraggingBackground) {
                SwingUtilities.invokeLater(() -> {
                    DialogNodeEditorFrame dialogNodeEditorFrame = new DialogNodeEditorFrame(curVisualNode.getDialogNode());
                });
                
                return;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.isDraggingOptionNode) {
            isDraggingOptionNode = false;
        }
        for (int i = 0; i < visualNodeShells.size(); i++) {
            visualNodeShells.get(i).setIsBeingDragged(false);
            this.isDraggingBackground = false;
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

    public int getMouseX() {
        return this.mouseX;
    }
    public int getMouseY() {
        return this.mouseY;
    }

    public boolean getIsDraggingOptionNode() {
        return this.isDraggingOptionNode;
    }

    public DialogOption getDraggedOptionNode() {
        return this.draggedOptionNode;
    }

    public int getDraggedOptionNodeSlot() {
        return this.draggedOptionNodeSlot;
    }

}
