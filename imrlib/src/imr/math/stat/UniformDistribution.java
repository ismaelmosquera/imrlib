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
* UniformDistribution.java
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

import java.util.Arrays;
import java.util.HashMap;

/**
* The <code>UniformDistribution</code> class computes equaly probable distributions.
* <ul>
* <li>Absolute - abs</li>
* <li>Probability density function - pdf</li>
* <li>Cumulative density function - cdf</li>
* </ul>
* This class uses assertions; to enable them, you can use the '-ea' modifier: <p>
* <code>java -ea MyApp</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
@SuppressWarnings("unchecked")
public final class UniformDistribution
{

/**
* Constructor. <p>
* Makes a new instance for a <code>UniformDistribution</code> object.
*
*/
public UniformDistribution()
{
	_mean = 0.0;
	_var = 0.0;
	_dev = 0.0;
	_size = 0;
	_abs = null;
	_pdf = null;
	_cdf = null;
	_map = new HashMap<>();
}

/**
* Static method to get a integer array with 'n' samples randomly choosed between 'min' and 'max' both included. <p>
* @param nSamples
* A positive integer value greater than 0.
* <p>
* @param min
* An integer value for the minimum value in the wanted range.
* <p>
* @param max
* An integer value for the maximum value in the wanted range.
* <p>
* @return computed integer array having the required samples.
*
*/
public static int[] samples(int nSamples, int min, int max)
{
assert(nSamples > 0): "UniformDistribution -> samples(...): Bad parameter; number of samples to compute must be greater than 0";
int[] _samples = new int[nSamples];
for(int i = 0; i < nSamples; i++)
{
_samples[i] = RandomNumberGenerator.generate(min, max);
}
return _samples;
}

/**
* Computes all density functions according to the data array passed as parameter. <p>
* You can retrieve the result of your interest by calling some other functions below. <p>
* @param data
* An integer array having the required samples.
*
*/
public void compute(int[] data)
{
_size = data.length;
assert(_size > 0): "UniformDistribution -> compute(...): Bad parameter.";
// fill hash map
_map.clear();
for(int i = 0; i < _size; i++)
{
	_mean += (double)data[i];
	_var += (double)(data[i]*data[i]);
if(_map.containsKey(data[i]))
{
	_map.replace(data[i], _map.get(data[i])+1);
}
else
{
	_map.put(data[i], 1);
}
}
// Compute mean, variance and standard deviation
_mean /= (double)_size;
_var /= (double)_size;
_var -= (_mean*_mean);
_dev = Math.sqrt(_var);
// compute density functions
Object[] _k = _map.keySet().toArray();
int[] keys = new int[_k.length];
for(int i = 0; i < _k.length; i++) keys[i] = (int)_k[i];
Arrays.sort(keys);
_abs = new Pair[keys.length];
_cdf = new Pair[keys.length];
_pdf = new Pair[keys.length];
int value = 0;
for(int i = 0; i < keys.length; i++)
{
value = _map.get(keys[i]);
_abs[i] = new Pair<>(keys[i], value);
_pdf[i] = new Pair<>(keys[i], (double)value/(double)_size);
_cdf[i] = (i == 0) ? new Pair<>(_pdf[i].getFirst(), _pdf[i].getSecond()) : new Pair<>(_pdf[i].getFirst(), _pdf[i].getSecond()+_cdf[i-1].getSecond());
}
}

/**
* Gets the absolute density function. <p>
* The density function is returned as an array of pairs where the event is in its first value and the number of times that it appears <p>
* for this experiment is in its second value. <p>
* @return An array of pair objects having the concrete absolute distribution.
* @see imr.util.Pair for more information.
*
*/
public Pair<Integer, Integer>[] abs()
{
assert(_size >0): "UniformDistribution -> abs(): No data set yet.";
return _abs;
}

/**
* Gets the cumulative density function. <p>
* The density function is returned as an array of pairs where the event is in its first value and the cumulative probability for this experiment in its second value. <p>
* @return An array of pair objects having the concrete cumulative distribution.
* @see imr.util.Pair for more information.
*
*/
public Pair<Integer, Double>[] cdf()
{
assert(_size > 0): "UniformDistribution -> cdf(): No data set yet.";
return _cdf;
}

