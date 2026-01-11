/*
 * Copyright (c) 2025-2026 Ismael Mosquera Rivera
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
* Polynomial.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.polynomial;

import imr.util.Convert;
import imr.util.iArray;
import imr.math.ComplexNumber;

/**
* The <code>Polynomial</code> class has static methods to operate with polynomials of any degree.
* <p>
* We can compute some basic operations and others like finding roots.
* <ul>
* <li>Adition.</li>
* <li>Substraction.</li>
* <li>Multiplication.</li>
* <li>Scale.</li>
* <li>Derivation.</li>
* <li>Integration.</li>
* <li>Roots finding.</li>
* </ul>
* All the operations are available for any type of coefficient.
* <ul>
* <li>Integer.</li>
* <li>Real.</li>
* <li>Complex.</li>
* </ul>
* At this moment, finding roots has support just for first, second, third and 4th degree polynomials.
* <ul>
* <li>Linear.</li>
* <li>Quadratic.</li>
* <li>Cubic.</li>
* <li>Quartic.</li>
* </ul>
* equations.
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public class Polynomial
{
// integer coefficient implementation

/**
* Adds two integer coefficient polynomials.
* <p>
* @param p1
* An integer number array representing a polynomial.
* <p>
* @param p2
* An integer number array representing a polynomial.
* <p>
* @return p1 + p2
*
*/
public static int[] add(int[] p1, int[] p2)
{
return Convert.toIntArray(add(Convert.toFloatArray(p1), Convert.toFloatArray(p2)));
}

/**
* Substracts two integer coefficient polynomials.
* <p>
* @param p1
* An integer number array representing a polynomial.
* <p>
* @param p2
* An integer number array representing a polynomial.
* <p>
* @return p1 - p2
*
*/
public static int[] sub(int[] p1, int[] p2)
{
return Convert.toIntArray(sub(Convert.toFloatArray(p1), Convert.toFloatArray(p2)));
}

/**
* Multiplies two integer coefficient polynomials.
* <p>
* @param p1
* An integer number array representing a polynomial.
* <p>
* @param p2
* An integer number array representing a polynomial.
* <p>
* @return p1 * p2
*
*/
public static int[] mul(int[] p1, int[] p2)
{
return Convert.toIntArray(mul(Convert.toFloatArray(p1), Convert.toFloatArray(p2)));
}

/**
* Sccales the polynomial passed as first parameter by the factor passed as second parameter.
* <p>
* @param p A int polynomial.
* <p>
* @param factor Scale factor.
* <p>
* @return p scaled by factor.
*
*/
public static int[] scale(int[] p, int factor)
{
	int n = p.length;
int[] out = new int[n];
for(int i = 0; i < n; i++)
{
	out[i] = p[i] * factor;
}
return out;
}

/**
* Computes the derivative for the polynomial passed as parameter.
* <p>
* @param p
* integer 1-dimensional array representing the polynomial to compute its derivative.
* <p>
* We could return an integer array but, since we must return a float array for integration, we do it in this way just for simetry.
* <p>
* @return Derivative for the polynomial passed as parameter.
*
*/
public static float[] derive(int[] p)
{
	return derive(Convert.toFloatArray(p));
}

/**
* Computes the integral for the polynomial passed as parameter.
* <p>
* @param p
* integer 1-dimensional array representing the polynomial to compute its integral.
* <p>
* Note that the constant term is always set to 0.
* <p>
* @return Integral for the polynomial passed as parameter.
*
*/
public static float[] integrate(int[] p)
{
return integrate(Convert.toFloatArray(p));
}

/**
* Prints an integer coefficient polynomial to the console.
* <p>
* @param p
* A 1-dimensional integer array representing the polynomial to print.
*
*/
public static void print(int[] p)
{
if(p == null)
{
	System.out.println("[ null ]");
	return;
}
int n = p.length;
System.out.print("[");
for(int i = 0; i < n; i++)
{
if(i > 0) System.out.print(", ");
System.out.print(p[i]);
}
System.out.println("]");
}

/**
* Computes the roots of the polynomial passed as parameter.
* <p>
* @param p
* A integer 1-dimensional array representing the polynomial.
* <p>
* Mind that the result can have complex solutions so, we return a complex number array.
* <p>
* @return complex number array with the n roots of the polynomial passed as parameter.
*
*/
public static ComplexNumber[] roots(int[] p)
{
return roots(Convert.toFloatArray(p));
}

// Real number implementation

