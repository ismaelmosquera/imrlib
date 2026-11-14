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
* ChebyshevTPolynomial.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.polynomial;

/**
* This class offers some functionallity related to 1st kind Chebyshev polynomials. <p>
* This class uses assertions; to enable them you can use the '-ea' modifier at execution time: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera rivera
*
*/
public final class ChebyshevTPolynomial
{

/**
* Static method to compute n-th Chebyshev polynomials of first kind. <p>
* Take in account that we return such a polynomial in ascending powers in order to prepare them to solve polynomial roots. <p>
* @param n
* An integer value greater than zero.
* <p>
* @param x
* A double floating-point value.
* <p>
* @return Computed polynomial.
*
*/
public static double[] compute(int n, double x)
{
assert(n > 0): "ChebyshevTPolynomial -> compute(...): Bad parameter; 'n' must be greater than zero.";
double[] p = new double[n+1];
for(int k = 0; k <= n; k++)
{
p[k] = term(k, x);
}
return Polynomial.reverse(p);
}

/**
* Static method to compute the n-th term for a first kind Chebyshev polynomial. <p>
* Take in account that for n = 0, the leading polynomial coefficient is returned. <p>
* @param n
* An integer value equal or greater than zero.
* <p>
* @param x
* A double floating-point value.
* <p>
* @return Computed term.
*
*/
public static double term(int n, double x)
{
	assert(n >= 0): "ChebyshevTPolynomial -> term(...): Bad parameter; 'n' must be equal or greater than zero.";
if(n == 0) return 1.0;
if(n == 1) return x;
return 2.0 * x * term(n-1, x) - term(n-2, x);
}


// Private constructor so that this class cannot be instantiated
private ChebyshevTPolynomial() {}

}

// END

