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
* JacobiPolynomial.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.polynomial;

import imr.math.Factorial;
import imr.math.stat.GammaFunction;

/**
* This class implements some functionallity related to Jacobi polynomials. <p>
* These kind of polynomials, occasionally called hypergeometric polynomials, are as follows: <p>
* n-permutations(alpha, beta)x, are a class of classic orthogonal polynomials. <p>
* They are orthogonal respect to the weight: <code>(1 - x)^alpha(1+x)^beta on the interval [-1, 1]</code>
* <p>
* The static methods in this class return polynomials in increase power order [0 .. n]. <p>
* This class uses assertions; to enable them, you can use the '-ea' modifier when execute: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class JacobiPolynomial
{

/**
* Static method to compute the coefficients for a Jacobi polynomial with params alpha and beta. <p>
* The computed polynomial will be returned as a double floating-point array in increased order [0 .. n] <p>
* @param n
* An integer value greater than zero to set the order of the wanted polynomial.
* <p>
* @param alpha
* A double floating-point value.
* <p>
* @param beta
* A double floating-point value.
* <p>
* @return Computed polynomial.
*
*/
public static double[] compute(int n, double alpha, double beta)
{
return compute(n, alpha, beta, 0.0);
}

/**
* Static method to compute the Jacobi polynomial evaluated for a given 'x' value. <p>
* @param n
* An integer value greater than zero to set the order for the wanted polynomial.
* <p>
* @param alpha
* A double floating-point value.
* <p>
* @param beta
* A double floating-point value.
* <p>
* @param x
* A double floating-point value.
* <p>
* @return Computed polynomial.
*
*/
public static double[] compute(int n, double alpha, double beta, double x)
{
assert(n > 0): "JacobiPolynomial -> compute(...): Bad parameter; 'n' must be greater than zero.";
double[] p = new double[n+1];
for(int k = 0; k <= n; k++)
{
p[k] = term(k, alpha, beta, x);
}
return Polynomial.reverse(p);
}

/**
* This method computes the n-th term of a Jacobi polynomial. <p>
* Mind that the 0-th term is, actually, the leading coefficient. <p>
* That is because we arrange terms in the needed order to solve polynomial roots. <p>
* @param n
* An integer value equal or greater than zero.
* <p>
* @param alpha
* A double floating-point value.
* <p>
* @param beta
* A double floating-point value.
* <p>
* @param x
* A double floating-point value.
* <p>
* @return Computed value.
*
*/
public static double term(int n, double alpha, double beta, double x)
{
	assert(n >= 0): "JacobiPolynomial -> term(...): Bad parameter; 'n' must be equal or greater than zero.";
	if(n == 0) return 1.0;
	double sum = 0.0;
double t = GammaFunction.compute(alpha+n+1) / (Factorial.compute(n)*GammaFunction.compute(alpha+beta+n+1));
for(int m = 0; m <= n; m++)
{
sum += BinomialCoefficient.compute(n, m).doubleValue() * GammaFunction.compute(alpha+beta+n+m+1)/GammaFunction.compute(alpha+m+1) * Math.pow((x-1.0)/2.0, m);
}
return t * sum;
}


// Private construbtor so that this class cannot be instantiated
private JacobiPolynomial() {}

}

// END
