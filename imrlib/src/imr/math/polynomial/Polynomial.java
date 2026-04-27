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
import imr.math.RationalNumber;
import imr.math.ComplexNumber;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

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
* <li>Rational.</li>
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
* Recently ( 26/04/2026 | day, month, year ) <p>
* support to find roots for polynomials of any degree was added, using the above computations until quartics combined with Newton's nethod. <p>
* In addition, this class has static methods to print formatted polynomials for any kind of coefficients. <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class Polynomial
{
	// We first declare and implement static methods to clear possible zeros until the leding coefficient is other than nught.

/**
* Static method to clear possible leading zeros. <p>
* @param p
* An integer coefficient polynomial.
* <p>
* @return cleared polynomial.
*
*/
	public static int[] clear(int[] p)
	{
if(p == null) return null;
int counter = 0;
for(int i = p.length-1; i >= 0; i--)
{
if(Math.abs(p[i]) != 0) break;
counter++;
}
int[] out = new int[p.length-counter];
for(int i = 0; i < out.length; i++)
{
out[i] = p[i];
}
return out;
	}

/**
* Static method to clear possible leading zeros. <p>
* @param p
* A float coefficient polynomial.
* <p>
* @return cleared polynomial.
*
*/
public static float[] clear(float[] p)
	{
if(p == null) return null;
int counter = 0;
for(int i = p.length-1; i >= 0; i--)
{
if(Math.abs(p[i]) > CLEAR_THRESHOLD) break;
counter++;
}
float[] out = new float[p.length-counter];
for(int i = 0; i < out.length; i++)
{
out[i] = p[i];
}
return out;
	}

/**
* Static method to clear possible leading zeros. <p>
* @param p
* A rational coefficient polynomial.
* <p>
* @return cleared polynomial.
*
*/
public static RationalNumber[] clear(RationalNumber[] p)
	{
if(p == null) return null;
int counter = 0;
for(int i = p.length-1; i >= 0; i--)
{
if(Math.abs(p[i].getNumerator()) != 0) break;
counter++;
}
RationalNumber[] out = new RationalNumber[p.length-counter];
for(int i = 0; i < out.length; i++)
{
out[i] = (RationalNumber)p[i].clone();
}
return out;
	}

/**
* Static method to clear possible leading zeros. <p>
* @param p
* A complex coefficient polynomial.
* <p>
* @return cleared polynomial.
*
*/
public static ComplexNumber[] clear(ComplexNumber[] p)
	{
if(p == null) return null;
int counter = 0;
for(int i = p.length-1; i >= 0; i--)
{
if(p[i].magnitude() > CLEAR_THRESHOLD) break;
counter++;
}
ComplexNumber[] out = new ComplexNumber[p.length-counter];
for(int i = 0; i < out.length; i++)
{
out[i] = (ComplexNumber)p[i].clone();
}
return out;
	}

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


// Static methods to print formatted polynomials

/**
* Static method to print to the console a formatted polynomial of integer coefficients. <p>
* @param p
* A polynomial as an integer array.
*
*/
public static void printFormatted(int[] p)
{
	if(p == null)
	{
		System.out.println("[]");
		return;
	}
	boolean initial = false;
StringBuilder sb = new StringBuilder();
sb.append("[");
for(int i = p.length-1; i >= 0; i--)
{
	if(p[i] != 0 && !initial)
	{
		initial = true;
		if(p[i] < 0) sb.append("-");
		sb.append(getFormattedTerm(p[i], i));
	}
	else
	{
if(p[i] != 0)
{
	if(p[i] < 0)
	{
		sb.append(" - ");
	}
	else
	{
		sb.append(" + ");
	}
	sb.append(getFormattedTerm(p[i], i));
}
}
}
sb.append("]");
System.out.println(sb.toString());
}

/**
* Static method to print to the console a formatted polynomial of rational coefficients. <p>
* @param q
* A polynomial as a rational array.
*
*/
public static void printFormatted(RationalNumber[]q)
{
if(q == null)
	{
		System.out.println("[]");
		return;
	}
	boolean initial = false;
StringBuilder sb = new StringBuilder();
sb.append("[");
for(int i = q.length-1; i >= 0; i--)
{
	if(Math.abs(q[i].value()) > THRESHOLD && !initial)
	{
		initial = true;
		if(q[i].signed()) sb.append("-");
		sb.append(getFormattedTerm(q[i], i));
	}
	else
	{
if(Math.abs(q[i].value()) > THRESHOLD)
{
	if(q[i].signed())
	{
		sb.append(" - ");
	}
	else
	{
		sb.append(" + ");
	}
	sb.append(getFormattedTerm(q[i], i));
}
}
}
sb.append("]");
System.out.println(sb.toString());
}

/**
* Static method to print to the console a formatted polynomial of real coefficients. <p>
* @param p
* A polynomial as a real array.
*
*/
public static void printFormatted(float[] p)
{
	if(p == null)
	{
		System.out.println("[]");
		return;
	}
boolean initial = false;
StringBuilder sb = new StringBuilder();
sb.append("[");
for(int i = p.length-1; i >= 0; i--)
{
	if(Math.abs(p[i]) > THRESHOLD && !initial)
	{
		initial = true;
		if(p[i] < 0.0f) sb.append("-");
		sb.append(getFormattedTerm(p[i], i));
	}
	else
	{
if(Math.abs(p[i]) > THRESHOLD)
{
	if(p[i] < 0.0f)
	{
		sb.append(" - ");
	}
	else
	{
		sb.append(" + ");
	}
	sb.append(getFormattedTerm(p[i], i));
}
}
}
sb.append("]");
System.out.println(sb.toString());
}

/**
* Static method to print to the console a formatted polynomial of complex coefficients. <p>
* @param p
* A polynomial as a complex array.
*
*/
public static void printFormatted(ComplexNumber[] p)
{
if(p == null)
{
System.out.println("[]");
return;
}
boolean initial = false;
StringBuilder sb = new StringBuilder();
sb.append("[");
for(int i = p.length-1; i >= 0; i--)
{
	if(p[i].magnitude() > THRESHOLD && !initial)
	{
		initial = true;
		sb.append(getFormattedTerm(p[i], i));
	}
	else
	{
if(p[i].magnitude() > THRESHOLD)
{
		sb.append(" + ");
	sb.append(getFormattedTerm(p[i], i));
}
}
}
sb.append("]");
System.out.println(sb.toString());
}

// end methods to print formatted polynomials


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


// rational number implementation

/**
* Static method to add two rational number polynomials. <p>
* @param q1
* A rational number array having the coefficients for the first parameter.
* <p>
* @param q2
* A rational number array having the coefficients for the second parameter.
* <p>
* @return q1 + q2
*
*/
public static RationalNumber[] add(RationalNumber[] q1, RationalNumber[] q2)
{
return addsub(q1, q2, "+");
}

/**
* Static method to substract two rational number polynomials. <p>
* @param q1
* A rational number array having the coefficients for the first parameter.
* <p>
* @param q2
* A rational number array having the coefficients for the second parameter.
* <p>
* @return q1 - q2
*
*/
public static RationalNumber[] sub(RationalNumber[] q1, RationalNumber[] q2)
{
return addsub(q1, q2, "-");
}

/**
* Static method to multiply 2 rational polynomials.
* <p>
* @param rp1
* A 1-dimensional rational number array representing first polynomial.
* <p>
* @param rp2
* A 1-dimensional rational number array representing second polynomial.
* <p>
* @return qp1 * qp2
*
*/
public static RationalNumber[] mul(RationalNumber[] rp1, RationalNumber[] rp2)
{
if(rp1 == null || rp2 == null) return null;
int n = rp2.length;
int m = (rp1.length + n) - 1;
RationalNumber[][] table = new RationalNumber[n][m];
for(int i = 0; i < n; i++)
{
	for(int j = 0; j < m; j++)
	{
		table[i][j] = new RationalNumber(0, 1);
	}
}
// compute products.
for(int i = 0; i < n; i++)
{
for(int j = 0; j < rp1.length; j++)
{
	table[i][j+i] = rp2[i].mul(rp1[j]);
}
}
RationalNumber[] rp = new RationalNumber[m];
for(int i = 0; i < m; i++) rp[i] = new RationalNumber(0, 1);
// add products in order to get result.
for(int j = 0; j < m; j++)
{
	for(int i = 0; i < n; i++)
	{
		rp[j] = rp[j].add(table[i][j]);
	}
}
return rp;
}

/**
* Scales the rational polynomial passed as first parameter by the factor passed as second parameter.
* <p>
* @param rp
* A rational polynomial to be scaled.
* <p>
* @param factor
* Factor to be scaled.
* <p>
* @return rp scaled by the factor passed as second parameter.
*
*/
public static RationalNumber[] scale(RationalNumber[] rp, RationalNumber factor)
{
	int n = rp.length;
	RationalNumber[] out = new RationalNumber[n];
	for(int i = 0; i < n; i++)
	{
	out[i] = rp[i].mul(factor);
	}
	return out;
}

/**
* Computes the derivative for the rational polynomial passed as parameter.
* <p>
* @param rp
* rational number 1-dimensional array representing the polynomial to compute its derivative.
* <p>
* @return Derivative for the rational polynomial passed as parameter.
*
*/
public static RationalNumber[] derive(RationalNumber[] rp)
{
if(rp == null) return null;
int size = rp.length-1;
RationalNumber n = new RationalNumber(1, 1);
RationalNumber[] dp = new RationalNumber[size];
int k = 0;
for(int i = 1; i < rp.length; i++)
{
dp[k++] = rp[i].mul(n);
n = n.add(new RationalNumber(1, 1));
}
return dp;
}

/**
* Computes the integral for the rational polynomial passed as parameter.
* <p>
* @param rp
* rational number 1-dimensional array representing the polynomial to compute its integral.
* <p>
* Note that the constant term is always set to 0.
* <p>
* @return Integral for the rational polynomial passed as parameter.
*
*/
public static RationalNumber[] integrate(RationalNumber[] rp)
{
if(rp == null) return null;
int size = rp.length + 1;
RationalNumber n = new RationalNumber(1, 1);
RationalNumber[] ip = new RationalNumber[size];
ip[0] = new RationalNumber(0, 1);
for(int i = 0; i < rp.length; i++)
{
ip[i+1] = rp[i].mul(n.pow(-1));
n = n.add(new RationalNumber(1, 1));
}
return ip;
}

/**
* Computes the roots of the rational polynomial passed as parameter.
* <p>
* @param p
* A 1-dimensional array rational representing the polynomial.
* <p>
* Mind that the result can have complex solutions so, we return a complex number array.
* <p>
* @return complex number array with the n roots of the polynomial passed as parameter.
*
*/
public static ComplexNumber[] roots(RationalNumber[] p)
{
return roots(Convert.toFloatArray(p));
}

/**
* Prints a rational coefficient polynomial to the console.
* <p>
* @param q
* A 1-dimensional rational array representing the polynomial to print.
*
*/
public static void print(RationalNumber[] q)
{
RationalNumber.print(q);
}

// end rational number implementation


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
p = clear(p);
if(p.length < 2) return null;
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

// helper function to add and substract 2 rational polynomials.
private static RationalNumber[] addsub(RationalNumber[] rp1, RationalNumber[] rp2, String s)
{
if(rp1 == null || rp2 == null) return null;
	RationalNumber[] pa = clone(rp1);
	RationalNumber[] pb = clone(rp2);
	int length_a = pa.length;
	int length_b = pb.length;
	int length_p = rp1.length;
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
	RationalNumber[] rp = new RationalNumber[length_p];
	for(int i = 0; i < length_p; i++)
	{
		rp[i] = (s.equals("+"))? pa[i].add(pb[i]) : pa[i].sub(pb[i]);
	}
	return rp;
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

// Helper function to clone a rational number array.
private static RationalNumber[] clone(RationalNumber[] q)
{
if(q == null) return null;
RationalNumber[] out = new RationalNumber[q.length];
for(int i = 0; i < out.length; i++) out[i] = (RationalNumber)q[i].clone();
return out;
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

// Helper function to resize a rational number array.
private static RationalNumber[] resize(RationalNumber[] q, int size)
{
if(size < 1 || q == null) return null;
int old_size = q.length;
RationalNumber[] out = (size > old_size) ? new RationalNumber[size] : new RationalNumber[old_size];
int n = (size < old_size) ? size : old_size;
for(int i = 0; i < n; i++) out[i] = (RationalNumber)q[i].clone();
if(size > old_size)
{
	for(int i = old_size; i < size; i++) out[i] = new RationalNumber(0, 1);
}
return out;
}

// Helper function to solve a one degree polynomial root.
private static ComplexNumber[] linearSolver(ComplexNumber[] cp)
{
	ComplexNumber[] out = new ComplexNumber[1];
out[0] = cp[0].scale(-1.0f).div(cp[1]);
return out;
}

// Helper function to solve quadratic polynomial roots.
private static ComplexNumber[] quadraticSolver(ComplexNumber[] cp)
{
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
ComplexNumber[] p = clear(cp);
ComplexNumber rt = null;
ComplexNumber[] qrt = null;
ComplexNumber[] _roots = new ComplexNumber[p.length-1];
RuffiniRule ruffini = new RuffiniRule();
int k = 0;
while(true)
{
	if(p.length == 5) // quartic
	{
qrt = roots(p);
break;
	}
rt = findRoot(p);
_roots[k++] = (ComplexNumber)rt.clone();
ruffini.compute(p, rt);
p = ruffini.quotient();
}
for(int i = 0; i < qrt.length; i++) _roots[k++] = (ComplexNumber)qrt[i].clone();
return _roots;
}

/*
* Helper method to compute a single root of a polynomial
*/
private static ComplexNumber findRoot(ComplexNumber[] p)
{
ComplexNumber[] fx = clear(p);
ComplexNumber[] dfx = derive(fx);
ComplexNumber z0 = new ComplexNumber(1.0f, 0.0f); // initial guess
ComplexNumber z1 = null;
ComplexNumber y = null;
ComplexNumber dy = null;
final float epsilon = 1E-5f;
final int MAX_ITERATIONS = 5050;
int k = 0;
while(k < MAX_ITERATIONS)
{
y = f(fx, z0);
dy = f(dfx, z0);
if(dy.magnitude() <= epsilon) break;
z1 = z0.sub(y.div(dy));
if(z1.sub(z0).magnitude() < 1E-4f) return z1;
z0 = (ComplexNumber)z1.clone();
k++;
}
return z1;
}

// Private helper method to compute f(x)
private static ComplexNumber f(ComplexNumber[] p, ComplexNumber z)
{
ComplexNumber out = new ComplexNumber(0.0f, 0.0f);
for(int i = 0; i < p.length; i++)
{
out = out.add(p[i].mul(z.pow(i)));
}
return out;
}

/*
* Static method to help printing formatted polynomials of integer coefficients.
*/
private static String getFormattedTerm(int coefficient, int exponent)
{
String s = "";
if (exponent == 0)
{
s += (int)Math.abs(coefficient);
}
else if(exponent == 1)
{
	s += ((int)Math.abs(coefficient) == 1)? "x" : (int)Math.abs(coefficient)+"x";
}
else if(exponent > 1)
{
s += ((int)Math.abs(coefficient) == 1) ? "x^"+exponent : (int)Math.abs(coefficient)+"x^"+exponent;
}
return s;
}

/*
* Static method to help printing formatted polynomials of rational coefficients.
*/
private static String getFormattedTerm(RationalNumber coefficient, int exponent)
{
String s = "";
if (exponent == 0)
{
s += coefficient.abs();
}
else if(exponent == 1)
{
	s += (Math.abs(coefficient.value()) == 1.0f) ? "x" : coefficient.abs()+"x";
}
else if(exponent > 1)
{
s += (Math.abs(coefficient.value()) == 1.0f) ? "x^"+exponent : coefficient.abs()+"x^"+exponent;
}
return s;
}

/*
* Static method to help printing formatted polynomials of real coefficients.
*/
private static String getFormattedTerm(float coefficient, int exponent)
{
	NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
String s = "";
if (exponent == 0)
{
s += formatter.format(Math.abs(coefficient));
}
else if(exponent == 1)
{
	s += (Math.abs(coefficient-(float)((int)coefficient)) < THRESHOLD && (int)Math.abs(coefficient) == 1)? "x" : formatter.format(Math.abs(coefficient))+"x";
}
else if(exponent > 1)
{
s += (Math.abs(coefficient-(float)((int)coefficient)) < THRESHOLD && (int)Math.abs(coefficient) == 1) ? "x^"+exponent : formatter.format(Math.abs(coefficient))+"x^"+exponent;
}
return s;
}

/*
* Static method to help printing formatted polynomials of complex coefficients.
*/
private static String getFormattedTerm(ComplexNumber coefficient, int exponent)
{
String s = "";
if (exponent == 0)
{
s += coefficient;
}
else if(exponent == 1)
{
	s += coefficient+"z";
}
else if(exponent > 1)
{
s += coefficient+"z^"+exponent;
}
return s;
}


// private constructor, so that this class cannot be instantiated
private Polynomial(){}

/**
* Public inner class to manage polynomial storage. <p>
* @author Ismael Mosquera rivera
*
*/
public static final class Storage
{

/**
* Static method to load an integer polynomial. <p>
* The format for this kind of file is as follows: <p>
* #n ( an integer which is the polynomial size ( degree+1) ) <p>
* a0 a1 a2 ... an-1 ( polynomial coefficients ) <p>
* @param filename
* File from to load such a polynomial.
* <p>
* @return loaded polynomial.
*/
public static int[] loadInt(String filename)
{
	int[] out = null;
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(filename)));
	in.useLocale(Locale.US);
	int size = in.nextInt();
	out = new int[size];
	for(int i=0;i<out.length;i++) out[i] = in.nextInt();
}
catch(FileNotFoundException e)
{
	System.err.printf("%s file not found.",filename);
}
finally
{
	if(in != null) in.close();
}
return out;
}

/**
* Static method to load a float-point polynomial. <p>
* The format for this kind of file is as follows: <p>
* #n ( an integer which is the polynomial size ( degree+1) ) <p>
* a0 a1 a2 ... an-1 ( polynomial coefficients ) <p>
* @param filename
* File from to load such a polynomial.
* <p>
* @return loaded polynomial.
*/
public static float[] loadFloat(String filename)
{
	float[] out = null;
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(filename)));
	in.useLocale(Locale.US);
	int size = in.nextInt();
	out = new float[size];
	for(int i=0;i<out.length;i++) out[i] = in.nextFloat();
}
catch(FileNotFoundException e)
{
	System.err.printf("%s file not found.",filename);
}
finally
{
	if(in != null) in.close();
}
return out;
}

