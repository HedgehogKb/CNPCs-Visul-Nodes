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
        String test = "13.json";
        System.out.println(test.split("\\.")[0]);
    }
}
