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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.tools.ToolProvider;

import com.hedgehogkb.OptionsPanels.DialogOptionsPanel;


public class DialogNodeEditorFrame {
    private DialogNode dialogNode;
    private JFrame frame;
    private JPanel mainPanel;
    
    //left panel components
    private JPanel leftPanel;
    private JTextArea dialogTextBox;
    private JButton confirmTextButton;
    private JButton cancelTextButton;
    private JButton availabilityOptionsButton;
    private JButton factionOptionsButton;
    private JButton dialogOptionsButton;
    private JButton otherOptionsButton;

    //initial, blank right panel
    private JPanel blankRightPanel;
    private DialogOptionsPanel dialogOptionsPanel;

    //other right panel components
    private JPanel otherOptionsPanel;
    private JLabel commandLabel;
    private JButton confirmCommandButton;
    private JButton cancelCommandButton;
    private JTextArea commandTextBox;
    private JLabel hideNPCLabel;
    private JCheckBox hideNPCCheckBox;
    private JLabel showDialogWheelLabel;
    private JCheckBox showDialogWheelCheckBox;
    private JLabel disableEscLabel;
    private JCheckBox disableEscCheckBox;
    private JLabel questNumberLabel;
    private JTextArea questNumberTextBox;
    private JButton confirmQuestButton;
    private JButton cancelQuestButton;

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
        this.frame = new JFrame("Dialog Id: "+dialogNode.getDialogId() + " - " + dialogNode.getDialogTitle());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 430);
        frame.setMinimumSize(new Dimension(600, 340));

        //Initialize the main panel which is divided in two parts,
        this.mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2));

        initializeTopPanel();

        initializeLeftPanel();

        initializeOtherOptionsPanel();

        //Initialize right panel
        this.blankRightPanel = new JPanel();
        blankRightPanel.setLayout(new GridLayout(4, 2));
        blankRightPanel.setBorder(BorderFactory.createEtchedBorder());

        dialogOptionsPanel = new DialogOptionsPanel(dialogNode);
    }

    private void initializeTopPanel() {
                this.topPanel = new JPanel();
                SpringLayout springLayout = new SpringLayout();
                topPanel.setLayout(springLayout);
                topPanel.setBorder(BorderFactory.createEtchedBorder());
                topPanel.setPreferredSize(new Dimension(350, 40));
        
                JLabel titleLabel = new JLabel("Dialog Title: ");
                topPanel.add(titleLabel);
        
                titleTextBox = new JTextArea(dialogNode.getDialogTitle());
                titleTextBox.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                
                titleTextBox.setPreferredSize(new Dimension(200, 25));
                topPanel.add(titleTextBox);
                
                confirmTitleButton = new JButton("Confirm Title"); 
                topPanel.add(confirmTitleButton);
        
                cancelTitleButton = new JButton("Cancel Title");
                topPanel.add(cancelTitleButton);
        
                JLabel dialogIdLabel = new JLabel("Dialog Id: " + dialogNode.getDialogId());
                topPanel.add(dialogIdLabel);
        
                springLayout.putConstraint(springLayout.WEST, titleLabel, 5, springLayout.WEST, topPanel);
                springLayout.putConstraint(springLayout.NORTH, titleLabel, 7, springLayout.NORTH, topPanel);
                springLayout.putConstraint(springLayout.WEST, titleTextBox, 5, springLayout.EAST, titleLabel);
                springLayout.putConstraint(springLayout.NORTH, titleTextBox, 5, springLayout.NORTH, topPanel);
                springLayout.putConstraint(springLayout.WEST, confirmTitleButton, 5, springLayout.EAST, titleTextBox);
                springLayout.putConstraint(springLayout.NORTH, confirmTitleButton, 5, springLayout.NORTH, topPanel);
                springLayout.putConstraint(springLayout.WEST, cancelTitleButton, 5, springLayout.EAST, confirmTitleButton);
                springLayout.putConstraint(springLayout.NORTH, cancelTitleButton, 5, springLayout.NORTH, topPanel);
                springLayout.putConstraint(springLayout.EAST, topPanel, 0, springLayout.EAST, dialogIdLabel);
                springLayout.putConstraint(springLayout.WEST, dialogIdLabel, 20, springLayout.EAST, cancelTitleButton);
                springLayout.putConstraint(springLayout.NORTH, dialogIdLabel, 7, springLayout.NORTH, topPanel);        
    }

    private void initializeLeftPanel() {
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

        confirmTextButton = new JButton("Confirm");
        c.weightx = 0.3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 20, 0, 5);
        leftPanel.add(confirmTextButton, c);

        cancelTextButton = new JButton("Cancel");
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 00);
        leftPanel.add(cancelTextButton, c);

        dialogTextBox = new JTextArea(dialogNode.getDialogText());
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

        dialogOptionsButton = new JButton("Dialog Options");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 3;
        //c.gridheight = 1;
        //c.weighty = 1;
        c.ipady = 12;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10, 10, 0, 10);
        leftPanel.add(dialogOptionsButton, c);

        otherOptionsButton = new JButton("Other Options");
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 3;
        //c.gridheight = 1;
        //c.weighty = 1;
        c.ipady = 12;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10, 10, 10, 10);
        leftPanel.add(otherOptionsButton, c);
    }

    private void initializeOtherOptionsPanel() {
        this.otherOptionsPanel = new JPanel();
        otherOptionsPanel.setLayout(new GridBagLayout());
        otherOptionsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Other Options"));
        otherOptionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        commandLabel = new JLabel("Command: ");
        c.ipady = 12;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHEAST;
        otherOptionsPanel.add(commandLabel, c);

        confirmCommandButton = new JButton("Confirm");
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.3;
        c.ipady = 0;
        c.ipadx = 30;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 20, 0, 5);
        otherOptionsPanel.add(confirmCommandButton, c);

        cancelCommandButton = new JButton("Cancel");
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTHWEST;
        otherOptionsPanel.add(cancelCommandButton, c);

        commandTextBox = new JTextArea(dialogNode.getDialogCommand());
        commandTextBox.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        c.ipady = 20;
        c.ipadx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.gridwidth = 3;
        c.insets = new Insets(3, 10, 0, 10);
        otherOptionsPanel.add(commandTextBox, c);

        hideNPCLabel = new JLabel("Hide NPC: ");
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        c.ipady = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        otherOptionsPanel.add(hideNPCLabel, c);

        hideNPCCheckBox = new JCheckBox();
        hideNPCCheckBox.setSelected(dialogNode.getIsHideNPC());
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0.3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHEAST;
        otherOptionsPanel.add(hideNPCCheckBox, c);

        showDialogWheelLabel = new JLabel("Show Dialog Wheel: ");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 0.3;
        c.ipady = 0;
        c.ipadx = 20;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        otherOptionsPanel.add(showDialogWheelLabel, c);

        showDialogWheelCheckBox = new JCheckBox();
        showDialogWheelCheckBox.setSelected(dialogNode.getIsShowDialogueWheel());
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 0.5;
        c.ipadx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTH;
        otherOptionsPanel.add(showDialogWheelCheckBox, c);

        disableEscLabel = new JLabel("Diable Esc: ");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.weightx = 2;
        c.ipady = 0;
        c.ipadx = 40;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHEAST;
        otherOptionsPanel.add(disableEscLabel, c);

        disableEscCheckBox = new JCheckBox();
        dialogOptionsButton.setSelected(dialogNode.getIsShowDialogueWheel());
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        c.weightx = 0.3;
        c.ipadx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTH;
        otherOptionsPanel.add(disableEscCheckBox, c);

        questNumberLabel = new JLabel("Quest #: ");
        questNumberLabel.setMinimumSize(new Dimension(70, 20));
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth =1;
        c.weightx = 1;
        c.weighty = 0.5;
        c.ipadx = 40;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHEAST;
        //c.insets = new Insets(0, 20, 0, 20);
        otherOptionsPanel.add(questNumberLabel, c);

        questNumberTextBox = new JTextArea(dialogNode.getDialogCommand());
        questNumberTextBox.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        questNumberTextBox.setMinimumSize(new Dimension(30, 20));
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        c.weightx = 0.3;
        c.ipady = 1;
        c.ipadx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.gridwidth = 2;
        otherOptionsPanel.add(questNumberTextBox, c);


    }

    private void buildLayout() {
        this.mainPanel.add(leftPanel, 0);
        this.mainPanel.add(blankRightPanel, 1);

        this.frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        //this.frame.pack();

        this.frame.setVisible(true);
    }

    public void handleInputs() {
        confirmTextButton.addActionListener(e -> {
            String text = dialogTextBox.getText();
            dialogNode.setDialogText(text);
        });

        cancelTextButton.addActionListener(e -> {
            dialogTextBox.setText(dialogNode.getDialogText());
        });

        confirmTitleButton.addActionListener(e -> {
            String title = titleTextBox.getText();
            dialogNode.setDialogTitle(title);
            frame.setTitle("Dialog Id: "+dialogNode.getDialogId() + " - " + dialogNode.getDialogTitle());
        });

        cancelTitleButton.addActionListener(e -> {
            titleTextBox.setText(dialogNode.getDialogTitle());
        });

        otherOptionsButton.addActionListener(e -> {
            mainPanel.remove(1);
            mainPanel.add(otherOptionsPanel, 1);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        dialogOptionsButton.addActionListener(e -> {
            mainPanel.remove(1);
            mainPanel.add(dialogOptionsPanel.getPanel(), 1);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }
}
