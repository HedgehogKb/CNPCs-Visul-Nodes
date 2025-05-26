package com.hedgehogkb;

import com.hedgehogkb.NodeHandler;
import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayFrame;
import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayRunner;

public class NodeGroup {
    String name;
    ProjectInfo projectInfo;
    NodeHandler nodeHandler;
    VisualNodeDisplayFrame visualNodeDisplayFrame;
    VisualNodeDisplayRunner visualNodeDisplayRunner;

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
}