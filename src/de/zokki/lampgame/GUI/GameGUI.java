package de.zokki.lampgame.GUI;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;

import de.zokki.lampgame.Utils.OSValidator;

public class GameGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private Rectangle bounds = getBounds();

    public GameGUI(String name, int width, int height) {
	super(name);
	setMinimumSize(new Dimension(width, height));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setFocusable(true);

	setContentPane(new GraphicsPanel(width, height));
	pack();
	setVisible(true);

	addListeners();
    }

    private void addListeners() {
	addWindowStateListener(new WindowStateListener() {
	    @Override
	    public void windowStateChanged(WindowEvent e) {
		if (e.getNewState() == JFrame.MAXIMIZED_BOTH) {
		    setFullScreen();
		}
	    }
	});

	addComponentListener(new ComponentAdapter() {
	    @Override
	    public void componentResized(ComponentEvent e) {
		setBounds();
	    }

	    @Override
	    public void componentMoved(ComponentEvent e) {
		setBounds();
	    }
	});

	addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		if (getExtendedState() == JFrame.MAXIMIZED_BOTH && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		    setBounds(bounds);
		    dispose();
		    setUndecorated(false);
		    setVisible(true);
		} else if (getExtendedState() != JFrame.MAXIMIZED_BOTH && e.getKeyCode() == KeyEvent.VK_F11) {
		    setFullScreen();
		}
	    }
	});
    }

    private void setBounds() {
	if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
	    bounds = getBounds();
	}
    }

    private void setFullScreen() {
	if (OSValidator.IS_UNIX) {
	    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    gd.setFullScreenWindow(this);
	} else {
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    dispose();
	    setUndecorated(true);
	    setVisible(true);
	}
    }
}