/**
* Adds 2 polynomials.
* <p>
* @param p1
* A 1-dimensional float array representing first polynomial.
* <p>
* @param p2
* A 1-dimensional float array representing second polynomial.
* <p>
* @return p1 + p2
*
*/
public static float[] add(float[] p1, float[] p2)
{
	return addsub(p1, p2, "+");
}

/**
* Substracts 2 polynomials.
* <p>
* @param p1
* A 1-dimensional float array representing first polynomial.
* <p>
* @param p2
* A 1-dimensional float array representing second polynomial.
* <p>
* @return p1 - p2
*
*/
public static float[] sub(float[] p1, float[] p2)
{
	return addsub(p1, p2, "-");
}

/**
* Multiplies 2 polynomials.
* <p>
* @param p1
* A 1-dimensional float array representing first polynomial.
* <p>
* @param p2
* A 1-dimensional float array representing second polynomial.
* <p>
* @return p1 * p2
*
*/
public static float[] mul(float[] p1, float[] p2)
{
if(p1 == null || p2 == null) return null;
int n = p2.length;
int m = (p1.length + n) - 1;
float[][] table = new float[n][m];
for(int i = 0; i < n; i++)
{
	for(int j = 0; j < m; j++)
	{
		table[i][j] = 0.0f;
	}
}
// compute products.
for(int i = 0; i < n; i++)
{
for(int j = 0; j < p1.length; j++)
{
	table[i][j+i] = p2[i] * p1[j];
}
}
float[] p = new float[m];
for(int i = 0; i < m; i++) p[i] = 0.0f;
// add products in order to get result.
for(int j = 0; j < m; j++)
{
	for(int i = 0; i < n; i++)
	{
		p[j] += table[i][j];
	}
}
return p;
}

/**
* Sccales the polynomial passed as first parameter by the factor passed as second parameter.
* <p>
* @param p A float polynomial.
* <p>
* @param factor Scale factor.
* <p>
* @return p scaled by factor.
*
*/
public static float[] scale(float[] p, float factor)
{
	int n = p.length;
float[] out = new float[n];
for(int i = 0; i < n; i++)
{
	out[i] = p[i] * factor;
}
return out;
}

/**
* Computes the derivative for the polynomial passed as parameter.
* <p>
* @param p
* float 1-dimensional array representing the polynomial to compute its derivative.
* <p>
* @return Derivative for the polynomial passed as parameter.
*
*/
public static float[] derive(float[] p)
{
if(p == null) return null;
int size = p.length-1;
float n = 1.0f;
float[] dp = new float[size];
int k = 0;
for(int i = 1; i < p.length; i++)
{
dp[k++] = n * p[i];
n += 1.0f;
}
return dp;
}

/**
* Computes the integral for the polynomial passed as parameter.
* <p>
* @param p
* float 1-dimensional array representing the polynomial to compute its integral.
* <p>
* Note that the constant term is always set to 0.
* <p>
* @return Integral for the polynomial passed as parameter.
*
*/
public static float[] integrate(float[] p)
{
if(p == null) return null;
int size = p.length + 1;
float n = 1.0f;
float[] ip = new float[size];
ip[0] = 0.0f;
for(int i = 0; i < p.length; i++)
{
ip[i+1] = p[i] / n;
n += 1.0f;
}
return ip;
}

/**
* Prints a real polynomial to the console.
* <p>
* @param rp
* A 1-dimensional float array representing the polynomial to print.
*
*/
public static void print(float[] rp)
{
if(rp == null)
{
System.out.println("[ null ]");
return;
}
System.out.print("[");
for(int i = 0; i < rp.length; i++)
{
	if(i > 0) System.out.print(", ");
	System.out.print(rp[i]);
}
System.out.println("]");
}

/**
* Computes the roots of the polynomial passed as parameter.
* <p>
* @param p
* A 1-dimensional array floating-point representing the polynomial.
* <p>
* Mind that the result can have complex solutions so, we return a complex number array.
* <p>
* @return complex number array with the n roots of the polynomial passed as parameter.
*
*/
public static ComplexNumber[] roots(float[] p)
{
return roots(Convert.toComplexArray(p));
}


// ComplexNumber implementation

/**
* Adds 2 complex polynomials.
* <p>
* @param cp1
* A 1-dimensional complex number array representing first polynomial.
* <p>
* @param cp2
* A 1-dimensional complex number array representing second polynomial.
* <p>
* @return cp1 + cp2
*
*/
public static ComplexNumber[] add(ComplexNumber[] cp1, ComplexNumber[] cp2)
{
	return addsub(cp1, cp2, "+");
}

