package com.hedgehogkb.NodeDisplayFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class VisualNodeDisplayMenuBar {
    private VisualNodeDisplayFrame visualNodeDisplay;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu addNodeMenu;

    private JMenuItem addNodeButton;
    private JMenuItem impotCNPCsNode;
    private JMenuItem importProjectNode;

    public VisualNodeDisplayMenuBar() {
        this.menuBar = new JMenuBar();
        
        initializeMenuBar();
        addMenuItems();
        handleMenuInputs();
    }

    public VisualNodeDisplayMenuBar(VisualNodeDisplayFrame visualNodeDisplay) {
        this();
        this.visualNodeDisplay = visualNodeDisplay;
    }
    
    private void initializeMenuBar() {
        fileMenu = new JMenu("File");
        //editMenu = new JMenu("Edit");
        addNodeMenu = new JMenu("Add Node");
        
        addNodeButton = new JMenuItem("Add Node");
        addNodeMenu.add(addNodeButton);

        impotCNPCsNode = new JMenuItem("Import CNPCs Node");
        addNodeMenu.add(impotCNPCsNode);

        importProjectNode = new JMenuItem("Import Dialog Node");
        addNodeMenu.add(importProjectNode);
        
    }


    private void addMenuItems() {
        menuBar.add(fileMenu);
        //menuBar.add(editMenu);
        menuBar.add(addNodeMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    private void handleMenuInputs() {
        addNodeButton.addActionListener(e -> {
            visualNodeDisplay.addVisualNode();
        });

        impotCNPCsNode.addActionListener(e -> {

        });

        importProjectNode.addActionListener(e -> {
        
        });
    }
}
