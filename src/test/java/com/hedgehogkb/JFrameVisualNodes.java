package com.hedgehogkb;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

public class JFrameVisualNodes {
    private JFrame frame;
    private JPanel panel;
    private Graphics g;
    private final int WIDTH = 800;
    private final int HEIGHT = 500;
    private Color color = Color.RED;

    public JFrameVisualNodes() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("DialogNode Visualizer");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(this.WIDTH, HEIGHT);
        this.frame.setTitle("Test Dialog Node Visualizer");
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(new BorderLayout(10,5));

        this.panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.fillRect(0, 0, 800, 500 );

                VisualNodeShell node = new VisualNodeShell();
                node.draw(g);
            }
        };

        //this.panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        //panel.setSize(new Dimension(WIDTH, HEIGHT));
        this.frame.add(panel);

        //this.frame.pack();
        this.frame.setVisible(true);



    }

    public void repaint() {
        this.panel.repaint();
    }

    public void changeColor() {
        this.color = new Color((int) (Math.random() * 255),(int) (Math.random() * 255),(int) (Math.random() * 255));
    }

}
