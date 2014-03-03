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

import java.io.*;
import java.util.regex.*;
import java.util.*;

public class Fast 
{
	private static List<List<Point>> lines;
	private static Point[] points;
	private static int amount;

	public static void main(String[] args)throws IOException
	{
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);

		String filename = args[0];
		In in = new In(filename);
		String[] coordinates = new String[2];
		lines = new ArrayList<List<Point>>();
		amount = in.readInt();
		points = new Point[amount];//set the array to be the size of the first number in the file

		for (int i = 0; i < amount; i++) 
		{
			points[i] = new Point(in.readInt(), in.readInt());
		}
		
		in.close();

		Arrays.sort(points);

		getLines();

		mergeCongruentLines();

		print();

		drawLines();
	}

	private static void getLines()
	{
		for(int i = 0; i < amount; i++)
		{

			boolean match = false;
			int start = 0;
			List<Point> linePoints = new ArrayList<Point>();
			Point[] slopes = new Point[amount-(i+1)];

			for(int j = i+1; j < amount; j++)
			{
				slopes[j-(i+1)] = points[j];
			}

			Arrays.sort(slopes, points[i].SLOPE_ORDER);

			for(int j = 1; j < slopes.length; j++)
			{
				if(points[i].slopeTo(slopes[j]) == points[i].slopeTo(slopes[j-1]) && !match)
				{
					match = true;
					start = j-1;
				}
				else if((points[i].slopeTo(slopes[j]) != points[i].slopeTo(slopes[j-1])  || j + 1 == slopes.length) && match)
				{
					if((j - start + 1 > 3)||(j + 1 == slopes.length && j - start + 1 >= 3))
					{
						linePoints.add(points[i]);
						for(int k = start; k < j; k++)
						{
							linePoints.add(slopes[k]);
						}
						if(points[i].slopeTo(slopes[j]) == points[i].slopeTo(slopes[j-1]))
						{
							linePoints.add(slopes[j]);
						}
						lines.add(linePoints);
						linePoints = new ArrayList<Point>();
					}
					match = false;
				}
			}
		}
	}

	private static void print()
	{
		//StdOut.println(lines.size());
		for(int i = 0; i < lines.size(); i++)
		{
			for(int j = 0; j < lines.get(i).size(); j++)
			{
				StdOut.print(lines.get(i).get(j));
				if(j != lines.get(i).size()-1)
				{
					StdOut.print(" -> ");
				}
			}
			StdOut.println();
		}
	}

	private static void mergeCongruentLines()
	{
		for(int i = 0;  i < lines.size()-1; i++)
		{
			for(int j = i+1;  j < lines.size(); j++)
			{
				if(lines.get(i).get(0).slopeTo(lines.get(i).get(1)) == lines.get(i).get(0).slopeTo(lines.get(j).get(1)))
				{
					lines.get(i).addAll(lines.get(j));
					lines.remove(j);
					Collections.sort(lines.get(i));
					for(int k = 1; k < lines.get(i).size(); k++)
					{
						if(lines.get(i).get(k-1).X() == lines.get(i).get(k).X() && lines.get(i).get(k-1).Y() == lines.get(i).get(k).Y())
						{
							lines.get(i).remove(k);
							k--;
						}
					}
					j--;
				}
			}
		}
	}

	private static void drawLines()
	{
		for(int i = 0; i < lines.size(); i++)
		{
			lines.get(i).get(0).drawTo(lines.get(i).get(lines.get(i).size()-1));
		}
		for(int i = 0; i < amount; i++)
		{
			points[i].draw();
		}
		StdDraw.show();
	}
}
