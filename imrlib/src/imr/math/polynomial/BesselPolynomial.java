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
* BesselPolynomial.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial;

import imr.math.ComplexNumber;
import imr.math.Factorial;
import imr.math.stat.GammaFunction;

/**
* The <code>BesselPolynomial</code> class computes Bessel polynomials for real and complex values. <p>
* this class uses assertions; to enable them you can use the '-ea' when execute: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class BesselPolynomial
{

/**
* Static method to compute the coefficients for Bessel polynomials. <p>
* @param n
* An integer value for the order of the polynomial.
* <p>
* @return Coefficients for the Bessel polynomial of order 'n'.
*
*/
	public static double[] compute(int n)
	{
	return compute(n, 1.0);
	}

/**
* Static method to compute the reverse Bessel polynomial coefficients of order 'n' <p>
* @param n
* An integer value of the wanted order for the polynomial.
* <p>
* @return Computed coefficients in reverse order.
*
*/
	public static double[] computeReverse(int n)
	{
		return Polynomial.reverse(compute(n, 1.0));
	}

/**
* Static method to compute Bessel polynomials for real values. <p>
* If you only wish to get the coefficients, just set x = 1 <p>
* @param n
* An integer value greater than zero to set the order of the polynomial.
* <p>
* @param x
* A floating-point value.
* <p>
* @return Computed polynomial.
*
*/
public static double[] compute(int n, double x)
{
	assert(n > 0): "BesselPolynomial -> compute(...): Bad parameter; filter order must be greater than zero. ";
double[] p = new double[n+1];
for(int k = 0; k < p.length; k++)
{
p[k] = Factorial.compute(n+k)/(Factorial.compute(n-k)*Factorial.compute(k))*Math.pow(x/2.0, k);
}
return p;
}

/**
* Static method to compute Bessel polynomials for complex values. <p>
* If you only wish to get the coefficients, just set z = 1 + 0i <p>
* @param n
* An integer value greater than zero to set the order of the polynomial.
* <p>
* @param z
* A complex value.
* <p>
* @return Computed polynomial.
*
*/
public static ComplexNumber[] compute(int n, ComplexNumber z)
{
assert(n > 0): "BesselPolynomial -> compute(...): Bad parameter; filter order must be greater than zero. ";
ComplexNumber[] p = new ComplexNumber[n+1];
for(int k = 0; k < p.length; k++)
{
p[k] = GammaFunction.compute(new ComplexNumber((double)(n+k+1), 0.0)).div(GammaFunction.compute(new ComplexNumber((double)(n-k+1), 0.0)).mul(GammaFunction.compute(new ComplexNumber((double)(k+1), 0.0)))).mul(z.scale(1.0/2.0).pow(k));
}
return p;
}


// Private constructor so that this class cannot be instantiated
private BesselPolynomial() {}

}

// END
