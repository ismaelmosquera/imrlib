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
* Factorization.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math;

import imr.util.Pair;

import java.util.LinkedList;
import java.util.ListIterator;

/**
* This class performs prime factorization, that is, decomposes an integer in a product of prime numbers. <p>
*
* @author Ismael Mosquera Rivera.
*
*/
@SuppressWarnings("unchecked")
public final class Factorization
{

/**
* Static method to factorize an integer in product of prime numbers. <p>
* Factorization is stored in a <code>PairInteger, Integer</code> object, where first is a prime number and second the number of times. <p>
* For instance, (2, 3) means 2^3 <p>
* 12 = 2^2 * 3 <p>
* @param n
* An integer value to be factorized.
* <p>
* @return An array of <code>Pair</code> objects having the factorization.
*
*/
public static Pair<Integer, Integer>[] compute(int n)
{
	// n must be greater than 1
	assert(n > 1): "Factorization -> compute(...): Bad parameter, n must be greater than 1";
	if(n < 2) return null; // maybe assertions are not enabled
if(isPrime(n)) return new Pair[]{new Pair<Integer, Integer>(n, 1)};
int k = 0;
int p = 0;
int d = n;
LinkedList<Pair<Integer, Integer>> factors = new LinkedList<>();
// factorize
while(d > 1)
{
k = 0;
p = nextPrimeDivisor(d); // get the first p|d ( prime dividing d ) found
// iterate until p|d get false
while((d % p) == 0)
{
d /= p; // divide d by p
k++; // increment number of times
}
// add (p, k) pir
factors.add(new Pair<Integer, Integer>(p, k));
}
// factorization already done
// build pair array from the elements in the factor list
k = factors.size();
Pair<Integer, Integer>[] out = new Pair[k];
ListIterator<Pair<Integer, Integer>> it = factors.listIterator();
p = 0;
while(it.hasNext())
{
	out[p++] = it.next();
}
// arrange pairs by prime divisor in ascending order
// for this task the bubble sort algorithm will be fine
Pair<Integer, Integer> tmp = null;
for(int i = 0; i < k-1; i++)
{
	for(int j = i+1; j < k; j++)
	{
		if(out[i].getFirst() > out[j].getFirst())
		{
			tmp = out[i];
			out[i] = out[j];
			out[j] = tmp;
		}
	}
}
// Return factorization in a pair array structure
return out;
}

/**
* Static method to get a string representation for a factorization. <p>
* @param p
* An array of pair having the factorization.
* <p>
* @return A string representing the factorization.
*
*/
public static String toString(Pair<Integer, Integer>[] p)
{
if(p == null) return "";
String s = "";
for(int i = 0; i < p.length; i++)
{
if(i > 0) s += " * ";
s += p[i].getFirst();
if(p[i].getSecond() > 1) s+= "^" + p[i].getSecond();
}
return s;
}


/*
* Gets the first prime dividing the integer passed as parameter.
*/
private static int nextPrimeDivisor(int n)
{
int p = 1;
while(p < n)
{
p = nextPrime(p);
if((n % p) == 0) break;
}
return p;
}

/*
* Gets the next prime according to the integer value passed as parameter.
*/
private static int nextPrime(int n)
{
int k = n+1;
while(!isPrime(k)) k++;
return k;
}

/*
* Evaluates if the integer value passed as parameter is prime or not.
*/
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
private Factorization() {}

}

// END
