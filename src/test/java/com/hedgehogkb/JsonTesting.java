package com.hedgehogkb;

import org.json.JSONObject;


public class JsonTesting {
    public static void main(String[] args) {
        DialogOption option = new DialogOption();
        
        DialogNode node = new DialogNode();

        System.out.println("DialogNode JSON: " + node.buildJson().toString(4));
    }
}
