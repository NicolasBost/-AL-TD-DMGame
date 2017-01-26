package mygame.rule;

import java.awt.Point;

public class Node implements Comparable<Node>
{

	public Point point;
	public Node parent;
	public int G;
	public int H;
	public int F;

	public Node(Point p)
	{
		this.point = p;
	}

	public Node(Point p, Node parent, int g, int h, int f)
	{
		this.point = p;
		this.parent = parent;
		G = g;
		H = h;
		F = f;
	}

	@Override
	public int compareTo(Node o)
	{
		if (o == null) return -1;
		return (point.equals(o.point))? 0: -1;
	}
}