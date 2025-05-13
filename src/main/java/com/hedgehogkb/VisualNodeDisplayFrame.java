package com.hedgehogkb;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VisualNodeDisplayFrame {
    private JFrame frame;
    private JPanel panel;

    /**
     * visual node shells are listed in their visual order.
     * the further left in the list, the higher up in the order: 0 is on the top, size()-1 is on the bottom.
     */
    private ArrayList<VisualNodeShell> visualNodeShells;
    private HashMap<Integer, VisualNodeShell> visualNodeShellsByID = new HashMap<>();

    private MouseInputDetector mouseInputDetector;

    public VisualNodeDisplayFrame() {
        this.frame = new JFrame("Visual Node Display");
        this.panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                drawBackground(g);
                drawVisualNodeShells(g);
                drawDialogOptionLine(g);
            }
        };


        this.visualNodeShells = new ArrayList<>();
        visualNodeShells.add(new VisualNodeShell(100, 100, 0, 0, new DialogNode(1)));
        visualNodeShells.add(new VisualNodeShell(400, 300, 0, 0, new DialogNode(2)));
        
        for (int i = 0; i < visualNodeShells.size(); i++) {
            VisualNodeShell curNode = visualNodeShells.get(i);
            visualNodeShellsByID.put(curNode.getDialogNode().getDialogId(), curNode);
        }

        this.mouseInputDetector = new MouseInputDetector(visualNodeShells);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(panel);
        
        panel.addMouseMotionListener(mouseInputDetector);
        panel.addMouseListener(mouseInputDetector);
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
        for(int i = visualNodeShells.size()-1; i >=0 ; i--) {
            VisualNodeShell curNodeShell = visualNodeShells.get(i);
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
            for (int i = 0; i < visualNodeShells.size(); i++) {
                VisualNodeShell curNode = visualNodeShells.get(i);
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

        for (int i = 0; i < visualNodeShells.size(); i++) {
            DialogNode curNode = visualNodeShells.get(i).getDialogNode();
            ArrayList<DialogOption> curOptions = curNode.getOptions();
            for (int v = 0; v < curOptions.size(); v++) {
                DialogOption curOption = curOptions.get(v);
                int endDialog = curOption.getDialog();
                if (curOption.getOptionType() == 1 && !!mouseInputDetector.getDraggedOptionNode().equals(curNode) && curOption.getDialog() != -1) {
                    int startX = offsetX + visualNodeShells.get(i).getPosX() + 138;
                    int startY = offsetY + visualNodeShells.get(i).getPosY() + 10 + v*15;  
                    int endX = offsetX + visualNodeShellsByID.get(endDialog).getPosX() + 15;
                    int endY = offsetY + visualNodeShellsByID.get(endDialog).getPosY() + 70;
                    g.drawLine(startX, startY, endX, endY);

                }
            }
        }
    }


}
