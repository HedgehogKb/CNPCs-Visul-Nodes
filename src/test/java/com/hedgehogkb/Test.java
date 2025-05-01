package com.hedgehogkb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {

    /**
     * Default constructor for Test.class
     */
    public Test() {
        initComponents();
    }

    public static void main(String[] args) {

        /**
         * Create GUI and components on Event-Dispatch-Thread
         */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Test test = new Test();
            }
        });
    }

    /**
     * Initialize GUI and components (including ActionListeners etc)
     */
    private void initComponents() {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.add(new MyPanel());
        jFrame.setSize(400, 400);

        //pack frame (size JFrame to match preferred sizes of added components and set visible
        //jFrame.pack();
        jFrame.setVisible(true);
    }

    protected class MyPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int[] x = new int[]{65, 122, 77, 20};
            int[] y = new int[]{226, 258, 341, 310};
            g.setColor(Color.RED);
            g.drawPolygon(x, y, x.length);
        }
    
        //so our panel is the corerct size when pack() is called on Jframe
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }
    }
}


