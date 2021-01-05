package de.zokki.lampgame.Components;

import java.awt.Color;

import de.zokki.lampgame.GUI.GraphicsPanel;

public class Spinner extends Thread {

    private GraphicsPanel panel;
    
    private Color currentColor;
    
    public Spinner(GraphicsPanel panel) {
	this.panel = panel;
    }
    
    @Override
    public void run() {
	while (true) {
	    for (Lamp lamp : panel.getLamps()) {
		currentColor = lamp.getColor();
		lamp.setColor(Color.GREEN);
		panel.repaint();
		try {
		    Thread.sleep((long) (Math.random() * 20));
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		lamp.setColor(currentColor);
	    }
	}
    }
    
    public Color getCurrentColor() {
	return currentColor;
    }
    
    @SuppressWarnings("deprecation")
    public void kill() {
	for (Lamp lamp : panel.getLamps()) {
	    lamp.setColor(lamp.getYAngle() == -1 ? Color.RED : Color.BLACK);
	}
	stop();
    }
}
