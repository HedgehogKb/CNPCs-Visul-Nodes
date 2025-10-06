package com.hedgehogkb;

import com.hedgehogkb.LauncherFrame.LauncherFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //This is the script for packaging 
        //jpackage --verbose --name CNPCVisualDialog --input target --main-jar VisualCNPCDialogMaker-jar-with-dependencies.jar --main-class com.hedgehogkb.App --app-version 1.x.x
        LauncherFrame frame = new LauncherFrame();
        System.out.println("Hello World!");
    }
}
