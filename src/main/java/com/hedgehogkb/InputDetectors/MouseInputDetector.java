package com.hedgehogkb.InputDetectors;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;
import com.hedgehogkb.EditorPanels.DialogNodeEditorFrame;


public class MouseInputDetector implements MouseMotionListener, MouseListener {
    private int mouseX;
    private int mouseY;
    private int offsetX;
    private int offsetY;

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<VisualNodeShell> visualNodeShells;
    private boolean mouseDown;
    private boolean isDraggingBackground;
    private boolean isDraggingOption;
    private int draggedOptionSlot;
    private VisualNodeShell draggedOptionNode;
    private ArrayList<DialogNodeEditorFrame> dialogNodeEditorFrames;

    public MouseInputDetector(ArrayList<VisualNodeShell> visualNodeShells) {
        this.mouseX = 0;
        this.mouseY = 0;
        this.visualNodeShells = visualNodeShells;
        this.isDraggingBackground = false;
        this.mouseDown = false;
        this.dialogNodeEditorFrames = new ArrayList<>();
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        if (!mouseDown) {
            this.mouseX = e.getX();
            this.mouseY = e.getY();
        }
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
        mouseDown = true;
        for (int i = 0; i < visualNodeShells.size(); i++) {
            VisualNodeShell curVisualNode = visualNodeShells.get(i);
            if (curVisualNode.isTouchingMouse(e.getX(), e.getY()) && !curVisualNode.getIsBeingDragged() && !isDraggingBackground && !isDraggingOption) {
                SwingUtilities.invokeLater(() -> {
                    if (!editorExistsForNode(curVisualNode.getDialogNode())) {
                        dialogNodeEditorFrames.add(new DialogNodeEditorFrame(curVisualNode.getDialogNode()));
                    } else {
                        try {
                            DialogNodeEditorFrame curEditorFrame = getEditorFrameForDialogNode(curVisualNode.getDialogNode());
                            curEditorFrame.setVisible(true);
                            curEditorFrame.moveFrameToTop();
                        } catch (Exception ex) {}
                    }
                });
                
                return;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
        this.isDraggingBackground = false;

        if (isDraggingOption) {
            for (int i = 0; i < visualNodeShells.size(); i++) {
                VisualNodeShell curNode = visualNodeShells.get(i);
                if (!curNode.equals(draggedOptionNode) && curNode.isTouchingMouse(mouseX, mouseY)) {
                    int linkedNodeId = curNode.getDialogNode().getDialogId();
                    draggedOptionNode.getDialogNode().getOptions().get(draggedOptionSlot).setDialog(linkedNodeId);
                    updateEditorFrameValues(draggedOptionNode.getDialogNode());
                    break;
                }
                if (i == visualNodeShells.size() -1) {
                    draggedOptionNode.getDialogNode().getOptions().get(draggedOptionSlot).setDialog(-1);
                    updateEditorFrameValues(draggedOptionNode.getDialogNode());
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

    public void updateEditorFrameValues(DialogNode changedNode) {
        for (int i = dialogNodeEditorFrames.size() -1; i >= 0; i--) {
            DialogNodeEditorFrame curFrame = dialogNodeEditorFrames.get(i);
            if (curFrame == null) {
                dialogNodeEditorFrames.remove(i);
            } else {
                if (curFrame.getDialogOptionsPanel().getDialogNode() == changedNode) {
                    curFrame.getDialogOptionsPanel().updateDialogValue();
                }
            }
        }
    }

    public boolean editorExistsForNode(DialogNode node) {
        for (int i = dialogNodeEditorFrames.size() -1; i >= 0; i--) {
            if (node.equals(dialogNodeEditorFrames.get(i).getDialogNode())) {
                return true;
            }
        }
        return false;
    }

    public boolean editorIsVisible(DialogNode node) {
        try {
        if (getEditorFrameForDialogNode(node).isVisible()) {
            return true;
        }
        } catch (Exception e) {}
        return false;
    }


    public DialogNodeEditorFrame getEditorFrameForDialogNode(DialogNode node) throws Exception {
        for (int i = dialogNodeEditorFrames.size() -1; i >= 0; i--) {
            if (node.equals(dialogNodeEditorFrames.get(i).getDialogNode())) {
                return dialogNodeEditorFrames.get(i);
            }
        }
        throw new Exception();
    }

    //getters and setters

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
