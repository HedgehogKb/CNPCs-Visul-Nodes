package com.hedgehogkb.ProjectEditorFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ProjectEditorMenuBar {
    private final ProjectEditorFrame projectEditorFrame;

    private JMenuBar menuBar;

    private JMenu fileMenu;
    private JMenuItem saveButton;

    public ProjectEditorMenuBar(ProjectEditorFrame projectEditorFrame) {
        this.projectEditorFrame = projectEditorFrame;

        this.menuBar = new JMenuBar();

        initializeMenuBar();
        menuBar.add(fileMenu);
        handleMenuInputs();
    }

    private void initializeMenuBar() {
        fileMenu = new JMenu("File");
        
        saveButton = new JMenuItem("Save");
        fileMenu.add(saveButton);
    }

    private void handleMenuInputs() {
        saveButton.addActionListener(e -> {
            projectEditorFrame.saveProject();
        });
    }

    public JMenuBar getMenuBar() {
        return this.menuBar;
    }
}
