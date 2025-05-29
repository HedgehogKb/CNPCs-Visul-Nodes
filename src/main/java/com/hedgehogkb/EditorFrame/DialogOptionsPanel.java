package com.hedgehogkb.EditorFrame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.DialogOption;

public class DialogOptionsPanel {
    private DialogNodeEditorFrame dialogNodeEditorFrame;
    private DialogNode dialogNode;
    private DialogOption dialogOption;
    private JPanel panel;
    private JPanel specificOptionPanel;
    private JLabel specificOptionLabel;
    private JComboBox<String> specificOptionBox;
    private boolean[] optionTitleSaved;

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
    
    public DialogOptionsPanel(DialogNode dialogNode, DialogNodeEditorFrame dialogNodeEditorFrame) {
        this.dialogNodeEditorFrame = dialogNodeEditorFrame;
        this.dialogNode = dialogNode;

        this.optionTitleSaved = new boolean[6];
        for (int i = 0; i < optionTitleSaved.length; i++) {
            optionTitleSaved[i] = true;
        }

        initializeMainPanelComponents();
        initializeSpecificOptionPanelComponents();
        this.dialogOption = dialogNode.getOptions().get(0);
        initializeOptionPanelValues(dialogOption);

        handleOptionPanelInputs();
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
        optionTypeBox.addItem("Close");
        optionTypeBox.addItem("Dialog");
        optionTypeBox.addItem("Disabled");
        optionTypeBox.addItem("Role");
        optionTypeBox.addItem("Command Block");
        optionTypeBox.setSelectedIndex(1);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 0.5;
        c.weighty = 0.01;
        c.ipady = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        specificOptionPanel.add(optionTypeBox, c);

        this.specificOptionTypeLabel = new JLabel("Next Dialog: ");
        specificOptionTypeLabel.setVisible(false);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 1;
        c.ipady = 10;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        specificOptionPanel.add(specificOptionTypeLabel, c);

        this.specificOptionTypeTextArea = new JTextArea();
        specificOptionTypeTextArea.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        specificOptionTypeTextArea.setVisible(false);
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2;
        c.weightx = 0.5;
        c.weighty = 1;
        c.ipady = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(5, 0, 0, 0);
        specificOptionPanel.add(specificOptionTypeTextArea, c);
    }

    //ms egger needs to ask the rotc people in third block. (remember newspaper).
    public void initializeOptionPanelValues(DialogOption option) {
        optionTitleTextArea.setText(option.getTitle());
        colorTextArea.setText(String.valueOf(option.getDialogColor()));
        setOptionTypeBoxValue(option);

        revealOptionTypeComponents();
        if (optionTypeBox.getSelectedItem().equals("Dialog")) {
            specificOptionTypeTextArea.setText(String.valueOf(option.getDialog()));
        } else if (optionTypeBox.getSelectedItem().equals("Command Block")) {
            specificOptionTypeTextArea.setText(option.getOptionCommand());
        }
    }

