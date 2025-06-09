package com.hedgehogkb.DialogNodeComponents;

import java.util.ArrayList;

import org.json.JSONObject;

public class DialogNode {
    private int dialogId;
    private String dialogTitle;

    //simple boolean or string variables;
    private String dialogText;
    private String dialogCommand;
    private boolean showDialogWheel;
    private boolean hideNPC;
    private boolean disableEsc;
    private int dialogQuest;

    //availability options:
        //dialog Options:
    private int[] availabilityDialogIds;
    private int[] availabilityDialogs;

        //quest options:
    private int[] availabilityQuests;
    private int[] availabilityQuestIds;

        //scoreboard options:
    private int[] availabilityScoreboardTypes;
    private int[] availabilityScoreboardValues;
    private String[] availabilityScoreboardObjectives;

        //faction options:
    private int[] availabilityFactions;
    private int[] availabilityFactionsId;
    private int[] availabilityFactionStance;
        //other
    private int availabilityPlayerLevel;
    private int availabilityDayTime;

    //faction options
    private int[] optionFactions;
    private int[] optionFactionPoints;
    private boolean[] decreaseOptionFactionPoints;

    //dialog options
    private ArrayList<DialogOption> options;

    enum availabilityQuestTypes {
        ALWAYS,
        AFTER,
        BEFORE,
        WHEN_ACTIVE,
        WHEN_NOT_ACTIVE,
        COMPLETED,
        CAN_START,
    }
    
    enum availabilityDayTimeTypes {
        WHOLE_DAY,
        DURING_THE_NIGHT,
        DURING_THE_DAY,
    }

