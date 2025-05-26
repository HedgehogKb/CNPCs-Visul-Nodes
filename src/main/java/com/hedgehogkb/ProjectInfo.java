package com.hedgehogkb;

import java.io.File;
import java.util.ArrayList;

import com.hedgehogkb.NodeGroup;

public class ProjectInfo {
    ArrayList<NodeGroup> groups;
    File projectDirectory;
    int lowestNodeNumber;

    public ProjectInfo(File projectDirectory) {
        this.projectDirectory = projectDirectory;
        this.groups = new ArrayList<>();
        this.lowestNodeNumber = 1;
    }

    public ProjectInfo(File projectDirectory, int lowestNodeNumber) {
        this(projectDirectory);
        this.lowestNodeNumber = lowestNodeNumber;
    }

    public void addGroup(NodeGroup group) {
        groups.add(group);
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

    public int getLowestNodeNumber() {
        return lowestNodeNumber;
    }

    public void setLowestNodeNumber(int lowestNodeNumber) {
        this.lowestNodeNumber = lowestNodeNumber;
    } 
}