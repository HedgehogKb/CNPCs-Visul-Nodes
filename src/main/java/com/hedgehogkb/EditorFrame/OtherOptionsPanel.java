package com.hedgehogkb.EditorFrame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.codehaus.plexus.util.cli.CommandLineException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.hedgehogkb.DialogNodeComponents.DialogNode;

public class OtherOptionsPanel {
    
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
    //private JButton confirmQuestButton;
    //private JButton cancelQuestButton;
    
    private boolean saved;

    private DialogNode dialogNode;

    public OtherOptionsPanel(DialogNode dialogNode) {
        this.dialogNode = dialogNode;
        initializeOtherOptionsPanel();
        handleOptionPanelInputs();
    }

    private void initializeOtherOptionsPanel() {
        this.saved = true;
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
        showDialogWheelCheckBox.setSelected(dialogNode.getIsShowDialogWheel());
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
        disableEscCheckBox.setSelected(dialogNode.getIsShowDialogWheel());
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

    private void handleOptionPanelInputs() {
        this.confirmCommandButton.addActionListener(e -> {
            dialogNode.setDialogCommand(commandTextBox.getText());
            saved = true;
        });

        this.cancelCommandButton.addActionListener(e -> {
            commandTextBox.setText(dialogNode.getDialogCommand());
            saved = true;
        });

        this.commandTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                saved = false;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                saved = false;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        this.hideNPCCheckBox.addItemListener(e -> {
                int state = e.getStateChange();
                dialogNode.setHideNPC(state == 1);
        });

        this.showDialogWheelCheckBox.addItemListener(e-> {
            int state = e.getStateChange();
            dialogNode.setShowDialogWheel(state == 1);
        });

        this.disableEscCheckBox.addItemListener(e -> {
            int state = e.getStateChange();
            dialogNode.setDisableEsc(state == 1);
        });

        this.questNumberTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    int dialogId = Integer.valueOf(questNumberTextBox.getText());
                    dialogNode.setDialogQuest(dialogId);
                } catch (Exception ex) {
                    if (!questNumberTextBox.getText().equals("-")) {
                        SwingUtilities.invokeLater(() -> {
                            questNumberTextBox.setText(String.valueOf(dialogNode.getDialogQuest()));
                        });
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    int questId = Integer.valueOf(questNumberTextBox.getText());
                    dialogNode.setDialogQuest(questId);
                } catch (Exception ex) {
                    dialogNode.setDialogQuest(-1);
                    //I want the person to be able to remove more (ex. removing the 1 from -1 results in just -, but the person still
                    //needs to delete mroe stuff to get a regular number.) also, since this isn't a number I set it to -1 to not cause  confusion.
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
            
        });

    }

    public void save() {
        dialogNode.setDialogCommand(commandTextBox.getText());
        saved = true;
    }

    public boolean isSaved() {
        return this.saved;
    }

    public JPanel getPanel() {
        return this.otherOptionsPanel;
    }

}
