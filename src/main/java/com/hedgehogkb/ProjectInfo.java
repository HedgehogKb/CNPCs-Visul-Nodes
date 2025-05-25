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

    public int getLowestNodeNumber() {
        return lowestNodeNumber;
    }
}