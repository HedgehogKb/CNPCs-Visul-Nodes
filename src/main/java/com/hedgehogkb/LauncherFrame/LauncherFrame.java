package com.hedgehogkb.LauncherFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.ProjectInfo;
import com.hedgehogkb.ProjectEditorFrame.ProjectEditorFrame;



public class LauncherFrame {
    private JFrame frame;
    private JFileChooser fileChooser;
    private File selectedDirectory;
    private JButton createProjectButton;

    private int selectedProjectType;

    public static final int BLANK_PROJECT = 0;
    public static final int EXISTING_PROJECT = 1;
    public static final int EXISTING_NODES = 2;

    //left panel components
    private JPanel leftPanel;
    private JLabel projectTypeLabel;
    private JButton blankProjectButton;
    private JButton existingProjectButton;
    private JButton existingNodesButton;

    //blank right panel components
    private JPanel blankRightPanel;

    //blank project components
    private JPanel blankProjectPanel;
    private JLabel blankProjectExplinationLabel;
    private JLabel startingNumberLabel;
    private JTextArea startingNumberTextArea;
    private int startingNumber;
    private JLabel blankProjectFileLocationLabel;
    private JButton blankProjectFolderSelectorButton;
    private JLabel selectedFolderLabel;

    //existing project components
    private JPanel existingProjectPanel;

    //existing nodes components
    private JPanel existingNodesPanel;

    public LauncherFrame() {
        this.frame = new JFrame("Dialog Node Editor");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(550, 300);
        this.frame.setMinimumSize(new Dimension(540, 200));
        this.frame.setLayout(new GridLayout(0, 2));

        this.createProjectButton = new JButton("Create Project");

        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.fileChooser.setSelectedFile(new File("C:\\Users\\kereb\\AppData\\Roaming\\.minecraft\\saves"));
        this.selectedDirectory = null;

        this.leftPanel = new JPanel();
        this.blankProjectPanel = new JPanel();
        this.existingProjectPanel = new JPanel();
        this.existingNodesPanel = new JPanel();

        initializeLeftPanel();
        initializeBlankProjectPanel();
        initializeExistingProjectPanel();
        initializeExistingNodesPanel();

        buildFrame();

        handleGeneralInputs();
        handleLeftPanelInputs();
        handleBlankProjectInputs();
    }

    private void initializeLeftPanel() {
        this.leftPanel.setBorder(BorderFactory.createEtchedBorder());
        this.leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        projectTypeLabel = new JLabel("Select Project Type:");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets.set(10, 10, 10, 10);
        leftPanel.add(projectTypeLabel, c);

        blankProjectButton = new JButton("Create Blank Project");
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets.set(0, 10, 10, 10);
        leftPanel.add(blankProjectButton, c);

        existingProjectButton = new JButton("Open Existing Project");
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(existingProjectButton, c);

        existingNodesButton = new JButton("Open Existing Nodes");
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(existingNodesButton, c);
    }

    private void initializeBlankProjectPanel() {
        this.blankProjectPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Blank Project"));
        this.blankProjectPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        blankProjectExplinationLabel = new JLabel("<html>"+"Creates a completely new project off of a starting number."+"</html>");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 10, 0);
        blankProjectPanel.add(blankProjectExplinationLabel, c);

        startingNumberLabel = new JLabel("Lowest Number:");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.9;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 5, 0);
        blankProjectPanel.add(startingNumberLabel, c);

        startingNumberTextArea = new JTextArea("1");
        startingNumberTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 5, 0);
        blankProjectPanel.add(startingNumberTextArea, c);

        this.startingNumber = 1;

        blankProjectFileLocationLabel = new JLabel("Select Project Location:");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 0.9;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 0, 0, 0);
        blankProjectPanel.add(blankProjectFileLocationLabel, c);

        blankProjectFolderSelectorButton = new JButton("Select Folder");
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        blankProjectPanel.add(blankProjectFolderSelectorButton, c);

        selectedFolderLabel = new JLabel("<html>" + "Selected Folder: Null" + "</html>");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,8,0);
        blankProjectPanel.add(selectedFolderLabel, c);

        //create project button
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        blankProjectPanel.add(createProjectButton, c);
    } 

    private void initializeExistingProjectPanel() {

    }

    private void initializeExistingNodesPanel() {

    }

    private void buildFrame() {
        this.frame.getContentPane().add(leftPanel);

        this.blankRightPanel = new JPanel();
        this.blankRightPanel.setBorder(BorderFactory.createEtchedBorder());
        this.frame.getContentPane().add(blankRightPanel);

        this.frame.setVisible(true);
    }

    public void handleGeneralInputs() {
        createProjectButton.addActionListener(e -> {
            ProjectInfo projectInfo = new ProjectInfo(selectedDirectory, Integer.parseInt(startingNumberTextArea.getText()));
            ProjectEditorFrame projectEditorFrame = new ProjectEditorFrame(projectInfo);
            this.frame.dispose();
        });
    }

    private void handleLeftPanelInputs() {
        blankProjectButton.addActionListener(e -> {
            this.frame.remove(blankRightPanel);
            this.frame.add(this.blankProjectPanel);
            SwingUtilities.updateComponentTreeUI(frame);

            this.selectedProjectType = BLANK_PROJECT;
        });
    }
    
    private void handleBlankProjectInputs() {
        blankProjectFolderSelectorButton.addActionListener(e -> {
            int returnValue = fileChooser.showOpenDialog(this.frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                this.selectedDirectory = fileChooser.getSelectedFile();
                selectedFolderLabel.setText("<html>"+"Selected Folder: " + selectedDirectory.getAbsolutePath() + "</html>");
            }
        });

        startingNumberTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    startingNumber = Integer.valueOf(startingNumberTextArea.getText());
                    if (startingNumber < 1) {
                        startingNumber = 1;
                        SwingUtilities.invokeLater(() -> {
                            startingNumberTextArea.setText("1");
                        });
                    }
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        startingNumberTextArea.setText(String.valueOf(startingNumber));
                    });
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String colorString = startingNumberTextArea.getText();
                if (colorString.equals("")) {
                    startingNumber = 1;
                    return;
                }
                startingNumber = Integer.valueOf(colorString);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
    }
}
