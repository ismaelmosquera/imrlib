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

import imr.util.iArray;

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
* It returns an integer vector with all coprimes respect to the parameter, and its value is the length of that array.
* <p>
* That is, if 'a' is an array of integer values then:
* <p>
* a = phi(n); a has the coprime values and its value is a.length
* <p>
* @param n
* A positive integer value greater than zero.
* <p>
* @return an array with the list of coprimes respect to n.
*
*/
public static int[] phi(int n)
{
	if(n == 1)
	{
		int[] out = new int[1];
		out[0] = 1;
		return out;
	}
	assert(n > 0): "EulerTotient.phi: Bad parameter.";
int[] phin = new int[n];
int counter = 0;
for(int i = 1; i <= n; i++)
{
if(GCD.compute(i, n) == 1) phin[counter++] = i;
}
phin = (int[])iArray.resize(phin, counter);
return phin;
}

/**
* This static method computes the Euler's cototient function.
* <p>
* It returns an integer vector with the values not prime respect to the parameter, and its value is the length of that array.
* <p>
* Notice that since phi(1) = 1, and cophi(n) = n - phi(n) then:
* <p>
* cophi(1) = 1 - phi(1) = 1 - 1 = 0
* <p>
* So, take in account that for cophi(1) this method returns an empty array ( length = 0 ).
* <p>
* @param n
* A positive integer greater than zero.
* <p>
* @return an integer array with the values not prime respect to 'n'.
*
*/
public static int[] cophi(int n)
{
	if(n == 1) return new int[0];
assert(n > 0): "EulerTotient.cophi: Bad parameter.";
int[] cophin = new int[n];
int counter = 0;
for(int i = 1; i <= n; i++)
{
if(GCD.compute(i, n) != 1) cophin[counter++] = i;
}
cophin = (int[])iArray.resize(cophin, counter);
return cophin;
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
if(n == 1) return true;
assert(n > 0): "EulerTotient.isPerfect: Bad parameter.";
int d = phi(n).length;
int sum = d;
while(d > 1)
{
	d = phi(d).length;
	sum += d;
}
return (sum == n) ? true : false;
}


// Private constructor, so that this class cannot be instantiated.
private EulerTotient() {}

}

// END
