package com.hedgehogkb.ImportingAndExporting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.ProjectInfo;
import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.GroupNodeShell;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;

public class ProjectExporter {
    private ProjectInfo projectInfo;
    private File outputDirectory;
    private JSONObject projectJson;

    public ProjectExporter(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
        this.outputDirectory = projectInfo.getProjectDirectory();

        createProjectJson();
    }

    public void createProjectJson() {

        this.projectJson = new JSONObject();
        projectJson.put("lowestNodeNumber", projectInfo.getLowestNodeNumber());

        for (NodeGroup group : projectInfo.getGroups()) {
            JSONObject groupJson = group.buildJson();
            projectJson.append("nodeGroups", groupJson);
        }
    }

    public void exportProjectInfo() {
        createProjectJson();

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
        File outputFile = new File(outputDirectory, "cnpcsProjectSettings.json");
        if (outputFile.delete()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer = new FileWriter(outputFile);
            String jsonString = projectJson.toString(4);
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void exportProjectNodes() {
        for (int i = 0; i < projectInfo.getGroups().size(); i++) {
            NodeGroup group = projectInfo.getGroups().get(i);
            File groupDirectory = new File(outputDirectory, group.getName());
            if (!groupDirectory.exists()) {
                groupDirectory.mkdirs();
            }

            for (int v = 0; v < group.getNodeHandler().size(); v++) {
                VisualNodeShell curShell = group.getNodeHandler().getIndex(v);

                //group nodes are simply nodes loaded from other groups meaning the dialog node will be exported some other time.
                if (curShell instanceof GroupNodeShell) {
                    System.out.println("Skipping GroupNodeShell: " + curShell.getDialogId());
                    continue;
                }
                
                DialogNode curNode = curShell.getDialogNode();
                JSONObject nodeJson = curNode.buildJson();
                File nodeFile = new File(groupDirectory, curNode.getDialogId() + ".json");
                if (nodeFile.delete()) {
                    try {
                        nodeFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    FileWriter writer = new FileWriter(nodeFile);
                    String jsonString = nodeJson.toString(4).replace("\\n", "\n").replace("\\u2019", "'").replace("\\u2014", "—");
                    writer.write(jsonString);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void export() {
        exportProjectInfo();
        exportProjectNodes();
        projectInfo.setProjectSaved(true);
    }

}
