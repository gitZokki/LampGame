package de.zokki.lampgame.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.zokki.lampgame.Components.Lamp;
import de.zokki.lampgame.Components.Spinner;

public class GraphicsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private int points = 0, presses = 0;
    
    private final Dimension preferredSize;

    private JLabel pointsLabel = new JLabel("0 Points");
    private JLabel pressedLabel = new JLabel("0 Presses");

    private ArrayList<Lamp> lamps = new ArrayList<Lamp>();

    private Spinner spinner;

    public GraphicsPanel(int width, int height) {
	this.preferredSize = new Dimension(width, height);

	addComponents();
	addListeners();

	initLamps();

	startSpinning();
    }

    @Override
    public Dimension getPreferredSize() {
	return preferredSize;
    }

    @Override
    public void paint(Graphics g) {
	super.paint(g);
	int xCenter = getWidth() >> 1;
	int yCenter = getHeight() >> 1;
	int lampWidth = xCenter >> 5;
	int lampHeight = yCenter >> 5;
	for (Lamp lamp : lamps) {
	    int xPoint = (int) (xCenter + (xCenter - 25) * lamp.getXAngle());
	    int yPoint = (int) (yCenter - (yCenter - 25) * lamp.getYAngle());
	    g.setColor(lamp.getColor());
	    g.fillOval(xPoint - (lampWidth >> 1), yPoint - (lampHeight >> 1), lampWidth, lampHeight);
	}
    }

    public ArrayList<Lamp> getLamps() {
	return lamps;
    }

    private void addListeners() {
	KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
	    boolean pressed = false;
	    
	    @Override
	    public boolean dispatchKeyEvent(KeyEvent e) {
		synchronized (GraphicsPanel.class) {
		    switch (e.getID()) {
		    case KeyEvent.KEY_PRESSED:
			if (e.getKeyCode() == KeyEvent.VK_SPACE && !pressed) {
			    if(spinner.getCurrentColor() == Color.RED) {
				pointsLabel.setText(++points + " Points in");
			    }
			    pressedLabel.setText(++presses + " Presses");
			}
			pressed = true;
			break;

		    case KeyEvent.KEY_RELEASED:
			pressed = false;
			break;
		    }
		    return false;
		}
	    }
	});
    }

    private void initLamps() {
	double PIDividedBy30 = 0.1047197551196597705332654489514728d;

	for (float i = 0; i < 60; i += 0.5) {
	    double xAngle = Math.sin(i * PIDividedBy30);
	    double yAngle = Math.cos(i * PIDividedBy30);
	    lamps.add(new Lamp(xAngle, yAngle, yAngle == -1 ? Color.RED : Color.BLACK));
	}
    }

    private void startSpinning() {
	if(spinner != null) {
	    spinner.kill();
	}
	spinner = new Spinner(this);
	spinner.start();
    }

    private void addComponents() {
	setLayout(new GridBagLayout());
	GridBagConstraints grid = new GridBagConstraints();
	
	add(pointsLabel, grid);
	grid.gridy = 1;
	add(pressedLabel, grid);
	grid.insets = new Insets(15, 0, 0, 0);
	grid.gridy = 2;
	
	add(getResetButton(), grid);
    }
    
    private JButton getResetButton() {
	JButton button = new JButton("RESET");
	button.setFocusable(false);
	button.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		startSpinning();
		presses = points = 0;
		pointsLabel.setText("0 Points");
		pressedLabel.setText("0 Presses");
	    }
	});
	return button;
    }
}
