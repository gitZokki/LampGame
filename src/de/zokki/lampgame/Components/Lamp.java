package de.zokki.lampgame.Components;

import java.awt.Color;

public class Lamp {

    private double xAngle, yAngle;

    private Color color;

    public Lamp(double xAngle, double yAngle, Color color) {
	this.xAngle = xAngle;
	this.yAngle = yAngle;
	this.color = color;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getXAngle() {
        return xAngle;
    }

    public double getYAngle() {
        return yAngle;
    }
}
