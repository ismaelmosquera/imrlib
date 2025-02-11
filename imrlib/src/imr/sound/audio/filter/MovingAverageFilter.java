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

/**
* The <code>MovingAverageFilter</code> class has just one method to perform its task.
* <p>
* It works only with pure sinusoidal signals.
* <p>
* @author Ismael Mosquera Rivera
*
*/
public class MovingAverageFilter
{

/**
* This static method filters a signal composed by independent pure sinusoidals.
* <p>
* It performs average filtering over the signal passed as parameter according of the requested order.
* <p>
* @param x Signal to be filtered.
* @param order Order of the filter.
* <p>
* @return The filtered signal
*
*/
public static float[] filter(float[] x, int order)
{
	int k_order = order + 1;
	float factor = 1.0f / (float)k_order;
	float[] y = new float[x.length];
	for(int i = 0; i < k_order; i++) y[i] = 0.0f;
	for(int n = 0; n < k_order; n++)
	{
		for(int i = n; i >= 0; i--) y[n] += x[n-i] * factor;
	}

	for(int n = k_order; n < x.length; n++)
	{
	y[n] = x[n]*factor+(y[n-1]-x[n-k_order]*factor);
	}
	return y;
}

}

// END
