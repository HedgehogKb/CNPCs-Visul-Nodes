package com.hedgehogkb;

import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.DialogNodeComponents.GroupNodeShell;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;
import com.hedgehogkb.ImportingAndExporting.ProjectExporter;
import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayMenuBar;
import com.hedgehogkb.NodeHandlers.NodeHandler;
import com.hedgehogkb.ProjectEditorFrame.ProjectEditorFrame;

public class ProjectInfo {
    ArrayList<NodeGroup> groups;
    File projectDirectory;
    int lowestNodeNumber;
    ProjectExporter projectExporter;
    boolean projectSaved;

    public ProjectInfo(File projectDirectory) {
        this.projectDirectory = projectDirectory;
        this.groups = new ArrayList<>();
        this.lowestNodeNumber = 1;
        this.projectExporter = new ProjectExporter(this);
        this.projectSaved = false;
    }

    public ProjectInfo(File projectDirectory, int lowestNodeNumber) {
        this(projectDirectory);
        this.lowestNodeNumber = lowestNodeNumber;
        this.projectExporter = new ProjectExporter(this);
        this.projectSaved = false;
    }

    public ProjectInfo(File projectDirectory, int lowestNodeNumber, boolean projectSaved) {
        this(projectDirectory, lowestNodeNumber);
        this.projectSaved = projectSaved;
    }

    public void addGroup(NodeGroup group) {
        groups.add(group);
    }

    public Boolean removeGroup(NodeGroup group) {
        File groupDirectory = new File(projectDirectory, group.getName());
        boolean deleted = recurseiveDelete(groupDirectory) && groups.remove(group);
        projectExporter.export();
        this.projectSaved = true;
        return deleted;
    }

    //this is defenitly not the best method.
    public Boolean recurseiveDelete(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.listFiles() == null || file.listFiles().length == 0) {
            return file.delete();
        }

        File[] children = file.listFiles();
        for (File child : children) {
            if (!recurseiveDelete(child)) {
                return false;
            }
        }

        return file.delete();
    }

    public ArrayList<NodeGroup> getGroups() {
        return groups;
    }

    public NodeGroup getGroup(String groupName) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getName().equals(groupName)) {
                return groups.get(i);
            }
        }
        return null;
    }

    /**
     * Removes all visual nodes that have a matching id.
     * This means that any project nodes will be deleted when the main node is deleted.
     * @param nodeId
     */
    public void removeVisualNode(int nodeId) {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).getNodeHandler().removeVisualNode(nodeId);
        }
        if (nodeId == lowestNodeNumber-1) {
            lowestNodeNumber--;
        }
    }

    public int getLowestNodeNumber() {
        return lowestNodeNumber;
    }

    public void setLowestNodeNumber(int lowestNodeNumber) {
        this.lowestNodeNumber = lowestNodeNumber;
    } 

    public File getProjectDirectory() {
        return projectDirectory;
    }

    public ProjectExporter getProjectExporter() {
        return projectExporter;
    }

    public boolean isProjectSaved() {
        return projectSaved;
    }
    public void setProjectSaved(boolean projectSaved) {
        this.projectSaved = projectSaved;
    }

    public VisualNodeShell getNodeById(int dialogId) {
        for (NodeGroup group : groups) {
            VisualNodeShell node = group.getNodeHandler().get(dialogId);
            if (node != null) {
                return node;
            }
        }
        return null;
    }

}