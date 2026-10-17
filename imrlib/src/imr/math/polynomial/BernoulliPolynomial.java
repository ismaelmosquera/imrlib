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
* BernoulliPolynomial.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.polynomial;

import imr.math.ComplexNumber;
import imr.math.BernoulliNumber;

/**
* This class computes nth degree Bernoulli polynomials. <p>
* You can get just the coefficients, but also evaluate them for real and complex values.
* <p>
* this class uses assertions; to enable them you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <P>
* @author Ismael Mosquera Rivera
*
*/
public final class BernoulliPolynomial
{

/**
* This static method computes the coefficients for a nth degree Bernoulli polynomial. <p>
* We decided that the degree 'n' be greater than zero, since we understand that a polynomial should have more than one monomial. <p>
* @param n
* An integer greater than zero.
* <p>
* @return nth Bernoulli polynomial coefficients.
*
*/
public static double[] compute(int n)
{
return compute(1.0, n);
}

/**
* This static method computes the nth degree Bernoulli polynomial evaluated for a concrete 'x' real value. <p>
* If 'x' is a root, as you already know, the sum of the terms for the computed polynomial must be 0. <p>
* @param x
* A real value.
* <p>
* @param n
* An integer value greater than 0 to set the degree of the wanted polynomial.
* <p>
* @return Computed nth degree Bernoulli polynomial for a given 'x'.
*
*/
public static double[] compute(double x, int n)
{
assert(n > 0): "BernoulliPolynomial -> compute(...): Bad parameter; 'n' must be greater than zero.";
double[] bn = new double[n+1];
for(int k = 0; k <= n; k++)
{
bn[k] = BinomialCoefficient.compute(n, k).doubleValue() * BernoulliNumber.compute(n-k).value() * Math.pow(x, k);
if(k == (n- 1)) bn[k] *= -1.0;
}
return bn;
}

/**
* This static method computes the nth degree Bernoulli polynomial evaluated for a concrete 'z' complex value. <p>
* If 'z' is a root, as you already know, the sum of the terms for the computed polynomial must be 0. <p>
* @param z
* A complex value.
* <p>
* @param n
* An integer value greater than 0 to set the degree of the wanted polynomial.
* <p>
* @return Computed nth degree Bernoulli polynomial for a given 'z'.
*
*/
public static ComplexNumber[] compute(ComplexNumber z, int n)
{
assert(n > 0): "BernoulliPolynomial -> compute(...): Bad parameter; 'n' must be greater than zero.";
ComplexNumber[] bn = new ComplexNumber[n+1];
for(int k = 0; k <= n; k++)
{
bn[k] = (new ComplexNumber(BinomialCoefficient.compute(n, k).doubleValue(), 0.0)).mul(new ComplexNumber(BernoulliNumber.compute(n-k).value(), 0.0)).mul(z.pow(k));
if(k == (n-1)) bn[k] = bn[k].scale(-1.0);
}
return bn;
}


// Private constructor so that this class cannot be instantiated
private BernoulliPolynomial() {}

}

// END
