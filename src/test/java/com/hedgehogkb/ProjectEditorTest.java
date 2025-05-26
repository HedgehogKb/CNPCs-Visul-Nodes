package com.hedgehogkb;

import java.io.File;

import com.hedgehogkb.ProjectEditorFrame.ProjectEditorFrame;

public class ProjectEditorTest {
    public static void main(String[] args) {
        ProjectInfo projectInfo = new ProjectInfo(new File("testProject"), 1);
        projectInfo.addGroup(new NodeGroup("Test Group 1", projectInfo));
        projectInfo.addGroup(new NodeGroup("Test Group 2", projectInfo));
        projectInfo.addGroup(new NodeGroup("Test Group 3", projectInfo));

        ProjectEditorFrame projectEditorFrame = new ProjectEditorFrame(projectInfo);
        System.out.println("Project Editor Frame initialized.");
    }
}
