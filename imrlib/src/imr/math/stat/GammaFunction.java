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
* GammaFunction.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

import imr.math.ComplexNumber;

/**
* this class implements the Gamma function for real and complex numbers. <p>
* The Gamma function was first introduced by Leonhard Euler, and this implementation follows the Launczos aproximation. <p>
* Actually, this Java code was inspired after reading the Lanczos aproximation article in Wikipedia.
* <p>
* This class uses assertions; to enable them you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <p>
*
* @author Ismael Mosquera Rivera.
*
*/
public final class GammaFunction
{

/**
* Static method to compute the Gamma function for real numbers. <p>
* The parameter x must be greater than zero. <p>
* @param x
* A double floating point value greater than zero.
* <p>
* @return Computed value.
*
*/
public static double compute(double x)
{
	assert(x > 0.0): "GammaFunction -> compute(...): Bad parameter; x must be greater than zero.";
if(x < 0.5) return Math.PI / (Math.sin(Math.PI*x)* compute(1.0-x));
double g = 7.0;
x -= 1.0;
double t = x+g+0.5;
double w = p[0];
for(int k = 1; k < p.length; k++)
{
w += p[k]/(x+(double)k);
}
return Math.sqrt(2.0*Math.PI) * Math.pow(t, x+0.5) * Math.exp(-t) * w;
}

/**
* Static method to compute the Gamma function for complex numbers. <p>
* Notice that the real part must be greater than zero. <p>
* @param z
* A complex number
* <p>
* @return Computed value.
*
*/
public static ComplexNumber compute(ComplexNumber z)
{
	assert(z.getReal() > 0.0): "GammaFunction -> compute(...): Bad parameter; real part must be greater than zero.";
ComplexNumber c = null;
if(Math.abs(z.getImag()) < 1E-6)
{
c = new ComplexNumber(z.getReal(), 0.0);
}
else
{
	c = (ComplexNumber)z.clone();
}
ComplexNumber one = new ComplexNumber(1.0, 0.0);
ComplexNumber pi = new ComplexNumber(Math.PI, 0.0);
if(c.getReal() < 0.5)
{
return pi.div(pi.mul(c).sin().mul(compute(one.sub(c))));
}
ComplexNumber g =new ComplexNumber(7.0, 0.0);
ComplexNumber twoPI = new ComplexNumber(2.0*Math.PI, 0.0);
c = c.sub(one);
ComplexNumber t = c.add(g).add(new ComplexNumber(0.5, 0.0));
ComplexNumber w = new ComplexNumber(p[0], 0.0);
for(int k = 1; k < p.length; k++)
{
w = w.add((new ComplexNumber(p[k], 0.0)).div(c.add(new ComplexNumber((double)k, 0.0))));
}
return twoPI.sqrt().mul(t.pow(c.add(new ComplexNumber(0.5, 0.0)))).mul(t.negate().exp()).mul(w);
}

/*
* Table of pre-computed values needed to do the wanted aproximation.
* Notice that a few of ones is sufficient.
*/
private static double[] p =
{
    0.99999999999980993,
    676.5203681218851,
    -1259.1392167224028,
    771.32342877765313,
    -176.61502916214059,
    12.507343278686905,
    -0.13857109526572012,
    9.9843695780195716e-6,
    1.5056327351493116e-7
};


// Private constructor so that this class cannot be instantiated
private GammaFunction() {}

}

// END
