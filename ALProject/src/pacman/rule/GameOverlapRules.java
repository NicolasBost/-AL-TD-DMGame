package pacman.rule;

import java.util.Vector;

import gameframework.core.GameEntity;
import gameframework.core.GameUniverse;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

public class GameOverlapRules extends OverlapRulesApplierDefaultImpl {

	protected GameUniverse universe;
	
	public GameOverlapRules() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setUniverse(GameUniverse universe) {
		this.universe = universe;

	}
	
	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables){
		super.applyOverlapRules(overlappables);
	}
	
	public void overlapRule(GameEntity e1, GameEntity e2){
		//e1.strike();
		//e2.strike();
	}

}
