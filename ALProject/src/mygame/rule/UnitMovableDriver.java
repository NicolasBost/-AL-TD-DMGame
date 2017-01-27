package mygame.rule;

import java.awt.Point;
import java.util.Set;

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
		SoldierEntity s = (SoldierEntity) m;
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
					possibleSpeedVector.setSpeed(s.getSpeed());
					if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
						minDist = dist;
						target_pos = possibleSpeedVector;
					}
				}
			}
		}
		if (target_pos != null)
			return target_pos;
		if(Math.abs(targetBase.getClosestPoint(new Point(m.getPosition().x / SPRITE_SIZE, m.getPosition().y / SPRITE_SIZE)).distance(m.getPosition().x/SPRITE_SIZE, m.getPosition().y/SPRITE_SIZE) )<= 1){
			fight(s, targetBase);
			return SpeedVectorDefaultImpl.createNullVector();
		}
		
		pf.setEndPoint(targetBase.getClosestPoint(new Point(m.getPosition().x / SPRITE_SIZE, m.getPosition().y / SPRITE_SIZE)));
		possibleSpeedVector = pf.getSpeedVector();
		possibleSpeedVector.setSpeed(s.getSpeed());
		if (moveBlockerChecker.moveValidation(m, possibleSpeedVector)) {
			return possibleSpeedVector;
		}
		
		
		return SpeedVectorDefaultImpl.createNullVector();
	}
	
	public void fight(SoldierEntity e1, Base e2) {
		float st;
		st = e1.getUnit().strike();
		e2.getUnit().parry(st);
		System.out.println(e2.getUnit().getHealthPoints());
		if(!e2.getUnit().alive())
		{
			//e2.notify();
			System.out.println("Base out");
		}
	}
	

}
