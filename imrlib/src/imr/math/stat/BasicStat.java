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
* BasicStat.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

/**
* The <code>BasicStat</code> class has just a few static methods in order to compute some basic functions.
* <ul>
* <li>Mean</li>
* <li>Deviation</li>
* <li>Variance</li>
* </ul>
*
* @author Ismael Mosquera Rivera.
*
*/
public class BasicStat
{

/**
* This static method computes the mean for the values in the array passed as parameter. <p>
* @param x
* A double floating-point aray.
* <p>
* @return Arithmetic mean of the values in the array passed as parameter.
*
*/
public static double mean(double[] x)
{
int n = x.length;
double m = 0.0;
for(int i = 0; i < n; i++) m += x[i];
return m/(double)n;
}

/**
* This static method computes the variance for the values in the array passed as parameter. <p>
* @param x
* A double floating-point aray.
* <p>
* @return Variance of the values in the array passed as parameter.
*
*/
public static double var(double[] x)
{
int n = x.length;
double v = 0.0;
for(int i = 0; i < n; i++) v += square(x[i]);
v /= (double)n;
return v - square(mean(x));
}

/**
* This static method computes the deviation for the values in the array passed as parameter. <p>
* @param x
* A double floating-point aray.
* <p>
* @return Deviation of the values in the array passed as parameter.
*
*/
public static double dev(double[] x)
{
return Math.sqrt(var(x));
}

// Helper static method.
static double square(double x)
{
return x*x;
}


// Private constructor so that this class cannot be instantiated
private BasicStat() {}
}

// END
