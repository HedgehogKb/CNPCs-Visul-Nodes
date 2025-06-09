package com.hedgehogkb.LauncherFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.json.JSONException;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.ProjectInfo;
import com.hedgehogkb.Exceptions.ProjectExistsException;
import com.hedgehogkb.ImportingAndExporting.CnpcNodesConverter;
import com.hedgehogkb.ImportingAndExporting.ProjectImporter;
import com.hedgehogkb.ProjectEditorFrame.ProjectEditorFrame;



public class LauncherFrame {
    private JFrame frame;
    private JFileChooser fileChooser;
    private File selectedDirectory;

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
    private JButton createProjectButton;


    //existing project components
    private JPanel existingProjectPanel;
    private JLabel existingProjectExplinationLabel;
    private JLabel existingProjectFileLocationLabel;
    private JButton existingProjectFolderSelectionButton;
    private JLabel existingProjectSelectedFolderLabel;
    private JButton existingProjectCreateProjectButton;

    //existing nodes components
    private JPanel existingNodesPanel;
    private JLabel existingNodesExplinationLabel;
    private JLabel existingNodesFileLocationLabel;
    private JButton existingNodesFolderSelectionButton;
    private JLabel existingNodesSelectedFolderLabel;
    private JButton existingNodesCreateProjectButton;

    public LauncherFrame() {
        this.frame = new JFrame("Dialog Node Editor");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(550, 300);
        this.frame.setMinimumSize(new Dimension(540, 200));
        this.frame.setLayout(new GridLayout(0, 2));

        this.createProjectButton = new JButton("Create Project");

        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File startFile = new File(System.getProperty("user.home"), "AppData\\Roaming\\.minecraft\\saves");
        if (!startFile.exists()) {
            startFile = new File(System.getProperty("user.home"));
        }
        this.fileChooser.setSelectedFile(startFile);
        this.fileChooser.setCurrentDirectory(startFile);
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
        handleExistingProjectInputs();
        handleExistingNodesInputs();
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
        existingProjectPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Existing project"));
        existingProjectPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        existingProjectExplinationLabel = new JLabel("<html>" + "Opens an exising project from the selected folder. Make sure the folder contains a Project Settings Json file." + "</html>");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 10, 0);
        existingProjectPanel.add(existingProjectExplinationLabel, c); 

