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
* Filter.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.filter;

import imr.util.Range;
import imr.sound.audio.window.Window;
import imr.sound.audio.window.HammingWindow;


/**
* The <code>Filter</code> abstract class acts as a base class from you can derive concrete filter classes.
* <p>
* This class implements most of the needed functionallyty. Just implement its protected <code>getFilter</code> abstract method
* <p>
* in order to get the desired filter kernel.
* <p>
* The idea is to build FIR filters using convolution, that is, convolving the input signal with the impulsional filter response.
* <p>
* Convolution is coded using FFT in order to increase the computational efficiency.
*
* @see imr.sound.audio.filter.Convolution The convolution algorithm performed in frequency domain.
*
* @author Ismael Mosquera Rivera
*
*/
public abstract class Filter
{

/**
* Constructor.
* <p>
* Generic constructor. Calls the constructor with default parameters.
*
*/
public Filter()
{
this(44100.0f, 1000.0f, new Range(400.0f, 700.0f), 512);
}

/**
* Constructor.
* <p>
* @param sr sample rate.
* @param cf cut off frequency ( used in low and high pass filters )
* @param band vand pass or reject.
* @param size filter size.
*
*/
public Filter(float sr, float cf, Range band, int size)
{
_sampleRate = sr;
_cutOffFrequency = cf;
_band = band;
_size = size;
_currentSize = -1;
_mustUpdate = true;
h = null;
_window = new HammingWindow();
}

/**
* Gets the band pass or reject for this filter.
* <p>
* @see imr.util.Range the <code>Range</code> class.
* <p>
* @return a <code>Range</code> object with the information band.
*
*/
public Range getBand()
{
return _band;
}

/**
* Gets the cut off frequency for this filter.
* <p>
* The cut off frequency attribute can be used in low and high pass filters.
* <p>
* @return cut off frequency value.
*
*/
public float getCutOffFrequency()
{
return _cutOffFrequency;
}

/**
* Gets the sample rate for this filter.
* <p>
* Knowing the sample rate is needed to compute the cut off frequency coefficient.
* <p>
* @return sample rate.
*
*/
public float getSampleRate()
{
return _sampleRate;
}

/**
* Gets the size of this filter.
* <p>
* Take in account that the filter size must be less or equal than the size of the incoming signal to be filtered.
* <p>
* @return filter size
*
*/
public int getSize()
{
return _size;
}

/**
* Sets the band for this filter.
 * <p>
 * The band can be used in band pass or reject filters.
 * <p>
 * @param r Range object with the band values.
 *
 */
public void setBand(Range r)
{
_band = r;
_mustUpdate = true;
}

/**
* Sets the cut off frequency for this filter.
* <p>
* The cut off frequency value can be used in low and high pass filters.
* <p>
* @param cf cut off frequency value.
*
*/
public void setCutOffFrequency(float cf)
{
_cutOffFrequency = cf;
_mustUpdate = true;
}

/**
* Sets the sample rate for this filter.
* <p>
* Knowing the sample rate is needed to compute the cut off frequency coefficient.
* <p>
* @param sr sample rate.
*
*/
public void setSampleRate(float sr)
{
_sampleRate = sr;
_mustUpdate = true;
}

/**
* Sets the size for this filter.
* <p>
* Take in account that the filter size must be less or equal of the incoming signal size.
* <p>
* @param size filter size
*
*/
public void setSize(int size)
{
_size = size;
_mustUpdate = true;
}

/**
* This method performs filtering to the incoming signal.
* <p>
* @param x incoming signal.
* <p>
* @return filtered signal.
*
*/
public float[] filter(float[] x)
{
	checkFilterSize(x.length);
	return Convolution.convolve(x, getFilter(x.length));
}


/**
* This method must be implementted in subclasses of <code>Filter</code> in order to get the desired filter kernel.
* <p>
* @param size size of the incoming signal to be filtered.
* <p>
* @return filter kernel.
*
*/
protected abstract float[] getFilter(int size);

/*
* This method computes the sinc function.
*/
protected float sinc(float x)
{
if(x == 0.0f) return 1.0f;
double z = (double)x;
return (float)(Math.sin(z)/z);
}

/*
* This method applies windowing to the floating point signal passed as parameter.
* <p>
* @param x Floating point vector to be windowing.
*
*/
protected void applyWindow(float[] x)
{
	int size = x.length;
	int half_size = (int)((double)size/2.0);
float[] w = new float[size];
_window.get(w);
	// just apply windowing
for(int i = 0; i < size; i++) x[i] *= w[i];
}

/*
* This method checks whether the current filter size is correct.
*/
private void checkFilterSize(int size)
{
	if(size != _currentSize)
	{
		_currentSize = size;
		_mustUpdate = true;
	}
if(size < _size)
{
	_size = size;
	_mustUpdate = true;
}
if(_mustUpdate) Convolution.update();
}


protected boolean _mustUpdate;
protected float _cutOffFrequency;
protected Range _band;
protected float _sampleRate;
protected float[] h;
protected int _size;

private int _currentSize;
private Window _window;
}

// END
