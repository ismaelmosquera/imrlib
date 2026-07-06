/*
 * Copyright (c) 2025 Ismael Mosquera Rivera
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
* LagrangeInterpolator.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial;

import imr.math.Point2D;

/**
* The <code>LagrangeInterpolator</code> class performs Lagrange polynomial interpolation.
* <p>
* The Lagrange interpolating polynomial is the unique polynomial
* <p>
* of lowest degree that interpolates a given set of data.
* <p>
* Given a data set of (xi, yi) coordinates.
* <p>
* The interpolated values are computed passing a vector with the x-nodes for which we want to get the desired y-values.
* <p>
* Example:
* <p>
* For the points: (-2.0, 0.0), (0.0, 1.0), (2.0, 0.0).
* <p>
* If we wish to get the interpolated y-values for x = [-1.5 -1.0 1.0 1.5]
* <p>
* LagrangeInterpolator li = new LagrangeInterpolator(points);
* <p>
* float[] y = li.interpolate(x);
* <p>
* y = [0.43 0.75 0.75 0.43]
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class LagrangeInterpolator
{

/**
* Constructor.
* <p>
* @param points
* Set of points.
*
*/
public LagrangeInterpolator(Point2D[] points)
{
this.setPoints(points);
}

/**
* Sets the set of points.
* <p>
* @param points
* Set of points.
*
*/
public void setPoints(Point2D[] points)
{
_n = points.length;
_x = null;
_y = null;

_x = new double[_n];
_y = new double[_n];

for(int i = 0; i < _n; i++)
{
_x[i] = points[i].getX();
_y[i] = points[i].getY();
}
}

/**
* Interpolates the x-points passed as parameter to get y-values.
* <p>
* @param x
* X-points to get its interpolated y-values.
* <p>
* @return Interpolated y-values.
*
*/
public double[] interpolate(double[] x)
{
	int n = x.length;
double[] px = new double[n];
for(int i = 0; i < n; i++)
{
px[i] = Pn(_x, _y, x[i]);
}
return px;
}


/*
* Gets the Pn(x) value.
*/
private double Pn(double[] x, double[] y, double xn)
{
double pn = 0.0;
for(int i = 0; i < _n; i++)
{
pn += Li(i, x, xn) * y[i];
}
return pn;
}

/*
* Gets the Li(x) value.
*/
private double Li(int i, double[] x, double xn)
{
double retval = 1.0;
for(int j = 0; j < _n; j++)
{
if(i != j)
{
	retval *= (xn-x[j]) / (x[i]-x[j]);
}
}
return retval;
}


private int _n; // number of points
private double[] _x; // x-coordinates
private double[] _y; // y-coordinates
}

// END
