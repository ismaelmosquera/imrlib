/*
 * Copyright (c) 2026 Ismael Mosquera Rivera
 *
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

/*
* TestLagrange.java
*
* Author: Ismael Mosquera rivera.
*/

import imr.util.iArray;
import imr.math.Point2D;
import imr.math.polynomial.LagrangeInterpolator;

/*
* This example demonstrates Lagrange polynomial interpolation.
* <p>
* Having a set of points, this algorithm performs polynomial interpolation.
* <p>
* The algorithm takes as input a float array with the 'x' coordinates where we want to get the 'y' coordinates for the interpolated points.
*
*/
public class TestLagrange
{
public static void main(String[] args)
{
// load point set.
Point2D[] p = Point2D.loadPoint2DArray("pointset.dat");
System.out.println("Points to pass through:");
Point2D.print(p);

// array having the 'x' coordinates where we want to interpolate points.
float[] x = {-2.5f, -1.5f, -1.0f, -0.5f, 0.5f, 1.0f, 1.5f, 2.5f};

// Make an instance for a LagrangeInterpolator object.
LagrangeInterpolator li = new LagrangeInterpolator(p); // pass the set of points as parameter for the constructor
float[] y = li.interpolate(x); // pass the 'x' coordinates array as parameter to the interpolation method to get the 'y' coordinates.

// print the result to the console.
iArray.print(x, "input x = ");
iArray.print(y, "output y = ");

// build and print the entire set of points, included the initial set.
Point2D[] points = new Point2D[11];
points[0] = new Point2D(x[0], y[0]);
points[1] = (Point2D)p[0].clone();
points[5] = (Point2D)p[1].clone();
points[9] = (Point2D)p[2].clone();
points[10] = new Point2D(x[7], y[7]);

for(int i = 1; i < 4; i++) points[i+1] = new Point2D(x[i], y[i]);
for(int i = 4; i < 7; i++) points[i+2] = new Point2D(x[i], y[i]);

System.out.println("All points:");
Point2D.print(points);

// Save a result to a file.
Point2D.storePoint2DArray(points, "result.dat");
System.out.println("Output points saved to bin/result.dat file.");

System.out.println();
System.out.println("bye.");
}
}

// END
