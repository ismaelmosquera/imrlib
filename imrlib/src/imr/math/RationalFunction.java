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
* RationalFunction.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math;

import imr.math.polynomial.Polynomial;

/**
* The <code>RationalFunction</code> class has functionallity to evaluate a rational function p/q <p>
* where p and q are complex number polynomials. <p>
* Such a function is evaluated against a complex number parameter. <p>
* @author Ismael Mosquera Rivera.
*
*/
public class RationalFunction
{

/**
* Constructor. <p>
* Makes a new instance for a <code>RationalFunction</code> object. <p>
* @param pnum
* Numerator polynomial.
* <p>
* @param pden
* Denominator polynomial.
*
*/
public RationalFunction(ComplexNumber[] pnum, ComplexNumber[] pden)
{
	this.set(pnum, pden);
}

/**
* Gets the numerator polynomial. <p>
* @return numerator polynomial.
*
*/
public ComplexNumber[] numerator()
{
return _numerator;
}

/**
* Gets the denominator polynomial. <p>
* @return denominator polynomial.
*
*/
public ComplexNumber[] denominator()
{
return _denominator;
}

/**
* Sets the needed data. <p>
* @param pnum
* A complex number array representing the numerator polynomial.
* <p>
* @param pden
* A complex number array representing the denominator polynomial.
*
*/
public void set(ComplexNumber[] pnum, ComplexNumber[] pden)
{
	_numerator = Polynomial.clear(pnum);
		_denominator = Polynomial.clear(pden);
}

/**
* Evaluates according to the complex number passed as parameter. <p>
* This method evaluates for positive powers of z. <p>
* @param z
* A complex number from to evaluate the rational function.
* <p>
* @return result of the evaluation.
*
*/
public ComplexNumber evaluatePositive(ComplexNumber z)
{
ComplexNumber b = evaluatePositive(_numerator, z);
ComplexNumber a = evaluatePositive(_denominator, z);
return b.div(a);
}

/**
* Evaluates according to the complex number passed as parameter. <p>
* This method evaluates for negative powers of z. <p>
* @param z
* A complex number from to evaluate the rational function.
* <p>
* @return result of the evaluation.
*
*/
public ComplexNumber evaluateNegative(ComplexNumber z)
{
ComplexNumber b = evaluateNegative(_numerator, z);
ComplexNumber a = evaluateNegative(_denominator, z);
return b.div(a);
}

/**
* Evaluates for a single polynomial according to the complex number passed as second parameter. <p>
* This method evaluates for positive powerrs of z. <p>
* @param p
* A complex number array representing the polynomial.
* <p>
* @param z
* A complex number from evaluate.
* <p>
* @return result of the evaluation as a complex number.
*
*/
public static ComplexNumber evaluatePositive(ComplexNumber[] p, ComplexNumber z)
{
	if(p == null) return ComplexNumber.NaN;
int n = p.length;
if(n == 0) return ComplexNumber.NaN;
ComplexNumber result = (ComplexNumber)p[0].clone();
for(int i = 1; i < n; i++) result = result.add(z.pow(i).mul(p[i]));
return result;
}

/**
* Evaluates for a single polynomial according to the complex number passed as second parameter. <p>
* This method evaluates for negative powerrs of z. <p>
* @param p
* A complex number array representing the polynomial.
* <p>
* @param z
* A complex number from evaluate.
* <p>
* @return result of the evaluation as a complex number.
*
*/
public static ComplexNumber evaluateNegative(ComplexNumber[] p, ComplexNumber z)
{
	if(p == null) return ComplexNumber.NaN;
int n = p.length;
if(n == 0) return ComplexNumber.NaN;
ComplexNumber result = (ComplexNumber)p[0].clone();
for(int i = 1; i < n; i++) result = result.add(z.pow(-i).mul(p[i]));
return result;
}


// numerator and denominator polynomials to compose a rational function.
private ComplexNumber[] _numerator;
private ComplexNumber[] _denominator;
}

// END
