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
* BinomialCoefficient.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math.polynomial;

import imr.math.Factorial;
import java.math.BigInteger;

/**
* This class computes binomial coefficients. <p>
* Such a coefficient expansion can be arranged to form Pascal's triangle, where each coefficient is the sum of the two immediately above. <p>
* Notice that every expansion is also a palindrome. <p>
* This class offers functionallity to compute polynomial expansions and also a concrete entry without compute the entire expansion and avoids integer overflow. <p>
* this class uses assertions; to enable them you can use the '-ea' modifier when execute: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class BinomialCoefficient
{

/**
* Static method to compute a single entry ( binomial coefficient ) without need to compute the hole polynomial expansion. <p>
* Notice that the result is equivalent to compute the combinatorial number (n/k). <p>
*@param n
* An integer equal or greater than zero to set the order of the expansion ( here we don't need to compute the expansion, just the required entry ).
* <p>
* @param k
* An integer value to set the required entry we wish.
* <p>
* @return Integer value for the wanted entry in the required expansion.
*
*/
public static BigInteger compute(Integer n, Integer k)
{
	assert(n >= 0): "BinomialCoefficient -> compute(n, k): Bad parameter.:";
	assert(k >= 0 && k <=n): "BinomialCoefficient -> compute(n, k): Bad parameter.:";
	if(n == 0 || n == 1) return new BigInteger("1");
	if(k == 0 || k == n) return new BigInteger("1");
	return Factorial.bigFactorial(n).divide(Factorial.bigFactorial(k).multiply(Factorial.bigFactorial(n-k)));
}

/**
* Static method to compute the expansion into a binomial coefficients. <p>
* Notice that the resulting expansion will be a palindrome. <p>
* @param n
* An integer value for the order of the required expansion.
* <p>
* @return Expansion into binomial coefficients for the required order.
*
*/
	public static BigInteger[] compute(Integer n)
	{
	assert(n >= 0): "BinomialNumber -> compute(...): Bad parameter; 'n' must be equal or greater than zero.";
	if(n == 0) return new BigInteger[]{new BigInteger("1")};
	if(n == 1) return new BigInteger[]{new BigInteger("1"), new BigInteger("1")};
	BigInteger[] bc = new BigInteger[n+1];
	bc[0] = new BigInteger("1");
	bc[n] = new BigInteger("1");
	for(int k = 1; k < n; k++)
	{
	bc[k] = Factorial.bigFactorial(n).divide(Factorial.bigFactorial(k).multiply(Factorial.bigFactorial(n-k)));
	}
	return bc;
	}

/**
* Static method to convert a <code>BigInteger</code> array to a double floating-point array. <p>
* @param p
* A <code>BigInteger</code> array.
* <p>
* @return A double floating-point array.
*
*/
public static double[] toDoubleArray(BigInteger[] p)
{
if(p == null || p.length == 0) return new double[0];
double[] d = new double[p.length];
for(int i = 0; i < d.length; i++)
{
	d[i] = p[i].doubleValue();
}
return d;
}

/**
* Static method to print a <code>BigInteger</code> to the console. <p>
* @param bn
* A <code>BigInteger</code> array.
*
*/
public static void print(BigInteger[] bn)
{
if(bn == null || bn.length == 0)
{
System.out.println("[]");
return;
}
System.out.print("[");
for(int i = 0; i < bn.length; i++)
{
	if(i > 0) System.out.print(", ");
	System.out.print(bn[i]);
}
System.out.println("]");
}


// Private constructor so that this class cannot be instantiated
private BinomialCoefficient() {}

}

// END
