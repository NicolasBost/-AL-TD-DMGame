package mygame.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.core.Drawable;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.core.Overlappable;
import gameframework.core.SpriteManager;
import gameframework.core.SpriteManagerDefaultImpl;
import soldier.core.Unit;
import soldier.units.UnitCenturion;

public class Warrior extends GameMovable implements Drawable, GameEntity, Overlappable, SoldierEntity {

	protected final SpriteManager spriteManager;
	public static final int RENDERING_SIZE = 16; //taille finale
	public static final int SPEED = 2;
	protected boolean movable = true;
	protected UnitCenturion unit;
	protected boolean isFriend;
	
	public Unit getUnit() {
		return unit;
	}

	public Warrior(Canvas defaultCanvas, boolean isFriend) {
		spriteManager = new SpriteManagerDefaultImpl("images/warrior.png",
				defaultCanvas, RENDERING_SIZE, 5); //source, defautCanvas, | , nbSpriteParLigne
		spriteManager.setTypes(
				//
				"right", "left", "up",
				"down",//
				"static");
		unit = new UnitCenturion("jojo");
		this.isFriend = isFriend;
	}
	
	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}

	@Override
	public void draw(Graphics g) {
		String spriteType;
		Point tmp = getSpeedVector().getDirection();
		movable = true;
		if (tmp.getX() == 1) {
			spriteType = "right";
		} else if (tmp.getX() == -1) {
			spriteType = "left";
		} else if (tmp.getY() == 1) {
			spriteType = "down";
		} else if (tmp.getY() == -1) {
			spriteType = "up";
		} else {
			spriteType = "static";
			spriteManager.reset();
			movable = false;
		}
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());

	}

	
	@Override
	public void oneStepMoveAddedBehavior() {
		if (movable) {
			spriteManager.increment();
		}
	}

	@Override
	public int getSpeed() {
		return SPEED;
	}

	@Override
	public boolean isFriend() {
		return isFriend;
	}

}
