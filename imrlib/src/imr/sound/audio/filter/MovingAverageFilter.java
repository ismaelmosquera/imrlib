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
* MovingAverageFilter.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.filter;

import imr.util.CircularBuffer;

/**
* This class implements a Moving Average filter.
* <p>
* In such a filter, the output is the average sum of the current incoming sample and the n previous samples,
* <p>
* where n is the order of the filter.
* <p>
* For example, if the order of the filter is 2 and x the input signal and y the output signal
* <p>
* <code>y[n] = ( x[n]+x[n-1]+x[n-2] ) / 3</code>
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class MovingAverageFilter
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>MovingAverageFilter</code> object.
* <p>
* @param order
* Order of the filter.
*
*/
public MovingAverageFilter(int order)
{
assert (order > 0): "MovingAverageFilter constructor: Error, order must be greater than 0";
_order = order;
_flag = false;
_n = _order+1;
_a_constant = 1.0f/(float)_n;
_buffer = new CircularBuffer<Float>(_n);
}

/**
* Filters the signal passed as parameter.
* <p>
* @param x
* Input signal.
* <p>
* @return filtered signal.
*
*/
public float[] filter(float[] x)
{
if(x == null) return null;
float[] y = new float[x.length];
if(!_flag)
{
	_flag = true;
Float[] initialData = new Float[_order+1];
for(int i = 0; i < _order; i++) initialData[i] = 0.0f;
initialData[_order] = x[0];
_buffer.write(initialData);
}
else
{
	_buffer.set(x[0]);
}
// filter first sample
float s = 0.0f;
for(int i = 0; i < _n; i++)
{
s += _buffer.pick(i);
}
y[0] = s * _a_constant;
// filter all
for(int i = 1; i < x.length; i++)
{
	y[i] = nextFilteredSample(x[i]);
}
return y;
}

/**
* Resets the filter to its original state.
*
*/
public void reset()
{
_flag = false;
_buffer.reset();
}

// Method to compute the next filtered sample.
private float nextFilteredSample(float x)
{
_buffer.set(x);
float s = 0.0f;
for(int i = 0; i < _n; i++)
{
	s += _buffer.pick(i);
}
s *= _a_constant;
return s;
}


private boolean _flag;
private int _order;
private int _n;
private float _a_constant;
private CircularBuffer<Float> _buffer;

}

// END
