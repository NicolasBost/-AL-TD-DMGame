package mygame;

import gameframework.core.GameLevel;
import mygame.level.GameLevelOne;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		BasicGame g = new BasicGame();
		ArrayList<GameLevel> levels = new ArrayList<>();

		levels.add(new GameLevelOne(g)); // only one level is available
		
		g.setLevels(levels);
		g.start();
	}
}
