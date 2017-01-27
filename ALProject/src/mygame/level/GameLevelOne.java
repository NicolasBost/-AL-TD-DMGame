package mygame.level;

import java.awt.Canvas;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import gameframework.core.CanvasDefaultImpl;
import gameframework.core.GameMovable;
import gameframework.core.GameUniverseDefaultImpl;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveBlockerCheckerDefaultImpl;
import gameframework.moves_rules.OverlapProcessor;
import gameframework.moves_rules.OverlapProcessorDefaultImpl;
import gameframework.moves_rules.OverlapRulesApplier;
import mygame.BasicGame;
import mygame.core.GameUniverseViewPort;
import mygame.core.SendingController;
import mygame.entity.Base;
import mygame.entity.BlockerWall;
import mygame.entity.HorseMan;
import mygame.entity.SoldierEntity;
import mygame.entity.Warrior;
import mygame.rule.MoveStrategyPathFinding;
import mygame.rule.MyGameOverlapRules;
import mygame.rule.UnitMovableDriver;

public class GameLevelOne extends MyGameLevel {
	Canvas canvas;
	private static final int NB_WARRIORS = 8;
	private static final int NB_HORSEMEN = 3;

	// 0 : empty; 1 : NonMovable; 2: PlayerBase; 3: IABase
	static int[][] tab = { 
		  { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0 ,0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 0, 0, 2, 2, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
			{ 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
			{ 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 },
			{ 1, 1, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 },
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 },
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 3, 3, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 3, 3, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 3, 3, 3, 0, 0, 0 },
			{ 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 3, 3, 3, 0, 1, 0 },
			{ 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1 }};

	public static final int SPRITE_SIZE = 16;
	
	private HashSet<SoldierEntity> player_units;
	private HashSet<SoldierEntity> enemy_units;
	
	private Base myBase;
	private Base advBase;

	@Override
	protected void init() {
		HashMap<String,Integer> count_units = new HashMap<>();
		count_units.put("warrior", NB_WARRIORS);
		count_units.put("horsemen", NB_HORSEMEN);
		playerAvailableUnits = new ObservableValue<HashMap<String,Integer>>(count_units);
		
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		OverlapRulesApplier overlapRules = new MyGameOverlapRules(new Point(14 * SPRITE_SIZE, 17 * SPRITE_SIZE),
				new Point(14 * SPRITE_SIZE, 15 * SPRITE_SIZE), life[0], endOfGame);//a travailler les points
		overlapProcessor.setOverlapRules(overlapRules);
		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);
		gameBoard = new GameUniverseViewPort(canvas, universe);
		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);
		
		myBase = new Base();
		advBase = new Base();
		player_units = new HashSet<>();
		enemy_units = new HashSet<>();
		
		// Filling up the universe with basic non movable entities and inclusion in the universe
		for (int i = 0; i < 31; ++i) {
			for (int j = 0; j < 37; ++j) {
				//universe.addGameEntity(new Pacgum(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				if (tab[i][j] == 1) {
					universe.addGameEntity(new BlockerWall(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
				}else if (tab[i][j] == 2) {
					myBase.addCoordonate(new Point(j, i));
				}else if (tab[i][j] == 3) {
					advBase.addCoordonate(new Point(j, i));
				}
			}
		}
		universe.addGameEntity(myBase);
		universe.addGameEntity(advBase);
		
		//sending control with keyboard
		SendingController sendCtrl = new SendingController(this);
		canvas.addKeyListener(sendCtrl);
		
		//setUnit
		HashMap<String,Integer> init_unit = new HashMap<String,Integer>();
		init_unit.put("warrior", NB_WARRIORS);
		init_unit.put("horseman", NB_HORSEMEN);
		playerAvailableUnits = new ObservableValue<HashMap<String,Integer>>(init_unit);
		IAAvailableUnits = init_unit;
		
		//playerAvailableUnits.notify();
		
		UnitMovableDriver xDriver = new UnitMovableDriver(player_units, myBase, SPRITE_SIZE);
		HorseMan x = new HorseMan(canvas, false);
		xDriver.setmoveBlockerChecker(moveBlockerChecker);
		MoveStrategyPathFinding pathFinding = new MoveStrategyPathFinding(tab);
		xDriver.setStrategy(pathFinding);
		x.setDriver(xDriver);
		x.setPosition(new Point(35 * SPRITE_SIZE, 20 * SPRITE_SIZE));
		universe.addGameEntity(x);
		enemy_units.add(x);
		
		
		HorseMan v = new HorseMan(canvas, false);
		v.setDriver(xDriver);
		v.setPosition(new Point(33 * SPRITE_SIZE, 20 * SPRITE_SIZE));
		universe.addGameEntity(v);
		enemy_units.add(v);
		
	

	}

	public GameLevelOne(BasicGame g) {
		super(g);
		canvas = g.getCanvas();
		HashMap<String,Integer> init_unit = new HashMap<String,Integer>();
		init_unit.put("warrior", NB_WARRIORS);
		init_unit.put("horseman", NB_HORSEMEN);
		playerAvailableUnits = new ObservableValue<HashMap<String,Integer>>(init_unit);
		playerAvailableUnits.addObserver(g);
	}

	public void invokeEntity(String type) {
		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		UnitMovableDriver wDriver = new UnitMovableDriver(enemy_units, advBase, SPRITE_SIZE);
		MoveStrategyPathFinding pathFinding = new MoveStrategyPathFinding(tab);
		GameMovable w = null;
		switch(type){
			case "warrior":
					HashMap<String, Integer> nbUnits = playerAvailableUnits.getValue();
					if(playerAvailableUnits.getValue().get("warrior") > 0){
						w = new Warrior(canvas, true);
						playerAvailableUnits.getValue().put("warrior", playerAvailableUnits.getValue().get("warrior")-1);
					}
						
					
				break;
			case "horseman":
				if(playerAvailableUnits.getValue().get("horseman") > 0){
					w = new HorseMan(canvas, true);
					playerAvailableUnits.getValue().put("horseman", playerAvailableUnits.getValue().get("horseman")-1);
				}
					
				break;
		}
		if(w == null)
			return;
		wDriver.setmoveBlockerChecker(moveBlockerChecker);
		
		wDriver.setStrategy(pathFinding);
		w.setDriver(wDriver);
		Point p = myBase.getSpawnablePoint();
		w.setPosition(new Point(p.x * SPRITE_SIZE, p.y * SPRITE_SIZE));
		SoldierEntity s = (SoldierEntity) w;
		universe.addGameEntity(s);
		player_units.add(s);
		
	}
}
