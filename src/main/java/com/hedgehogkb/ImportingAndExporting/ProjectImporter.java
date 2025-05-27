package com.hedgehogkb.ImportingAndExporting;

import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.ProjectInfo;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;

public class ProjectImporter {
    private File inputDirectory;
    private ProjectInfo projectInfo;

    public ProjectImporter(File inputDirectory) throws FileNotFoundException, JSONException, IOException {
        this.inputDirectory = inputDirectory;
        JSONObject projectSettings = new JSONObject();
        projectSettings = new JSONObject(Files.readString(Path.of(inputDirectory + File.separator + "cnpcsProjectSettings.json")));
        int lowestNodeNumber = projectSettings.getInt("lowestNodeNumber");
        this.projectInfo = new ProjectInfo(inputDirectory,  lowestNodeNumber);
        
        importGroups(projectSettings);
    }

    public void importGroups(JSONObject projectSettings) throws JSONException, IOException {
        JSONArray nodeGroupsJson = projectSettings.getJSONArray("nodeGroups");
        for (int i = 0; i < nodeGroupsJson.length(); i++) {
            String groupName = nodeGroupsJson.getJSONObject(i).getString("name");
            int offsetX = nodeGroupsJson.getJSONObject(i).getInt("offsetX");
            int offsetY = nodeGroupsJson.getJSONObject(i).getInt("offsetY");
            NodeGroup group = new NodeGroup(groupName, projectInfo, offsetX, offsetY);
            //TODO: change Nodes to be lowercase in the exporter
            JSONArray visualNodesJson = nodeGroupsJson.getJSONObject(i).getJSONArray("Nodes");
            for (int v = 0; v < visualNodesJson.length(); v++) {
                int posX = visualNodesJson.getJSONObject(v).getInt("posX");
                int posY = visualNodesJson.getJSONObject(v).getInt("posY");

                VisualNodeShell visualNodeShell = new VisualNodeShell(posX, posY);
                visualNodeShell.setDialogNode(new DialogNodeBuilder(inputDirectory, nodeGroupsJson.getJSONObject(i).getString("name"), visualNodesJson.getJSONObject(v).getInt("nodeId")).getDialogNode());
                group.getNodeHandler().add(visualNodeShell);
            }
            projectInfo.addGroup(group);
        }
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

}
