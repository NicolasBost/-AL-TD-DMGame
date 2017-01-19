package pacman;

import java.awt.Canvas;

import gameframework.core.GameUniverse;
import gameframework.core.GameUniverseViewPortDefaultImpl;

public class GameUniverseViewPort extends GameUniverseViewPortDefaultImpl {

	public GameUniverseViewPort(Canvas canvas, GameUniverse universe) {
		super(canvas, universe);
	}
	
	protected String backgroundImage() {
		return "images/map2.png";
	}

}
