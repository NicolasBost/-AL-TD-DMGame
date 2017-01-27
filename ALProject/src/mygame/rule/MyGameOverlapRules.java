package mygame.rule;

import java.awt.Point;
import java.util.Vector;

import gameframework.core.GameUniverse;
import gameframework.core.Movable;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;
import mygame.entity.Base;
import mygame.entity.HorseMan;
import mygame.entity.SoldierEntity;
import mygame.entity.Warrior;

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
		this.endOfGame = endOfGame;	
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}
	
	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables){
		super.applyOverlapRules(overlappables);
	}
	public void overlapRule(Warrior e1, Warrior e2) {
		fight(e1,e2);
	}
	public void overlapRule(Warrior e1, HorseMan e2) {
		fight(e1,e2);
	}
	public void overlapRule(HorseMan e1, HorseMan e2) {
		fight(e1,e2);
	}
	
	
	public void fight(SoldierEntity e1, SoldierEntity e2) {
		if(e1.isFriend() == e2.isFriend())
			return;
		float st;
		st = e1.getUnit().strike();
		e2.getUnit().parry(st);
		st=e2.getUnit().strike();
		e1.getUnit().parry(st);
		if(!e1.getUnit().alive())
			universe.removeGameEntity(e1);
		if(!e2.getUnit().alive())
			universe.removeGameEntity(e2);
	}
	
}