    public void handleOptionPanelInputs() {
        specificOptionBox.addActionListener(e -> {
            if (dialogOption.getOptionSlot() == specificOptionBox.getSelectedIndex()) {
                return; //no need to change anything if the same option is selected
            }
            if (!isSaved()) {
                int saveChoice = JOptionPane.showConfirmDialog(panel, "Do you want to save the edited Dialog Title?", "Save Changes", JOptionPane.YES_NO_OPTION);
                    switch(saveChoice) {
                        case JOptionPane.NO_OPTION:
                            optionTitleSaved[dialogOption.getOptionSlot()] = true;
                            break;
                        case JOptionPane.YES_OPTION:
                            saveDialogOption();
                            break;
                    }
            }
            int previousOptionId = dialogOption.getOptionSlot();
            int optionId = specificOptionBox.getSelectedIndex();
            this.dialogOption = dialogNode.getOptions().get(optionId);
            revealOptionTypeComponents();
            initializeOptionPanelValues(dialogOption);
            optionTitleSaved[specificOptionBox.getSelectedIndex()] = true;
        });

        optionTitleTextArea.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                optionTitleSaved[specificOptionBox.getSelectedIndex()] = false;

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                optionTitleSaved[specificOptionBox.getSelectedIndex()] = false;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
            
        });

        confirmOptionTitleButton.addActionListener(e -> {
            setProjectUnsaved();
            dialogOption.setTitle(optionTitleTextArea.getText());
            int selectedIndex = specificOptionBox.getSelectedIndex();
            specificOptionBox.insertItemAt(dialogOption.getOptionSlot() + " - " + cutString(15, dialogOption.getTitle()), selectedIndex);
            specificOptionBox.removeItemAt(selectedIndex + 1);
            specificOptionBox.setSelectedIndex(selectedIndex);
            optionTitleSaved[selectedIndex] = true;
        });

        cancelOptionTitleButton.addActionListener(e -> {
            optionTitleTextArea.setText(dialogOption.getTitle());
            optionTitleSaved[specificOptionBox.getSelectedIndex()] = true;
        });

        colorTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setProjectUnsaved();
                try {
                    int dialogColor = Integer.valueOf(colorTextArea.getText());
                    dialogOption.setDialogColor(dialogColor);
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        colorTextArea.setText(String.valueOf(dialogOption.getDialogColor()));
                    });
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                setProjectUnsaved();
                String colorString = colorTextArea.getText();
                if (colorString.equals("")) {
                    return;
                }
                int dialogColor = Integer.valueOf(colorString);
                dialogOption.setDialogColor(dialogColor);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        optionTypeBox.addActionListener(e -> {
            setProjectUnsaved();
           revealOptionTypeComponents();
           int optionTypeValue = optionTypeBox.getSelectedIndex();
           dialogOption.setOptionType(optionTypeValue);
        });

        specificOptionTypeTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setProjectUnsaved();
                if (optionTypeBox.getSelectedItem().equals("Dialog")) {
                    try {
                        int dialogId = Integer.valueOf(specificOptionTypeTextArea.getText());
                        dialogOption.setDialog(dialogId);
                    } catch (Exception ex) {
                        if (!specificOptionTypeTextArea.getText().equals("-")) {
                            SwingUtilities.invokeLater(() -> {
                                specificOptionTypeTextArea.setText(String.valueOf(dialogOption.getDialog()));
                            });
                        }
                    }
                } else {
                    dialogOption.setOptionCommand(specificOptionTypeTextArea.getText());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (optionTypeBox.getSelectedItem().equals("Dialog")) {
                    setProjectUnsaved();
                     try {
                        int dialogId = Integer.valueOf(specificOptionTypeTextArea.getText());
                        dialogOption.setDialog(dialogId);
                    } catch (Exception ex) {
                        dialogOption.setDialog(-1);
                        //I want the person to be able to remove more (ex. removing the 1 from -1 results in just -, but the person still
                        //needs to delete mroe stuff to get a regular number.) also, since this isn't a number I set it to -1 to not cause  confusion.
                    }
                } else {
                    dialogOption.setOptionCommand(specificOptionTypeTextArea.getText());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
    }

    public void setOptionTypeBoxValue(DialogOption option) {
        optionTypeBox.setSelectedIndex(option.getOptionType());
    }

    public void revealOptionTypeComponents() {
         if (optionTypeBox.getSelectedItem().equals("Dialog")) {
                specificOptionTypeLabel.setVisible(true);
                specificOptionTypeLabel.setText("Next Dialog Id: ");
                specificOptionTypeTextArea.setVisible(true);
                specificOptionTypeTextArea.setText(String.valueOf(dialogOption.getDialog()));
        } else if (optionTypeBox.getSelectedItem().equals("Command Block")) {
                specificOptionTypeLabel.setVisible(true);
                specificOptionTypeLabel.setText("Command: ");
                specificOptionTypeTextArea.setVisible(true);
                specificOptionTypeTextArea.setText(dialogOption.getOptionCommand());
        } else {
                specificOptionTypeLabel.setVisible(false);
                specificOptionTypeTextArea.setVisible(false);

        }
    }

    public void updateOptionValue() {
        setOptionTypeBoxValue(this.dialogOption);
        revealOptionTypeComponents();
    }

    public String cutString(int maxLength, String input) {
        if (input.length() <= maxLength) {
            return input;
        } else {
            return input.substring(0, maxLength) + "...";
        }
    }

    public void saveDialogOption() {
        dialogOption.setTitle(optionTitleTextArea.getText());
        int selectedIndex = dialogOption.getOptionSlot();
        specificOptionBox.insertItemAt(dialogOption.getOptionSlot() + " - " + cutString(15, dialogOption.getTitle()), selectedIndex);
        specificOptionBox.removeItemAt(selectedIndex + 1);
        optionTitleSaved[dialogOption.getOptionSlot()] = true;
    }

    //getters and setters
    public JPanel getPanel() {
        return panel;
    }

    public DialogNode getDialogNode() {
        return this.dialogNode;
    }

    public boolean isSaved() {
        for (int i = 0; i < optionTitleSaved.length; i++) {
            if (!optionTitleSaved[i]) {
                return false;
            }
        }
        return true;
    }

    public void setProjectUnsaved() {
        
    }

}
