package com.hedgehogkb.NodeHandlers;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.ProjectInfo;
import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.GroupNodeShell;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;

public class NodeHandler {
    private ArrayList<VisualNodeShell> visualNodeShells;
    private HashMap<Integer, VisualNodeShell> visualNodeShellsByID;
    private ProjectInfo projectInfo;
    private NodeGroup group;

    public NodeHandler(NodeGroup group) {
        this.group = group;
        this.visualNodeShells = new ArrayList<>();
        this.visualNodeShellsByID = new HashMap<>();
    }

    public NodeHandler(NodeGroup group, ProjectInfo projectInfo) {
        this(group);
        this.projectInfo = projectInfo;
    }

    /**
     * Adds a visual node shell to the handler.
     * This method takes in an existing VisualNodeShell object.
     * @param visualNodeShell
     */
    public void add(VisualNodeShell visualNodeShell) {
        visualNodeShells.add(visualNodeShell);
        visualNodeShellsByID.put(visualNodeShell.getDialogId(), visualNodeShell);
    }

    /**
     * Adds a VisualNodeShell to the handler by creating a new one from the given inputs.
     * This takes an existing DialogNode and assigns it to the new VisualNodeShell.
     * @param node
     * @param mouseX
     * @param mouseY
     * @param offsetX
     * @param offsetY
     */
    public void add(DialogNode node, int mouseX, int mouseY, int offsetX, int offsetY) {
        visualNodeShells.add(new VisualNodeShell(mouseX - offsetX, mouseY - offsetY, offsetX, offsetY, node, group));
        visualNodeShellsByID.put(node.getDialogId(),visualNodeShells.get(visualNodeShells.size() - 1));
    }

    /**
     * Adds a new VisualNodeShell to the handler.
     * This method creates a new DialogNode with the next available ID.
     * @param mouseX
     * @param mouseY
     * @param offsetX
     * @param offsetY
     */
    public void add(int mouseX, int mouseY, int offsetX, int offsetY) {
        DialogNode curNode = new DialogNode(getNextAvailableNodeId());

        VisualNodeShell curVisualNode = new VisualNodeShell(mouseX - offsetX, mouseY - offsetY, offsetX , offsetY, curNode, group);
        visualNodeShells.add(curVisualNode);
        visualNodeShellsByID.put(curVisualNode.getDialogId(), curVisualNode);
    }

    public void add(int mouseX, int mouseY, int offsetX, int offsetY, DialogNode node) {
        VisualNodeShell curVisualNode = new VisualNodeShell(mouseX - offsetX, mouseY - offsetY, offsetX , offsetY, node, group);
        visualNodeShells.add(curVisualNode);
        visualNodeShellsByID.put(curVisualNode.getDialogId(), curVisualNode);
    }



    public VisualNodeShell removeVisualNode(VisualNodeShell node) {
        visualNodeShells.remove(node);
        if (projectInfo.getLowestNodeNumber() == node.getDialogId()+1 && !(node instanceof GroupNodeShell)) {
            projectInfo.setLowestNodeNumber(projectInfo.getLowestNodeNumber() -1);
        }
        return visualNodeShellsByID.remove(node.getDialogId());
    }

    public VisualNodeShell get(int nodeId) {
        return visualNodeShellsByID.get(nodeId);
    }

    /**
     * Returns the VisualNodeShell at the specified index.
     * This is to be used for other classes to iterate through the nodes
     * and porform their own operations.
     * @param index
     * @return VisualNodeShell
     */
    public VisualNodeShell getIndex(int index) {
        return visualNodeShells.get(index);
    }

    public VisualNodeShell removeVisualNode(int nodeId) {
        VisualNodeShell node = visualNodeShellsByID.get(nodeId);
        if (node != null) {
            visualNodeShells.remove(node);
            visualNodeShellsByID.remove(nodeId);
        }
        return node;
    }

    public int getNextAvailableNodeId() {
        int nextId = projectInfo.getLowestNodeNumber();
        while (visualNodeShellsByID.containsKey(nextId)) {
            nextId++;
        }
        projectInfo.setLowestNodeNumber(nextId + 1);
        return nextId;
    }

    public boolean contains(VisualNodeShell node) {
        return visualNodeShellsByID.containsKey(node.getDialogId());
    }

    public void moveToFront(VisualNodeShell node) {
        visualNodeShells.remove(node);
        visualNodeShells.add(0, node);
    }

    public VisualNodeShell[] getArray() {
        VisualNodeShell[] array = new VisualNodeShell[size()];
        for (int i = 0; i < size(); i++) {
            array[i] = visualNodeShells.get(i);
        }
        return array;
    }

    public int size() {
        return visualNodeShells.size();
    }

    public ArrayList<JSONObject> buildJson() {
        ArrayList<JSONObject> visualNodeJsons = new ArrayList<>();
        for (VisualNodeShell visualNodeShell : visualNodeShells) {
            visualNodeJsons.add(visualNodeShell.buildJson());
        }
        return visualNodeJsons;
    }
}
