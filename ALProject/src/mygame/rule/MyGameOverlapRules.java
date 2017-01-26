package mygame.rule;

import java.awt.Point;
import java.util.Vector;

import gameframework.core.GameUniverse;
import gameframework.core.Movable;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

public class MyGameOverlapRules extends OverlapRulesApplierDefaultImpl {
	protected GameUniverse universe;
	protected Point myBase;
	protected Point enemyBase;
	private final ObservableValue<Integer> life;
	private final ObservableValue<Boolean> endOfGame;
	
	public MyGameOverlapRules(Point point, Point point2, ObservableValue<Integer> life,
			ObservableValue<Boolean> endOfGame) {
		myBase = (Point) point.clone();
		enemyBase = (Point) point2.clone();
		this.life = life;
		this.endOfGame = endOfGame;	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}
	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables){
		super.applyOverlapRules(overlappables);
	}
	public void overlapRule(Movable e1, Movable e2) {
		//todo fight
	}
	
}
