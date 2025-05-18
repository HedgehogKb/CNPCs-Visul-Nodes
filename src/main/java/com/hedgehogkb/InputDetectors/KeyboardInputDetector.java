package com.hedgehogkb.InputDetectors;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import com.hedgehogkb.VisualNodeDisplayFrame;



public class KeyboardInputDetector implements KeyListener {
    private boolean shiftHeld;
    private boolean aHeld;
    //timer variables
    private Timer keyPressTimer;
    private Boolean timerRunning;
    private ActionListener timerActionListener;
    private VisualNodeDisplayFrame visualNodeDisplay;

    public KeyboardInputDetector(VisualNodeDisplayFrame visualNodeDisplay) {
        this.shiftHeld = false;
        this.aHeld = false;
        this.timerRunning = false;
        this.timerActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerRunning = false;
            }
            
        };
        this.keyPressTimer = new Timer(100, timerActionListener);
        this.keyPressTimer.setRepeats(false);
        this.visualNodeDisplay = visualNodeDisplay;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_SHIFT) {
            shiftHeld = true;
            cancelTimer();
        } else if  (e.getKeyCode() == e.VK_A) {
            aHeld = true;
            cancelTimer();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == e.VK_SHIFT) {
            if (this.timerRunning) {
                cancelTimer();
                System.err.println("Shift and A pressed");
                visualNodeDisplay.addVisualNode();
            } else {
                this.timerRunning = true;
                this.keyPressTimer.start();
            }
        } else if  (e.getKeyCode() == e.VK_A) {
            if (this.timerRunning) {
                cancelTimer();
                System.err.println("Shift and A pressed");
                visualNodeDisplay.addVisualNode();

            } else {
                this.timerRunning = true;
                this.keyPressTimer.start();
            }
        }    
    }

    public boolean getIsTimerRunning() {
        return this.timerRunning;
    }

    public void cancelTimer() {
        if (timerRunning) {
            this.timerRunning = false;
            this.keyPressTimer.stop();
        }
    }
    
}
