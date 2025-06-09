package com.hedgehogkb.NodeDisplayFrame;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.json.JSONException;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.ProjectInfo;
import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.GroupNodeShell;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;
import com.hedgehogkb.ImportingAndExporting.DialogNodeBuilder;
import com.hedgehogkb.NodeHandlers.NodeHandler;

public class VisualNodeDisplayMenuBar {
    private VisualNodeDisplayFrame visualNodeDisplay;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveButton;

    private JMenu addNodeMenu;
    private JMenuItem addNodeButton;
    private JMenuItem importProjectNode;
    private JMenu projectNodesMenu;
    private DefaultListModel<String> groupListModel;
    private JList<String> groupList;
    private JScrollPane projectNodesScrollPane;

    private JMenuItem impotCNPCsNode;
    private JFileChooser fileChooser;

    public VisualNodeDisplayMenuBar() {
        this.menuBar = new JMenuBar();
        this.fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(visualNodeDisplay.getGroup().getProjectInfo().getProjectDirectory());
        
        initializeMenuBar();
        addMenuItems();
        handleMenuInputs();
    }

    public VisualNodeDisplayMenuBar(VisualNodeDisplayFrame visualNodeDisplay) {
        this.visualNodeDisplay = visualNodeDisplay;
        this.fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(visualNodeDisplay.getGroup().getProjectInfo().getProjectDirectory());
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

        projectNodesMenu = new JMenu("Project Nodes");
  
        groupListModel = new DefaultListModel<>();
        groupList = new JList<>(groupListModel);
        projectNodesScrollPane = new JScrollPane(groupList);
        projectNodesMenu.add(projectNodesScrollPane);
        addNodeMenu.add(projectNodesMenu);


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
        });

        impotCNPCsNode.addActionListener(e -> {
            fileChooser.showOpenDialog(visualNodeDisplay.getFrame());
            File selectedNode = fileChooser.getSelectedFile();
            if (selectedNode.equals(null) && selectedNode.length() < 1) {
                JOptionPane.showMessageDialog(visualNodeDisplay.getFrame(), "No node created. selected file was null", "Null file selected", 0);
                return;
            }
            int selectedNodeId = -1;
            try {
                selectedNodeId = Integer.parseInt(selectedNode.getName().split("\\.")[0]);
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(visualNodeDisplay.getFrame(), "No node created. Selected file wasn't a CNPCs dialog node file.", "Wrong file selected", 0);
                return;
            }

            if (visualNodeDisplay.getGroup().getProjectInfo().getNodeById(selectedNodeId) != null) {
                JOptionPane.showMessageDialog(visualNodeDisplay.getFrame(), "No node created. Selected node already exists.", "Node already exists", 0);
                return;
            }

            DialogNode node = null;
            try {
                node = new DialogNodeBuilder(visualNodeDisplay.getGroup().getProjectInfo().getProjectDirectory(), selectedNode.getParentFile().getName(), selectedNodeId).getDialogNode();
            } catch (JSONException | IOException e1) {
                JOptionPane.showMessageDialog(visualNodeDisplay.getFrame(), "No node created. Couldn't build ndoe.", "Couldn't build node", 0);
            }
            VisualNodeShell shell = new VisualNodeShell(visualNodeDisplay.getGroup());
            shell.setDialogNode(node);
            shell.setPosition(visualNodeDisplay.getMouseX()-visualNodeDisplay.getOffsetX(), visualNodeDisplay.getMouseY()-visualNodeDisplay.getOffsetY());
            visualNodeDisplay.getGroup().getNodeHandler().add(shell);
        });

        importProjectNode.addActionListener(e -> {
            //Everything is done in the mouse listener for the project nodes list.
        });

        projectNodesMenu.addMenuListener(new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) {
                groupListModel.clear();
                ArrayList<NodeGroup> groups = visualNodeDisplay.getGroup().getProjectInfo().getGroups();
                for (NodeGroup nodeGroup : groups) {
                    if (nodeGroup != visualNodeDisplay.getGroup()) {
                        groupListModel.addElement(nodeGroup.getName());
                    }
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {/*do nothing*/}

            @Override
            public void menuCanceled(MenuEvent e) {/*do nothing*/}
            
        });

        groupList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String selectedName = groupList.getSelectedValue();
                NodeGroup selectedGroup = visualNodeDisplay.getGroup().getProjectInfo().getGroup(selectedName);
                Object[] possibilities = selectedGroup.getNodeHandler().getArray();
                if (possibilities.length <= 0) {
                    JOptionPane.showMessageDialog(visualNodeDisplay.getFrame(), "This group has no nodes.", "No nodes", JOptionPane.WARNING_MESSAGE);

                }
                VisualNodeShell selectedNode = (VisualNodeShell) JOptionPane.showInputDialog(visualNodeDisplay.getFrame(), "Which node would you like to choose?", "Select Project Node", JOptionPane.PLAIN_MESSAGE, null, possibilities, possibilities[0]);

                if (selectedNode == null) {
                    JOptionPane.showMessageDialog(visualNodeDisplay.getFrame(), "No node selected.", "None selected", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (visualNodeDisplay.getGroup().getNodeHandler().contains(selectedNode)) {
                    JOptionPane.showMessageDialog(visualNodeDisplay.getFrame(), "Node already exists in project.", "Node already exists", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                visualNodeDisplay.getGroup().getNodeHandler().add(new GroupNodeShell(visualNodeDisplay.getOffsetX(), visualNodeDisplay.getOffsetY(), selectedGroup, selectedNode.getDialogNode()));
               
            }
        });

        saveButton.addActionListener(e -> {
            visualNodeDisplay.saveProject();
        });
    }
}
