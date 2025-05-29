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
        if (timer != null) {
            timer.start();
        }
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }
}
