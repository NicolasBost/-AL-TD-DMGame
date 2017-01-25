package mygame.moveRules;

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

	public UnitMovableDriver(Set<GameEntity> units, Base base) {
		this.units = units;
		this.targetBase = base;
	}

	public SpeedVector getSpeedVector(Movable m) {
		SpeedVector target_pos = null;
		int speed = 1;
		float minDist = AGRO_DIST;
		int nbTries = 10;
		while (true) {
			nbTries--;
			// Search near adverse
			for (GameEntity unit : units) {
				GameMovable obj = (GameMovable) unit;
				float dist = (float) obj.getPosition().distance(m.getPosition());
				if (dist < minDist) {
					SpeedVector tmp_speed = new SpeedVectorDefaultImpl(getDirection(m.getPosition(), obj.getPosition()), speed);
					if (moveBlockerChecker.moveValidation(m, tmp_speed)) {
						minDist = dist;
						target_pos = tmp_speed;
					}
				}
			}
			if (target_pos != null) {
				return target_pos;
			} else {
				// Go to adverse base
				Point dir = getDirection(m.getPosition(), targetBase.getClosestPoint(m.getPosition()));
				SpeedVector tmp_speed = new SpeedVectorDefaultImpl(dir, speed);
				if (moveBlockerChecker.moveValidation(m, tmp_speed)) {
					return tmp_speed;
				}				
				tmp_speed = new SpeedVectorDefaultImpl(new Point(0,dir.y), speed);
				if (moveBlockerChecker.moveValidation(m, tmp_speed)) {
					return tmp_speed;
				}
				tmp_speed = new SpeedVectorDefaultImpl(new Point(dir.x,0), speed);
				if (moveBlockerChecker.moveValidation(m, tmp_speed)) {
					return tmp_speed;
				}
			}
			if (nbTries < 1)
				break;
		}
		return SpeedVectorDefaultImpl.createNullVector();

	}

	public Point getDirection(Point src, Point dir) {
		int x = 0;
		int y = 0;
		x = (src.getX() - dir.getX() > 0) ? 1 : -1;
		y = (src.getY() - dir.getY() > 0) ? 1 : -1;
		return new Point(x, y);
	}

}
