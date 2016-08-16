Colliner-Pattern-Recognition
============================

Collinear Pattern Recognition assignment for my Data Structures class.


This is my code for a Collinear Pattern Recognition assignment for my Data Structures class.
I was supposed to write three classes: A Point class, a Brute class, and a Fast class.
The Point class' purpose is obvious enough, it's a data type to store the coordinates and help with sorting.
The Brute class is a very ham-handed at pattern recognition, every point is checked against every other point not already checked.
The Fast class is a much smarter: It selects a point and then sorts all the other points according to their angle to the selected point
then it searches for four or more points with the same angle and says that's a line. Then it doe this again and again until all points are used up.

This code is © (Copyright) Cormac O'Connor, A.K.A. Sable Shinigami and is licensed under GPL V2.
This file may be used in any non-commercial or commercial project as long as the following conditions are met:
You may not claim this code as being your own.
You may not use this code for any harmful, malicious or otherwise damaging programs.
You must attribute it to Cormac O'Connor appropriately and include this paragraph.
