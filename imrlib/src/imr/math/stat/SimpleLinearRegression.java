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
* SimpleLinearRegression.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

import imr.math.Point2D;
import imr.math.matrix.Vector;

/**
* The <code>SimpleLinearRegression</code> class performs linear prediction for an independent variable 'x' to a dependent variable 'y'. <p>
* According to a data set of points p(x, y) in the Cartesian Plane. <p>
* where the 'x' coordinate acts as the independent variable, and 'y' as the dependent one. <p>
*
* @author Ismael Mosquera Rivera.
*
*/
public final class SimpleLinearRegression
{

/**
* Constructor. <p>
* Makes a new instance for a <code>SimpleLinearRegression</code> object. <p>
* @param dataSet
* An array of point(x, y) in the Cartesian Plane.
*
*/
public SimpleLinearRegression(Point2D[] dataSet)
{
this.set(dataSet);
}

/**
* Constructor. <p>
* Makes a new instance for a <code>SimpleLinearRegression</code> object.
* @param filename
* A string having the path to a file containing the required data.
*
*/
public SimpleLinearRegression(String filename)
{
Point2D[] p = Point2D.loadPoint2DArray(filename);
this.set(p);
}

/**
* Sets the data for a given simple linear regression. <p>
* @param points
* An array of points (x, y) in the Cartesian Plane in order to init the concrete algorithm.
*
*/
public void set(Point2D[] points)
{
double[] x = new double[points.length];
double[] y = new double[points.length];
for(int i = 0; i < points.length; i++)
{
x[i] = points[i].getX();
y[i] = points[i].getY();
}
double x_mean = BasicStat.mean(x);
double y_mean = BasicStat.mean(y);
// compute slope
double num = 0.0;
double den = 0.0;
int n = x.length;
for(int i = 0; i < n; i++)
{
num += (x[i]-x_mean) * (y[i]-y_mean);
den += (x[i]-x_mean) * (x[i]-x_mean);
}
_b = num / den;
// compute interceptor
_a = y_mean - _b*x_mean;
}

/**
* Predicts the value for a dependent variable 'y' according to a 'x' one. <p>
* @param x
* A double floating-point value, for the independent variable.
* <p>
* @return Predicted value for the 'y' dependent variable.
*
*/
public double predict(double x)
{
return _a + _b*x;
}

/**
* Predicts the 'y' dependent values according to the 'x' coordinates passed in the parameter vector. <p>
* @param x
* A <code>imr.math.matrix.Vector</code> having the wanted 'x' coordinates.
* <p>
* @return A vector with the predicted 'y' values
*
*/
public Vector predict(Vector x)
{
	int n = x.size();
	Vector y = new Vector(n);
	for(int i = 0; i < n; i++)
	{
y.set(i, predict(x.get(i)));
}
return y;
}


private double _b; // slope
private double _a; // interceptor

}

// END
