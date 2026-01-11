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
* LowPassFilter.java
*
* imr-lib
*
* Author:Ismael Mosquera Rivera
*/

package imr.sound.audio.filter;

import imr.util.iArray;
import imr.math.Range;

/**
* The <code>LowPassFilter</code> class implements a filter allowing to pass frequencies below a concrete cut-off frequency.
* <p>
* The desired filter kernel implementation is coded inside the protected <code>getFilter</code> method.
* <p>
* @see imr.sound.audio.filter.Filter base abstract class.
* @see imr.sound.audio.filter.Convolution implemented by spectrum product.
*
* @author Ismael Mosquera rivera
*
*/
public final class LowPassFilter extends Filter
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>LowPassFilter</code> object.
*
*/
public LowPassFilter()
{
super();
}

/**
* Constructor.
* <p>
* Makes a new instance for a <code>LowPassFilter</code> object.
* <p>
* @param sr sample rate.
* @param cf cut-off frequency ( for low and high pass filters ).
* @param band band pass or reject ( for band-pass or band-reject filters ).
* @param size filter size.
*
*/
public LowPassFilter(float sr, float cf, Range band, int size)
{
super(sr, cf, band, size);
}

/**
* Gets the computed filter kernel.
* <p>
* @param size actually, the size of the input signal to be filtered.
* <p>
* This parameter is not related with the filter size.
* <p>
* Anyway, you do not have to worry about it, since it is managed by the filter method in the <code>Filter</code> superclass.
* <p>
* We recommend you to pick up at the source code to better understand it.
* <p>
* @return computed low-pass filter.
*
*/
protected float[] getFilter(int size)
{
	if(!_mustUpdate) return h;
	float c_fc = 2.0f*(float)Math.PI*(_cutOffFrequency/_sampleRate);
	int h_len = _size;
	if(h_len % 2 == 0) h_len--;
	h = new float[h_len];
	int middle = (h_len-1)/2;
	for(int i = 0; i < h_len; i++)
	{
		h[i] = sinc(c_fc*(float)(-(i-middle)));
	}
	applyWindow(h);
float sum = 0.0f;
for(int i = 0; i < h.length; i++) sum += h[i];
for(int i = 0; i < h.length; i++) h[i] /= sum;
h = (float[])iArray.resize(h, size);
_mustUpdate = false;
return h;
}

}

// END
