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
* LaguerrePolynomial.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.polynomial;

import imr.math.Factorial;

/**
* This class offers functionallity related to Laguerre polynomials. <p>
* You can compute just a single term or an entire n-th degree polynomial.
* <p>
* This class uses assertions; to enable them you can use the '-ea' modifier at execution time: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class LaguerrePolynomial
{

/**
* Static method to compute a n-th degree Laguerre polynomial. <p>
* @param n
* An integer value greater than 0 to set the order for the wanted polynomial.
* <p>
* @param x
* A double floating-point value.
* <p>
* @return Computed polynomial.
*
*/
public static double[] compute(int n, double x)
{
assert(n > 0): "LaguerrePolynomial -> compute(...): Bad parameter; 'n' must be greater than zero.";
double[] p = new double[n+1];
for(int k = 0; k <= n; k++)
{
p[k] = term(k, x);
}
return p;
}

/**
* Static method to compute a single term of a Laguerre polynomial. <p>
* @param n
* An integer value equal or greater than 0 to get the wanted term.
* <p>
* @param x
* A double floating-point value.
* <p>
* @return Computed term.
*
*/
public static double term(int n, double x)
{
assert(n >= 0): "LaguerrePolynomial -> term(...) Bad parameter; 'n' must be equal or greater than zero.:";
double s = 0.0;
for(int k = 0; k <= n; k++)
{
s += Math.pow(-1.0, k)/Factorial.compute(k)*BinomialCoefficient.compute(n, k).doubleValue()*Math.pow(x, k);
}
return s;
}


// Private constructor so that this class cannot be instantiated
private LaguerrePolynomial() {}

}

// END
