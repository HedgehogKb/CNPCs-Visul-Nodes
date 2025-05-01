package com.hedgehogkb;

import javax.swing.Timer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        VisualNodeDisplayFrame frame = new VisualNodeDisplayFrame();

        Timer timer = new Timer(10, (e) -> {
            frame.repaint();
        });

        timer.start();

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //timer.stop();
    }
}
