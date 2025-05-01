package com.hedgehogkb;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

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

    private MouseInputDetector mouseInputDetector;

    public VisualNodeDisplayFrame() {
        this.frame = new JFrame("Visual Node Display");
        this.panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                drawBackground(g);
                drawVisualNodeShells(g);
            }
        };


        this.visualNodeShells = new ArrayList<>();
        visualNodeShells.add(new VisualNodeShell(100, 100, 0, 0, new DialogNode()));
        visualNodeShells.add(new VisualNodeShell(400, 300, 0, 0, new DialogNode()));

        this.mouseInputDetector = new MouseInputDetector(visualNodeShells);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(panel);
        
        panel.addMouseMotionListener(mouseInputDetector);
        panel.addMouseListener(mouseInputDetector);
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
        g.setColor(Color.gray);
        g.fillRect(0, 0, width, height);
        int gridSize = 30;
        int offsetX = mouseInputDetector.getMouseOffsetX();
        int offsetY = mouseInputDetector.getMouseOffsetY();
        int x = (offsetX%gridSize)-gridSize; 
        int y = (offsetY%gridSize)-gridSize;
        g.setColor(new Color(50, 50, 50));
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


}
