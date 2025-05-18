package com.hedgehogkb;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;


public class KeyboardInputDetector implements KeyListener {
    private boolean shiftHeld;
    private boolean aHeld;
    //timer variables
    private Timer keyPressTimer;
    private Boolean timerRunning;

    public KeyboardInputDetector() {
        this.shiftHeld = false;
        this.aHeld = false;
        this.timerRunning = false;
        this.keyPressTimer = new Timer();
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
        System.out.println();
        if (e.getKeyCode() == e.VK_SHIFT) {
          
        } else if  (e.getKeyCode() == e.VK_A) {

        }    
    }

    public boolean getIsTimerRunning() {
        return this.timerRunning;
    }

    public void cancelTimer() {
        if (timerRunning) {
        this.timerRunning = false;
        this.keyPressTimer.cancel();
        }
    }
    
}