/**
* Gets the probability density function. <p>
* The density function is returned as an array of pairs where the event is in its first value and its probability for this experiment in its second value. <p>
* @return An array of pair objects having the concrete probability density distribution.
* @see imr.util.Pair for more information.
*
*/
public Pair<Integer, Double>[] pdf()
{
assert(_size > 0): "UniformDistribution -> pdf(): No data set yet.";
return _pdf;
}

/**
* Gets the number of times that a concrete event appears in a given experiment. <p>
* @param k
* Indentifier for a concrete event.
* <p>
* @return Number of times that appears for a concrete experiment.
*
*/
public int abs(int k)
{
assert(_size >0): "UniformDistribution -> abs(...): No data set yet.";
	return (_map.containsKey(k)) ? _map.get(k) :  0;
}

/**
* Gets the probability in the range [0 .. 1] for the event with the identifier passed as parameter. <p>
* @param k
* Identifier for a concrete event.
* <p>
* @return Probability for the event to appear in a concrete experiment.
*
*/
public double pdf(int k)
{
assert(_size > 0): "UniformDistribution -> pdf(...): No data set yet.";
	return (_map.containsKey(k)) ? (double)_map.get(k)/(double)_size :  0.0;
}

/**
* gets the value for the cumulative distribution of a concrete event. <p>
* @param k
* Identifier for a concrete event.
* <p>
* @return Cumulative value for a concrete event to appear in a concrete experiment.
*
*/
public double cdf(int k)
{
assert(_size > 0): "UniformDistribution -> cdf(...): No data set yet.";
if(k < _cdf[0].getFirst()) return 0.0;
if(k > _cdf[_cdf.length-1].getFirst()) return _cdf[_cdf.length-1].getSecond();
if(k == _cdf[0].getFirst()) return _cdf[0].getSecond();
// find
double retval = 0.0;
for(int i = 1; i < _cdf.length; i++)
{
	if(k == _cdf[i].getFirst())
	{
		retval = _cdf[i].getSecond();
		break;
	}
	else if(k < _cdf[i].getFirst())
	{
		retval = _cdf[i-1].getSecond();
		break;
	}
}
return retval;
}

/**
* Gets the pdf as a <code>Vector</code> object with its values ( probabilities [0 .. 1] ) arranged in increased order according event keys. <p>
* @return Probability density function values.
*
*/
public Vector pdfValues()
{
	assert(_size > 0): "UniformDistribution -> pdfValues(): No distribution computed yet.";
	int n = _pdf.length;
	Vector p = new Vector(n);
	for(int i = 0; i < n; i++) p.set(i, _pdf[i].getSecond());
	return p;
}

/**
* Gets the cdf as a <code>Vector</code> object with its values ( probabilities [0 .. 1] ) arranged in increased order according event keys. <p>
* @return Cumulative density function values.
*
*/
public Vector cdfValues()
{
assert(_size > 0): "UniformDistribution -> cdfValues(): No distribution computed yet.";
		int n = _cdf.length;
		Vector v = new Vector(n);
		for(int i = 0; i < n; i++)
		{
		v.set(i, _cdf[i].getSecond());
		}
		return v;
}

/**
* This method gets the identifier of the class having the median of this distribution.
* <p>
* @return Class identifier having the median value for this distribution.
*
*/
public int medianID()
{
	assert(_size > 0): "UniformDistribution -> cdf(...): No data set yet.";
Pair<Integer, Integer>[] abs = sortAbs();
int n = abs.length;
if(n%2 == 0) // even
{
	double m = median();
	// find median identifier
	for(int k = 0; k < abs.length; k++)
	{
	if(abs[k].getSecond() == m)
	{
		n = k;
		break;
	}
	else if(abs[k].getSecond() > m)
	{
		int p1 = k;
		int p0 = k-1;
		double d0 = m-abs[p0].getSecond();
		double d1 = abs[p1].getSecond() - m;
		n = (d0 < d1) ? p0 : p1;
		break;
	}
	}
}
else // odd
{
	n /= 2;
}
return abs[n].getFirst();
}

