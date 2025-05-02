package com.hedgehogkb;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DialogNodeEditorFrame {
    private DialogNode dialogNode;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel topPanel;
    
    private JButton changeColorButton;

    public DialogNodeEditorFrame(DialogNode dialogNode) {
        this.dialogNode = dialogNode;
        initializeComponents();
        buildLayout();
    }

    private void initializeComponents() {

        // Initialize JFrame
        this.frame = new JFrame("Dialog Id: "+dialogNode.getDialogId() + " - " + dialogNode.getTitle());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 500);

        //Initialize the main panel which is divided in two parts,
        this.mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Dialog Node Editor"));

        // Initialize top panel for dialog node properties
        this.topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridheight = 1;
        gbc.gridwidth = 2;

        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titleLabel = new JLabel("Title: ");
        topPanel.add(titleLabel, gbc);



        //Initialize left panel
        this.leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(4, 2));
        leftPanel.setBorder(BorderFactory.createEtchedBorder());

        //Initialize right panel
        this.rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(4, 2));
        rightPanel.setBorder(BorderFactory.createEtchedBorder());


    }

    private void buildLayout() {
        this.mainPanel.add(leftPanel, 0);
        this.mainPanel.add(rightPanel, 1);
        this.frame.add(mainPanel, BorderLayout.CENTER);
        this.frame.add(topPanel, BorderLayout.NORTH);

        this.frame.setVisible(true);
    }
}
