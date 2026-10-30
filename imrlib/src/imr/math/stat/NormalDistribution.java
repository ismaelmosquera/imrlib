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
* NormalDistribution.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

import imr.math.Range;
import imr.math.matrix.Vector;

import java.util.LinkedList;
import java.util.ListIterator;

/**
* This class implements some functionallity related to normal distributions. <p>
* Notation: N(m, v); where m = mean and v = variance. <p>
* A special case is when mean = 0 and variance = 1, where we call it a standard normal distribution or a Gaussian distribution. <p>
* When variance is not equal to 1, we scale using the standard deviation: scale_factor = 1.0/sqrt(var) to ensure that the pdf sum will be 1. <p>
* This class uses assertions; to enable them you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera rivera.
*
*/
public final class NormalDistribution
{

/**
* Static method to compute the probability for a given 'x'. <p>
* @param x
* A double floating-point value in the range [-infinity .. +infinity].
* <p>
* @param m
* A double floating-point value equal or greater than 0 to set the mean.
* <p>
* @param v
* A double floating-point value greater than 0 to set the variance.
* <p>
* @return Probability value according to the parameters.
*
*/
public static double pr(double x, double m, double v)
{
	assert(m >= 0.0): "NormalDistribution -> pr(...): Bad parameter; 'm' ( mean ) must be equal or greater than 0";
	assert(v > 0.0): "NormalDistribution -> pr(...): Bad parameter; 'v' ( var ) must be greater than 0.0";
return 1.0/Math.sqrt(2.0*Math.PI*v) * Math.exp(-Math.pow(x-m, 2)/(2.0*v));
}

/**
* Static method to compute the entire pdf for a given interval. <p>
* The width of the interval will be set around the mean value, for instance: <p>
* if mean = 2 and span = 4, interval = [0 .. 4]. <p>
* The 'step' parameter is just to get more resolution, to have a plot with a smooth curve; the increment will be 1/step so, for step = 1 there is no change. <p>
* Take in account that incrementing resolution will result in a sum greater than 1 for the pdf; to avoid it, we apply a normalization. <p>
* Anyway, you are free to rescale the pdf in order to get a plot with a taller peak in the resulting bell curve. <p>
* @param m
* A double floating-point value to set the mean.
* <p>
* @param v
* A double floating-point value to set the variance.
* <p>
* @param span
* A double floating-point value to set the width of the interval.
* <p>
* @param step
* An integer value greater than 0 ( set it to 1 for default behaviour ).
* <p>
* @return Computed pdf as a double floating-point array.
*
*/
public static double[] pdf(double m, double v, double span, int step)
{
	assert(step > 0): "NormalDistribution -> pdf(...): Bad parameter; 'step' must be greater than 0";
double h = span/2.0;
Range r = new Range(m-h, m+h);
double std = 1.0/Math.sqrt(v);
LinkedList<Double> list = new LinkedList<>();
double sum = 0.0;
double p = 0.0;
for(double x = r.getMin(); x <= r.getMax(); x += 1.0/(double)step)
{
p = pr(x, m, v)*std;
list.add(p);
sum += p;
}
int k = 0;
double[] _pdf = new double[list.size()];
ListIterator<Double> it = list.listIterator();
while(it.hasNext())
{
_pdf[k++] = it.next()/sum;
}
return _pdf;
}

/**
* Static method to compute the entire pdf for a given interval and return it as a <code>imr.math.matrix.Vector</code> object. <p>
* @param m
* A double floating-point value to set the mean.
* <p>
* @param v
* A double floating-point value to set the variance.
* <p>
* @param span
* A double floating-point value to set the width of the interval.
* <p>
* @param step
* An integer value greater than 0 ( set it to 1 for default behaviour ).
* <p>
* @return Computed pdf as a <code>Vector</code> object.
*
*/

public static Vector pdfVector(double m, double v, double span, int step)
{
double[] _pdf = pdf(m, v, span, step);
int n = _pdf.length;
Vector a = new Vector(n);
for(int i = 0; i < n; i++) a.set(i, _pdf[i]);
return a;
}

/**
* Static method to compute the entire cdf of a normal distribution for a given interval. <p>
* @param m
* A double floating-point value to set the mean.
* <p>
* @param v
* A double floating-point value to set the variance.
* <p>
* @param span
* A double floating-point value to set the width of the interval.
* <p>
* @param step
* An integer value greater than 0 ( set it to 1 for default behaviour ).
* <p>
* @return Computed cdf as a double floating-point array.
*
*/
public static double[] cdf(double m, double v, double span, int step)
{
	assert(step > 0): "NormalDistribution -> cdf(...): Bad parameter; 'step' must be greater than 0";
double[] _pdf = pdf(m, v, span, step);
int n = _pdf.length;
double[] _cdf = new double[n];
_cdf[0] = _pdf[0];
for(int k = 1; k < n; k++)
{
_cdf[k] = _cdf[k-1] + _pdf[k];
}
return _cdf;
}

/**
* Static method to compute the entire cdf for a given interval and return it as a <code>imr.math.matrix.Vector</code> object. <p>
* @param m
* A double floating-point value to set the mean.
* <p>
* @param v
* A double floating-point value to set the variance.
* <p>
* @param span
* A double floating-point value to set the width of the interval.
* <p>
* @param step
* An integer value greater than 0 ( set it to 1 for default behaviour ).
* <p>
* @return Computed cdf as a <code>Vector</code> object.
*
*/

public static Vector cdfVector(double m, double v, double span, int step)
{
double[] _cdf = cdf(m, v, span, step);
int n = _cdf.length;
Vector a = new Vector(n);
for(int i = 0; i < n; i++) a.set(i, _cdf[i]);
return a;
}


// Private constructor so that this class cannot be instantiated
private NormalDistribution() {}

}

// END
