package com.hedgehogkb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DialogNodeEditorFrame {
    private DialogNode dialogNode;
    private JFrame frame;

    public DialogNodeEditorFrame(DialogNode dialogNode) {
        this.dialogNode = dialogNode;

        this.frame = new JFrame("Dialog "+dialogNode.getTitle() + " - Id:" + dialogNode.getDialogId());
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 500);
        this.frame.setResizable(false);
        this.frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0,2));
        mainPanel.setBackground(Color.GREEN);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(0,2));
        leftPanel.setBackground(Color.RED);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLUE);
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.YELLOW);
        topPanel.setLayout(new GridLayout(0, 2));
        topPanel.setPreferredSize(new Dimension(800, 50));
        topPanel.add(new Label("Title: " + dialogNode.getTitle()), BorderLayout.WEST);
        topPanel.add(new Label(dialogNode.getTitle() + "Title: "), BorderLayout.AFTER_LINE_ENDS);

        
        //rightPanel.setLayout();



        this.frame.add(mainPanel, BorderLayout.CENTER);
        this.frame.add(topPanel, BorderLayout.NORTH);
        //mainPanel.getLayout().addLayoutComponent(null, rightPanel);
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        this.frame.setVisible(true);
    }
}
