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
* GeometricDistribution.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

import imr.math.matrix.Vector;

import java.util.LinkedList;
import java.util.ListIterator;

/**
* This class implements a model for the geometric distribution to know the number of expected failures before having a success for k = 0, 1, 2 ... <p>
* Notation: G(p), where p is a real number in the range (0 .. 1] ( probability of success ). <p>
* This class uses assertions: to enable them you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class GeometricDistribution
{

/**
* Static method to get the probability of having 'k' failures before a success with probability 'p'. <p>
* @param k
* An integer value equal or greater than 0.
* <p>
* @param p
* A double floating-point value in the range (0 .. 1] ( probability of success ).
* <p>
* @return Computed probability value.
*
*/
public static double pr(int k, double p)
{
	assert(k >= 0): "GeometricDistribution -> pr(...): Bad parameter; 'k' must be equal or greater than 0";
	assert(p > 0.0 && p <= 1.0): "GeometricDistribution -> pr(...): Bad parameter; 'p' must be in the range (0 .. 1]";
	return Math.pow(1.0-p, k) * p;
}

/**
* Static method to get the cumulative probability of having 'k' failures for a given probability 'p' of success. <p>
* @param k
* An integer value equal or greater than 0.
* <p>
* @param p
* A double floating-point value in the range (0 .. 1] ( probability of success ).
* <p>
* @return Computed cumulative probability.
*
*/
public static double cu(int k, double p)
{
	assert(k >= 0): "GeometricDistribution -> cu(...): Bad parameter; 'k' must be equal or greater than 0";
	assert(p > 0.0 && p <= 1.0): "GeometricDistribution -> cu(...): Bad parameter; 'p' must be in the range (0 .. 1]";
	double s = 0.0;
	for(int i = 0; i <= k; i++)
	{
	s += pr(i, p);
	}
	return s;
}

/**
* Static method to compute the entire probability mass function ( pmf ) for a geometric distribution. <p>
* @param p
* A double floating-point value in the range (0 .. 1] ( probability of success ).
* <p>
* @return Computed pmf following a geometric distribution stored in a double floating-point array.
*
*/
public static double[] pmf(double p)
{
LinkedList<Double> list = new LinkedList<>();
double sum = 0.0;
double _p = 0.0;
for(int k = 0; k < PascalDistribution.MAX_ITERATIONS; k++)
{
_p = pr(k, p);
list.add(_p);
sum += _p;
if(1.0-sum < PoissonDistribution.EPSILON) break;
}
int q = 0;
double[] _pmf = new double[list.size()];
ListIterator<Double> it = list.listIterator();
while(it.hasNext())
{
_pmf[q++] = it.next();
}
return _pmf;
}

/**
* Static method to compute the entire probability mass function ( pmf ) for a geometric distribution. <p>
* @param p
* A double floating-point value in the range (0 .. 1] ( probability of success ).
* <p>
* @return Computed pmf following a geometric distribution stored in a <code>Vector</code> object.
*
*/
public static Vector pmfVector(double p)
{
double[] _pmf = pmf(p);
int n = _pmf.length;
Vector v = new Vector(n);
for(int i = 0; i < n; i++) v.set(i, _pmf[i]);
return v;
}

/**
* Static method to compute the entire cumulative density function ( cdf ) for a geometric distribution. <p>
* @param p
* A double floating-point value in the range (0 .. 1] ( probability of success ).
* <p>
* @return Computed cdf following a geometric distribution stored in a double floating-point array.
*
*/
public static double[] cdf(double p)
{
double[] _pmf = pmf(p);
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
* Static method to compute the entire cumulative density function ( cdf ) for a geometric distribution. <p>
* @param p
* A double floating-point value in the range (0 .. 1] ( probability of success ).
* <p>
* @return Computed cdf following a geometric distribution stored in a <code>Vector</code> object.
*
*/
public static Vector cdfVector(double p)
{
double[] _cdf = cdf(p);
int n = _cdf.length;
Vector v = new Vector(n);
for(int i = 0; i < n; i++) v.set(i, _cdf[i]);
return v;
}

/**
* Static method to compute the mean for a geometric distribution. <p>
* @param p
* A double floating-point value in the range (0 .. 1] ( probability of success ).
* <p>
* @return Computed mean value.
*
*/
public static double mean(double p)
{
assert(p > 0.0 && p <= 1): "GeometricDistribution -> mean(...): Bad parameter; 'p' must be in the range (0 .. 1]";
return (1.0-p) / p;
}

/**
* Static method to compute the variance for a geometric distribution. <p>
* @param p
* A double floating-point value in the range (0 .. 1] ( probability of success ).
* <p>
* @return Computed variance value.
*
*/
public static double var(double p)
{
assert(p > 0.0 && p <= 1): "GeometricDistribution -> var(...): Bad parameter; 'p' must be in the range (0 .. 1]";
return (1.0-p) / (p*p);
}


// Private constructor so that this class cannot be instantiated
private GeometricDistribution() {}

}

// END