/**
* Static method to load a rational polynomial. <p>
* The format for this kind of file is as follows: <p>
* #n ( an integer which is the polynomial size ( degree+1) ) <p>
* a0 a1 a2 ... an-1 ( polynomial coefficients ) <p>
* @param filename
* File from to load such a polynomial.
* <p>
* @return loaded polynomial.
*/
public static RationalNumber[] loadRational(String filename)
{
return RationalNumber.loadRationalArray(filename);
}

/**
* Static method to load a complex polynomial. <p>
* The format for this kind of file is as follows: <p>
* #n ( an integer which is the polynomial size ( degree+1) ) <p>
* a0 a1 a2 ... an-1 ( polynomial coefficients ) <p>
* @param filename
* File from to load such a polynomial.
* <p>
* @return loaded polynomial.
*/
public static ComplexNumber[] loadComplex(String filename)
{
return ComplexNumber.loadComplexArray(filename);
}

/**
* Static method to store an int polynomial. <p>
* @param p
* An int array representing the required polynomial.
* <p>
* @param filename
* A name for the file where the polynomial will be stored.
*
*/
public static void store(int[] p, String filename)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(filename);
	out.println(p.length);
	for(int i=0;i<p.length;i++)
	{
		if(i>0) out.print(" ");
		out.print(p[i]);
	}
	out.println();
}
catch(IOException e)
{
	System.err.println("Polynomial.Storage IOException: "+e);
}
finally
{
	if(out != null) out.close();
}
}

