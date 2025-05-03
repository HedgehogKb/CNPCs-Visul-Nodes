package com.hedgehogkb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class DialogNodeEditorFrame {
    private DialogNode dialogNode;
    private JFrame frame;
    private JPanel mainPanel;
    
    //left panel components
    private JPanel leftPanel;
    private JTextArea dialogTextBox;
    private JButton availabilityOptionsButton;
    private JButton factionOptionsButton;
    private JButton dialogOptionsButton;

    private JPanel rightPanel;

    //top panel components
    private JPanel topPanel;
    private JTextArea titleTextBox;

    
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
        frame.setSize(600, 400);
        frame.setMinimumSize(new Dimension(350, 300));

        //Initialize the main panel which is divided in two parts,
        this.mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2));
        //mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Dialog Node Editor"));

        // Initialize top panel for dialog node properties
        this.topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        topPanel.add(new JLabel("Dialog Title: "), BorderLayout.WEST);
        titleTextBox = new JTextArea(dialogNode.getTitle());
        topPanel.add(titleTextBox, BorderLayout.CENTER);
        titleTextBox.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        topPanel.add(new JLabel("Dialog Id: "+dialogNode.getDialogId()), BorderLayout.EAST);



        //Initialize left panel
        this.leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createEtchedBorder());

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHEAST;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 10, 0, 0);
        leftPanel.add(new JLabel("Dialog Text: "), c);

        dialogTextBox = new JTextArea("testing Text:");
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.5;
        c.ipady = 40;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.gridwidth = 3;
        c.insets = new Insets(3, 10, 0, 10);
        dialogTextBox.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        leftPanel.add(dialogTextBox, c);

        availabilityOptionsButton = new JButton("Availability Options");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        //c.gridheight = 1;
        c.weighty = 0.0;
        c.ipady = 12;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.SOUTH;
        c.insets = new Insets(10, 10, 0, 10);
        leftPanel.add(availabilityOptionsButton, c);

        factionOptionsButton = new JButton("Faction Options");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        //c.gridheight = 1;
        //c.weighty = 1;
        c.ipady = 12;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.SOUTH;
        c.insets = new Insets(10, 10, 0, 10);
        leftPanel.add(factionOptionsButton, c);

        dialogOptionsButton = new JButton("Faction Options");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 3;
        //c.gridheight = 1;
        //c.weighty = 1;
        c.ipady = 12;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10, 10, 10, 10);
        leftPanel.add(dialogOptionsButton, c);


        //Initialize right panel
        this.rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(4, 2));
        rightPanel.setBorder(BorderFactory.createEtchedBorder());


    }

    private void buildLayout() {
        this.mainPanel.add(leftPanel, 0);
        this.mainPanel.add(rightPanel, 1);
        this.frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        //this.frame.pack();

        this.frame.setVisible(true);
    }
}
