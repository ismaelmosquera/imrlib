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
* TrigonometricInterpolator.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial;

import imr.math.Point2D;

/**
* This class performs trigonometric interpolation according to a given dataset of points in the plane. <p>
* The set of points must be equaly spaced in the x-axis. <p>
*
* @author Ismael Mosquera Rivera.
*
*/
public final class TrigonometricInterpolator implements PolynomialInterpolator
{

/**
* Constructor. <p>
* @param points
* Set of points in the plane equaly spaced in the x-axis.
*
*/
public TrigonometricInterpolator(Point2D[] points)
{
this.setPoints(points);
}

/**
* Sets the points in order to build a polynomial interpolator. <p>
* @param points
* Set of known points as an array.
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
double d = 2.0 / (double)_n;
_sf = (_x[1]-_x[0]) / d;
for(int i = 0; i < _n; i++) _x[i] /= _sf;
}

/**
* This method gets an array having the interpolated y-coordinates. <p>
* @param x
* An array of double floating-point values having the x-coordinates where we want interpolate.
* <p>
* @return An array of double floating-point values having the wanted interpolated y-coordinates.
*
*/
public double[] interpolate(double[] x)
{
	int n = x.length;
double[] p = new double[n];
for(int i = 0; i < n; i++)
{
p[i] = pn(x[i]/_sf);
}
return p;
}


/*
* This method performs trigonometric interpolation for a single value ( x-coordinate )
* returns the associated y-coordinate value.
*/
private double pn(double x)
{
double s = 0.0;
for(int k = 0; k < _n; k++)
{
s += trigint(x - _x[k]) * _y[k];
}
return s;
}

/*
* This method performs trigonometric interpolation.
*/
private double trigint(double x)
{
if(x == 0.0) return 1.0;
double t = 1.0;
if((_n % 2) == 0) // even
{
	t = Math.sin((double)_n*Math.PI*(x/2.0)) / ((double)_n * Math.tan(Math.PI * (x/2.0)));
}
else // odd
{
t = Math.sin((double)_n*Math.PI*(x/2.0)) / ((double)_n * Math.sin(Math.PI*(x/2.0)));
}
return t;
}


private int _n;
private double _sf;
private double[] _x;
private double[] _y;

}

// END

