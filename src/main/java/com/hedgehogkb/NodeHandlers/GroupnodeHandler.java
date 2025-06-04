package com.hedgehogkb.NodeHandlers;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.GroupLayout.Group;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.GroupNodeShell;

public class GroupnodeHandler {
    //private JList<GroupNodeShell> projectNodesList;
    private DefaultListModel<GroupNodeShell> projectNodesListModel;
    private NodeGroup group;

    public GroupnodeHandler(NodeGroup group) {
        projectNodesListModel = new DefaultListModel<>();
        this.group = group;
    }

    public void addGroupNode(NodeGroup group, DialogNode dialogNode) {
        projectNodesListModel.addElement(new GroupNodeShell(0, 0, group, dialogNode));
    }

    /**
     * this may not be necessary because the group node points to a nodeId, and so it would automatically update.
     * @return
     */
    //public void updateGroupNode(int nodeId)
    
    public DefaultListModel<GroupNodeShell> cullList(DefaultListModel<GroupNodeShell> model) {
        DefaultListModel<GroupNodeShell> culledModel = new DefaultListModel<>();
        for (int i = 0; i < model.size(); i++) {
            if (!model.getElementAt(i).getGroup().equals(group)) {
            culledModel.addElement(model.getElementAt(i));
            }
        }
        return culledModel;
    }

    public DefaultListModel getList() {
        return this.projectNodesListModel;
    }

    public void setList(DefaultListModel<GroupNodeShell> projectNodesListModel) {
        this.projectNodesListModel = cullList(projectNodesListModel);
    }

}
