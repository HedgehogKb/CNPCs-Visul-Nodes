package com.hedgehogkb;

import java.io.File;

import com.hedgehogkb.ProjectEditorFrame.ProjectEditorFrame;

public class ProjectEditorTest {
    public static void main(String[] args) {
        ProjectInfo projectInfo = new ProjectInfo(new File("testProject"), 1);
        NodeGroup group1 = new NodeGroup("Test Group 1", projectInfo);
        projectInfo.addGroup(group1);
        projectInfo.addGroup(new NodeGroup("Test Group 2", projectInfo));
        projectInfo.addGroup(new NodeGroup("Test Group 3", projectInfo));

        ProjectEditorFrame projectEditorFrame = new ProjectEditorFrame(projectInfo);

        try{
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(group1.buildJson().toString(4));
    }
}
