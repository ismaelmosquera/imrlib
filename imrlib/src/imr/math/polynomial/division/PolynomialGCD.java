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
* PolynomialGCD.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial.division;

import imr.util.Convert;
import imr.math.ComplexNumber;
import imr.math.polynomial.Polynomial;

/**
* The <code>PolynomialGCD</code> class has static method to compute polynomial greatest common divisor ( GCD ). <p>
* There is support to compute gcd for integer, ´floating-point and complex polynomials. <p>
* The order of p1 must be greater or equal than the order of p2. <p>
* To compute gcd, an adapted version of the Euclide's algorithm was implemented.
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class PolynomialGCD
{

/**
* Static method to compute gcd of two integer polynomials. <p>
* Notice that this method returns a float array; that is because an integer division can get a floating-point value as a result. <p>
* @param p1
* An integer array representing a polynomial.
* <p>
* @param p2
* An integer array representing a polynomial.
* <p>
* @return gcd(p1, p2)
*
*/
public static float[] compute(int[] p1, int[] p2)
{
return compute(Convert.toFloatArray(p1), Convert.toFloatArray(p2));
}

/**
* Static method to compute gcd for two float polynomials. <p>
* @param p1
* A float array representing a polynomial.
* <p>
* @param p2
* A float array representing a polynomial.
* <p>
* @return gcd(p1, p2)
*
*/
public static float[] compute(float[] p1, float[] p2)
{
float[] d = null;
float[] r = null;
float[] _p1 = Polynomial.clear(p1);
float[] _p2 = Polynomial.clear(p2);
if(_p2.length > _p1.length)
{
_p1 = Polynomial.clear(p2);
_p2 = Polynomial.clear(p1);
}
FloatPolynomialDivision fpd = new FloatPolynomialDivision();
while(true)
{
	if(_p2.length == 1)
	{
		if(Math.abs(_p2[0]) < THRESHOLD) _p2[0] = 1.0f;
		return _p2;
	}
	if(!fpd.div(_p1, _p2)) return null;
	if(zeroRemainder(fpd.remainder()))
	{
	d = Polynomial.clear(_p2);
	break;
	}
	r = fpd.remainder();
	_p1 = Polynomial.clear(_p2);
	_p2 = Polynomial.clear(r);
}
return d;
}

/**
* Static method to compute gcd for complex polynomials. <p>
* @param p1
* A complex array representing a polynomial.
* <p>
* @param p2
* A complex array reprepresenting a polynomial.
* <p>
* @return gcd(p1, p2)
*
*/
public static ComplexNumber[] compute(ComplexNumber[] p1, ComplexNumber[] p2)
{
ComplexNumber[] d = null;
ComplexNumber[] r = null;
ComplexNumber[] _p1 = Polynomial.clear(p1);
ComplexNumber[] _p2 = Polynomial.clear(p2);
if(_p2.length > _p1.length)
{
_p1 = Polynomial.clear(p2);
_p2 = Polynomial.clear(p1);
}
ComplexPolynomialDivision cpd = new ComplexPolynomialDivision();
while(true)
{
	if(_p2.length == 1)
	{
		if(_p2[0].magnitude() < THRESHOLD) _p2[0] = new ComplexNumber(1.0f, 0.0f);
		return _p2;
	}
	if(!cpd.div(_p1, _p2)) return null;
	if(zeroRemainder(cpd.remainder()))
	{
	d = Polynomial.clear(_p2);
	break;
	}
	r = cpd.remainder();
	_p1 = Polynomial.clear(_p2);
	_p2 = Polynomial.clear(r);
}
return d;
}


// Private stuff

private static boolean zeroRemainder(float[] r)
{
float f = 0.0f;
for(int i = 0; i < r.length; i++) f += r[i];
return (Math.abs(f) < THRESHOLD) ? true : false;
}

private static boolean zeroRemainder(ComplexNumber[] zin)
{
ComplexNumber z = new ComplexNumber(0.0f, 0.0f);
for(int i = 0; i < zin.length; i++) z = z.add(zin[i]);
return (z.magnitude() < THRESHOLD) ? true : false;
}


// Private constructor, so that this class cannot be instantiated
private PolynomialGCD() {}

// Helper symbolic constant declared by convinience
private static final float THRESHOLD = 1E-5f;
}

// END
