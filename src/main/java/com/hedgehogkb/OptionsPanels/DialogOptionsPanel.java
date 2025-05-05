package com.hedgehogkb.OptionsPanels;

import com.hedgehogkb.DialogNode;
import com.hedgehogkb.DialogOption;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class DialogOptionsPanel {
    private DialogNode dialogNode;
    private JPanel panel;
    private JPanel specificOptionPanel;
    private JLabel specificOptionLabel;
    private JComboBox<String> specificOptionBox;

    //specific option panel components
    private JLabel optionTitleLabel;
    private JButton confirmOptionTitleButton;
    private JButton cancelOptionTitleButton;
    private JTextArea optionTitleTextArea;
    private JLabel colorLabel;
    private JTextArea colorTextArea;
    private JLabel optionTypeLable;
    private JComboBox<String> optionTypeBox;
    private JLabel specificOptionTypeLabel;
    private JTextArea specificOptionTypeTextArea;
    
    public DialogOptionsPanel(DialogNode dialogNode) {
        this.dialogNode = dialogNode;
        initializeMainPanelComponents();
        initializeSpecificOptionPanelComponents(); 
    }

    private void initializeMainPanelComponents() {
        this.panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Dialog Options"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        specificOptionLabel = new JLabel("Specific Dialog Option:");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.ipady = 10;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(specificOptionLabel, c);

        specificOptionBox = new JComboBox<>();
        ArrayList<DialogOption> dialogOptions = dialogNode.getOptions();
        for(int i = 0; i < dialogOptions.size(); i++) {
            DialogOption curOption = dialogOptions.get(i);
            specificOptionBox.addItem(curOption.getOptionSlot() + " - " + curOption.getTitle());
        }
        specificOptionBox.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.ipady = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(specificOptionBox, c);

        specificOptionPanel = new JPanel();
        specificOptionPanel.setBorder(BorderFactory.createEtchedBorder());
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new java.awt.Insets(5, 5, 5, 5);
        panel.add(specificOptionPanel, c);
    }

    private void initializeSpecificOptionPanelComponents() {
        specificOptionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.optionTitleLabel = new JLabel("Option Title: ");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.ipady = 10;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new java.awt.Insets(5, 0, 0, 0);
        specificOptionPanel.add(optionTitleLabel, c);

        this.confirmOptionTitleButton = new JButton("Confirm");
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.3;
        c.ipady = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        specificOptionPanel.add(confirmOptionTitleButton, c);

        this.cancelOptionTitleButton = new JButton("Cancel");
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.3;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        specificOptionPanel.add(cancelOptionTitleButton, c);

        this.optionTitleTextArea = new JTextArea();
        optionTitleTextArea.setLineWrap(true);
        optionTitleTextArea.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.ipady = 30;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new java.awt.Insets(5, 5, 5, 5);
        specificOptionPanel.add(optionTitleTextArea, c);

        this.colorLabel = new JLabel("Dialog Color: ");
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        c.ipady = 10;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new java.awt.Insets(0, 0, 0, 0);
        specificOptionPanel.add(colorLabel, c);

        this.colorTextArea = new JTextArea();
        colorTextArea.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = 0.5;
        c.ipady = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        specificOptionPanel.add(colorTextArea, c);

        this.optionTypeLable = new JLabel("Option Type: ");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.ipady = 10;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        specificOptionPanel.add(optionTypeLable, c);

        this.optionTypeBox = new JComboBox<>();
        optionTypeBox.addItem("Dialog");
        optionTypeBox.addItem("Disabled");
        optionTypeBox.addItem("Role");
        optionTypeBox.addItem("Command Block");
        optionTypeBox.addItem("Close");
        optionTypeBox.setSelectedIndex(1);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.ipady = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        specificOptionPanel.add(optionTypeBox, c);

        this.specificOptionTypeLabel = new JLabel("Next Dialog: ");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.ipady = 10;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        this.specificOptionTypeTextArea = new JTextArea();
        specificOptionTypeTextArea.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        c.weightx = 0.5;
        c.ipady = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
    }

    public JPanel getPanel() {
        return panel;
    }
}
