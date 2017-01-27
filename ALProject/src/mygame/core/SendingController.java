package mygame.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import mygame.level.GameLevelOne;

public class SendingController implements KeyListener {

	GameLevelOne g;
	public SendingController(GameLevelOne game) {
		super();
		this.g = game;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()==KeyEvent.VK_1)
			g.invokeEntity("warrior");
		if(arg0.getKeyCode()==KeyEvent.VK_2)
			g.invokeEntity("horseman");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
