/*
 * Copyright (c) 2025 Ismael Mosquera Rivera
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
* RuffiniRule.java
*
* imr-lib
*
* author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial;

import imr.math.ComplexNumber;
import imr.util.Convert;

/**
* This class implements the Rufini's rule.
* <p>
* This rule applies to divide a polynomial pn(x) by a binomial (x - a).
* <p>
* {a0 + a1 + ... + a(n-1) + an} / {-z + 1}
* <p>
* Note that the coefficients are placed in ascending order of powers (0 .. n), where n is the degree of the polynomial.
* <p>
* If you wish to divide by a binomial (x + a), just change the sign of a:
* <p>
* (x - (-a)).
* <p>
* Written it in clasical way:
* <p>
* P(x) / (x - a)
* <p>
* Note that we always return quotient and remainder as a complex number array and a complex number array of just one position respectively.
*
* @see imr.util.Convert
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class RuffiniRule
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>RuffiniRule</code> object.
*
*/
public RuffiniRule()
{
_q = null;
_r = new ComplexNumber[1];
_r[0] = new ComplexNumber(0.0f, 0.0f);
}

/**
* Computes the division pn / (x- n).
* <p>
* Note that, since in (x - n), the coefficient of x is always 1, we pass just n as parameter.
* <p>
* @param p
* An integer 1-dimensional array representing p.
* <p>
* @param n
* The n component.
* <p>
* The quotient will be a polynomial of degree n-1, where n is the degree of the polynomial passed as first parameter.
* <p>
* You can retrieve the quotient an remainder using the methods below.
*
*/
public void compute(int[] p, int n)
{
	compute(Convert.toFloatArray(p), (float)n);
}

/**
* Computes the division pn / (x- r).
* <p>
* Note that, since in (x - r), the coefficient of x is always 1, we pass just r as parameter.
* <p>
* @param p
* A float 1-dimensional array representing p.
* <p>
* @param r
* The r component.
* <p>
* The quotient will be a polynomial of degree n-1, where n is the degree of the polynomial passed as first parameter.
* <p>
* You can retrieve the quotient an remainder using the methods below.
*
*/
public void compute(float[] p, float r)
{
compute(Convert.toComplexArray(p), new ComplexNumber(r, 0.0f));
}

/**
* Computes the division pn / (x- z).
* <p>
* Note that, since in (x - z), the coefficient of x is always 1, we pass just z as parameter.
* <p>
* @param p
* A complex number 1-dimensional array representing p.
* <p>
* @param z
* The z component.
* <p>
* The quotient will be a polynomial of degree n-1, where n is the degree of the polynomial passed as first parameter.
* <p>
* You can retrieve the quotient an remainder using the methods below.
*
*/
public void compute(ComplexNumber[] p, ComplexNumber z)
{
int n = p.length;
assert(n > 1): "RuffiniRule compute: Bad parameter.";
int k = n-1;
_q = null;
_q = new ComplexNumber[k];
_q[k-1] = p[n-1];
k -= 2;
for(int i = n-2; i > 0; i--)
{
_q[k--] = z.mul(_q[i]).add(p[i]);
}
_r[0] = z.mul(_q[0]).add(p[0]);
}

/**
* Gets the quotient.
* <p>
* @return quotient as an array of complex number.
*
*/
public ComplexNumber[] quotient()
{
return _q;
}

/**
* Gets the remainder.
* <p>
* @return remainder as an array of just one position ( size = 1 ).
*
*/
public ComplexNumber[] remainder()
{
return _r;
}

/**
* Prints the computation result to the console.
*
*/
public void print()
{
System.out.print("quotient = ");
Polynomial.print(_q);
System.out.print("remainder = ");
Polynomial.print(_r);
}


private ComplexNumber[] _q; // quotient
private ComplexNumber[] _r; // remainder, just one position ( size = 1 )

}

// END
