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
* ChebyshevUPolynomial.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.polynomial;

/**
* This class offers some functionallity related to 2nd kind Chebyshev polynomials. <p>
* This class uses assertions; to enable them you can use the '-ea' modifier at execution time: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera rivera
*
*/
public final class ChebyshevUPolynomial
{

/**
* Static method to compute polynomial coefficients of a n-th degree Chebyshev polynomials of seccond kind for a given 'x'. <p>
* @param n
* An integer value greater than 0 to set the polynomial degree.
* <p>
* @param x
* A double floating-point value.
* <p>
* @return Computed polynomial.
*
*/
public static double[] compute(int n, double x)
{
	assert(n > 0): "ChebyshevUPolynomial -> compute(...): Bad parameter; 'n' must be greater than zero."; // we understand that a polynomial must have at least degree 1; otherwise it is a momonomial.
return Polynomial.scale(JacobiPolynomial.compute(n, 0.5, 0.5, x), Math.pow(2.0, 2*n+1)/BinomialCoefficient.compute(2*n+2, n+1).doubleValue());
}

/**
* Static method to evaluate the n-th  degree of a second kind Chebyshev polynomial for a given 'x' point. <p>
* @param n
* An integer value equal or greater than zero.
* <p>
* @param x
* A double floating-point value.
* <p>
* @return Computed value.
*
*/
public static double evaluate(int n, double x)
{
assert(n >= 0): "ChebyshevUPolynomial -> evaluate(...): Bad parameter, 'n' must be equal or greater than zero.";
if(n == 0) return 1.0;
if(n == 1) return 2.0*x;
return 2.0 * x * evaluate(n-1, x) - evaluate(n-2, x);
}


// Private constructor so that this class cannot be instantiated
private ChebyshevUPolynomial() {}

}

// END

