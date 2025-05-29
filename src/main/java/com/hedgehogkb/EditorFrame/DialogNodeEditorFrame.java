package com.hedgehogkb.EditorFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;

//import com.hedgehogkb.OptionsPanels.DialogOptionsPanel;


public class DialogNodeEditorFrame {
    private DialogNode dialogNode;
    private VisualNodeShell visualNodeShell;
    private JFrame frame;
    private JPanel mainPanel;

    //saved information
    private boolean titleSaved;
    private boolean dialogSaved;
    
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
    private OtherOptionsPanel otherOptionsPanel;

    //top panel components
    private JPanel topPanel;
    private JTextArea titleTextBox;
    private JButton confirmTitleButton;
    private JButton cancelTitleButton;

    

    public DialogNodeEditorFrame(DialogNode dialogNode, VisualNodeShell visualNodeShell) {
        this.dialogNode = dialogNode;
        this.visualNodeShell = visualNodeShell;
        this.titleSaved = true;
        this.dialogSaved = true;
        initializeComponents();
        buildLayout();
        handleInputs();
    }

    private void initializeComponents() {

        // Initialize JFrame
        this.frame = new JFrame("Dialog Id: "+dialogNode.getDialogId() + " - " + dialogNode.getDialogTitle());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 430);
        frame.setMinimumSize(new Dimension(600, 340));
        

        //Initialize the main panel which is divided in two parts,
        this.mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2));

        initializeTopPanel();

        initializeLeftPanel();

        this.otherOptionsPanel = new OtherOptionsPanel(dialogNode);

        //Initialize right panel
        this.blankRightPanel = new JPanel();
        blankRightPanel.setLayout(new GridLayout(4, 2));
        blankRightPanel.setBorder(BorderFactory.createEtchedBorder());

        dialogOptionsPanel = new DialogOptionsPanel(dialogNode, this);
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

    private void buildLayout() {
        this.mainPanel.add(leftPanel, 0);
        this.mainPanel.add(blankRightPanel, 1);

        this.frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.frame.getContentPane().add(topPanel, BorderLayout.NORTH);
    }

    public void handleInputs() {
        confirmTextButton.addActionListener(e -> {
            setProjectUnsaved();
            String text = dialogTextBox.getText();
            dialogNode.setDialogText(text);
            dialogSaved = true;
        });

        cancelTextButton.addActionListener(e -> {
            dialogTextBox.setText(dialogNode.getDialogText());
            dialogSaved = true;

        });

        confirmTitleButton.addActionListener(e -> {
            setProjectUnsaved();
            String title = titleTextBox.getText();
            dialogNode.setDialogTitle(title);
            frame.setTitle("Dialog Id: "+dialogNode.getDialogId() + " - " + dialogNode.getDialogTitle());
            titleSaved = true;
            visualNodeShell.getGroup().getProjectInfo().refreshProjectNodes();
        });

        cancelTitleButton.addActionListener(e -> {
            titleTextBox.setText(dialogNode.getDialogTitle());
            titleSaved = true;
        });

        otherOptionsButton.addActionListener(e -> {
            mainPanel.remove(1);
            mainPanel.add(otherOptionsPanel.getPanel(), 1);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        dialogOptionsButton.addActionListener(e -> {
            mainPanel.remove(1);
            mainPanel.add(dialogOptionsPanel.getPanel(), 1);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if(isPanelSaved()) {
                    frame.dispose();
                } else {
                    int saveChoice = JOptionPane.showConfirmDialog(frame, "You have unsaved changes. Do you want to save them before closing?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
                    switch(saveChoice) {
                        case JOptionPane.NO_OPTION:
                            titleTextBox.setText(dialogNode.getDialogTitle());
                            dialogTextBox.setText(dialogNode.getDialogText());
                            setPanelSaved();
                            frame.dispose();
                            break;
                        case JOptionPane.YES_OPTION:
                            dialogNode.setDialogTitle(titleTextBox.getText());
                            dialogNode.setDialogText(dialogTextBox.getText());

                            if (!dialogOptionsPanel.isSaved()) {
                                dialogOptionsPanel.saveDialogOption();
                            }

                            setPanelSaved();
                            frame.dispose();
                            break;
                    }
                }
            }
        });

        titleTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                titleSaved = false;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                titleSaved = false;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        dialogTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                dialogSaved = false;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                dialogSaved = false;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

    }

    public void moveFrameToTop() {
        this.frame.setAlwaysOnTop(true);
        this.frame.setAlwaysOnTop(false);
    }

    public void updateOptionValues() {
        this.dialogOptionsPanel.updateOptionValue();
    }

    /**
     * Does not actually update the dialogNode, but sets the panel as saved.
     */
    public void setPanelSaved() {
        titleSaved = true;
        dialogSaved = true;
    }

    public boolean isPanelSaved() {
        boolean saved = titleSaved && dialogSaved && dialogOptionsPanel.isSaved();
        return saved;
    }

    public void setProjectUnsaved() {
        visualNodeShell.setProjectUnsaved();
    }

    public DialogOptionsPanel getDialogOptionsPanel() {
        return this.dialogOptionsPanel;
    }

    public DialogNode getDialogNode() {
        return this.dialogNode;
    }

    public Boolean isVisible() {
        return this.frame.isVisible();
    }
    public void setVisible(Boolean visibility) {
        this.frame.setVisible(visibility);
    }

}
