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
* Window.java
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.window;

/**
* The <code>Window</code> abstract class is the base class from you can derive any subclass.
* <p>
* It has just one abstract method to implement your concrete window type.
* <p>
* There are one public already implemented method to invert any window type.
* <p>
* Currently, we have the following subclasses already available:
* <ul>
* <li>BlackmanHarris92Window</li>
* <li>GaussianWindow</li>
* <li>HammingWindow</li>
* <li>TriangularWindow</li>
* </ul>
*
* <p>
* Windowing is necessary to perform spectral analysis
*
* @see imr.sound.audio.analysis.SpectralAnalyzer
* @see imr.sound.audio.window.Windowing
* @see imr.sound.audio.window.WindowType
*
* @author Ismael Mosquera Rivera
*
*/
public abstract class Window
{

/**
* Constructor.
*
*/
public Window() {}

/**
* This abstract method must be implemented in any subclass in order to build the concrete window.
* <p>
* @param w Floating point vector to be filled with the window data.
*
*/
public abstract void get(float[] w);

/**
* Method suitable to invert a window of any kind.
* <p>
* You can call this method from any subclass of <code>Window</code>
* @param w Window to be inverted
*
*/
public void invert(float[] w)
{
	int size = w.length;
	int half_size = (int)((double)size/2.0);
	if(size % 2 != 0)
	{
		w[size-1] = 0.0f;
	}
	for(int i = 0; i < half_size; i++)
	{
		if(w[i] != 0.0f) w[i] = w[size-(i+1)] = 1.0f / w[i];
	}
}

}

// END
