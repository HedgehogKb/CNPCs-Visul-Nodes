package com.hedgehogkb.ImportingAndExporting;

import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hedgehogkb.NodeGroup;
import com.hedgehogkb.ProjectInfo;
import com.hedgehogkb.DialogNodeComponents.GroupNodeShell;
import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;
import com.hedgehogkb.NodeHandlers.NodeHandler;

public class ProjectImporter {
    private File inputDirectory;
    private ProjectInfo projectInfo;

    public ProjectImporter(File inputDirectory) throws FileNotFoundException, JSONException, IOException {
        this.inputDirectory = inputDirectory;
        JSONObject projectSettings = new JSONObject();
        projectSettings = new JSONObject(Files.readString(Path.of(inputDirectory + File.separator + "cnpcsProjectSettings.json")));
        int lowestNodeNumber = projectSettings.getInt("lowestNodeNumber");
        this.projectInfo = new ProjectInfo(inputDirectory,  lowestNodeNumber);
        projectInfo.setProjectSaved(true);
        importGroups(projectSettings);
        importNodes(projectSettings);
    }

    public void importGroups(JSONObject projectSettings) throws JSONException, IOException {
        JSONArray nodeGroupsJson = projectSettings.getJSONArray("nodeGroups");
        for (int i = 0; i < nodeGroupsJson.length(); i++) {
            String groupName = nodeGroupsJson.getJSONObject(i).getString("name");
            int offsetX = nodeGroupsJson.getJSONObject(i).getInt("offsetX");
            int offsetY = nodeGroupsJson.getJSONObject(i).getInt("offsetY");
            NodeGroup group = new NodeGroup(groupName, projectInfo, offsetX, offsetY);
            projectInfo.addGroup(group);
        }
    }

    /**
     * Imports the nodes using the projectSettings json file and the individual dialog node json files. This step is done seperately so that
     * group nodes can be assigned to the correct group.
     * @param projectSettings
     * @throws JSONException
     * @throws IOException
     */
    public void importNodes(JSONObject projectSettings) throws JSONException, IOException{
        for (int i = 0; i < projectInfo.getGroups().size(); i++) {
            JSONArray nodeGroupsJson = projectSettings.getJSONArray("nodeGroups");
            NodeGroup group = projectInfo.getGroups().get(i);
            JSONArray visualNodesJson = nodeGroupsJson.getJSONObject(i).getJSONArray("nodes");
            for (int v = 0; v < visualNodesJson.length(); v++) {
                int posX = visualNodesJson.getJSONObject(v).getInt("posX");
                int posY = visualNodesJson.getJSONObject(v).getInt("posY");

                VisualNodeShell visualNodeShell;
                if (visualNodesJson.getJSONObject(v).getString("type").equals("groupNode")) {
                    visualNodeShell = new GroupNodeShell(posX, posY, projectInfo.getGroup(visualNodesJson.getJSONObject(v).getString("groupName")));
                    visualNodeShell.setDialogNode(new DialogNodeBuilder(inputDirectory, visualNodesJson.getJSONObject(v).getString("groupName"), visualNodesJson.getJSONObject(v).getInt("nodeId")).getDialogNode());
                } else {
                    visualNodeShell = new VisualNodeShell(posX, posY, group);
                    visualNodeShell.setDialogNode(new DialogNodeBuilder(inputDirectory, nodeGroupsJson.getJSONObject(i).getString("name"), visualNodesJson.getJSONObject(v).getInt("nodeId")).getDialogNode());
                }
                    group.getNodeHandler().add(visualNodeShell);
            }
        }        
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

}
