package com.hedgehogkb;

import org.json.JSONObject;

public class DialogOption {
    private int optionSlot;
    private int optionType;
    private String title;
    private String optionCommand;
    private int dialog;
    private int dialogColor;



    public DialogOption() {
        optionSlot = 0;
        optionType = 0;
        optionCommand = "";
        dialog = -1;
        title = "[title]";
        dialogColor = 14737632;
    }

    public DialogOption(int optionSlot) {
        this();
        this.optionSlot = optionSlot;
    }

    public JSONObject buildJson() {
        JSONObject optionJsonWrapper = new JSONObject();
        optionJsonWrapper.put("OptionSlot", optionSlot);
        
        JSONObject optionJson = new JSONObject();
        optionJson.put("DialogCommand", optionCommand);
        optionJson.put("Dialog", dialog);
        optionJson.put("Title", title);
        optionJson.put("DialogColor", dialogColor);
        optionJson.put("OptionType", optionType);

        optionJsonWrapper.put("Option", optionJson);

        //optionJsonString.put
        return optionJsonWrapper;
    }

    //getters and setters

    public int getOptionType() {
        return this.optionType;
    }
    public void setOptionType(int optionType) {
        this.optionType = optionType;
    }

    public int getOptionSlot() {
        return this.optionSlot;
    }
    public void setOptionSlot(int optionSlot) {
        this.optionSlot = optionSlot;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptionCommand() {
        return this.optionCommand;
    }
    public void setOptionCommand(String optionCommand) {
        this.optionCommand = optionCommand;
    }

    public int getDialog() {
        return this.dialog;
    }
    public void setDialog(int dialog) {
        this.dialog = dialog;
    }

    public int getDialogColor() {
        return this.dialogColor;
    }
    public void setDialogColor(int dialogColor) {
        this.dialogColor = dialogColor;
    }

}
