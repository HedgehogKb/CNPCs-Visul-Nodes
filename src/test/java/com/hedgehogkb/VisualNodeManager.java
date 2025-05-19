package com.hedgehogkb;

import java.util.ArrayList;
import java.util.HashMap;

import com.hedgehogkb.DialogNodeComponents.VisualNodeShell;

public class VisualNodeManager {
    private ArrayList<VisualNodeShell> visualNodeShells;
    private HashMap<Integer, VisualNodeShell> visualNodeShellsByID;

    public VisualNodeManager() {
        this.visualNodeShells = new ArrayList<>();
        this.visualNodeShellsByID = new HashMap<>();
    }

    public void addVisualNodeShell(VisualNodeShell visualNodeShell) {
        this.visualNodeShells.add(visualNodeShell);
        this.visualNodeShellsByID.put(visualNodeShell.getDialogNode().getDialogId(), visualNodeShell);
    }

    public void moveVisualNodeToFront(VisualNodeShell visualNodeShell) {
        this.visualNodeShells.remove(visualNodeShell);
        this.visualNodeShells.add(0, visualNodeShell);
    }
}