/**
* Substracts 2 complex polynomials.
* <p>
* @param cp1
* A 1-dimensional complex number array representing first polynomial.
* <p>
* @param cp2
* A 1-dimensional complex number array representing second polynomial.
* <p>
* @return cp1 - cp2
*
*/
public static ComplexNumber[] sub(ComplexNumber[] cp1, ComplexNumber[] cp2)
{
	return addsub(cp1, cp2, "-");
}

/**
* Multiplies 2 complex polynomials.
* <p>
* @param cp1
* A 1-dimensional complex number array representing first polynomial.
* <p>
* @param cp2
* A 1-dimensional complex number array representing second polynomial.
* <p>
* @return cp1 * cp2
*
*/
public static ComplexNumber[] mul(ComplexNumber[] cp1, ComplexNumber[] cp2)
{
if(cp1 == null || cp2 == null) return null;
int n = cp2.length;
int m = (cp1.length + n) - 1;
ComplexNumber[][] table = new ComplexNumber[n][m];
for(int i = 0; i < n; i++)
{
	for(int j = 0; j < m; j++)
	{
		table[i][j] = new ComplexNumber(0.0f, 0.0f);
	}
}
// compute products.
for(int i = 0; i < n; i++)
{
for(int j = 0; j < cp1.length; j++)
{
	table[i][j+i] = cp2[i].mul(cp1[j]);
}
}
ComplexNumber[] cp = new ComplexNumber[m];
for(int i = 0; i < m; i++) cp[i] = new ComplexNumber(0.0f, 0.0f);
// add products in order to get result.
for(int j = 0; j < m; j++)
{
	for(int i = 0; i < n; i++)
	{
		cp[j] = cp[j].add(table[i][j]);
	}
}
return cp;
}

/**
* Scales the complex polynomial passed as first parameter by the factor passed as second parameter.
* <p>
* @param cp
* A complex polynomial to be scaled.
* <p>
* @param factor
* Factor to be scaled.
* <p>
* @return cp scaled by the factor passed as second parameter.
*
*/
public static ComplexNumber[] scale(ComplexNumber[] cp, float factor)
{
	int n = cp.length;
	ComplexNumber[] out = new ComplexNumber[n];
	for(int i = 0; i < n; i++)
	{
	out[i] = cp[i].scale(factor);
	}
	return out;
}

/**
* Computes the derivative for the complex polynomial passed as parameter.
* <p>
* @param cp
* complex number 1-dimensional array representing the polynomial to compute its derivative.
* <p>
* @return Derivative for the complex polynomial passed as parameter.
*
*/
public static ComplexNumber[] derive(ComplexNumber[] cp)
{
if(cp == null) return null;
int size = cp.length-1;
float n = 1.0f;
ComplexNumber[] dp = new ComplexNumber[size];
int k = 0;
for(int i = 1; i < cp.length; i++)
{
dp[k++] = cp[i].scale((float)n);
n += 1.0f;
}
return dp;
}

/**
* Computes the integral for the complex polynomial passed as parameter.
* <p>
* @param cp
* complex number 1-dimensional array representing the polynomial to compute its integral.
* <p>
* Note that the constant term is always set to 0.
* <p>
* @return Integral for the complex polynomial passed as parameter.
*
*/
public static ComplexNumber[] integrate(ComplexNumber[] cp)
{
if(cp == null) return null;
int size = cp.length + 1;
float n = 1.0f;
ComplexNumber[] ip = new ComplexNumber[size];
ip[0] = new ComplexNumber(0.0f, 0.0f);
for(int i = 0; i < cp.length; i++)
{
ip[i+1] = cp[i].scale(1.0f/(float)n);
n += 1.0f;
}
return ip;
}

/**
* Prints a complex polynomial to the console.
* <p>
* @param cp
* A 1-dimensional complex number array representing the polynomial to print.
*
*/
public static void print(ComplexNumber[] cp)
{
if(cp == null)
{
System.out.println("[ null ]");
return;
}
System.out.print("[");
for(int i = 0; i < cp.length; i++)
{
	if(i > 0) System.out.print(", ");
	System.out.print(cp[i]);
}
System.out.println("]");
}

/**
* Computes the roots of the polynomial passed as parameter.
* <p>
* @param p
* A complex number 1-dimensional array representing the polynomial.
* <p>
* @return complex number array with the n roots of the polynomial passed as parameter.
*
*/
public static ComplexNumber[] roots(ComplexNumber[] p)
{
if(p == null) return null;
 assert (p.length > 1): "Roots Solver: Bad parameter.";
if(p.length == 2) return linearSolver(p);
if(p.length == 3) return quadraticSolver(p);
if(p.length == 4) return cubicSolver(p);
if(p.length == 5) return quarticSolver(p);
return polyrootsFinder(p);
}