/**
* This method finds the statistic median for this experiment. <p>
* Such a value is the mid point in the distribution, that is, half of the classes are equal or less and the other half are equal or greater than it according the number of occurrences.
* <p>
* @return Found median as a double value.
*
*/
public double median()
{
	assert(_size > 0): "UniformDistribution -> median(): No distribution computed yet.";
assert(_abs.length > 1): "UniformDistribution -> median(): Bad length for the absolute distribution; it must be greater than 1";
double retval = 0.0;
Pair<Integer, Integer>[] abs = sortAbs();
int n = abs.length;
if(n % 2 == 0) // even
{
	int i = (int)(n/2);
	int j = i-1;
	retval = (double)(abs[i].getSecond() + abs[j].getSecond()) / 2.0;
}
else // odd
{
retval = (double)abs[(int)(n/2)].getSecond();
}
return retval;
}

/**
* Computes the mode, that is, the event having major number of occurrences for this experiment.
* <p>
* @return Identifier ( key ) for the event having more occurrences than the other ones.
*
*/
public int mode()
{
	assert(_size > 0): "UniformDistribution -> mode(): No distribution computed yet.";
	int n = _abs.length;
	Pair<Integer, Integer> max = _abs[0];
	for(int i = 1; i < n; i++)
	{
	if(max.getSecond() < _abs[i].getSecond()) max = _abs[i];
	}
	return max.getFirst();
}

/**
* Method to get the mean for this uniform distribution.
* <p>
* @return Mean value.
*
*/
public double mean()
{
	assert(_size > 0): "UniformDistribution -> mean(): NO distribution computed yet.";
	return _mean;
}

/**
* Method to get the variance for this uniform distribution.
* <p>
* @return Variance value.
*
*/
public double var()
{
	assert(_size > 0): "UniformDistribution -> var(): NO distribution computed yet.";
	return _var;
}

/**
* Method to get the standard deviation for this uniform distribution.
* <p>
* @return  Standard deviation value.
*
*/
public double dev()
{
	assert(_size > 0): "UniformDistribution -> dev(): NO distribution computed yet.";
	return _dev;
}

/**
* Gets the number of samples for this experiment.
* <p>
* @return number of samples ( size ) for this experiment.
*
*/
public int samples()
{
return _size;
}

/**
* Method to print to the console the absolute density function. <p>
* Notice that the values in a <code>Pair</code> object are stored in its first and second values.
*
*/
public void printAbs()
{
Pair.print(_abs);
}

/**
* Method to print to the console the probability density function. <p>
* Notice that the values in a <code>Pair</code> object are stored in its first and second values.
*
*/
public void printPdf()
{
Pair.print(_pdf);
}

/**
* Method to print to the console the cumulative density function. <p>
* Notice that the values in a <code>Pair</code> object are stored in its first and second values.
*
*/
public void printCdf()
{
Pair.print(_cdf);
}


/*
* Private method to sort the vector of absolute frequencies.
* to do this task, the bubble sort algorithm will be fine.
*/
private Pair<Integer, Integer>[] sortAbs()
{
assert(_size > 0): "UniformDistribution -> private sortAbs(): No distribution computed yet.";
int n = _abs.length;
Pair<Integer, Integer>[] abs = new Pair[n];
for(int i = 0; i < n; i++) abs[i] = new Pair<Integer, Integer>(_abs[i].getFirst(), _abs[i].getSecond());
Pair<Integer, Integer> tmp = null;
for(int i = 0; i < n-1; i++)
{
for(int j = i+1; j < n; j++)
{
	if(abs[i].getSecond() > abs[j].getSecond())
	{
		// swap
		tmp = abs[i];
		abs[i] = abs[j];
		abs[j] = tmp;
	}
}
}
return abs;
}


private int _size; // number of samples
private double _mean; // mean value
private double _var; // variance value
private double _dev; // standard deviation value

private Pair<Integer, Integer>[] _abs; // absolute probability function
private Pair<Integer, Double>[] _pdf; // probability density function
private Pair<Integer, Double>[] _cdf; // cumulative density function

private HashMap<Integer, Integer> _map; // absolute probability storage

}

// END
