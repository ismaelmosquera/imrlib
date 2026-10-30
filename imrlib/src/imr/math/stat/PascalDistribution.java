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
* PascalDistribution.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

import imr.math.matrix.Vector;
import imr.math.polynomial.BinomialCoefficient;

import java.util.LinkedList;
import java.util.ListIterator;

/**
* This class implements some functionallity related to Pascal distribution. <p>
* There are methods to compute the hole pmf or cdf, but also to get a single value from both of them. <p>
* The following formula is used: <p>
* <code>sum k=0 to infinity B(k+r-1, k)p^r(1-p)^k</code>
* <p>
* where <code>B</code> is the binomial coefficient, 'r' is the number of successes before having 'k' failures with a probability 'p' of success for any single trial. <p>
* 'r' integer greater than 0; 'p' in the range [0 .. 1] and 'k' = 0, 1, 2 .. infinity and epsilon close to 0 for achieve convergence when compute pmf and cdf. <p>
* This class uses assertions; to enable them you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class PascalDistribution
{

/**
* Static method to compute the probability for a single trial for a 'k' point in the probability mass function ( pmf ) according the values passed as parameters. <p>
* @param k
* An integer value equal or greater than zero.
* <p>
* @param r
* An integer value greater than zero.
* <p>
* @param p
* A double floating-point value in the range [0 .. 1] to set the wished probability.
* <p>
* @return Probability value.
*
*/
	public static double pr(Integer k, Integer r, double p)
	{
	assert(r > 0): "PascalDistribution -> pr(...): Bad parameter; 'r' must be greater than zero.";
	assert(k >= 0): "PascalDistribution -> pr(...): Bad parameter; 'k' must be greater or equal than zero.";
	assert(p >= 0.0 && p <= 1.0): "PascalDistribution -> pr(...): Bad parameter; 'p' must be in the range [0 .. 1]";
	return BinomialCoefficient.compute(k+(r-1), k).doubleValue() * Math.pow(1.0-p, k) * Math.pow(p, r);
}

/**
* Static method to compute the cumulative probability for a single trial for a 'k' point in the cumulative density function ( cdf ) according the values passed as parameters. <p>
* @param k
* An integer value equal or greater than zero.
* <p>
* @param r
* An integer value greater than zero.
* <p>
* @param p
* A double floating-point value in the range [0 .. 1] to set the wished probability.
* <p>
* @return Cumulative probability value.
*
*/
public static double cu(Integer k, Integer r, double p)
{
assert(r > 0): "PascalDistribution -> cu(...): Bad parameter; 'r' must be greater than zero.";
	assert(k >= 0): "PascalDistribution -> cu(...): Bad parameter; 'k' must be greater or equal than zero.";
	assert(p >= 0.0 && p <= 1.0): "PascalDistribution -> cu(...): Bad parameter; 'p' must be in the range [0 .. 1]";
		double _cu = 0.0;
		for(int i = 0; i <= k; i++)
		{
		_cu += pr(i, r, p);
		}
		return _cu;
		}

/**
* Static method to compute the hole probability mass function ( pmf ) for a Pascal distribution acording the values passed as parameters. <p>
* The length of the returned array can be bigger or smaller depending on the parameter values. <p>
* @param r
* An integer value greater than zero.
* <p>
* @param p
* A double floating-point value in the range [0 .. 1] to set the wished probability.
* <p>
* @return Probability mass function according the values passed as parameter.
*
*/
public static double[] pmf(Integer r, double p)
{
LinkedList<Double> list = new LinkedList<>();
double _p;
double sum = 0.0;
for(int k = 0; k < MAX_ITERATIONS; k++)
{
_p = pr(k, r, p);
list.add(_p);
sum += _p;
if(1.0-sum < PoissonDistribution.EPSILON) break;
}
double[] out = new double[list.size()];
int q = 0;
ListIterator<Double> it = list.listIterator();
while(it.hasNext())
{
out[q++] = it.next();
}
return out;
}

