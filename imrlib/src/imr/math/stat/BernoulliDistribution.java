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
* BernoulliDistribution.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

import imr.util.Pair;
import imr.math.RandomNumberGenerator;
import imr.math.matrix.Vector;

/**
* This class implements some functionallity related to Bernoulli distribution. <p>
* such a distribution is useful to manage problems requiring a response true or false. <p>
* that is, if probability P in the range (0 .. 1) then: <p>
* We randomly select 'k' between 0 an 1 <p>
* if k = 0, outcome = 1-P <p>
* if k = 1, outcome = P
* <p>
* @author Ismael Mosquera rivera.
*
*/
public final class BernoulliDistribution
{

/**
* Static method to randomly choose an integer value in the set {0, 1}. <p>
* Maybe you want to know how many 0s and 1s are in a concrete experiment. <p>
* Afterwards, you can easily get its probability from the pmf by index.
* <p>
* @return Randomly choosed value from the set {0, 1}
*
*/
public static int kValue()
{
return RandomNumberGenerator.generate(0, 1);
}

/**
* Static method to compute a single trial. <p>
* @param p
* A double floating-point value in the range (0 .. 1)
* <p>
* @return A <code>imr.util.Pair</code> object, having the 0 or 1 ( failure or success ) as first, and the probability of failure or success in it second.
*
*/
public static Pair<Integer, Double> outcomeTrial(double p)
{
int k = RandomNumberGenerator.generate(0, 1);
return new Pair<Integer, Double>(k, BinomialDistribution.pr(k, 1, p));
}

/**
* Static method to compute the Probability Mass Function ( pmf ) for a Bernoulli distribution with probability in the range (0 .. 1). <p>
* Such a pmf will be an array of two positions: <p>
* pmf[0] = (1 - P) <p>
* pmf[1] = P <p>
* @param p
* A double floating-point value in the range (0 .. 1)
* <p>
* @return Probability mass function.
*
*/
public static double[] pmf(double p)
{
	return BinomialDistribution.pmf(1, p);
}

/**
* Static method to compute the pmf for a Bernoulli distribution and returned as a <code>Vector</code> object. <p>
* @param p
* A double floating-point value in the range (0 .. 1)
* <p>
* @return Pmf as a <code>Vector</code> object.
*
*/
public static Vector pmfVector(double p)
{
return BinomialDistribution.pmfVector(1, p);
}

/**
* Static method to compute the Cumulative Distribution Function ( cdf ) of a Bernoulli distribution with probability in the range (0 .. 1). <p>
* such a distribution will be an array of two positions: <p>
* cdf[0] = (1 - P) <p>
* cdf[1] = 1 <p>
* @param p
* A double floating-point value in the range (0 .. 1)
* <p>
* @return Cumulative distribution function for a Bernoulli distribution with probability = 'p' ( parameter )
*
*/
public static double[] cdf(double p)
{
	return BinomialDistribution.cdf(1, p);
}

/**
* Static method to compute the Cumulative Distribution Function ( cdf ) of a Bernoulli distribution with probability in the range (0 .. 1) and returned as a <code>Vector</code> object. <p>
* @param p
* A double floating-point value in the range (0 .. 1)
* <p>
* @return Cumulative distribution function as a <code>Vector</code> object.
*
*/
public static Vector cdfVector(double p)
{
return BinomialDistribution.cdfVector(1, p);
}

// Private constructor so that this class cannot be instantiated.
private BernoulliDistribution() {}

}

// END
