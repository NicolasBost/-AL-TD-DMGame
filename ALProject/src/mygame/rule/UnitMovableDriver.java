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

public class UnitMovableDriver extends GameMovableDriverDefaultImpl {

	private Set<GameEntity> units;
	private Base targetBase;
	private static int AGRO_DIST = 10;
	int SPRITE_SIZE;

	public UnitMovableDriver(Set<GameEntity> units, Base base, int spriteSize) {
		this.units = units;
		this.targetBase = base;
		this.SPRITE_SIZE = spriteSize;
	}

	public SpeedVector getSpeedVector(Movable m) {
		SpeedVector target_pos = null;

		MoveStrategyPathFinding pf = (MoveStrategyPathFinding) super.moveStrategy;
		pf.setStartPoint(new Point(m.getPosition().x / SPRITE_SIZE, m.getPosition().y / SPRITE_SIZE));

		int speed = 1;

		float minDist = AGRO_DIST;
		for (GameEntity unit : units) {
			GameMovable obj = (GameMovable) unit;
			float dist = (float) obj.getPosition().distance(m.getPosition());
			if (dist < minDist) {
				pf.setEndPoint(targetBase.getClosestPoint(obj.getPosition()));
				SpeedVector possibleSpeedVector = pf.getSpeedVector();
				// SpeedVector tmp_speed = new
				// SpeedVectorDefaultImpl(getDirection(m.getPosition(),
				// obj.getPosition()), speed);
				if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
					minDist = dist;
					target_pos = possibleSpeedVector;
				}
			}
		}
		if (target_pos != null)
			return target_pos;

		pf.setEndPoint(targetBase
				.getClosestPoint(new Point(m.getPosition().x / SPRITE_SIZE, m.getPosition().y / SPRITE_SIZE)));
		SpeedVector possibleSpeedVector = pf.getSpeedVector();
		if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
			return possibleSpeedVector;
		}
		if (moveBlockerChecker.moveValidation(m, m.getSpeedVector())) {
			return possibleSpeedVector;
		}
		return SpeedVectorDefaultImpl.createNullVector();

	}

}