/**
* Static method to compute the hole probability mass function ( pmf ) for a Pascal distribution acording the values passed as parameters. <p>
* The returned pmf will be stored in a <code>imr.math.matrix.Vector</code> object. <p>
* The size of the returned vector can be bigger or smaller depending on the parameter values. <p>
* @param r
* An integer value greater than zero.
* <p>
* @param p
* A double floating-point value in the range [0 .. 1] to set the wished probability.
* <p>
* @return Probability mass function according the values passed as parameter stored in a <code>Vector</code> object.
*
*/
public static Vector pmfVector(Integer r, double p)
{
double[] _pmf = pmf(r, p);
int n = _pmf.length;
Vector v = new Vector(n);
for(int i = 0; i < n; i++) v.set(i, _pmf[i]);
return v;
}

/**
* Static method to compute the hole cumulative density function ( cdf ) for a Pascal distribution acording the values passed as parameters. <p>
* The length of the returned array can be bigger or smaller depending on the parameter values. <p>
* @param r
* An integer value greater than zero.
* <p>
* @param p
* A double floating-point value in the range [0 .. 1] to set the wished probability.
* <p>
* @return Cumulative density function according the values passed as parameter.
*
*/
public static double[] cdf(Integer r, double p)
{
double[] _pmf = pmf(r, p);
int n = _pmf.length;
double[] _cdf = new double[n];
_cdf[0] = _pmf[0];
for(int i = 1; i < n; i++)
{
_cdf[i] = _cdf[i-1] + _pmf[i];
}
return _cdf;
}

/**
* Static method to compute the hole cumulative density function ( cdf ) for a Pascal distribution acording the values passed as parameters. <p>
* The computed cdf will be returned as a <code>imr.math.matrix.Vector</code> object. <p>
* The size of the returned vector can be bigger or smaller depending on the parameter values. <p>
* @param r
* An integer value greater than zero.
* <p>
* @param p
* A double floating-point value in the range [0 .. 1] to set the wished probability.
* <p>
* @return Cumulative density function according the values passed as parameter stored in a <code>Vector</code> object.
*
*/
public static Vector cdfVector(Integer r, double p)
{
double[] _cdf = cdf(r, p);
int n = _cdf.length;
Vector v = new Vector(n);
for(int i = 0; i < n; i++) v.set(i, _cdf[i]);
return v;
}

/**
* Static method to get the mean for a concrete Pascal distribution according some parameters. <p>
* @param r
* An integer value greater than zero.
* <p>
* @param p
* A double floating-point value in the range [0 .. 1] to set the wished probability.
* <p>
* @return Mean value for the Pascal distribution according the values passed as parameters.
*
*/
public static double mean(int r, double p)
{
assert(r > 0): "PascalDistribution -> mean(...): Bad parameter; 'r' must be greater than zero.";
	assert(p >= 0.0 && p <= 1.0): "PascalDistribution -> mean(...): Bad parameter; 'p' must be in the range [0 .. 1]";
return ((double)r*(1.0-p))/p;
}

/**
* Static method to get the variance for a concrete Pascal distribution according some parameters. <p>
* @param r
* An integer value greater than zero.
* <p>
* @param p
* A double floating-point value in the range [0 .. 1] to set the wished probability.
* <p>
* @return Variance value for the Pascal distribution according the values passed as parameters.
*
*/
public static double var(int r, double p)
{
assert(r > 0): "PascalDistribution -> var(...): Bad parameter; 'r' must be greater than zero.";
	assert(p >= 0.0 && p <= 1.0): "PascalDistribution -> var(...): Bad parameter; 'p' must be in the range [0 .. 1]";
return ((double)r*(1.0-p))/(p*p);
}


// Private constructor so that this class cannot be instantiated
private PascalDistribution() {}

// Symbolic constant declared as package scope just for convinience
static final int MAX_ITERATIONS = 10000;
}

// END

