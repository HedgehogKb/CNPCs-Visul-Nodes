package com.hedgehogkb.ImportingAndExporting;

import java.awt.Dialog;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.DialogOption;

public class DialogNodeBuilder {
    private File projectDirectory;
    private File dialogNodeFile;
    private String groupName;
    private int nodeId;
    private DialogNode dialogNode;

    
    public  DialogNodeBuilder(File projectDirectory, String groupName, int nodeId) throws JSONException {
        this.projectDirectory = projectDirectory;
        this.groupName = groupName;
        this.nodeId = nodeId;
        this.dialogNodeFile = new File(projectDirectory.getAbsolutePath() + File.separator + groupName + File.separator + nodeId + ".json");

        JSONObject dialogNodeJson = new JSONObject();
        try {
            dialogNodeJson = new JSONObject(Files.readString(Path.of(dialogNodeFile.getAbsolutePath())));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        this.dialogNode = new DialogNode();
        dialogNode.setDialogId(nodeId);
        dialogNode.setDialogTitle(dialogNodeJson.getString("DialogTitle"));
        dialogNode.setDialogText(dialogNodeJson.getString("DialogText"));
        dialogNode.setDialogCommand(dialogNodeJson.getString("DialogCommand"));
        dialogNode.setShowDialogWheel(jsonToBoolean(dialogNodeJson, "DialogShowWheel"));
        dialogNode.setHideNPC(jsonToBoolean(dialogNodeJson,"DialogHideNPC"));
        dialogNode.setDisableEsc(jsonToBoolean(dialogNodeJson,"DialogDisableEsc"));
        ArrayList<DialogOption> dialogOptions = buildDialogOption(dialogNodeJson);
        dialogNode.setOptions(dialogOptions);
    }

    public ArrayList<DialogOption> buildDialogOption(JSONObject dialogOptionJson) throws JSONException {
        ArrayList<DialogOption> dialogOptions = new ArrayList<>();
        JSONArray dialogOptionArray = dialogOptionJson.getJSONArray("Options");
        for(int i = 0; i < dialogOptionArray.length(); i++) {
            JSONObject optionJson = dialogOptionArray.getJSONObject(i).getJSONObject("Option");
            DialogOption dialogOption = new DialogOption(i);
            dialogOption.setOptionType(optionJson.getInt("OptionType"));
            dialogOption.setOptionCommand(optionJson.getString("DialogCommand"));
            dialogOption.setDialog(optionJson.getInt("Dialog"));
            dialogOption.setTitle(optionJson.getString("Title"));
            dialogOption.setDialogColor(optionJson.getInt("DialogColor"));
            dialogOptions.add(dialogOption);
        }
        return dialogOptions;
    }

    public boolean intToBoolean(int num) {
        return num != 0;
    }

    public DialogNode getDialogNode() {
        return dialogNode;
    }

    public Boolean jsonToBoolean(JSONObject json, String key) {
        if (!json.has(key)) {
            return false;
        }

        if (json.get(key) instanceof Boolean) {
            return json.getBoolean(key);
        } else if (json.get(key) instanceof Integer) {
            return intToBoolean(json.getInt(key));
        } else {
            return false; // Default case if the type is unexpected
        }
    }
}
