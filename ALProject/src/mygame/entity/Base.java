package mygame.entity;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gameframework.core.GameEntity;
import gameframework.moves_rules.MoveBlocker;
import soldier.core.Unit;
import soldier.units.UnitBase;

public class Base implements MoveBlocker, GameEntity {

	List<Point> coordonates;
	protected UnitBase unit;

	public Base() {
		coordonates = new ArrayList<Point>();
		unit = new UnitBase("base");
	}

	public Unit getUnit() {
		return unit;
	}

	public void addCoordonate(Point p) {
		coordonates.add(p);
	}

	@Override
	public Rectangle getBoundingBox() {
		Rectangle r = new Rectangle();
		for (Point p : coordonates)
			r.add(p);
		return r;
	}

	public Point getClosestPoint(Point position) {
		Point closest = null;
		float min_dist = Float.MAX_VALUE;
		for (Point p : coordonates) {
			float dist = (float) position.distance(p);
			if (dist < min_dist) {
				closest = p;
				min_dist = dist;
			}
		}
		return closest;
	}

	public Point getSpawnablePoint() {
		return new Point(coordonates.get(coordonates.size()-1));
	}

		
}
