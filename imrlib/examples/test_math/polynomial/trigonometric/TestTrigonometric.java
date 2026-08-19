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
* TestTrigonometric.java
*
* Author: Ismael Mosquera rivera.
*/

import imr.util.iArray;
import imr.math.Point2D;
import imr.math.polynomial.TrigonometricInterpolator;
import imr.math.polynomial.PolynomialInterpolator;

/*
* This example demonstrates the TrigonometricInterpolator class.
* First we load a dataset of points equaly spaced in the 'x' axis with the associated 'y' values.
* Afterwards, we passed an array of x-coordinates to get the associated interpolated values of y-coordinates.
*
* Author: Ismael Mosquera rivera
*
*/
public class TestTrigonometric
{
public static void main(String[] args)
{
// load point set.
Point2D[] p = Point2D.loadPoint2DArray("points.dat");
System.out.println("Points to pass through:");
Point2D.print(p);

// array having the 'x' coordinates where we want to interpolate points.
double[] x = {-1.25, -0.75, -0.25, 0.25, 0.75, 1.25};

PolynomialInterpolator ti = new TrigonometricInterpolator(p);
double[] y = ti.interpolate(x);

// print the result to the console.
iArray.print(x, "input x = ");
iArray.print(y, "output y = ");

System.out.println();
System.out.println("bye.");
}
}

// END

