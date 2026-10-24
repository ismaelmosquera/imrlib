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
* PoissonDistribution.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

import imr.math.Factorial;
import imr.math.matrix.Vector;

import java.util.LinkedList;
import java.util.ListIterator;

/**
* This class implements some functionallity related to Poisson distribution. <p>
* There are methods to compute the hole pmf or cdf, but also to get a single value from both of them. <p>
* The following formula is used: <p>
* <code>sum k=0 to infinity lambda^ke^-lambda / k!</code>
* <p>
* where 'lambda' is the average or rate for an occurrence, 'e' is the euler's number, k! is the factorial of 'k' and 'k' an integer equal or greater than 0 and some epsilon close to 0 to achieve convergence when computing pmf and cdf. <p>
* This class uses assertions; to enable them you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class PoissonDistribution
{

/**
* Static method to compute the probability for a single trial for a 'k' point in the probability mass function ( pmf ) according the values passed as parameters. <p>
* @param k
* An integer value equal or greater than zero.
* <p>
* @param lambda
* A double floating-point value greater than zero.
* <p>
* @return Probability value.
*
*/
	public static double pr(int k, double lambda)
	{
		assert(lambda > 0.0): "PoissonDistribution -> pr(...): Bad parameter; lambda must be greater than zero.";
		assert(k >= 0): "PoissonDistribution -> pr(...): Bad parameter; 'k' must be greater or equal than zero.";
		return Math.pow(lambda, k) * Math.exp(-lambda) / Factorial.compute(k);
	}

/**
* Static method to compute the cumulative probability for a single trial for a 'k' point in the cumulative density function ( cdf ) according the values passed as parameters. <p>
* @param k
* An integer value equal or greater than zero.
* <p>
* @param lambda
* A double floating-point value greater than zero.
* <p>
* @return Cumulative probability value.
*
*/
public static double cu(int k, double lambda)
{
assert(lambda > 0.0): "PoissonDistribution -> cu(...): Bad parameter; lambda must be greater than zero.";
		assert(k >= 0): "PoissonDistribution -> cu(...): Bad parameter; 'k' must be greater or equal than zero.";
double _cu = 0.0;
for(int i = 0; i <= k; i++)
{
_cu += pr(i, lambda);
}
return _cu;
		}

/**
* Static method to compute the hole probability mass function ( pmf ) for a Poisson distribution acording the value passed as parameter. <p>
* The length of the returned array can be bigger or smaller depending on the parameter value. <p>
* @param lambda
* A double floating-point value greater than zero.
* <p>
* @return Probability mass function according the value passed as parameter.
*
*/
public static double[] pmf(double lambda)
{
LinkedList<Double> list = new LinkedList<>();
double _p;
double sum = 0.0;
for(int k = 0; k < PascalDistribution.MAX_ITERATIONS; k++)
{
_p = pr(k, lambda);
list.add(_p);
sum += _p;
	if(1.0-sum < EPSILON) break;
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
* Static method to compute the hole probability mass function ( pmf ) for a Poisson distribution acording the value passed as parameter. <p>
* The returned pmf will be stored in a <code>imr.math.matrix.Vector</code> object. <p>
* The size of the returned vector can be bigger or smaller depending on the parameter value. <p>
* @param lambda
* A double floating-point value greater than zero.
* <p>
* @return Probability mass function according the value passed as parameter stored in a <code>Vector</code> object.
*
*/
public static Vector pmfVector(double lambda)
{
double[] _pmf = pmf(lambda);
int n = _pmf.length;
Vector v = new Vector(n);
for(int i = 0; i < n; i++) v.set(i, _pmf[i]);
return v;
}

/**
* Static method to compute the hole cumulative density function ( cdf ) for a Poisson distribution acording the value passed as parameter. <p>
* The length of the returned array can be bigger or smaller depending on the parameter value. <p>
* @param lambda
* A double floating-point value greater than zero.
* <p>
* @return Cumulative density function according the value passed as parameter.
*
*/
public static double[] cdf(double lambda)
{
double[] _pmf = pmf(lambda);
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
* Static method to compute the hole cumulative density function ( cdf ) for a Poisson distribution acording the value passed as parameter. <p>
* The computed cdf will be returned as a <code>imr.math.matrix.Vector</code> object. <p>
* The size of the returned vector can be bigger or smaller depending on the parameter value. <p>
* @param lambda
* A double floating-point value greater than zero.
* <p>
* @return Cumulative density function according the value passed as parameter stored in a <code>Vector</code> object.
*
*/
public static Vector cdfVector(double lambda)
{
double[] _cdf = cdf(lambda);
int n = _cdf.length;
Vector v = new Vector(n);
for(int i = 0; i < n; i++) v.set(i, _cdf[i]);
return v;
}

/**
* Static method to get the mean for a concrete Poisson distribution according some parameter. <p>
* @param lambda
* A double floating-point value greater than zero.
* <p>
* @return Mean value for the Poisson distribution according the value passed as parameter.
*
*/
public static double mean(double lambda)
{
	assert(lambda > 0.0): "PoissonDistribution -> mean(...): Bad parameter; lambda must be greater than zero.";
	return lambda;
}

/**
* Static method to get the variance for a concrete Poisson distribution according some parameter. <p>
* @param lambda
* A double floating-point value greater than zero.
* <p>
* @return Variance value for the Poisson distribution according the value passed as parameter.
*
*/
public static double var(double lambda)
{
assert(lambda > 0.0): "PoissonDistribution -> var(...): Bad parameter; lambda must be greater than zero.";
		return lambda;
}


// Private constructor so that this class cannot be instantiated
private PoissonDistribution() {}

// Symbolic constant declared as package scope just for convinience
static final double EPSILON = 1E-9;
}

// END

