package mygame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import gameframework.core.CanvasDefaultImpl;
import gameframework.core.Game;
import gameframework.core.GameLevel;
import gameframework.core.ObservableValue;
import mygame.level.MyGameLevel;

public class BasicGame implements Game, Observer {
	protected static final int NB_ROWS = 31;
	protected static final int NB_COLUMNS = 37;
	protected static final int SPRITE_SIZE = 16;
	public static final int NUMBER_OF_PLAYER = 2;
	
	protected CanvasDefaultImpl defaultCanvas = null;
	protected ObservableValue<Integer> life[] = new ObservableValue[NUMBER_OF_PLAYER];

	// initialized before each level
	protected ObservableValue<Boolean> endOfGame = null;

	private Frame f;

	private MyGameLevel currentPlayedLevel = null;
	protected int levelNumber;
	protected ArrayList<GameLevel> gameLevels;

	protected Label lifeText;
	protected Label lifeValue;
	protected Label enemyLifeText;
	protected Label enemyLifeValue;
	protected Label currentLevel;
	protected Label currentLevelValue;
	protected Label warriorText;
	protected Label warriorValue;
	protected Label horseManText;
	protected Label horseManValue;
	protected Label instructionsText;

	public BasicGame() {
		for (int i = 0; i < NUMBER_OF_PLAYER; ++i) {//player + enemy life
			life[i] = new ObservableValue<Integer>(0);
		}
		lifeText = new Label("Lives:");
		enemyLifeText = new Label("Enemy Lives:");
		currentLevel = new Label("Level:");
		warriorText = new Label("Warriors left:");
		horseManText = new Label("Horse men left:");
		instructionsText = new Label("Press '1' to send warrior and '2' to send horseman");
		createGUI();
	}
	
	@Override
	public void createGUI() {
		f = new Frame("Water Emblem Clash");
		f.dispose();

		Container topBar = createStatusBar();
		Container bottomBar = createSendingBar();
		defaultCanvas = new CanvasDefaultImpl();
		defaultCanvas.setSize(SPRITE_SIZE * NB_COLUMNS, SPRITE_SIZE * NB_ROWS);
		f.add(defaultCanvas);
		f.add(topBar, BorderLayout.NORTH);
		f.add(bottomBar, BorderLayout.SOUTH);
		f.pack();
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
	}
	private Container createStatusBar() {
		JPanel c = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		c.setLayout(layout);
		lifeValue = new Label(Integer.toString(life[0].getValue()));
		enemyLifeValue = new Label(Integer.toString(life[1].getValue()));
		currentLevelValue = new Label(Integer.toString(levelNumber));
		c.add(lifeText);
		c.add(lifeValue);
		c.add(enemyLifeText);
		c.add(enemyLifeValue);
		c.add(currentLevel);
		c.add(currentLevelValue);
		return c;
	}
	
	private Container createSendingBar(){
		JPanel c = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		c.setLayout(layout);
		//System.out.println(Integer.toString(currentPlayedLevel.playerAvailableUnits.getValue().get("warrior")));
		warriorValue = new Label("0");
		horseManValue = new Label("0");
		c.add(instructionsText);
		c.add(warriorText);
		//c.add(warriorValue);
		c.add(horseManText);
		//c.add(horseManValue);
		return c;
	}
		@Override
	public Canvas getCanvas() {
		return defaultCanvas;
	}
	@Override
	public void start() {
		for (int i = 0; i < NUMBER_OF_PLAYER; ++i) {
			life[i].addObserver(this);
			life[i].setValue(5);
		}
		levelNumber = 0;
		for (GameLevel level : gameLevels) {
			endOfGame = new ObservableValue<Boolean>(false);
			endOfGame.addObserver(this);
			try {
				if (currentPlayedLevel != null && currentPlayedLevel.isAlive()) { //placer la condition d'arret ici ?
					currentPlayedLevel.interrupt();
					currentPlayedLevel = null;
				}
				currentPlayedLevel = (MyGameLevel) level;
				levelNumber++;
				currentLevelValue.setText(Integer.toString(levelNumber));
				currentPlayedLevel.start();
				currentPlayedLevel.join();
			} catch (Exception e) {
			}
		}
		
	}
	
	public ObservableValue<Integer>[] life() {
		return life;
	}

	public void setLevels(ArrayList<GameLevel> levels) {
		gameLevels = levels;
	}

	public void update(Observable o, Object arg) {
		if (o == endOfGame) {
			if (endOfGame.getValue()) {
				currentPlayedLevel.interrupt();
				currentPlayedLevel.end();
			}
		} 
		/*if (o==currentPlayedLevel.playerAvailableUnits){
			warriorValue.setText(Integer.toString(currentPlayedLevel.playerAvailableUnits.getValue().get("warrior")));
			horseManValue.setText(Integer.toString(currentPlayedLevel.playerAvailableUnits.getValue().get("horseman")));
		}*/
	}
		/*else {
			for (ObservableValue<Integer> lifeObservable : life) {
				if (o == lifeObservable) {
					int lives = ((ObservableValue<Integer>) o).getValue();
					lifeValue.setText(Integer.toString(lives));
					if (lives == 0) {
						currentPlayedLevel.interrupt();
						currentPlayedLevel.end();
					}
				}
			}
		}*/

	@Override
	public void restore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ObservableValue<Integer>[] score() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObservableValue<Boolean> endOfGame() {
		return endOfGame;
	}

}