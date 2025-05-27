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

    
    public  DialogNodeBuilder(File projectDirectory, String groupName, int nodeId) {
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
        dialogNode.setShowDialogWheel(dialogNodeJson.getBoolean("ShowDialogueWheel"));
        dialogNode.setHideNPC(dialogNodeJson.getBoolean("DialogHideNPC"));
        dialogNode.setDisableEsc(dialogNodeJson.getBoolean("DialogDisableEsc"));
        ArrayList<DialogOption> dialogOptions = buildDialogOption(dialogNodeJson);
        dialogNode.setOptions(dialogOptions);
    }

    public ArrayList<DialogOption> buildDialogOption(JSONObject dialogOptionJson) {
        ArrayList<DialogOption> dialogOptions = new ArrayList<>();
        JSONArray dialogOptionArray = dialogOptionJson.getJSONArray("DialogOptions");
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
}
