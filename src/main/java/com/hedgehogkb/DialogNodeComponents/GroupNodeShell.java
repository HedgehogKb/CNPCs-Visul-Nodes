package com.hedgehogkb.DialogNodeComponents;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.json.JSONObject;

import com.hedgehogkb.NodeGroup;

public class GroupNodeShell extends VisualNodeShell {
    private String groupName;

    public GroupNodeShell(int posX, int posY, NodeGroup group) {
        super(posX, posY, group);
        this.groupName = group.getName();
    }

    public GroupNodeShell(int posX, int posY, NodeGroup group, DialogNode dialogNode) {
        super(posX, posY, group);
        this.groupName = group.getName();
        setDialogNode(dialogNode);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int xScreenPos = getPosX() + getOffsetX();
        int yScreenPos = getPosY() + getOffsetY();

        int colorValue = 125;
        g2d.setColor(new Color(colorValue, colorValue, colorValue));
        g2d.fillRect(xScreenPos, yScreenPos, 150, 100);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(xScreenPos+3, yScreenPos+3, 150-6, 100-6);

        g2d.setStroke(new BasicStroke(2));


        g2d.fillRect(xScreenPos+10, yScreenPos +55, 10, 30);

        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        g2d.drawString(trimString(getDialogNode().getDialogTitle(), 20), xScreenPos+10, yScreenPos+22);
        g2d.drawString("Id: " + getDialogNode().getDialogId(), xScreenPos+10, yScreenPos+44);
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 15));
        g2d.drawString("Group: " + trimString(groupName, 7), xScreenPos+25, yScreenPos+ 66);
    }

    @Override
    public int isOptionTouchingMouse(int mouseX, int mouseY) {
        return -1;
    }

    @Override
    public JSONObject buildJson() {
        JSONObject visualNodeJson = new JSONObject();
        visualNodeJson.put("nodeId", getDialogNode().getDialogId());
        visualNodeJson.put("type", "groupNode");
        visualNodeJson.put("groupName", this.groupName);
        visualNodeJson.put("posX", getPosX());
        visualNodeJson.put("posY", getPosY());
        return visualNodeJson;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public String toString() {
        return "" + getDialogId() + " - " + getDialogNode().getDialogTitle();
    }

}
