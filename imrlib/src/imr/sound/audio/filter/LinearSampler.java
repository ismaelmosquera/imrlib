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
* LinearSampler.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.sound.audio.filter;

import imr.util.iArray;

/**
* The <code>LinearSampler</code> class has methods to perform interpolation and decimation.
* <p>
* These methods work just with signals composed by pure independent sinusoids.
* <p>
* Interpolation expands the signal and decimation contracts it.
* <p>
* @author Ismael Mosquera Rivera
*
*/
public class LinearSampler
{

/**
* This static method decimates ( contracts ) a signal composed by pure independent sinosoids.
* <p>
* The result is like we oversample the incoming signal.
* <p>
* @param x Signal to be decimated.
* @param nsamples Number of samples to decimate for each interval.
* <p>
* @return The decimated signal.
*
*/
public static float[] decimate(float[] x, int nsamples)
{
	int k_order = nsamples +1;
int size = (x.length%k_order == 0) ? x.length/k_order : x.length/k_order+1;
float[] y = new float[size];
for(int i = 0; i < size; i++)
{
y[i] = x[k_order*i];
}
if((y.length % 4) != 0)
{
	int new_size = y.length - (y.length % 4);
	y = (float[])iArray.resize(y, new_size);
}
return y;
}

/**
* This static method interpolates a number of samples between each interval of two samples over a signal composed by pure independent sinusoids.
* <p>
* The resulting effect is like downsampling the incoming signal.
* <p>
* @param x The signal to be interpolated.
* @param nsamples Number of samples to interpolate for each interval.
* <p>
* @return The interpolated signal.
*
*/
public static float[] interpolate(float[] x, int nsamples)
{
if(x.length == 1 || x.length == 0 || x == null) return x;
int size = x.length;
float[] y = new float[nsamples*(size-1)+size];
int k_order = nsamples+1;
float factor = 1.0f/(float)k_order;
float step =0.0f;
y[0] = x[0];
for(int n = 1; n < size; n++)
{
step = (x[n]-x[n-1])*factor;
for(int i = k_order*(n-1)+1; i < k_order*n+1; i++)
{
y[i] = y[i-1]+step;
}
}
if((y.length % 4) != 0)
{
	int new_size = y.length - (y.length % 4);
	y = (float[])iArray.resize(y, new_size);
}
return y;
}

}

// END
