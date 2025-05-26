package com.hedgehogkb.NodeDisplayFrame;

import javax.swing.Timer;

public class VisualNodeDisplayRunner {
    private VisualNodeDisplayFrame visualNodeDisplayFrame;
    private Timer timer;

    public VisualNodeDisplayRunner(VisualNodeDisplayFrame visualNodeDisplayFrame) {
        this.visualNodeDisplayFrame = visualNodeDisplayFrame;
        timer = new Timer(10, e -> {
            visualNodeDisplayFrame.repaint();
        });
    }

    public void start() {
        System.out.println("VisualNodeDisplayRunner starting");
        if (timer != null) {
            timer.start();
            System.out.println("Started VisualNodeDisplayRunner timer");

        }
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }
}
