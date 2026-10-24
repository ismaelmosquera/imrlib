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
* BinomialDistribution.java
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
import imr.math.polynomial.BinomialCoefficient;

/**
* This class implements some functionallity related to binomial distribution. <p>
* B(n, p) where n = 0, 1, 2, ... that is an integer greater or equal than zero; and p in the range (0 .. 1). <p>
* Notice than 'n' expands to a n+1 array to form the probability mass function ( pmf ) to get 'k' successes on 'n' trials with probability = p (0 .. 1) <p>
* We can also realize that if p = 0.5, we will get a palindrome as pmf. <p>
* So, pmf expansions from n = 0 .. N will form a Pascals's Triangle.
* <p>
* This class uses assertions; to enable them you can use the '-ea' modifier when execute: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class BinomialDistribution
{

/**
* Static method to randomly choose an integer value in the set {0, 1, 2 .. n} <p>
* Afterwards, you can easily retrieve its associated probability from the pmf or using the pr(k, n , p) method. <p>
* @param n
* An integer value greater or equal than zero.
* <p>
* @return Randomly choosed integer from the set {0, 1, 2 .. n}
*
*/
public static int kValue(int n)
{
return RandomNumberGenerator.generate(0, n);
}

/**
 * Static method to get the outcome for a single trial. <p>
* @param n
* An integer greater or equal to zero.
* <p>
* @param p
* A double floating-point value to set the wished probability (0 .. 1)
* <p>
* @return Outcome for a random trial, stored in a <code>imr.util.Pair</code> object having the randomly choosed k value as first and the computed probability as second.
*
*/
public static Pair<Integer, Double> outcomeTrial(Integer n, double p)
{
int k = RandomNumberGenerator.generate(0, n);
return new Pair<Integer, Double>(k, pr(k, n, p));
}

/**
* Static method to compute the probability mass function following a binomial distribution. <p>
* @param n
* An integer greater or equal to zero.
* <p>
* @param p
* A double floating-point value to set the wished probability (0 .. 1)
* <p>
* @return Probability mass function as a double floating-point array.
*
*/
public static double[] pmf(Integer n, double p)
{
	double[] _pmf = new double[n+1];
	for(int k = 0; k <= n; k++ )
	{
	_pmf[k] = pr(k, n, p);
	}
	return _pmf;
}

/**
* Static method to get the probability mass function following a binomial distribution. <p>
* This method computes the same result as the <code>double[] pmf(Integer n, double p)</code> but returns the result as a <code>Vector</code> object. <p>
 * @param n
 * An integer greater or equal to zero.
 * <p>
 * @param p
 * A double floating-point value to set the wished probability (0 .. 1)
* <p>
* @return Probability mass function as a <code>Vector</code> object.
*
*/
public static Vector pmfVector(Integer n, double p )
{
double[] _pmf = pmf(n, p);
int k = _pmf.length;
Vector v = new Vector(k);
for(int i = 0; i < k; i++)
{
v.set(i, _pmf[i]);
}
return v;
}

/**
* Static method to compute a single entry in the probability mass function following a binomial distribution. <p>
* That is, n|k, p. With n = 0, 1, 2 ... k = 0 .. n and p in the interval (0 .. 1) <p>
* Here we compute the probability 'p' of having 'k' successes in 'n' trials. <p>
* @param k
* An integer value in the range [0 .. n]
* <p>
* @param n
 * An integer greater or equal to zero.
 * <p>
 * @param p
 * A double floating-point value to set the wished probability (0 .. 1)
* <p>
* @return Probability for having 'k' successes.
*
*/
public static double pr(Integer k, Integer n, double p)
{
assert(n >= 0): "BinomialDistribution -> pr(...): Bad parameter; 'n' must be equal or greater than zero.";
assert(k >= 0 && k <= n): "BinomialDistribution -> pr(...): Bad parameter; 'k' must be in the range[0 .. n]";
assert(p >= 0.0 && p <= 1.0): "BinomialDistribution -> pr(...): Bad parameter; 'p' must be in the range [0 .. 1]";
return BinomialCoefficient.compute(n, k).doubleValue() * Math.pow(p, k) * Math.pow(1.0-p, n-k);
}

/**
* Static method to compute the cumulative distribution function following a binomial distribution. <p>
* @param n
 * An integer greater or equal to zero.
 * <p>
 * @param p
 * A double floating-point value to set the wished probability (0 .. 1)
* <p>
* @return Cumulative distrubution function as a double floating-point array.
*
*/
public static double[] cdf(Integer n, double p)
{
	double[] _pmf = pmf(n, p);
	int _n = _pmf.length;
	double[] _cdf = new double[_n];
	_cdf[0] = _pmf[0];
	for(int i = 1; i < _n; i++)
	{
	_cdf[i] = _cdf[i-1] + _pmf[i];
	}
	return _cdf;
}

/**
* Static method to get the cumulative distrubution function following a binomial distribution. <p>
* This method computes the same result as the <code>double[] cdf(Integer n, double p)</code> but returns the result as a <code>Vector</code> object. <p>
 * @param n
 * An integer greater or equal to zero.
 * <p>
 * @param p
 * A double floating-point value to set the wished probability (0 .. 1)
* <p>
* @return Cumulative distrubution function as a <code>Vector</code> object.
*
*/
public static Vector cdfVector(Integer n, double p)
{
double[] _cdf = cdf(n, p);
int size = _cdf.length;
Vector v = new Vector(size);
for(int i = 0; i < size; i++)
{
v.set(i, _cdf[i]);
}
return v;
}

/**
* Static method to get the mean value for a binomial distribution according the parameters 'n' and 'p', <p>
* where 'n' is the number of trials and 'p' is the probability of success. <p>
* @param n
 * An integer greater or equal to zero.
 * <p>
 * @param p
 * A double floating-point value to set the wished probability (0 .. 1)
* <p>
* @return Mean value.
*
*/
public static double mean(int n, double p)
{
assert(n >= 0): "BinomialDistribution -> mean(...): Bad parameter; 'n' must be equal or greater than zero.";
assert(p >= 0.0 && p <= 1.0): "BinomialDistribution -> mean(...): Bad parameter; 'p' must be in the range [0 .. 1]";
return (double)n*p;
}

/**
* Static method to get the variance value for a binomial distribution according the parameters 'n' and 'p', <p>
* where 'n' is the number of trials and 'p' is the probability of success. <p>
* @param n
 * An integer greater or equal to zero.
 * <p>
 * @param p
 * A double floating-point value to set the wished probability (0 .. 1)
* <p>
* @return Variance value.
*
*/
public static double var(int n, double p)
{
assert(n >= 0): "BinomialDistribution -> var(...): Bad parameter; 'n' must be equal or greater than zero.";
assert(p >= 0.0 && p <= 1.0): "BinomialDistribution -> var(...): Bad parameter; 'p' must be in the range [0 .. 1]";
return (double)n*p*(1.0-p);
}


// Private constructor so that this class cannot be instantiated.
private BinomialDistribution() {}

}

// END
