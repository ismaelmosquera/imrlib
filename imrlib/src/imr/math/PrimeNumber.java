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
* PrimeNumber.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math;

import java.util.LinkedList;
import java.util.ListIterator;

/**
* This class provides some static useful methods to perform a few operations with prime numbers. <p>
* We decided to create this class because these methods could be used by other classes. <p>
*
* @author Ismael Mosquera Rivera.
*
*/
@SuppressWarnings("unchecked")
public final class PrimeNumber
{

/**
* Static method to get an array having all primes in a concrete range [first .. last]. <p>
* @param first
* An integer value for the first entry in the range.
* <p>
* @param last
* An integer for the last value in the range.
* <p>
* @return An integer array having the required prime numbers.
*
*/
public static int[] getPrimes(int first, int last)
{
assert(last > first && first > 0): "PrimeNumber -> getPrimes(...): Bad parameter.";
int p = first;
LinkedList<Integer> primeList = new LinkedList<>();
if(isPrime(p)) primeList.add(p);
while(p < last)
{
p = nextPrime(p);
if(p > last) break;
primeList.add(p);
}
p = 0;
int[] primes = new int[primeList.size()];
ListIterator<Integer> it = primeList.listIterator();
while(it.hasNext())
{
primes[p++] = it.next();
}
return primes;
}

/**
* This static method gets the next prime according to the integer value passed as parameter. <p>
* @param n
* An integer value greater than 0.
* <p>
* @return Next prime number after the value passed as parameter.
*
*/
public static int nextPrime(int n)
{
	assert(n > 0): "PrimeNumber -> nextPrime(...): Bad parameter.";
int k = n+1;
while(!isPrime(k)) k++;
return k;
}

/**
* This method evaluates if the integer value passed as parameter is prime or not. <p>
* @param n
* An integer value.
* <p>
* @return true if prime or false otherwise.
*
*/
public static boolean isPrime(int n)
{
if(n < 2) return false;
for(int i = 2; i < n; i++)
{
	if(i*i <= n && n%i == 0) return false;
}
return true;
}


// Private constructor so that this class cannot be instantiated
private PrimeNumber() {}

}

// END
