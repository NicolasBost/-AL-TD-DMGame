package mygame.level;

import java.util.Date;
import java.util.HashMap;

import gameframework.core.GameLevel;
import gameframework.core.GameUniverse;
import gameframework.core.GameUniverseViewPort;
import gameframework.core.ObservableValue;
import mygame.BasicGame;

public abstract class MyGameLevel extends Thread implements GameLevel {
	private static final int MINIMUM_DELAY_BETWEEN_GAME_CYCLES = 100;
	public static final int LIVES = 5;
	protected final BasicGame g;
	protected GameUniverse universe;
	protected GameUniverseViewPort gameBoard;

	public ObservableValue<HashMap<String, Integer>> playerAvailableUnits;
	protected HashMap<String, Integer> IAAvailableUnits;
	
	protected ObservableValue<Integer> playerUnits[];
	protected ObservableValue<Integer> enemyUnits[];
	
	protected ObservableValue<Integer> life[] = new ObservableValue[BasicGame.NUMBER_OF_PLAYER];
	protected ObservableValue<Boolean> endOfGame;

	boolean stopGameLoop;

	protected abstract void init();
	
	public MyGameLevel(BasicGame g){
		this.g =g;
		int nbPlayers = BasicGame.NUMBER_OF_PLAYER;
		for (int i = 0; i < nbPlayers; i++) {
			life[i] = new ObservableValue<Integer>(LIVES);
		}
	}
	
	@Override
	public void start() {  
		endOfGame = g.endOfGame();
		init();
		super.start();
		try {
			super.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		stopGameLoop = false;
		// main game loop 
		long start;
		while (!stopGameLoop && !this.isInterrupted()) {
			start = new Date().getTime();
			gameBoard.paint();
			universe.allOneStepMoves();
			universe.processAllOverlaps();
			try {
				long sleepTime = MINIMUM_DELAY_BETWEEN_GAME_CYCLES
						- (new Date().getTime() - start);
				if (sleepTime > 0) {
					Thread.sleep(sleepTime);
				}
			} catch (Exception e) {
			}
		}
	}
	public void end() {
		stopGameLoop = true;
	}
	protected void overlap_handler() {
	}
}