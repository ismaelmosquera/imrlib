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
* Covariance.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera.
*
*/

package imr.math.stat;

/**
* The <code>Covariance</code> class has a static method to compute the covariance value for two sequences. <p>
*
* @author Ismael Mosquera Rivera.
*
*/
public final class Covariance
{

/**
* This static method computes the auto-covariance coefficient for a single sequence. <p>
* actually, what this method does is calling the <code>cov(x, x)</code> method in order to do the task. <p>
* @param x
* A double floating-point array.
* <p>
* @return Covariacnce coefficient.
*
*/
public static double acov(double[] x)
{
return cov(x, x);
}

/**
* This static method computes the covariance between the sequences passed as parameters. <p>
* If the returned value is positive, that means the value in the 'x' sequence behaves in the same way that in the 'y' one. <p>
* if the returned value is negative, it means that a value in the 'x' sequence behaves opposite that a value in the 'y' sequence. <p>
* @param x
* A double floating-point array.
* <p>
* @param y
* A double floating-point array.
* <p>
* @return Covariance coefficient.
*
*/
public static double cov(double[] x, double[] y)
{
	int nx = x.length;
	int ny = y.length;
	assert(nx == ny): "Covariance -> cov(x[], y[]): x and y must have the same length.";
	double x_mean = BasicStat.mean(x);
	double y_mean = BasicStat.mean(y);
	double sum = 0.0;
	for(int i = 0; i < nx; i++)
	{
	sum += (x[i] - x_mean) * (y[i] - y_mean);
	}
	return sum / (double)(nx);
}


// Private constructor, so that this class cannot be instantiated.
private Covariance() {}

}

// END
