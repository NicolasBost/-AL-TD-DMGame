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

public class HorseMan extends GameMovable implements Drawable, GameEntity, Overlappable {

	protected final SpriteManager spriteManager;
	public static final int RENDERING_SIZE = 16; //taille finale
	protected boolean movable = true;
	
	public HorseMan(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerDefaultImpl("images/horseman.png",
				defaultCanvas, RENDERING_SIZE, 4); //source, defautCanvas, | , nbSpriteParLigne
		spriteManager.setTypes(
				//
				"left", "right", "up",
				"down",//
				"static");
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

}
