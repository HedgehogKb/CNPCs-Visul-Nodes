package com.hedgehogkb;

import org.json.JSONObject;

import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayFrame;
import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayRunner;
import com.hedgehogkb.NodeHandlers.NodeHandler;

public class NodeGroup {
    private String name;
    private ProjectInfo projectInfo;
    private NodeHandler nodeHandler;
    private VisualNodeDisplayFrame visualNodeDisplayFrame;
    private VisualNodeDisplayRunner visualNodeDisplayRunner;

    public NodeGroup(String name, ProjectInfo projectInfo) {
        this.name = name;
        this.projectInfo = projectInfo;
        this.nodeHandler = new NodeHandler(this, projectInfo);
        this.visualNodeDisplayFrame = new VisualNodeDisplayFrame(this);
        this.visualNodeDisplayRunner = new VisualNodeDisplayRunner(visualNodeDisplayFrame);
    }

    public NodeGroup(String name, ProjectInfo projectInfo, NodeHandler nodeHandler) {
        this.name = name;
        this.projectInfo = projectInfo;
        this.nodeHandler = nodeHandler;
        this.visualNodeDisplayFrame = new VisualNodeDisplayFrame(this);
    }

    public NodeGroup(String name, ProjectInfo projectInfo, int offsetX, int offsetY) {
        this.name = name;
        this.projectInfo = projectInfo;
        this.nodeHandler = new NodeHandler(this, projectInfo);
        this.visualNodeDisplayFrame = new VisualNodeDisplayFrame(this, offsetX, offsetY);
        this.visualNodeDisplayRunner = new VisualNodeDisplayRunner(visualNodeDisplayFrame);

    }

    public NodeHandler getNodeHandler() {
        return nodeHandler;
    }


    public VisualNodeDisplayFrame getVisualNodeDisplayFrame() {
        return visualNodeDisplayFrame;
    }

    public String getName() {
        return name;
    }
    
    public void setDisplayVisible(boolean visible) {
        visualNodeDisplayFrame.setVisible(visible);
    }

    public void startDisplay() {
        visualNodeDisplayRunner.start();
        visualNodeDisplayFrame.setVisible(true);
    }

    public void stopDisplay() {
        visualNodeDisplayRunner.stop();
        visualNodeDisplayFrame.setVisible(false);
    }

    public JSONObject buildJson() {
        JSONObject nodeGroupJson = new JSONObject();
        nodeGroupJson.put("name", name);
        nodeGroupJson.put("offsetX", visualNodeDisplayFrame.getOffsetX());
        nodeGroupJson.put("offsetY", visualNodeDisplayFrame.getOffsetY());
        nodeGroupJson.put("nodes", nodeHandler.buildJson());
        return nodeGroupJson;
    }

    public void saveProject() {
        projectInfo.getProjectExporter().export();
    }

    public void setProjectUnsaved() {
        projectInfo.setProjectSaved(false);
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }
}