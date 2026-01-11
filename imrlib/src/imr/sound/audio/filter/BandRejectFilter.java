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
* BandRejectFilter.java
*
* imr-lib
*
* Author:Ismael Mosquera Rivera
*/

package imr.sound.audio.filter;

import imr.util.iArray;
import imr.math.Range;

/**
* The <code>BandPassFilter</code> class implements a filter stopping to pass frequencies for a concrete band [MinHz .. MaxHz].
* <p>
* The desired filter kernel implementation is coded inside the protected <code>getFilter</code> method.
* <p>
* @see imr.sound.audio.filter.Filter base abstract class.
* @see imr.sound.audio.filter.Convolution implemented by spectrum product.
*
* @author Ismael Mosquera rivera
*
*/
public final class BandRejectFilter extends Filter
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>BandRejectFilter</code> object.
*
*/
public BandRejectFilter()
{
super();
}

/**
* Constructor.
* <p>
* Makes a new instance for a <code>BandRejectFilter</code> object.
* <p>
* @param sr sample rate.
* @param cf cut-off frequency ( for low and high pass filters ).
* @param band band pass or reject ( for band-pass or band-reject filters ).
* @param size filter size.
*
*/
public BandRejectFilter(float sr, float cf, Range band, int size)
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
	float c_fc0 = 2.0f*(float)Math.PI*(_band.getMin()/_sampleRate);
	float c_fc1 = 2.0f*(float)Math.PI*(_band.getMax()/_sampleRate);
	int h_len = _size;
	if(h_len % 2 == 0) h_len--;
	float[] h0 = new float[h_len];
	float[] h1 = new float[h_len];
	int middle = (h_len-1)/2;
	for(int i = 0; i < h_len; i++)
	{
		h0[i] = sinc(c_fc0*(float)(-(i-middle)));
		h1[i] = sinc(c_fc1*(float)(-(i-middle)));
	}
	applyWindow(h0);
	applyWindow(h1);
float sum0 = 0.0f;
float sum1 = 0.0f;
for(int i = 0; i < h_len; i++)
{
sum0 += h0[i];
sum1 += h1[i];
}
for(int i = 0; i < h_len; i++)
{
h0[i] /= sum0;
h1[i] /= sum1;
}
for(int i = 0; i < h_len; i++) h1[i] = -h1[i];
h1[middle] += 1.0f;
h = new float[h_len];
for(int i = 0; i < h_len; i++) h[i] = h0[i]+h1[i];
h = (float[])iArray.resize(h, size);
_mustUpdate = false;
return h;
}

}

// END
