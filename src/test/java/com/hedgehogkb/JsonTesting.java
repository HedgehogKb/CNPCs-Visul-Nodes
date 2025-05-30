package com.hedgehogkb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONException;
import org.json.JSONObject;

import com.hedgehogkb.DialogNodeComponents.DialogNode;
import com.hedgehogkb.DialogNodeComponents.DialogOption;
import com.hedgehogkb.ImportingAndExporting.DialogNodeBuilder;


public class JsonTesting {
    public static void main(String[] args) {
        File jsonTestFile = new File("C:\\Users\\kereb\\AppData\\Roaming\\.minecraft\\saves\\Example CNPC Dialog World\\customnpcs\\dialogs");
        try {
            //JSONObject testingJson = new JSONObject(Files.readString(Path.of(jsonTestFile.getAbsolutePath())));
            DialogNodeBuilder nodeBuilder = new DialogNodeBuilder(jsonTestFile, "Test", 9);
            System.out.println(nodeBuilder.getDialogNode().buildJson().toString(4));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
