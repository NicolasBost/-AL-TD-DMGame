package mygame.rule;

import java.awt.Point;
import java.util.Set;

import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.core.Movable;
import gameframework.moves_rules.SpeedVector;
import gameframework.moves_rules.SpeedVectorDefaultImpl;
import mygame.entity.Base;
import mygame.entity.SoldierEntity;

public class UnitMovableDriver extends GameMovableDriverDefaultImpl {

	private Set<SoldierEntity> units;
	private Base targetBase;
	private static int AGRO_DIST = 5;
	int SPRITE_SIZE;

	public UnitMovableDriver(Set<SoldierEntity> units, Base base, int spriteSize) {
		this.units = units;
		this.targetBase = base;
		this.SPRITE_SIZE = spriteSize;
	}

	public SpeedVector getSpeedVector(Movable m) {
		// If is in mouvment => keep the same direction
		if(m.getPosition().x % SPRITE_SIZE != 0 || m.getPosition().y % SPRITE_SIZE != 0)
				return m.getSpeedVector();
					
		SpeedVector target_pos = null;
		MoveStrategyPathFinding pf = (MoveStrategyPathFinding) super.moveStrategy;
		pf.setStartPoint(new Point(m.getPosition().x / SPRITE_SIZE, m.getPosition().y / SPRITE_SIZE));
		SpeedVector possibleSpeedVector;

		//int speed = 1;

		float minDist = AGRO_DIST;
		for (SoldierEntity unit : units) {
			if(unit.getUnit().alive()){
				GameMovable obj = (GameMovable) unit;
				float dist = (float) obj.getPosition().distance(m.getPosition())/SPRITE_SIZE;
				if (dist < minDist) {
					pf.setEndPoint(new Point(obj.getPosition().x/SPRITE_SIZE, obj.getPosition().y/SPRITE_SIZE));
					possibleSpeedVector = pf.getSpeedVector();
					possibleSpeedVector.setSpeed(4);
					if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
						minDist = dist;
						target_pos = possibleSpeedVector;
					}
				}
			}
		}
		if (target_pos != null)
			return target_pos;
		pf.setEndPoint(targetBase.getClosestPoint(new Point(m.getPosition().x / SPRITE_SIZE, m.getPosition().y / SPRITE_SIZE)));
		possibleSpeedVector = pf.getSpeedVector();
		possibleSpeedVector.setSpeed(4);
		if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
			return possibleSpeedVector;
		}
		
		return SpeedVectorDefaultImpl.createNullVector();
	}

}
