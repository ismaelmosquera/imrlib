/*
 * Copyright (c) 2024 Ismael Mosquera Rivera
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
* DensityFunction.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math.stat;

import imr.util.Pair;
import imr.math.matrix.Vector;

import java.util.Arrays;
import java.util.HashMap;

/**
* The <code>DensityFunction</code> class computes just a few density functions.
* <ul>
* <li>Absolute - abs</li>
* <li>Probability density function - pdf</li>
* <li>Cumulative density function - cdf</li>
* </ul>
*
* @author Ismael Mosquera Rivera.
*
*/
@SuppressWarnings("unchecked")
public final class DensityFunction
{

/**
* Constructor. <p>
* Makes a new instance for a <code>DensityFunction</code> object.
*
*/
public DensityFunction()
{
	_size = 0;
	_abs = null;
	_pdf = null;
	_cdf = null;
	_map = new HashMap<>();
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
assert(_size > 0): "DensityFunction -> compute(...): Bad parameter.";
// fill hash map
_map.clear();
for(int i = 0; i < _size; i++)
{
if(_map.containsKey(data[i]))
{
	_map.replace(data[i], _map.get(data[i])+1);
}
else
{
	_map.put(data[i], 1);
}
}
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
assert(_size >0): "DensityFunction -> abs(): No data set yet.";
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
assert(_size > 0): "DensityFunction -> cdf(): No data set yet.";
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
assert(_size > 0): "DensityFunction -> pdf(): No data set yet.";
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
assert(_size >0): "DensityFunction -> abs(...): No data set yet.";
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
assert(_size > 0): "DensityFunction -> pdf(...): No data set yet.";
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
assert(_size > 0): "DensityFunction -> cdf(...): No data set yet.";
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
	assert(_size > 0): "DensityFunction -> pdfValues(): No distribution computed yet.";
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
assert(_size > 0): "DensityFunction -> cdfValues(): No distribution computed yet.";
		int n = _cdf.length;
		Vector v = new Vector(n);
		for(int i = 0; i < n; i++)
		{
		v.set(i, _cdf[i].getSecond());
		}
		return v;
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
	assert(_size > 0): "DensityFunction -> median(): No distribution computed yet.";
assert(_abs.length > 1): "DensityFunction -> median(): Bad length for the absolute distribution; it must be greater than 1";
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
	assert(_size > 0): "DensityFunction -> mode(): No distribution computed yet.";
	int n = _abs.length;
	Pair<Integer, Integer> max = _abs[0];
	for(int i = 1; i < n; i++)
	{
	if(max.getSecond() < _abs[i].getSecond()) max = _abs[i];
	}
	return max.getFirst();
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
assert(_size > 0): "DensityFunction -> private sortAbs(): No distribution computed yet.";
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
private Pair<Integer, Integer>[] _abs; // absolute probability function
private Pair<Integer, Double>[] _pdf; // probability density function
private Pair<Integer, Double>[] _cdf; // cumulative density function

private HashMap<Integer, Integer> _map; // absolute probability storage

}

// END
