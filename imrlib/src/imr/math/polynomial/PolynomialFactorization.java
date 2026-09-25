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
* PolynomialFactorization.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.polynomial;

import imr.util.Convert;
import imr.math.ComplexNumber;

/**
* This class has static methods to compute polynomial factorization. <p>
* Notice that we always return factorization as complex numbers, since a polynomial like x^2 + 1 has complex roots. <p>
* Such a factorization results in a binomial product, that is: <p>
* P[x] = (x + a1)(x+a2) ... (x+an) <p>
* Take in account that the leading coefficient of the polynomial to factorize must be always equal to 1. <p>
* There are also methods to check wheteher the result is correct.
* <p>
* This class uses assertions; to enable them you can use the '-ea' modifier when execute: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class PolynomialFactorization
{

/**
* Static method to factorize a polynomial with integer coefficients. <p>
* @param poly
* An integer array having the polynomial to factorize.
* <p>
* @return A nx2 bidimensional array having the factorized binomials, that is, each row is a binomial.
*
*/
public static ComplexNumber[][] compute(int[] poly)
{
return compute(Convert.toDoubleArray(poly));
}

/**
* Static method to factorize a polynomial with double floating-point coefficients. <p>
* @param poly
* An double array having the polynomial to factorize.
* <p>
* @return A nx2 bidimensional array having the factorized binomials, that is, each row is a binomial.
*
*/
public static ComplexNumber[][] compute(double[] poly)
{
return compute(Convert.toComplexArray(poly));
}

/**
* Static method to factorize a polynomial with complex coefficients. <p>
* @param poly
* An complex array having the polynomial to factorize.
* <p>
* @return A nx2 bidimensional array having the factorized binomials, that is, each row is a binomial.
*
*/
public static ComplexNumber[][] compute(ComplexNumber[] poly)
{
	ComplexNumber one = new ComplexNumber(1.0, 0.0);
	int n = poly.length;
	assert(poly[n-1].equals(one)): "PolynomialFactorization -> compute(...): Bad parameter; the leading coefficient must be 1.";
ComplexNumber[] roots = Polynomial.roots(poly);
ComplexNumber[][] bi = new ComplexNumber[roots.length][2];
for(int i = 0; i < roots.length; i++)
{
bi[i] = getBinomial(roots[i]);
}
return bi;
}

/**
* Static method to compute binomials product. <p>
* Use this method to check whether the factorization was right. <p>
* @param bi
* A nx2 complex bidimensional array having the computed factorization.
* <p>
* @return Computed polynomial from the product of the binomials.
*
*/
public static ComplexNumber[] binomialProduct(ComplexNumber[][] bi)
{
	assert(bi[0].length == 2): "PolynomialFactorization -> binomialProduct(...): Bad parameter; it should be a nx2 one.";
ComplexNumber[] p = bi[0];
for(int i = 1; i < bi.length; i++)
{
p = Polynomial.mul(p, bi[i]);
}
return p;
}

/**
* Static method to get a concrete binomial by index from an already computed factorization. <p>
* Since each row of the bidimensional array having the factorization is a binomial, <p>
* the index parameter must be in range according to bi.length ( first parameter ). <p>
* @param bi
* An already computed factorization.
* <p>
* @param index
* An integer value for the index from you can get a binomial.
* <p>
* @return Requested binomial.
*
*/
public static ComplexNumber[] getBinomial(ComplexNumber[][] bi, int index)
{
	assert(bi[0].length == 2): "PolynomialFactorization -> getBinomial(...): Bad parameter; it should be a nx2 one.";
int n = bi.length;
assert(index >= 0 && index < n): "PolynomialFactorization -> getBinomial(...): Bad parameter; index out of range.";
return bi[index];
}

/**
* Static method to print the result of a factorization to the console. <p>
* @param bi
* An already computed factorization.
*
*/
public static void print(ComplexNumber[][] bi)
{
	assert(bi[0].length == 2): "PolynomialFactorization -> print(...): Bad parameter; it should be a nx2 one.";
for(int i = 0; i < bi.length; i++)
{
	System.out.print("p["+i+"] = "); Polynomial.print(bi[i]);
}
}

/**
* Static method to print formatted the result of a factorization to the console. <p>
* @param bi
* An already computed factorization.
*
*/
public static void printFormatted(ComplexNumber[][] bi)
{
assert(bi[0].length == 2): "PolynomialFactorization -> print(...): Bad parameter; it should be a nx2 one.";
for(int i = 0; i < bi.length; i++)
{
	System.out.print("p["+i+"] = "); Polynomial.printFormatted(bi[i]);
}
}


/*
* Helper method to construct a needed binomial.
*/
private static ComplexNumber[] getBinomial(ComplexNumber z)
{
ComplexNumber[] bi = new ComplexNumber[2];
bi[0] = z.negate();
bi[1] = new ComplexNumber(1.0, 0.0);
return bi;
}

// Private constructor so that this class cannot be instantiated
private PolynomialFactorization() {}

}

// END