/* PRIVATE STUFF */

// Helper function to add and substract 2 polynomials
private static float[] addsub(float[] p1, float[]p2, String s)
{
	if(p1 == null || p2 == null) return null;
	float[] pa = (float[])iArray.clone(p1);
	float[] pb = (float[])iArray.clone(p2);
	int length_a = pa.length;
	int length_b = pb.length;
	int length_p = p1.length;
	if(length_a != length_b)
	{
	if(length_a > length_b)
	{
	pb = (float[])iArray.resize(pb, length_a);
	length_p = length_a;
	}
	else
	{
	pa = (float[])iArray.resize(pa, length_b);
	length_p = length_b;
	}
	}
	float[] p = new float[length_p];
	for(int i = 0; i < length_p; i++)
	{
		p[i] = (s.equals("+"))? pa[i] + pb[i] : pa[i] - pb[i];
	}
	return p;
}

// helper function to add and substract 2 complex polynomials.
private static ComplexNumber[] addsub(ComplexNumber[] cp1, ComplexNumber[] cp2, String s)
{
if(cp1 == null || cp2 == null) return null;
	ComplexNumber[] pa = clone(cp1);
	ComplexNumber[] pb = clone(cp2);
	int length_a = pa.length;
	int length_b = pb.length;
	int length_p = cp1.length;
	if(length_a != length_b)
	{
	if(length_a > length_b)
	{
	pb = resize(pb, length_a);
	length_p = length_a;
	}
	else
	{
	pa = resize(pa, length_b);
	length_p = length_b;
	}
	}
	ComplexNumber[] cp = new ComplexNumber[length_p];
	for(int i = 0; i < length_p; i++)
	{
		cp[i] = (s.equals("+"))? pa[i].add(pb[i]) : pa[i].sub(pb[i]);
	}
	return cp;
}

// Helper function to clone a complex number array.
private static ComplexNumber[] clone(ComplexNumber[] c)
{
if(c == null) return null;
ComplexNumber[] out = new ComplexNumber[c.length];
for(int i = 0; i < c.length; i++) out[i] = (ComplexNumber)c[i].clone();
return out;
}

// Helper function to resize a complex number array.
private static ComplexNumber[] resize(ComplexNumber[] c, int size)
{
if(size < 1 || c == null) return null;
int old_size = c.length;
ComplexNumber[] out = (size > old_size) ? new ComplexNumber[size] : new ComplexNumber[old_size];
int n = (size < old_size) ? size : old_size;
for(int i = 0; i < n; i++) out[i] = (ComplexNumber)c[i].clone();
if(size > old_size)
{
	for(int i = old_size; i < size; i++) out[i] = new ComplexNumber(0.0f, 0.0f);
}
return out;
}

// Helper function to solve a one degree polynomial root.
private static ComplexNumber[] linearSolver(ComplexNumber[] cp)
{
	ComplexNumber[] out = new ComplexNumber[1];
if(cp[1].magnitude() == 0.0f)
{
	out[0] = new ComplexNumber(0.0f, 0.0f);
	return out;
}
out[0] = cp[0].scale(-1.0f).div(cp[1]);
return out;
}

// Helper function to solve quadratic polynomial roots.
private static ComplexNumber[] quadraticSolver(ComplexNumber[] cp)
{
if(cp[2].magnitude() == 0.0f) return linearSolver(resize(cp, 2));
ComplexNumber[] out = new ComplexNumber[2];  // roots array to return
// split into parts to clarify computation
ComplexNumber num1 = cp[1].scale(-1.0f);
ComplexNumber num2 = cp[1].square().sub(cp[2].scale(4.0f).mul(cp[0]));
ComplexNumber den = cp[2].scale(2.0f);
if(num2.getReal() < 0.0f) // check for negative sqrt
{
	num2.setReal(-num2.getReal());
ComplexNumber i = new ComplexNumber(0.0f, 1.0f);
out[0] = num1.add(num2.sqrt().mul(i)).div(den);
out[1] = num1.sub(num2.sqrt().mul(i)).div(den);
}
else
{
out[0] = num1.add(num2.sqrt()).div(den);
out[1] = num1.sub(num2.sqrt()).div(den);
}
return out;
}

