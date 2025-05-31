package com.hedgehogkb.ImportingAndExporting;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.json.JSONException;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.ProjectInfo;
import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;
import com.hedgehogkb.Exceptions.ProjectExistsException;

public class CnpcNodesConverter {
    private File inputDirectory;
    private ProjectInfo projectInfo;

    public CnpcNodesConverter(File inputDirectory) throws ProjectExistsException{
        this.inputDirectory = inputDirectory;
        this.projectInfo = new ProjectInfo(inputDirectory);
        
        if (new File(inputDirectory, "cnpcsProjectSettings.json").exists()) {
            System.out.println("This isn't a CNPCs node project");
            throw new ProjectExistsException("Already a Visual Node Project");
        }

        File[] folders = inputDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        for (int i = 0; i < folders.length; i++) {
            NodeGroup group = createGroup(folders[i]);
            projectInfo.addGroup(group);
        }
    }

    public NodeGroup createGroup(File groupDirectory) {
        File[] nodeFiles = groupDirectory.listFiles();
        NodeGroup group = new NodeGroup(groupDirectory.getName(), projectInfo);
        int groupX = 100;
        for (int i = 0; i < nodeFiles.length; i++) {
            File nodeFile = nodeFiles[i];
            DialogNode node = null;
            int nodeId = -1;
            if (nodeFile.getName().length() < 1) {
                continue;
            }
            try {
                nodeId = Integer.parseInt(nodeFile.getName().split("\\.")[0]);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "One file isn't a node file.", "Not node file", 0);
                continue;
            }
            System.out.println("parsing node: "+nodeId);
            try {
                node = new DialogNodeBuilder(inputDirectory, groupDirectory.getName(), nodeId).getDialogNode();
            } catch (IOException | JSONException e) {
                JOptionPane.showMessageDialog(null, "DialogNodeBuilder failed to build the node: " + nodeId, "Building node failed", 0);
                continue;
            }
            VisualNodeShell shell = new VisualNodeShell(group);
            shell.setDialogNode(node);
            shell.setPosition(groupX, 300);
            groupX += 100;
            group.getNodeHandler().add(shell);
        }
        return group;
    }

    public ProjectInfo getProjectInfo() {
        return this.projectInfo;
    }
}
