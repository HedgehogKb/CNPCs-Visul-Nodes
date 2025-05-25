package com.hedgehogkb.NodeDisplayFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.DialogOption;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;
import com.hedgehogkb.InputDetectors.KeyboardInputDetector;
import com.hedgehogkb.InputDetectors.MouseInputDetector;
import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.NodeHandler;


public class VisualNodeDisplayFrame {
    private JFrame frame;
    private JPanel panel;
    private VisualNodeDisplayMenuBar menuBar;

    private NodeGroup group;
    private NodeHandler nodeHandler;
    

    private MouseInputDetector mouseInputDetector;
    private KeyboardInputDetector keyboardInputDetector;

    public VisualNodeDisplayFrame(NodeGroup group) {
        this.group = group;
        this.nodeHandler = group.getNodeHandler();

        this.frame = new JFrame("Visual Node Display");
        this.menuBar = new VisualNodeDisplayMenuBar(this);
        this.frame.setJMenuBar(menuBar.getMenuBar());

        this.panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                drawBackground(g);
                drawVisualNodeShells(g);
                drawDialogOptionLine(g);
            }
        };


        nodeHandler.add(new VisualNodeShell(100, 100, 0, 0, new DialogNode(1)));
        

        this.mouseInputDetector = new MouseInputDetector(this, nodeHandler);
        this.keyboardInputDetector = new KeyboardInputDetector(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(panel);
        
        panel.addMouseMotionListener(mouseInputDetector);
        panel.addMouseListener(mouseInputDetector);
        frame.addKeyListener(keyboardInputDetector);
        panel.setBorder(BorderFactory.createEtchedBorder());
        //frame.addMouseMotionListener(mouseInputDetector);
        //frame.addMouseListener(mouseInputDetector);
        frame.setVisible(true);


    }

    public void repaint() {
        panel.repaint();
    }

    private void drawBackground(Graphics g) {
        int width = panel.getWidth();
        int height = panel.getHeight();
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, width, height);
        int gridSize = 30;
        int offsetX = mouseInputDetector.getMouseOffsetX();
        int offsetY = mouseInputDetector.getMouseOffsetY();
        int x = (offsetX%gridSize)-gridSize; 
        int y = (offsetY%gridSize)-gridSize;
        g.setColor(new Color(130, 130, 130));
        while (y < panel.getHeight() + gridSize) {
            while (x < panel.getWidth() + gridSize) {
                g.fillOval(x, y, 5, 5);
                x += gridSize;
            }
            x = (offsetX%gridSize)-gridSize; 
            y+= gridSize;
        }
    }

    private void drawVisualNodeShells(Graphics g) {
        for(int i = nodeHandler.size()-1; i >=0 ; i--) {
            VisualNodeShell curNodeShell = nodeHandler.getIndex(i);
            if (curNodeShell != null) {
                int offsetX = mouseInputDetector.getMouseOffsetX();
                int offsetY = mouseInputDetector.getMouseOffsetY();
                curNodeShell.setOffset(offsetX, offsetY);
                curNodeShell.draw(g);
            }
        }
    }

    public void drawDialogOptionLine(Graphics g) {
        if (mouseInputDetector.getIsDraggingOption()) {
            VisualNodeShell draggedNode = mouseInputDetector.getDraggedOptionNode();
            int curOptionSlot = mouseInputDetector.getDraggedOptionSlot();
           
            int mouseX = mouseInputDetector.getMouseX();
            int mouseY = mouseInputDetector.getMouseY();
            for (int i = 0; i < nodeHandler.size(); i++) {
                VisualNodeShell curNode = nodeHandler.getIndex(i);
                if (!curNode.equals(draggedNode) && curNode.isTouchingMouse(mouseX, mouseY)) {
                    curNode.drawOutline(g);
                    break;
                }
            }


            int offsetX = mouseInputDetector.getMouseOffsetX();
            int offsetY = mouseInputDetector.getMouseOffsetY();
            int startX = offsetX + draggedNode.getPosX() + 138;
            int startY = offsetY + draggedNode.getPosY() + 10 + curOptionSlot*15;
            g.setColor(Color.black);
            g.drawLine(startX, startY, mouseInputDetector.getMouseX(), mouseInputDetector.getMouseY());
        }

        int offsetX = mouseInputDetector.getMouseOffsetX();
        int offsetY = mouseInputDetector.getMouseOffsetY();

        for (int i = 0; i < nodeHandler.size(); i++) {
            DialogNode curNode = nodeHandler.getIndex(i).getDialogNode();
            ArrayList<DialogOption> curOptions = curNode.getOptions();
            for (int v = 0; v < curOptions.size(); v++) {
                DialogOption curOption = curOptions.get(v);
                int endDialog = curOption.getDialog();
                VisualNodeShell draggedNode = mouseInputDetector.getDraggedOptionNode();
                if (curOption.getOptionType() == 1 && 
                !(draggedNode != null && draggedNode.equals(nodeHandler.getIndex(i)) && v == mouseInputDetector.getDraggedOptionSlot()) && 
                curOption.getDialog() > 0) {
                    int startX = offsetX + nodeHandler.getIndex(i).getPosX() + 138;
                    int startY = offsetY + nodeHandler.getIndex(i).getPosY() + 10 + v*15;  
                    int endX = offsetX + nodeHandler.get(endDialog).getPosX() + 15;
                    int endY = offsetY + nodeHandler.get(endDialog).getPosY() + 70;
                    g.drawLine(startX, startY, endX, endY);

                }
            }
        }
    }

    public void addVisualNode() {
        DialogNode curNode = new DialogNode(nodeHandler.size()+1);
        int mouseX = mouseInputDetector.getMouseX();
        int mouseY = mouseInputDetector.getMouseY();
        int offsetX = mouseInputDetector.getMouseOffsetX();
        int offsetY = mouseInputDetector.getMouseOffsetY();

        VisualNodeShell curVisualNode = new VisualNodeShell(mouseX - offsetX, mouseY - offsetY, offsetX , offsetY, curNode);
        nodeHandler.add(curVisualNode);
    }

    public void removeVisualNode(VisualNodeShell node) {
        nodeHandler.removeVisualNode(node);
    }

    public JFrame getFrame() {
        return this.frame;
    }

}
