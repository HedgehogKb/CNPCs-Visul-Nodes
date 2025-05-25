package com.hedgehogkb.InputDetectors;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.hedgehogkb.NodeHandler;
import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;
import com.hedgehogkb.EditorFrame.DialogNodeEditorFrame;
import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayFrame;
import com.hedgehogkb.PopUpMenus.VisualNodePopUp;


public class MouseInputDetector implements MouseMotionListener, MouseListener {
    private int mouseX;
    private int mouseY;
    private int offsetX;
    private int offsetY;

    private VisualNodePopUp visualNodePopUp;

    @SuppressWarnings("FieldMayBeFinal")
    private VisualNodeDisplayFrame visualNodeDisplay;
    private NodeHandler nodeHandler;
    private boolean mouseDown;
    private boolean isDraggingBackground;
    private boolean isDraggingOption;
    private int draggedOptionSlot;
    private VisualNodeShell draggedOptionNode;
    private ArrayList<DialogNodeEditorFrame> dialogNodeEditorFrames;

    public MouseInputDetector(NodeHandler nodeHandler) {
        this.mouseX = 0;
        this.mouseY = 0;
        this.nodeHandler = nodeHandler;
        this.isDraggingBackground = false;
        this.mouseDown = false;
        this.dialogNodeEditorFrames = new ArrayList<>();
        this.visualNodePopUp = new VisualNodePopUp();
    }

    public MouseInputDetector(VisualNodeDisplayFrame visualNodeDisplay, NodeHandler nodeHandler) {
        this(nodeHandler);
        this.visualNodeDisplay = visualNodeDisplay;
        this.visualNodePopUp = new VisualNodePopUp(visualNodeDisplay);
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
            for (int i = 0; i < nodeHandler.size(); i++) {
                VisualNodeShell curVisualNode = nodeHandler.getIndex(i);

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
                    nodeHandler.moveToFront(curVisualNode);

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
        for (int i = 0; i < nodeHandler.size(); i++) {
            VisualNodeShell curVisualNode = nodeHandler.getIndex(i);
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (curVisualNode.isTouchingMouse(e.getX(), e.getY()) && !curVisualNode.getIsBeingDragged() && !isDraggingBackground && !isDraggingOption) {
                    int touchingOption = curVisualNode.isOptionTouchingMouse(e.getX(), e.getY());
                    if (touchingOption != -1) {
                        curVisualNode.getDialogNode().getOptions().get(touchingOption).toggleOptionType();
                        updateEditorFrameValues(curVisualNode.getDialogNode());
                    } else {
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
                    }
                return;
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                if (curVisualNode.isTouchingMouse(e.getX(), e.getY()) && !curVisualNode.getIsBeingDragged() && !isDraggingBackground && !isDraggingOption) {
                    visualNodePopUp.setVisualNodeShell(curVisualNode);
                    visualNodePopUp.showPopUp(visualNodeDisplay.getFrame(), e.getX(), e.getY());
                    return;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
        this.isDraggingBackground = false;

        if (isDraggingOption) {
            for (int i = 0; i < nodeHandler.size(); i++) {
                VisualNodeShell curNode = nodeHandler.getIndex(i);
                if (!curNode.equals(draggedOptionNode) && curNode.isTouchingMouse(mouseX, mouseY)) {
                    int linkedNodeId = curNode.getDialogNode().getDialogId();
                    draggedOptionNode.getDialogNode().getOptions().get(draggedOptionSlot).setDialog(linkedNodeId);
                    updateEditorFrameValues(draggedOptionNode.getDialogNode());
                    break;
                }
                if (i == nodeHandler.size() -1) {
                    draggedOptionNode.getDialogNode().getOptions().get(draggedOptionSlot).setDialog(-1);
                    updateEditorFrameValues(draggedOptionNode.getDialogNode());
                }
            }

            this.draggedOptionSlot = 0;
            this.isDraggingOption = false;
            this.draggedOptionNode = null;
        }

        for (int i = 0; i < nodeHandler.size(); i++) {
            VisualNodeShell curNode = nodeHandler.getIndex(i);
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
