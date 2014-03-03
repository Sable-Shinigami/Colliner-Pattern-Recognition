/*
 * Copyright © 2014 Sable Shinigami
 * 
 * This is my code for a Collinear Pattern Recognition assignment for my Data Structures class.
 * I was supposed to write three classes: A Point class, a Brute class, and a Fast class.
 * The Point class' purpose is obvious enough, it's a data type to store the coordinates and help with sorting.
 * The Brute class is a very ham-handed at pattern recognition, every point is checked against every other point not already checked.
 * The Fast class is a much smarter: It selects a point and then sorts all the other points according to their angle to the selected point
 * then it searches for four or more points with the same angle and says that's a line. Then it doe this again and again until all points are used up.
 */

import java.util.Comparator;

public class Point implements Comparable<Point> 
{
	public final Comparator<Point> SLOPE_ORDER;// compare points by slope to this point
	private final int x, y;

	public int X() { return x; }
	public int Y() { return y; }

	public Point(int x, int y)
	{ // construct the point (x, y)
		this.x = x;
		this.y = y;
		SLOPE_ORDER = new SLOPE_ORDER();
	}

	public void draw()
	{ // draw this point
		StdDraw.point(x, y);
	}

	public void drawTo(Point that)
	{ // draw the line segment from this point to that point
		StdDraw.line(this.x, this.y, that.X(), that.Y());
	}

	@Override public String toString()
	{ // string representation
		return "(" + x + ", " + y + ")";
	}

	@Override public int compareTo(Point that)
	{ // is this point lexicographically smaller than that point?
		if(that == null)
		{ throw new java.lang.NullPointerException(); }

		int result;

		result = this.y - that.Y();
		if(result == 0)
		{
			result = this.x - that.X();
		}

		return result;
	}

	public double slopeTo(Point that)
	{ // the slope between this point and that point
		double result;

		if(this.x == that.X() && this.y == that.Y())
		{
			result = Double.NEGATIVE_INFINITY;
		}
		else if(this.y == that.Y())
		{
			result = 0;
		}
		else if(this.x == that.X())
		{
			result = Double.POSITIVE_INFINITY;;
		}
		else
		{
			result = (((double)that.y - (double)this.Y())/((double)that.x - (double)this.X()));
		}

		return result;
	}

	private class SLOPE_ORDER implements Comparator<Point>
	{
		public int compare(Point point1, Point point2)
		{
			double m1 = slopeTo(point1), m2 = slopeTo(point2);
			int result;

			if(m1 == m2)
			{
				result =  0;
			}
			else if(m1 < m2)
			{
				result =  -1;
			}
			else
			{
				result = 1;
			}
			return result;
		}
	}
}
