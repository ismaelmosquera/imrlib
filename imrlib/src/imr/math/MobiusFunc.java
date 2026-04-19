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
* MobiusFunc.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math;


/**
* This class has support to compute the Mobius function, and its related one Mertens function. <p>
* @author Ismael Mosquera Rivera.
*
*/
public class MobiusFunc
{

/**
* Static method to compute the Mobius function. <p>
* @param n
* An integer value greater than 0.
* <p>
* The Mobius function mu(n) returns 0, 1 or -1 depending in the factorization of its parameter.
* <ul>
* <li>mu(1) = 1</li>
* <li>mu(n) = -1 { n is prime }</li>
* <li>mu(n) = 0 { there is an 'a' square prime dividing n }</li>
* <li>mu(n) = 1 { n has even prime factors }</li>
* <li>mu(n) = -1 { n has odd prime factors }</li>
* </ul>
*
* @return mu(n) value.
*
*/
public static int mu(int n)
{
	assert(n > 0): "MobiusFunc -> mu: Bad parameter, n must be greater than 0.";
if(n == 1) return 1;
if(isPrime(n)) return -1;
int p = 0;
for(int i = 1; i <= n; i++)
{
if(n%i == 0 && isPrime(i))
{
if(n%(i*i) == 0)
{
	return 0;
}
else
{
 p++;
}
}
}
return (p%2 == 0) ? 1 : -1;
}

/**
* Static method to compute the Mertens function. <p>
* @param n
* An integer value greater than 0.
* <p>
* Mertens function is computed as follows: <p>
* m(n) = sum k = 1..n {mu(k)}
* <p>
* where mu(k) is the value returned by the Mobius function. <p>
* @return m(n) value.
*
*/
public static int m(int n)
{
assert(n > 0): "MobiusFunc -> m: Bad parameter, n must be greater than 0.";
int _m = 0;
for(int k = 1; k <= n; k++) _m += mu(k);
return _m;
}


// Helper function to check if 'n' is prime or not.
private static boolean isPrime(int n)
{
if(n < 2) return false;
for(int i = 2; i < n; i++)
{
	if(i*i <= n && n%i == 0) return false;
}
return true;
}


// Private constructor so that this class cannot be instantiated
private MobiusFunc() {}

}

// END
