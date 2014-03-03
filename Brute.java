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

public class Brute 
{
	private static List<List<Integer>> lines;//try to optimise, for what little it's worth... instead of creating a massive list of points, I'll just store the index of the point in the array
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
		lines = new ArrayList<List<Integer>>();
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
		List<Integer> linePoints = new ArrayList<Integer>();//referencing the array instead of making objects which would take up more memory, should halve the actual memory used by the list

		for (int i = 0; i < amount-3;i++)
		{
			for (int j = i+1; j < amount-2;j++)
			{
				for (int k = j+1; k < amount-1;k++)
				{
					for (int l = k+1; l < amount;l++)
					{
						double m1 = points[i].slopeTo(points[j]);
						double m2 = points[i].slopeTo(points[k]);//get the three slopes
						double m3 = points[i].slopeTo(points[l]);
						if(m1 == m2 && m1 == m3)
						{
							linePoints.add(i);//add the slopes to a  list
							linePoints.add(j);
							linePoints.add(k);
							linePoints.add(l);
							lines.add(linePoints);//add the list to a list of lines
							linePoints = new ArrayList<Integer>();//make the first list empty again
						}
					}
				}
			}//seriously don't bother running this program, it takes ages
		}
	}

	private static void print()
	{
		//StdOut.println(lines.size());
		for(int i = 0; i < lines.size(); i++)
		{
			for(int j = 0; j < lines.get(i).size(); j++)
			{
				StdOut.print(points[lines.get(i).get(j)]);
				if(j != lines.get(i).size()-1)
				{
					StdOut.print(" -> ");
				}
			}
			StdOut.println();
		}
	}

	private static void mergeCongruentLines()//Since this bulky as hell code only creates lines of exactly four points, this will merge lines that should really be one... I'm pretty sure this was an extra credit method and not actually a requirement to pass
	{//a->b->c->d and b->c->d->e will become a->b->c->d->e and so on
		for(int i = 0;  i < lines.size()-1; i++)
		{
			for(int j = i+1;  j < lines.size(); j++)
			{
				if(points[lines.get(i).get(0)].slopeTo(points[lines.get(i).get(1)]) == points[lines.get(i).get(0)].slopeTo(points[lines.get(j).get(1)]))
				{//more or less the same principle as the regular code for finding collinear points
					lines.get(i).addAll(lines.get(j));
					lines.remove(j);
					Collections.sort(lines.get(i));
					for(int k = 1; k < lines.get(i).size(); k++)
					{
						if(lines.get(i).get(k-1) == lines.get(i).get(k))
						{
							lines.get(i).remove(k);
							j--;
						}
					}
				}
			}
		}
	}

	private static void drawLines()
	{
		for(int i = 0; i < lines.size(); i++)
		{
			points[lines.get(i).get(0)].drawTo(points[lines.get(i).get(lines.get(i).size()-1)]);
		}
		for(int i = 0; i < amount; i++)
		{
			points[i].draw();
		}
		StdDraw.show();
	}
}
