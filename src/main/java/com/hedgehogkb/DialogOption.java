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
}