    public DialogNode() {
        this.dialogId = 1;
        this.dialogTitle = "[title]";
        this.dialogText = "[text]";
        this.dialogCommand = "";
        this.showDialogWheel = false;
        this.hideNPC = false;
        this.disableEsc = false;

        this.availabilityDialogIds = new int[4];
        this.availabilityDialogs = new int[4];

        this.availabilityQuests = new int[4];
        this.availabilityQuestIds = new int[4];

        this.availabilityScoreboardTypes = new int[2];
        this.availabilityScoreboardValues = new int[2];
        this.availabilityScoreboardObjectives = new String[2];
        for (int i = 0; i < availabilityScoreboardObjectives.length; i++) {
            availabilityScoreboardObjectives[i] = "";
        }

        this.availabilityFactions = new int[2];
        this.availabilityFactionsId = new int[2];
        this.availabilityFactionStance = new int[2];

        this.availabilityPlayerLevel = 0;
        this.availabilityDayTime = 0;

        this.optionFactions = new int[2];
        this.optionFactionPoints = new int[2];
        this.decreaseOptionFactionPoints = new boolean[2];

        this.options = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            options.add(new DialogOption(i));
        }
    }

    public DialogNode(int dialogId) {
        this();
        this.dialogId = dialogId;
    }

    public JSONObject buildJson() {
        JSONObject dialogJsonWrapper = new JSONObject();
        //distincitive title and id
        dialogJsonWrapper.put("DialogId", dialogId);
        dialogJsonWrapper.put("DialogTitle", dialogTitle);

        //dialog values with single values
        dialogJsonWrapper.put("DialogText", dialogText);
        dialogJsonWrapper.put("DialogCommand", dialogCommand);
        dialogJsonWrapper.put("DialogShowWheel", booleanToInt(showDialogWheel));
        dialogJsonWrapper.put("DialogHideNPC", booleanToInt(hideNPC));
        dialogJsonWrapper.put("DialogDisableEsc", booleanToInt(disableEsc));
        dialogJsonWrapper.put("DialogQuest", dialogQuest);

        //dialog options array
        dialogJsonWrapper.put("Options", buildOptionsJson());

        addAvailabilityDialogJson(dialogJsonWrapper);
        addAvailabilityQuestJson(dialogJsonWrapper);
        addAvailabilityScoreboardJson(dialogJsonWrapper);
        addAvailabilityFactionJson(dialogJsonWrapper);

        optionFactionJson(dialogJsonWrapper);

        dialogJsonWrapper.put("AvailabilityMinPlayerLevel", availabilityPlayerLevel);
        dialogJsonWrapper.put("AvailabilityDayTime", availabilityDayTime);

        dialogJsonWrapper.put("ModRev", 18);

        dialogJsonWrapper.put("DialogMail", new JSONObject("{\r\n" + //
                        "        \"Sender\": \"\",\r\n" + //
                        "        \"BeenRead\": 0b,\r\n" + //
                        "        \"Message\": {\r\n" + //
                        "        },\r\n" + //
                        "        \"MailItems\": [\r\n" + //
                        "        ],\r\n" + //
                        "        \"MailQuest\": -1,\r\n" + //
                        "        \"TimePast\": 1745803250769L,\r\n" + //
                        "        \"Time\": 0L,\r\n" + //
                        "        \"Subject\": \"\"\r\n" + //
                        "    }"));


        return dialogJsonWrapper;

    }

    public ArrayList<JSONObject> buildOptionsJson() {
        ArrayList<JSONObject> optionsJson = new ArrayList<>();
        for (DialogOption option : options) {
            optionsJson.add(option.buildJson());
        }
        return optionsJson;
    }

    public void addAvailabilityDialogJson(JSONObject dialogJsonWrapper) {
        for(int i = 1; i <= 4; i++) {
            String dialogKey = "AvailabilityDialog";
            String dialogIdKey = "AvailabilityDialog";
            if (i != 1) {
                dialogKey += i;
                dialogIdKey += i;
            }
            dialogIdKey += "Id";
            dialogJsonWrapper.put(dialogKey, availabilityDialogs[i - 1]);
            dialogJsonWrapper.put(dialogIdKey, availabilityDialogIds[i - 1]);
        }
    }

    public void addAvailabilityQuestJson(JSONObject dialogJsonWrapper) {
        for(int i = 1; i <= 4; i++) {
            String questKey = "AvailabilityQuest";
            String questIdKey = "AvailabilityQuest";
            if (i != 1) {
                questKey += i;
                questIdKey += i;
            }
            questIdKey += "Id";
            dialogJsonWrapper.put(questKey, availabilityQuests[i - 1]);
            dialogJsonWrapper.put(questIdKey, availabilityQuestIds[i - 1]);
        }
    }

    public void addAvailabilityScoreboardJson(JSONObject dialogJsonWrapper) {
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

            dialogJsonWrapper.put(scoreboardTypeKey, availabilityScoreboardTypes[i - 1]);
            dialogJsonWrapper.put(scoreboardValueKey, availabilityScoreboardValues[i - 1]);
            dialogJsonWrapper.put(scoreboardObjectiveKey, availabilityScoreboardObjectives[i - 1]);
        }
    }

    public void addAvailabilityFactionJson(JSONObject dialogJsonWrapper) {
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

            dialogJsonWrapper.put(factionKey, availabilityFactions[i - 1]);
            dialogJsonWrapper.put(factionIdKey, availabilityFactionsId[i - 1]);
            dialogJsonWrapper.put(factionStanceKey, availabilityFactionStance[i - 1]);
        }
    }

    public void optionFactionJson(JSONObject dialogJsonWrapper) {
        for(int i = 1; i <= 2; i++) {
            String factionKey = "OptionFactions" + i;
            String factionPointsKey = "OptionFaction" + i + "Points";
            String decreaseFactionKey = "DecreaseFaction" + i + "Points";

            dialogJsonWrapper.put(factionKey, optionFactions[i - 1]);
            dialogJsonWrapper.put(factionPointsKey, optionFactionPoints[i - 1]);
            dialogJsonWrapper.put(decreaseFactionKey, booleanToInt(decreaseOptionFactionPoints[i - 1]));

        }
    }

    public int booleanToInt(boolean value) {
        return value ? 1 : 0;
    }

    //getters and setters

    public int getDialogId() {
        return this.dialogId;
    }
    public void setDialogId(int dialogId) {
        this.dialogId = dialogId;
    }
    
    public ArrayList<DialogOption> getOptions() {
        return this.options;
    }
    public void setOptions(ArrayList<DialogOption> options) {
        this.options = options;
    }

    public DialogOption getOption(int optionId) {
        return options.get(optionId);
    }


    public String getDialogTitle() {
        return this.dialogTitle;
    }
    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public String getDialogText() {
        return this.dialogText;
    }
    public void setDialogText(String dialogText) {
        this.dialogText = dialogText;
    }

    public String getDialogCommand() {
        return this.dialogCommand;
    }
    public void setDialogCommand(String dialogCommand)  {
        this.dialogCommand = dialogCommand;
    }

    public boolean getIsHideNPC() {
        return this.hideNPC;
    }
    public void setHideNPC(boolean hideNPC) {
        this.hideNPC = hideNPC;
    }

    public boolean getIsShowDialogWheel() {
        return this.showDialogWheel;
    }
    public void setShowDialogWheel(boolean showDialogWheel) {
        this.showDialogWheel = showDialogWheel;
    }

    public boolean getIsDisableEsc() {
        return this.disableEsc;
    }
    public void setDisableEsc(boolean disableEsc) {
        this.disableEsc = disableEsc;
    }

    public int getDialogQuest() {
        return this.dialogQuest;
    }
    public void setDialogQuest(int questId) {
        this.dialogQuest = questId;
    }

    public void setAvailabilityDialog(int availabilityDialog, int availabilityDialogId, int index) {
        this.availabilityDialogs[index] = availabilityDialog;
        this.availabilityDialogIds[index] = availabilityDialogId;
    }

    public void setAvailabilityQuest(int availabilityQuest, int availabilityQuestId, int index) {
        this.availabilityQuests[index] = availabilityQuest;
        this.availabilityQuestIds[index] = availabilityQuestId;
    }

    public void setAvailabilityScoreboard(int availabilityScoreboardType, int availabilityScoreboardValue, String availabilityScoreboardObjective, int index) {
        this.availabilityScoreboardTypes[index] = availabilityScoreboardType;
        this.availabilityScoreboardObjectives[index] = availabilityScoreboardObjective;
        this.availabilityScoreboardValues[index] = availabilityScoreboardValue;
    }

    public void setAvailabilityFaction(int availabilityFaction, int availabilityFactionId, int availabilityFactionStance, int index) {
        this.availabilityFactions[index] = availabilityFaction;
        this.availabilityFactionsId[index] = availabilityFactionId;
        this.availabilityFactionStance[index] = availabilityFactionStance;
    }

    public void setOptionFaction(int optionFaction, int optionFactionPoints, boolean decreaseOptionFactionPoints, int index) {
        this.optionFactions[index] = optionFaction;
        this.optionFactionPoints[index] = optionFactionPoints;
        this.decreaseOptionFactionPoints[index] = decreaseOptionFactionPoints;
    }
    

}
