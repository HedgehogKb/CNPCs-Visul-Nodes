package com.hedgehogkb;

import java.io.File;

import javax.swing.Timer;

import com.hedgehogkb.NodeDisplayFrame.VisualNodeDisplayFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        NodeGroup group = new NodeGroup("Test Group", new ProjectInfo(new File("null"), 1));

        group.setDisplayVisible(true);

        Timer timer = new Timer(5, (e) -> {
            group.getVisualNodeDisplayFrame().repaint();
        });

        timer.start();

    }
}
