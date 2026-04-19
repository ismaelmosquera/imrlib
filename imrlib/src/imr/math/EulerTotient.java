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
* EulerTotient.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math;

/**
* This class implements the Euler's totient function.
* <p>
* The totient function 'phi(n)' counts the coprime values respect to the parameter 'n'.
* <p>
* for d = 1, 2, 3 .. n
* <p>
* On the other hand, the cototient function 'cophi(n)' computes its inverse, that is, the number of values not prime respect to the parameter 'n'.
* <p>
* So, cophi(n) = n - phi(n)
* <p>
* This class uses assertions; you can enable assertions using the '-ea' modifier when executing.
* <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public class EulerTotient
{

/**
* This static method computes the Euler's totient function.
* <p>
* @param n
* A positive integer value greater than zero.
* <p>
* @return Euler's Totient value for the parameter 'n'.
*
*/
public static int phi(int n)
{
	assert(n > 0): "EulerTotient.phi: Bad parameter.";
	if(n == 1) return 1;
int counter = 0;
for(int i = 1; i <= n; i++)
{
if(GCD.compute(i, n) == 1) counter++;
}
return counter;
}

/**
* This static method computes the Euler's cototient function.
* <p>
* cophi(n) = n - phi(n)
* <p>
* @param n
* A positive integer greater than zero.
* <p>
* @return euler's co-Totient value for the parameter 'n'.
*
*/
public static int cophi(int n)
{
	assert(n > 0): "EulerTotient.cophi: Bad parameter.";
return n - phi(n);
}

/**
* This method evaluates the parameter 'n' to a perfect totient.
* <p>
* A perfect totient is a natural number 'n' which computing its successive totients until phi(n) = 1, the sum equals to 'n'.
* <p>
* Example:
* <p>
* 9 is a perfect totient.
* <p>
* phi(9) = 6, phi(6) = 2, phi(2) = 1.
* <p>
* 6 + 2 +1 = 9
* <p>
* @param n
* An integer value greater than zero.
* <p>
* @return true if 'n' is a perfect totient or false otherwise.
*
*/
public static boolean isPerfect(int n)
{
	assert(n > 0): "EulerTotient.isPerfect: Bad parameter.";
if(n == 1) return true;
int d = phi(n);
int sum = d;
while(d > 1)
{
	d = phi(d);
	sum += d;
}
return (sum == n) ? true : false;
}

/**
* Static method to compute the coprimes vector for the natural number passed as parameter. <p>
* Notice that the length of such a vector is, actually, the Totient value of 'n'. <p>
* @param n
* An integer number greater than 0.
* <p>
* @return coprimes vector respect to the parameter.
*
*/
public static int[] coprimes(int n)
{
assert(n > 0): "EulerTotient.coprimes: Bad parameter.";
	int[] out = null;
	if(n == 1)
	{
	out = new int[1];
	out[0] = 1;
	return out;
	}
	out = new int[phi(n)];
int counter = 0;
for(int i = 1; i <= n; i++)
{
if(GCD.compute(i, n) == 1) out[counter++] = i;
}
return out;
}

/**
* Static method to compute the nocoprimes for the natural number passed as parameter. <p>
* Notice that the length of such a vector equals the cophi ( co-Totient ) value of the parameter. <p>
* So, if cophi(n) = 0 ( cophi(1) = n - phi(1) = 1 - 1 = 0 ), this method returns an empty array. <p>
* @param n
* An integer value greater than 0.
* <p>
* @return vector of divisors for the 'n' parameter.
*
*/
public static int[] nocoprimes(int n)
{
assert(n > 0): "EulerTotient.divisors: Bad parameter.";
	int[] out = null;
	if(n == 1)
	{
	out = new int[0];
	return out;
	}
	out = new int[cophi(n)];
int counter = 0;
for(int i = 1; i <= n; i++)
{
if(GCD.compute(i, n) != 1) out[counter++] = i;
}
return out;
}


// Private constructor, so that this class cannot be instantiated.
private EulerTotient() {}

}

// END
