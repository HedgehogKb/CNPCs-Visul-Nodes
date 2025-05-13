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
    private boolean isDraggingOption;
    private int draggedOptionSlot;
    private VisualNodeShell draggedOptionNode;

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
        if (! isDraggingBackground) {
            for (int i = 0; i < visualNodeShells.size(); i++) {
                VisualNodeShell curVisualNode = visualNodeShells.get(i);

                if (curVisualNode.getIsBeingDragged()) {
                    curVisualNode.changePosition(e.getX() - mouseX, e.getY() - mouseY);
                    mouseX = e.getX();
                    mouseY = e.getY();
                    return;
                }

                if (isDraggingOption) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    return;
                }

                Boolean touching = curVisualNode.isTouchingMouse(e.getX(), e.getY());
                
                if (touching) {
                    int touchingOption = curVisualNode.isOptionTouchingMouse(e.getX(), e.getY());
                    if (touchingOption != -1 && curVisualNode.getDialogNode().getOptions().get(touchingOption).getOptionType() == 1) {
                        draggedOptionSlot = touchingOption;
                        isDraggingOption = true;
                        this.draggedOptionNode = curVisualNode;
                    } else {
                        curVisualNode.setIsBeingDragged(true);
                    }
                        visualNodeShells.add(0, visualNodeShells.remove(i)); // Move the dragged node to the front of the list

                    mouseX = e.getX();
                    mouseY = e.getY();
                    return;
                }
            }
        }
        this.isDraggingBackground = true;   
        offsetX += e.getX() - mouseX;
        offsetY += e.getY() - mouseY;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < visualNodeShells.size(); i++) {
            VisualNodeShell curVisualNode = visualNodeShells.get(i);
            if (curVisualNode.isTouchingMouse(e.getX(), e.getY()) && !curVisualNode.getIsBeingDragged() && !isDraggingBackground && !isDraggingOption) {
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
        this.isDraggingBackground = false;

        if (isDraggingOption) {
            for (int i = 0; i < visualNodeShells.size(); i++) {
                VisualNodeShell curNode = visualNodeShells.get(i);
                if (!curNode.equals(draggedOptionNode) && curNode.isTouchingMouse(mouseX, mouseY)) {
                    int linkedNodeId = curNode.getDialogNode().getDialogId();
                    draggedOptionNode.getDialogNode().getOptions().get(draggedOptionSlot).setDialog(linkedNodeId);
                    break;
                }
            }
            this.draggedOptionSlot = 0;
            this.isDraggingOption = false;
            this.draggedOptionNode = null;
        }

        for (int i = 0; i < visualNodeShells.size(); i++) {
            VisualNodeShell curNode = visualNodeShells.get(i);
            curNode.setIsBeingDragged(false);
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

    public boolean getIsDraggingOption() {
        return this.isDraggingOption;
    }

    public int getDraggedOptionSlot() {
        return this.draggedOptionSlot;
    }

    public VisualNodeShell getDraggedOptionNode() {
        return this.draggedOptionNode;
    }
}