// Helper function to solve cubic polynomial roots.
private static ComplexNumber[] cubicSolver(ComplexNumber[] cp)
{
if(cp[3].magnitude() == 0.0f) return quadraticSolver(resize(cp, 3));
ComplexNumber z = new ComplexNumber(-1.0f, 0.0f); // ausiliary
// split into parts to clarify computation
ComplexNumber d0 = cp[2].square().sub(cp[3].scale(3.0f).mul(cp[1]));
ComplexNumber d1 = cp[2].pow(3.0f).scale(2.0f).sub(cp[3].mul(cp[2]).mul(cp[1]).scale(9.0f)).add(cp[3].square().scale(27.0f).mul(cp[0]));
ComplexNumber c = d1.add(d1.square().sub(d0.pow(3.0f).scale(4.0f)).sqrt()).scale(1.0f/2.0f).curt();
ComplexNumber[] r = new ComplexNumber[3]; // roots array to return
// compute first root
r[0] = z.div(cp[3].scale(3.0f)).mul(cp[2].add(c).add(d0.div(c)));
// prepare to compute the other roots
ComplexNumber i = new ComplexNumber(0.0f, 1.0f); // imaginary unit
ComplexNumber three = new ComplexNumber(3.0f, 0.0f); // auxiliary
ComplexNumber q = z.add(three.sqrt().mul(i)).scale(1.0f / 2.0f); // factor to compute remaining roots
ComplexNumber c1 = q.mul(c);
ComplexNumber c2 = q.square().mul(c);
// compute second and third root
r[1] = z.div(cp[3].scale(3.0f)).mul(cp[2].add(c1).add(d0.div(c1)));
r[2] = z.div(cp[3].scale(3.0f)).mul(cp[2].add(c2).add(d0.div(c2)));
return r;
}

// Helper method to compute quartic polynomial roots
private static ComplexNumber[] quarticSolver(ComplexNumber[] cp)
{
if(cp[4].magnitude() == 0.0f) return cubicSolver(resize(cp, 4));
ComplexNumber[] out = new ComplexNumber[4];
// split into parts in order to clarify computation
ComplexNumber z = new ComplexNumber(1.0f, 0.0f);
ComplexNumber p = cp[4].scale(8.0f).mul(cp[2]).sub(cp[3].square().scale(3.0f)).div(cp[4].square().scale(8.0f));
ComplexNumber q = cp[3].pow(3.0f).sub(cp[4].mul(cp[3]).mul(cp[2]).scale(4.0f)).add(cp[4].square().scale(8.0f).mul(cp[1])).div(cp[4].pow(3.0f).scale(8.0f));
ComplexNumber d0 = cp[2].square().sub(cp[3].scale(3.0f).mul(cp[1])).add(cp[4].scale(12.0f).mul(cp[0]));
ComplexNumber d1 = cp[2].pow(3.0f).scale(2.0f).sub(cp[3].scale(9.0f).mul(cp[2]).mul(cp[1])).add(cp[3].square().scale(27.0f).mul(cp[0])).add(cp[4].scale(27.0f).mul(cp[1].square())).sub(cp[4].scale(72.0f).mul(cp[2]).mul(cp[0]));
ComplexNumber Q = d1.add(d1.square().sub(d0.pow(3.0f).scale(4.0f)).sqrt()).scale(1.0f/2.0f).curt();
ComplexNumber S = p.scale(-2.0f/3.0f).add(z.div(cp[4].scale(3.0f)).mul(Q.add(d0.div(Q)))).sqrt().scale(1.0f/2.0f);
// Compute roots
ComplexNumber num1 = cp[3].div(cp[4].scale(4.0f)).scale(-1.0f);
out[0] = num1.sub(S).add(S.square().scale(-4.0f).sub(p.scale(2.0f)).add(q.div(S)).sqrt().scale(1.0f/2.0f));
out[1] = num1.sub(S).sub(S.square().scale(-4.0f).sub(p.scale(2.0f)).add(q.div(S)).sqrt().scale(1.0f/2.0f));
out[2] = num1.add(S).add(S.square().scale(-4.0f).sub(p.scale(2.0f)).sub(q.div(S)).sqrt().scale(1.0f/2.0f));
out[3] = num1.add(S).sub(S.square().scale(-4.0f).sub(p.scale(2.0f)).sub(q.div(S)).sqrt().scale(1.0f/2.0f));
return out;
}

// Helper function to compute nth degree polynomial roots.
private static ComplexNumber[] polyrootsFinder(ComplexNumber[] cp)
{
int n = cp.length;
int k = n;
while(cp[n-1].magnitude() == 0.0f) n--;
if(n < 6) return quarticSolver(resize(cp, 5));
int size = (n == k) ? n-1 : n+1;

return null;
// TODO

/*
ComplexNumber[] out = new ComplexNumber[size];


return out;
*/

}


// private constructor, so that this class cannot be instantiated
private Polynomial(){}

}

// END
