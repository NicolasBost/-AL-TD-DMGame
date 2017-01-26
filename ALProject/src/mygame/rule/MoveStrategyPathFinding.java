package mygame.rule;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import gameframework.moves_rules.MoveStrategy;
import gameframework.moves_rules.SpeedVector;
import gameframework.moves_rules.SpeedVectorDefaultImpl;

public class MoveStrategyPathFinding implements MoveStrategy {

	private static int[][] tab;
	private Node startNode;
	private Node endNode;
	private List<Node> openSet = new ArrayList<Node>();
	private List<Node> closeSet = new ArrayList<Node>();

	public void setStartPoint(Point startPoint) {
		this.startNode = new Node(startPoint);
	}

	public void setEndPoint(Point endPoint) {
		this.endNode = new Node(endPoint);
	}

	public MoveStrategyPathFinding(int[][] tab) {
		MoveStrategyPathFinding.tab = tab;
	}

	@Override
	public SpeedVector getSpeedVector() {
		Node[][] vals = new Node[tab.length][tab[0].length];
		for (int i = 0; i < tab.length; i++)
			for (int j = 0; j < tab[i].length; j++)
				vals[i][j] = new Node(new Point(j, i));

		openSet.add(startNode);
		
		while (!openSet.isEmpty()) {
			int lowestIndex = 0;
			for (int i = 0; i < openSet.size(); i++) {
				if (openSet.get(i).F < openSet.get(lowestIndex).F) {
					
					lowestIndex = i;
				}
			}
			Node current = openSet.get(lowestIndex);
			if (current.point.equals(endNode.point)) {
				while(current.parent != null && !current.parent.point.equals(startNode.point))
					current = current.parent;
				return new SpeedVectorDefaultImpl(getDirection(startNode.point, current.point), 1);
			}
			openSet.remove(lowestIndex);
			closeSet.add(current);
			ArrayList<Point> neightbors = getNeighbors(current.point);
			for(int i = 0; i < neightbors.size(); i++){
				Point p = neightbors.get(i);
				Node neightbor = vals[p.y][p.x];
				if(!closeSet.contains(neightbor) && tab[neightbor.point.y][neightbor.point.x] != 1){
					int tmpG = current.G + 1;
					if(openSet.contains(neightbor)){
						if(tmpG < neightbor.G)
							neightbor.G = tmpG;
					}else{
						neightbor.G = tmpG;
						openSet.add(neightbor);
					}
					neightbor.H = heuristic(neightbor.point, endNode.point);
					neightbor.F = neightbor.G + neightbor.H;
					neightbor.parent = current;
					vals[p.y][p.x] = neightbor;
				}	
			}
		}
		System.out.println("No solution");
		return SpeedVectorDefaultImpl.createNullVector();
	}

	private int heuristic(Point neightbor, Point end) {
		//int d = (int) neightbor.point.distance(end.point);
		int d = Math.abs(end.x-neightbor.x) + Math.abs(end.x-neightbor.y);
		return d;
	}

	public ArrayList<Point> getNeighbors(Point p){
		ArrayList<Point> neighbors = new ArrayList<Point>();
		if(p.x+1 < tab[0].length)
			neighbors.add(new Point(p.x+1, p.y));
		if(p.x-1 >= 0)
			neighbors.add(new Point(p.x-1, p.y));
		if(p.y + 1 < tab.length)
			neighbors.add(new Point(p.x, p.y+1));
		if(p.y - 1 >= 0)
			neighbors.add(new Point(p.x, p.y-1));		
		return neighbors;
	}
	
	public Point getDirection(Point src, Point dir) {
		int x = 0;
		int y = 0;
		if(src.getX() - dir.getX() > 0)
			x = -1;
		else if(src.getX() - dir.getX() < 0)
			x = 1;
		if(x!=0)
			return new Point(x, y);
		if(src.getY() - dir.getY() > 0)
			y = -1;
		else if(src.getY() - dir.getY() < 0)
			y = 1;
		return new Point(x, y);
	}

}
