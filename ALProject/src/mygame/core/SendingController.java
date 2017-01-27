package mygame.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SendingController implements KeyListener {

	
	public SendingController() {
		super();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()==KeyEvent.VK_NUMPAD1)
			//sendWarrior();
			System.out.println("send warrior");
		if(arg0.getKeyCode()==KeyEvent.VK_NUMPAD2)
			//sendHorseMan();
			System.out.println("send horseman");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
