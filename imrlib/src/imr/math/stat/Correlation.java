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
* Correlation.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera.
*
*/

package imr.math.stat;

/**
* The <code>Correlation</code> class has a static method to compute ro. <p>
* That is the known Pearson's Correlation Coefficient. <p>
*
* @author Ismael Mosquera rivera.
*
*/
public final class Correlation
{

/**
* This static method computes the auto-correlation coefficient for a sequence of values. <p>
* Actually, this method calls <code>cor(x, x)</code> where x is a sequence of double floating-point values. <p>
* @param x
* A sequence of floating-point values.
* <p>
* @return Correlation coefficient.
*
*/
public static double acor(double[] x)
{
return cor(x, x);
}

/**
* This static method computes the <code>ro(x, y)</code> for the sequences passed as parameters. <p>
* The returned value is just the Pearson's Correlation Coefficient. <p>
* such a coefficient must be in the range [-1 .. 1] where |ro(x, y)| less-equals 1 <p>
* |ro(x, y)| = 1 means that both sequences are completely correlated. <p>
* If that value is positive, it means that when a value of the 'x' increases or decreases,also increases or decreases the value for 'y'. <p>
* On the other hand, if the value is negative, it means that when a value in a sequence increases or decreases, the value in the other sequence behaves opposite. <p>
* @param x
* A double floating-point array.
* <p>
* @param y
* A double floating-point array.
* <p>
* @return Correlation coefficient for the sequences passed as parameters.
*
*/
public static double cor(double[] x, double[] y)
{
	double dx = BasicStat.dev(x);
	double dy = BasicStat.dev(y);
	if(dx == 0.0 || dy == 0.0) return Double.NaN;
	double num = Covariance.cov(x, y);
	double den = dx * dy;
	return num / den;
}


// Private constructor so that this class cannot be instantiated.
private Correlation() {}

}

// END
