package com.hedgehogkb;

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
        NodeGroup group = new NodeGroup("Test Group");

        group.setDisplayVisible(true);

        Timer timer = new Timer(5, (e) -> {
            group.getVisualNodeDisplayFrame().repaint();
        });

        timer.start();

    }
}
