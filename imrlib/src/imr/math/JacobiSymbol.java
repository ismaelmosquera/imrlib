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
* JacobiSymbol.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math;

/**
* This class is an implementation of the Jacobi symbol. <p>
* Evaluates a | n where:
* <ul>
* <li>a is a positive integer; a = 1, 2, 3, 4 ... that is, a natural number</li>
* <li>n is an odd positive integer; n = 1, 3, 5, 7, 9 ...</li>
* </ul>
* The Legendre symbol is computed using the Jacobi one, since Jacobi symbol is a generalization of the Legendre symbol <p>
* That is, for the Legendre symbol having a | n, n must be a prime number other than 2 ( odd prime number ).
* <p>
* this implementation was inspired after reading the Jacobi symbol article in Wikipedia. <p>
* This class uses assertions, to enable them just type the '-ea' modifier when execute <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class JacobiSymbol
{

/**
* This static method implements the Jacobi symbol. <p>
* a | n where a is a integer number greater than 0 and n is a positive odd integer. <p>
* a | n =
* <ul>
* <li>0 if gcd(a, n) other than 1</li>
* <li>-1 or 1 if gcd(a, n) equals 1</li>
* </ul>
* also
* <ul>
* <li>1 if a is a quadratic residue mod n</li>
* <li>-1 if a is a quadratic nonresidue mod n</li>
* </ul>
* @param a
* A positive integer greater than 0
* <p>
* @param n
* A positive odd integer.
* <p>
* @return Jacobi symbol for a | n
*
*/
public static int jacobi(int a, int n)
{
	assert((a > 0) && (n%2 != 0)): "Symbol -> cjacobi(...): Bad parameter";
        int symbol = 1; // initial value
        int k = (a % n); // we have to test a mod n
        while(k > 0)
        {
			// extract any even k ( numerator )
            while(k % 2 == 0)
            {
                k /= 2;
                int r = n % 8;
                if(r == 3 || r == 5)
                {
                    symbol *= -1;  // flip sign value
                }
            }
            // now k and n are odd coprimes
            // swap k and n values
            int aux = n;
            n = k;
            k = aux;
            if(k % 4 == 3 && n % 4 == 3)
            {
                symbol *= -1;  // flip sign value
            }
            k %= n;
        }
        if(n == 1)
        {
            return symbol;  // symbol = -1 or 1 -> gcd(a, n) = 1
        }
        return 0;  // neither -1 nor 1 -> gcd(a, n) != 1
    }

/**
* Static method to compute the Legendre symbol. <p>
* a | p where a is a positive integer greater than 0 and p is an odd prime number. <p>
* a | p =
* <ul>
* <li>0 if a equivalent to 0 ( mod p)</li>
* <li>1 if a not identical to 0 ( mod p) and exist x equivalent to x^2 ( mod p)</li>
* <li>-1 if a not identical to 0 ( mod p ) and there is not an integer such in the case of a| p = 1</li>
* </ul>
* @param a
* A positive integer value.
* <p>
* @param p
* An odd positive prime number.
* <p>
* @return Legendre symbol value for a | p
*
*/
public static int legendre(int a, int p)
{
	assert(PrimeNumber.isPrime(p)): "Symbol -> legendre(...): Bad parameter.";
	return jacobi(a, p);
}


// Private constructor so that this class cannot be instantiated
private JacobiSymbol() {}

}

// END
