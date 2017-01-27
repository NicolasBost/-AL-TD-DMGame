package mygame.entity;

import gameframework.core.GameEntity;
import soldier.core.Unit;

public interface SoldierEntity extends GameEntity{
	Unit getUnit();
	int getSpeed();
}
