package com.hedgehogkb;

import com.hedgehogkb.NodeHandler;
import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayFrame;

public class NodeGroup {
    String name;
    NodeHandler nodeHandler;
    VisualNodeDisplayFrame visualNodeDisplayFrame;

    public NodeGroup(String name) {
        this.name = name;
        this.nodeHandler = new NodeHandler(this);
        this.visualNodeDisplayFrame = new VisualNodeDisplayFrame(this);
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
    
    public void setVisible(boolean visible) {
        visualNodeDisplayFrame.setVisible(visible);
    }
}