package com.hedgehogkb.NodeDisplayFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.NodeHandler;
import com.hedgehogkb.ProjectInfo;
import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.GroupNodeShell;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;

public class VisualNodeDisplayMenuBar {
    private VisualNodeDisplayFrame visualNodeDisplay;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveButton;

    private JMenu addNodeMenu;
    private JMenuItem addNodeButton;
    private JMenuItem importProjectNode;
    private JMenu projectNodesMenu;
    private JList<GroupNodeShell> projectNodesList;
    private DefaultListModel<GroupNodeShell> projectNodesListModel;
    private JScrollPane projectNodesScrollPane;
    private JMenuItem impotCNPCsNode;

    public VisualNodeDisplayMenuBar() {
        this.menuBar = new JMenuBar();
        
        initializeMenuBar();
        addMenuItems();
        handleMenuInputs();
    }

    public VisualNodeDisplayMenuBar(VisualNodeDisplayFrame visualNodeDisplay) {
        this.visualNodeDisplay = visualNodeDisplay;
        this.menuBar = new JMenuBar();
        
        initializeMenuBar();
        addMenuItems();
        handleMenuInputs();
    }
    
    private void initializeMenuBar() {
        fileMenu = new JMenu("File");
        
        saveButton = new JMenuItem("Save");
        fileMenu.add(saveButton);

        //editMenu = new JMenu("Edit");
        addNodeMenu = new JMenu("Add Node");
        
        addNodeButton = new JMenuItem("Add Node");
        addNodeMenu.add(addNodeButton);

        impotCNPCsNode = new JMenuItem("Import CNPCs Node");

        projectNodesListModel = new DefaultListModel<>();
        projectNodesList = new JList<>(projectNodesListModel);
        projectNodesScrollPane = new JScrollPane(projectNodesList);
        projectNodesMenu = new JMenu("Project Nodes");
        projectNodesMenu.add(projectNodesScrollPane);
        addNodeMenu.add(projectNodesMenu);
        addNodeMenu.add(impotCNPCsNode);

        importProjectNode = new JMenuItem("Import Other Group Node");
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
            visualNodeDisplay.setProjectUnsaved();
            visualNodeDisplay.getGroup().getProjectInfo().refreshProjectNodes();
        });

        impotCNPCsNode.addActionListener(e -> {

        });

        importProjectNode.addActionListener(e -> {
        
        });

        projectNodesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GroupNodeShell selectedValue = projectNodesList.getSelectedValue();
                if (selectedValue.equals(null)) {
                    return;
                }
                VisualNodeShell projectNode = selectedValue;
                if (visualNodeDisplay.getGroup().getNodeHandler().contains(projectNode)) {
                    return;
                }
                visualNodeDisplay.setProjectUnsaved();
                projectNode.setPosition(visualNodeDisplay.getMouseX() - visualNodeDisplay.getOffsetX(), visualNodeDisplay.getMouseY() - visualNodeDisplay.getOffsetY());
                visualNodeDisplay.getGroup().getNodeHandler().add(projectNode);
            }
        });

        saveButton.addActionListener(e -> {
            visualNodeDisplay.saveProject();
        });
    }

    public void refreshProjectNodes() {
        projectNodesListModel.clear();
        ProjectInfo projectInfo = visualNodeDisplay.getGroup().getProjectInfo();
        for (NodeGroup nodeGroup : projectInfo.getGroups()) {
            if (nodeGroup.equals(visualNodeDisplay.getGroup())) {
                continue;
            }
            NodeHandler curhandler = nodeGroup.getNodeHandler();
            for (int i = 0; i < curhandler.size(); i++) {
                VisualNodeShell node = nodeGroup.getNodeHandler().getIndex(i);
                if (!projectNodesListModel.contains(node.getDialogId())) {
                    projectNodesListModel.addElement(new GroupNodeShell(visualNodeDisplay.getOffsetX(), visualNodeDisplay.getOffsetY(), nodeGroup, node.getDialogNode()));
                }
            }
        }
    }
}
