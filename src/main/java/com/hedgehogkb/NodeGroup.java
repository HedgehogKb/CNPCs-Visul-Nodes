package com.hedgehogkb;

import com.hedgehogkb.NodeHandler;
import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayFrame;
import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayRunner;

public class NodeGroup {
    String name;
    NodeHandler nodeHandler;
    VisualNodeDisplayFrame visualNodeDisplayFrame;
    VisualNodeDisplayRunner visualNodeDisplayRunner;

    public NodeGroup(String name) {
        this.name = name;
        this.nodeHandler = new NodeHandler(this);
        this.visualNodeDisplayFrame = new VisualNodeDisplayFrame(this);
        this.visualNodeDisplayRunner = new VisualNodeDisplayRunner(visualNodeDisplayFrame);
    }

    public NodeGroup(String name, NodeHandler nodeHandler) {
        this.name = name;
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