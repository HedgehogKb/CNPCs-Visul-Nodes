package com.hedgehogkb;

import javax.swing.Timer;

public class VisualNodesTester {
    public static void main(String[] args) {

        JFrameVisualNodes frame = new JFrameVisualNodes();

        Timer timer = new Timer(1000, (e) -> {
            frame.changeColor();
            frame.repaint();
        });

        timer.start();

        try {
            Thread.sleep(10000); // Sleep for 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        timer.stop(); // Stop the timer after 10 seconds


    }
}
