package com.hedgehogkb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.SpinnerDateModel;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.tools.ToolProvider;


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
    private JButton confirmTitleButton;
    private JButton cancelTitleButton;

    

    public DialogNodeEditorFrame(DialogNode dialogNode) {
        this.dialogNode = dialogNode;
        initializeComponents();
        buildLayout();
        handleInputs();
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
        SpringLayout springLayout = new SpringLayout();
        topPanel.setLayout(springLayout);
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        topPanel.setPreferredSize(new Dimension(350, 40));

        JLabel titleLabel = new JLabel("Dialog Title: ");
        topPanel.add(titleLabel);

        titleTextBox = new JTextArea(dialogNode.getTitle());
        titleTextBox.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        
        titleTextBox.setPreferredSize(new Dimension(200, 25));
        topPanel.add(titleTextBox);
        
        confirmTitleButton = new JButton("Confirm Title"); 
        topPanel.add(confirmTitleButton);

        cancelTitleButton = new JButton("Cancel Title");
        topPanel.add(cancelTitleButton);

        JTextArea dialogIdLabel = new JTextArea("Dialog Id: " + dialogNode.getDialogId());
        topPanel.add(dialogIdLabel);

        springLayout.putConstraint(springLayout.WEST, titleLabel, 5, springLayout.WEST, topPanel);
        springLayout.putConstraint(springLayout.NORTH, titleLabel, 5, springLayout.NORTH, topPanel);

        springLayout.putConstraint(springLayout.WEST, titleTextBox, 5, springLayout.EAST, titleLabel);
        springLayout.putConstraint(springLayout.NORTH, titleTextBox, 5, springLayout.NORTH, topPanel);


        springLayout.putConstraint(springLayout.WEST, confirmTitleButton, 5, springLayout.EAST, titleTextBox);
        springLayout.putConstraint(springLayout.NORTH, confirmTitleButton, 5, springLayout.NORTH, topPanel);

        springLayout.putConstraint(springLayout.WEST, cancelTitleButton, 5, springLayout.EAST, confirmTitleButton);
        springLayout.putConstraint(springLayout.NORTH, cancelTitleButton, 5, springLayout.NORTH, topPanel);
        //springLayout.putConstraint(springLayout.EAST, cancelTitleButton, 100, springLayout.EAST, topPanel);


        springLayout.putConstraint(springLayout.WEST, dialogIdLabel, 20, springLayout.EAST, cancelTitleButton);
        springLayout.putConstraint(springLayout.NORTH, dialogIdLabel, 5, springLayout.NORTH, topPanel);
        springLayout.putConstraint(springLayout.EAST, topPanel, 5, springLayout.EAST, dialogIdLabel);



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
        dialogTextBox.setLineWrap(true);
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

    public void handleInputs() {
        //titleTextBox.gette
    }
}
