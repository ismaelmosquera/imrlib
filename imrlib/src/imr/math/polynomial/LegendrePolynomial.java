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
* LegendrePolynomial.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.polynomial;

import imr.math.Factorial;

/**
* This class implements some functionallity related to Legendre polynomials. <p>
* Legendre polynomials are a complete class of orthogonal polynomials. <p>
* The static methods in this class return polynomials in increase power order [0 .. n]. <p>
* this class uses assertions; to enable them, you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class LegendrePolynomial
{

/**
* Static method to compute the coefficients for a n-th order Legendre polynomial. <p>
* Mind that we return polynomial terms in ascending powers in order to prepare them to solve polynomial roots. <p >
* @param n
* An integer value greater than zero to set the order of the polynomial.
* <p>
* @return Computed polynomial.
*
*/
public static double[] compute(int n)
{
return compute(n, 0.0);
}

/**
* Static method to compute a n-th order Legendre polynomial evaluated at the param 'x'. <p>
* Mind that we return the polynomial terms in ascending order to prepare them for solve polynomial roots. <p>
* @param n
* An integer value greater than zero to set the order of the wanted polynomial.
* <p>
* @param x
* A double floating-point value to evaluate.
* <p>
* @return Computed polynomial.
*
*/
public static double[] compute(int n, double x)
{
assert(n > 0): "LegendrePolynomial -> compute(...): Bad parameter; 'n' must be equal or greater than zero.";
double[] p = new double[n+1];
for(int k = 0; k <= n; k++)
{
p[k] = term(k, x);
}
return Polynomial.reverse(p);
}

/**
* This method computes the n-th term of a Legendre polynomial. <p>
* Take in account that n = 0 is, actually, the leading polynomial coefficient. <p>
* @param n
* An integer value greater than zero to set the order for the wanted polynomial.
* <p>
* @param x
* A double floating-point value.
* <p>
* @return Computed term.
*
*/
public static double term(int n, double x)
{
	assert(n >= 0): "LegendrePolynomial -> term(...): Bad parameter; 'n' must be equal or greater than zero.";
	double sum = 0.0;
double t = 1.0 / Factorial.compute(n);
for(int m = 0; m <= n; m++)
{
sum += BinomialCoefficient.compute(n, m).doubleValue() * Factorial.compute(n+m)/Factorial.compute(m) * Math.pow((x-1.0)/2.0, m);
}
return t * sum;
}


// Private constructor so that this class cannot be instantiated
private LegendrePolynomial() {}

}

// END