/**
* Static method to store a float polynomial. <p>
* @param p
* A float array representing the required polynomial.
* <p>
* @param filename
* A name for the file where the polynomial will be stored.
*
*/
public static void store(float[] p, String filename)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(filename);
	out.println(p.length);
	for(int i=0;i<p.length;i++)
	{
		if(i>0) out.print(" ");
		out.print(p[i]);
	}
	out.println();
}
catch(IOException e)
{
	System.err.println("Polynomial.Storage IOException: "+e);
}
finally
{
	if(out != null) out.close();
}
}

/**
* Static method to store a rational polynomial. <p>
* @param p
* A rational number array representing the required polynomial.
* <p>
* @param filename
* A name for the file where the polynomial will be stored.
*
*/
public static void store(RationalNumber[] p, String filename)
{
RationalNumber.storeRationalArray(p, filename);
}

/**
* Static method to store a complex polynomial. <p>
* @param p
* A complex number array representing the required polynomial.
* <p>
* @param filename
* A name for the file where the polynomial will be stored.
*
*/
public static void store(ComplexNumber[] p, String filename)
{
ComplexNumber.storeComplexArray(p, filename);
}


// Private constructor so that this class cannot be instantiated.
private Storage() {}

}

// Symbolic constants declared for convinience.
private static final float THRESHOLD = 1E-3f;
private static final float CLEAR_THRESHOLD = 1E-5f;
}

// END
