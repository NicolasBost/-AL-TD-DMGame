package mygame.entity;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.core.GameEntity;
import gameframework.moves_rules.MoveBlocker;

public class BlockerWall implements MoveBlocker, GameEntity {
	int x, y;
	public static final int RENDERING_SIZE = 16;

	public BlockerWall(Canvas defaultCanvas, int xx, int yy) {
		x = xx;
		y = yy;
	}

	public Point getPos() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, RENDERING_SIZE, RENDERING_SIZE));
	}
}
