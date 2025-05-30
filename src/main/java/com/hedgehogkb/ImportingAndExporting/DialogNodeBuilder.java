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

    
    public  DialogNodeBuilder(File projectDirectory, String groupName, int nodeId) throws JSONException, IOException {
        this.projectDirectory = projectDirectory;
        this.groupName = groupName;
        this.nodeId = nodeId;
        this.dialogNodeFile = new File(projectDirectory.getAbsolutePath() + File.separator + groupName + File.separator + nodeId + ".json");

        JSONObject dialogNodeJson = new JSONObject();
        dialogNodeJson = new JSONObject(Files.readString(Path.of(dialogNodeFile.getAbsolutePath())));


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
        
        addAvailablilityVars(dialogNodeJson, dialogNode);
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
        
        for (int i = dialogOptions.size(); i < 6; i++) {
            dialogOptions.add(new DialogOption(i));
        }
        return dialogOptions;
    }

    public void addAvailablilityVars(JSONObject dialogNodeJson, DialogNode dialogNode) {
        for(int i = 1; i <= 4; i++) {
            String dialogKey = "AvailabilityDialog";
            String dialogIdKey = "AvailabilityDialog";
            if (i != 1) {
                dialogKey += i;
                dialogIdKey += i;
            }
            dialogIdKey += "Id";

            dialogNode.setAvailabilityDialog(dialogNodeJson.getInt(dialogKey), dialogNodeJson.getInt(dialogIdKey), i-1);
        }

        for(int i = 1; i <= 4; i++) {
            String questKey = "AvailabilityQuest";
            String questIdKey = "AvailabilityQuest";
            if (i != 1) {
                questKey += i;
                questIdKey += i;
            }
            questIdKey += "Id";
            //System.out.println(dialogNodeJson.getInt(questIdKey));
            dialogNode.setAvailabilityQuest(dialogNodeJson.getInt(questKey), dialogNodeJson.getInt(questIdKey), i-1);
        }

        for(int i = 1; i <= 2; i++) {
            String scoreboardTypeKey = "AvailabilityScoreboard";
            String scoreboardValueKey = "AvailabilityScoreboard";
            String scoreboardObjectiveKey = "AvailabilityScoreboard";
            if (i != 1) {
                scoreboardTypeKey += i;
                scoreboardValueKey += i;
                scoreboardObjectiveKey += i;
            }
            scoreboardTypeKey += "Type";
            scoreboardValueKey += "Value";
            scoreboardObjectiveKey += "Objective";

            dialogNode.setAvailabilityScoreboard(dialogNodeJson.getInt(scoreboardTypeKey), dialogNodeJson.getInt(scoreboardValueKey), dialogNodeJson.getString(scoreboardObjectiveKey), i-1);
        }

        for(int i = 1; i <= 2; i++) {
            String factionKey = "AvailabilityFaction";
            String factionIdKey = "AvailabilityFaction";
            String factionStanceKey = "AvailabilityFaction";
            if (i != 1) {
                factionKey += i;
                factionIdKey += i;
                factionStanceKey += i;
            }
            factionIdKey += "Id";
            factionStanceKey += "Stance";

            dialogNode.setAvailabilityFaction(dialogNodeJson.getInt(factionKey), dialogNodeJson.getInt(factionIdKey), dialogNodeJson.getInt(factionStanceKey), i-1);
        }

        for(int i = 1; i <= 2; i++) {
            String factionKey = "OptionFactions" + i;
            String factionPointsKey = "OptionFaction" + i + "Points";
            String decreaseFactionKey = "DecreaseFaction" + i + "Points";

            dialogNode.setOptionFaction(dialogNodeJson.getInt(factionKey), dialogNodeJson.getInt(factionPointsKey), jsonToBoolean(dialogNodeJson, decreaseFactionKey), i-1);
        }
    }

    public boolean intToBoolean(int num) {
        return num != 0;
    }

    public boolean stringToBoolean(String string) {
        try{
            if (Integer.parseInt(string.substring(0, 1)) == 1) {
                return true;
            }
        } catch(NumberFormatException e) {
            System.out.println("there was an error with the string to boolean :(");
            return false;
        }
        return false;
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
        } else if (json.get(key) instanceof String) {
            return stringToBoolean(json.getString(key));
        } else {
            return false; // Default case if the type is unexpected
        }
    }
}