        existingProjectFileLocationLabel = new JLabel("Select Project Folder:");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 0, 5, 0);
        existingProjectPanel.add(existingProjectFileLocationLabel, c);

        existingProjectFolderSelectionButton = new JButton("Select Folder");
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.9;
        //c.weighty = 0.5;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        existingProjectPanel.add(existingProjectFolderSelectionButton, c);

        existingProjectSelectedFolderLabel = new JLabel("<html>" + "Selected Folder: Null" + "</html>");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,8,0);
        existingProjectPanel.add(existingProjectSelectedFolderLabel, c);

        existingProjectCreateProjectButton = new JButton("Open Project");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        existingProjectPanel.add(existingProjectCreateProjectButton, c);
    }

    private void initializeExistingNodesPanel() {
        existingNodesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Existing Nodes"));
        existingNodesPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        existingNodesExplinationLabel = new JLabel("<html>" + "Used to import existing CNPCs nodes that were created with the official Minecraft mod." + "</html>");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 10, 0);
        existingNodesPanel.add(existingNodesExplinationLabel, c);

        existingNodesFileLocationLabel = new JLabel("Select Nodes Folder:");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 0, 5, 0);
        existingNodesPanel.add(existingNodesFileLocationLabel, c);

        existingNodesFolderSelectionButton = new JButton("Select Folder");
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.9;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        existingNodesPanel.add(existingNodesFolderSelectionButton, c);

        existingNodesSelectedFolderLabel = new JLabel("<html>" + "Selected Folder: Null" + "</html>");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,8,0);
        existingNodesPanel.add(existingNodesSelectedFolderLabel, c);

        existingNodesCreateProjectButton = new JButton("Open Nodes");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        existingNodesPanel.add(existingNodesCreateProjectButton, c);
    }

    private void buildFrame() {
        this.frame.getContentPane().add(leftPanel);

        this.blankRightPanel = new JPanel();
        this.blankRightPanel.setBorder(BorderFactory.createEtchedBorder());
        this.frame.getContentPane().add(blankRightPanel);

        this.frame.setVisible(true);
    }

    private void handleGeneralInputs() {
        createProjectButton.addActionListener(e -> {
            ProjectInfo projectInfo = new ProjectInfo(selectedDirectory, Integer.parseInt(startingNumberTextArea.getText()));
            projectInfo.setProjectSaved(false);
            ProjectEditorFrame projectEditorFrame = new ProjectEditorFrame(projectInfo);
            this.frame.dispose();
        });
    }

    private void handleLeftPanelInputs() {
        blankProjectButton.addActionListener(e -> {
            this.frame.getContentPane().remove(1);
            this.frame.add(this.blankProjectPanel);
            SwingUtilities.updateComponentTreeUI(frame);

            this.selectedProjectType = BLANK_PROJECT;
        });

        existingProjectButton.addActionListener(e -> {
            this.frame.getContentPane().remove(1);
            this.frame.add(this.existingProjectPanel);
            SwingUtilities.updateComponentTreeUI(frame);

            this.selectedProjectType = EXISTING_PROJECT;

        });

        existingNodesButton.addActionListener(e -> {
            this.frame.getContentPane().remove(1);
            this.frame.add(this.existingNodesPanel);
            SwingUtilities.updateComponentTreeUI(frame);

            this.selectedProjectType = EXISTING_NODES;
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

    private void handleExistingProjectInputs() {
        existingProjectFolderSelectionButton.addActionListener(e -> {
            int returnValue = fileChooser.showOpenDialog(this.frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                this.selectedDirectory = fileChooser.getSelectedFile();
                existingProjectSelectedFolderLabel.setText("<html>"+"Selected Folder: " + selectedDirectory.getAbsolutePath() + "</html>");
            }
        });

        existingProjectCreateProjectButton.addActionListener(e -> {
            ProjectImporter importer = null;
            try {
                importer = new ProjectImporter(selectedDirectory);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "Cannot find file.");
                return;
            } catch (JSONException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Json.");
                ex.printStackTrace();
                return;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error finding files. Ensure that the selected folder contains a project settings file and the specified group directories.");
                ex.printStackTrace();
                return;
            }

            if (importer != null) {
                System.out.println("don't with importing nodes. Making editor frame.");
                ProjectEditorFrame editorFrame = new ProjectEditorFrame(importer.getProjectInfo());
                System.out.println("done with editor frame.");
                this.frame.dispose();
            }
        });
    }

    private void handleExistingNodesInputs() {
        existingNodesFolderSelectionButton.addActionListener(e -> {
            int returnValue = fileChooser.showOpenDialog(this.frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                this.selectedDirectory = fileChooser.getSelectedFile();
                existingProjectSelectedFolderLabel.setText("<html>"+"Selected Folder: " + selectedDirectory.getAbsolutePath() + "</html>");
            }
        });

        existingNodesCreateProjectButton.addActionListener(e -> {
            CnpcNodesConverter importer = null;
            try {
                importer = new CnpcNodesConverter(selectedDirectory);
            } catch (ProjectExistsException ex) {
                JOptionPane.showMessageDialog(frame, "<html>You opened a file that is already a visual project. Either remove the cnpcsProjectSettings.json file or use Open Existing Project.</html>", "Existing Project", 0);
                return;
            }

            if (importer != null) {
                ProjectEditorFrame editorFrame = new ProjectEditorFrame(importer.getProjectInfo());
                this.frame.dispose();
            }
        });
    }
}
