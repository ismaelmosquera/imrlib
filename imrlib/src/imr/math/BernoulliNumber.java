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
* BernoulliNumber.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.math;

/**
* This class has a static method to compute Bernoulli numbers for n = 0, 1, 2 .. n <p>
* Where bn(0) = 1, bn(1) = +/- 1/2, 0 if n is odd greater than 1, negative if n is even divisible by 4 or positive otherwise. <p>
* Since the result is a rational number, in this case integers can grow very fast and huge. <p>
* There are several fields where Bernoulli numbers can be so useful.
* <p>
* This class uses assertions; to enable them you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class BernoulliNumber
{

/**
* Static method to compute the n-th Bernoulli number for n greater than or equal zero. <p>
* Since the result is a fraction where the numerator or denominator ( mainly the numerator ) can get so big, we coded <p>
* a <code>BigRational</code> class in order to avoid integer overflow. <p>
* @param n
* A integer value greater than or equal to zero.
* <p>
* @return Bernoulli number according to the 'n' parameter as a big rational.
*
*/
public static BigRational compute(int n)
{
assert(n >= 0): "BernoulliNumber -> compute(...): Bad parameter; 'n' must be greater or equal than zero.";
if(n >1 && n%2==1) return new BigRational();
BigRational[] bn = new BigRational[n+1];
for(int i = 0; i < bn.length; i++)
{
bn[i] = new BigRational(1, (i+1));
for(int j = i; j >= 1; j--)
{
	bn[j-1] = (bn[j-1].sub(bn[j])).mul(new BigRational(j));
}
}
return bn[0];
}


// Private constructor so that this class cannot be instantiated
private BernoulliNumber() {}

}

// END
