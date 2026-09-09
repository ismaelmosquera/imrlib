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
* BetaFunction.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

import imr.math.ComplexNumber;

/**
* This class implements the Euler's Beta function. <p>
* It was implemented for real and complex numbers. <p>
* This class uses assertions; to enable them you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <p>
*
* @author Ismael Mosquera rivera.
*
*/
public final class BetaFunction
{

/**
* Static method to compute the Beta function for real numbers. <p>
* Notice that x1 and x2 must be greater than zero. <p>
* @param x1
* A double floating-point precission value.
* <p>
* @param x2
* A double floating-point precission value.
* <p>
* @return Computed value.
*
*/
public static double compute(double x1, double x2)
{
	assert(x1 > 0.0 && x2 > 0.0): "BetaFunction -> compute(...): Bad parameter; x1 and x2 must be greater than zero.";
return (GammaFunction.compute(x1)*GammaFunction.compute(x2)) / GammaFunction.compute(x1+x2);
}

/**
* Static method to compute the Euler's Beta function for complex numbers. <p>
* Notice that the real part of z1 and z2 must be greater than zero. <p>
* @param z1
* A complex number.
* <p>
* @param z2
* A complex number.
* <p>
* @return Computed value.
*
*/
public static ComplexNumber compute(ComplexNumber z1, ComplexNumber z2)
{
assert(z1.getReal() > 0.0 && z2.getReal() > 0.0): "BetaFunction -> compute(...): Bad parameter; real part of z1 and z2 must be greater than zero.";
ComplexNumber zi = (Math.abs(z1.getImag()) < 1E-6) ? new ComplexNumber(z1.getReal(), 0.0) : (ComplexNumber)z1.clone();
ComplexNumber zj = (Math.abs(z2.getImag()) < 1E-6) ? new ComplexNumber(z2.getReal(), 0.0) : (ComplexNumber)z2.clone();
return GammaFunction.compute(zi).mul(GammaFunction.compute(zj)).div(GammaFunction.compute(zi.add(zj)));
}


// Private constructor so that this class cannot be instantiated
private BetaFunction() {}

}

// END
