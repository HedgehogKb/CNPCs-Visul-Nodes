package com.hedgehogkb.ProjectEditorFrame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.ProjectInfo;
import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayMenuBar;

public class ProjectEditorFrame {
    private JFrame frame;
    private ProjectInfo projectInfo;
    private JSplitPane splitPane;

    private JPanel leftPanel;
    private JLabel selectedGroupLabel;
    private JButton openGroupButton;
    private JButton deleteGroupButton;
    private JSeparator horizontalSeperator;
    private JLabel groupNameLabel;
    private JTextArea groupNameTextArea;
    private JButton addGroupButton;

    private JPanel rightPanel;
    private JScrollPane groupListScrollPane;
    private JList<String> groupList;
    private DefaultListModel<String> listModel;


    public ProjectEditorFrame(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;

        this.frame = new JFrame("Project Editor");
        this.frame.setLayout(new GridLayout(0,1));
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.setSize(500, 250);

        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        this.leftPanel = new JPanel();
        this.rightPanel = new JPanel();

        System.out.println("starting initialize group list");
        initializeGroupList();
        System.out.println("done initialize group list. Starting initialize left and right panels.");

        initializeLeftPanelComponents();
        initializeRightPanelComponents();
        System.out.println("done with left and right panels. Starting build frame and handle inputs.");

        buildFrame();

        handleInputs();
        System.out.println("all done");
    }

    public void initializeLeftPanelComponents() {
        this.leftPanel.setBorder(BorderFactory.createEtchedBorder());
        this.leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        selectedGroupLabel = new JLabel("<html>" + "Selected Group:" + "</html>");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 0, 5, 0);
        leftPanel.add(selectedGroupLabel, c);

        openGroupButton = new JButton("Open Group");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 5, 5, 5);
        leftPanel.add(openGroupButton, c);

        deleteGroupButton = new JButton("Delete Group");
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(deleteGroupButton, c);

        horizontalSeperator = new JSeparator(SwingConstants.HORIZONTAL);
        horizontalSeperator.setBackground(Color.black);
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0.5;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 0, 10, 0);
        leftPanel.add(horizontalSeperator, c);


        groupNameLabel = new JLabel("Group Name:");
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0.1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 5, 0);
        leftPanel.add(groupNameLabel, c);

        groupNameTextArea = new JTextArea("");
        groupNameTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 0.8;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 2, 5, 5);
        leftPanel.add(groupNameTextArea, c);

        addGroupButton = new JButton("Add Group");
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,5,0,5);
        leftPanel.add(addGroupButton, c);
    }

    public void initializeRightPanelComponents() {
        this.rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.LINE_AXIS));

        this.listModel = new DefaultListModel<>();

        for (int i = 0; i < projectInfo.getGroups().size(); i++) {
            String groupName = projectInfo.getGroups().get(i).getName();
            if (groupName != null && !groupName.isEmpty()) {
                listModel.addElement(groupName);
            } else {
                JOptionPane.showMessageDialog(frame, "Group name is invalid. It is either blank or already exists.");
            }
        }

        this.groupList = new JList<>(listModel);
        groupList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        this.groupListScrollPane = new JScrollPane(groupList);
        rightPanel.add(groupListScrollPane);

    }

    public void buildFrame() {
        this.splitPane.setLeftComponent(leftPanel);
        this.splitPane.setRightComponent(rightPanel);
        this.splitPane.setDividerLocation(200);
        this.splitPane.setResizeWeight(0.2);

        this.frame.add(splitPane);
        this.frame.setVisible(true);
    }

    public void initializeGroupList() {
        //projectInfo.refreshProjectNodes();
    }

    public void handleInputs() {
        groupList.addListSelectionListener(e -> {
                String selectedGroup = groupList.getSelectedValue();
                selectedGroupLabel.setText("<html>" + "Selected Group: " + selectedGroup + "</html>");
        });

        openGroupButton.addActionListener(e -> {
            String selectedGroupName = groupList.getSelectedValue();
            if (selectedGroupName != null) {
                NodeGroup selectedGroup = projectInfo.getGroup(selectedGroupName);
                selectedGroup.startDisplay();
            } else {
                JOptionPane.showMessageDialog(frame, "No group selected.");
            }
        });

        deleteGroupButton.addActionListener(e -> {
            String selectedGroupName = groupList.getSelectedValue();
            if (selectedGroupName == null) {
                JOptionPane.showMessageDialog(frame, "No group selected.");
                return;
            }

            NodeGroup selectedGroup = projectInfo.getGroup(selectedGroupName);
            
            if (selectedGroup == null) {
                JOptionPane.showMessageDialog(frame, "Somehow this group doesn't exist.");
                return;   
            }

            int confirmDecision = JOptionPane.showConfirmDialog(frame, "Are you sure that you want to delete "+selectedGroupName +"?", "Delete Group", JOptionPane.YES_NO_OPTION);
            
            if (confirmDecision == JOptionPane.YES_OPTION) {
                if (!projectInfo.removeGroup(selectedGroup)) {
                    JOptionPane.showMessageDialog(frame, "Group wasn't successfully deleted.");
                    return;
                }

                listModel.removeElement(selectedGroupName);

            }
        });

        addGroupButton.addActionListener(e -> {
            String groupName = groupNameTextArea.getText().trim();
            if (!groupName.isEmpty() && !listModel.contains(groupName)) {
                listModel.addElement(groupName);
                groupNameTextArea.setText("");

                projectInfo.addGroup(new NodeGroup(groupName, projectInfo));
            } else {
                JOptionPane.showMessageDialog(frame, "Group name is invalid. It is either blank or already exists.");
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (!projectInfo.isProjectSaved()) {
                int response = JOptionPane.showConfirmDialog(frame, "You have unsaved changes. Do you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    System.exit(0);
                }
                } else {
                    frame.dispose();
                    System.exit(0); 
                }
            }
        });
    }
}
