/*
 * Copyright (c) 2023-2024 Ismael Mosquera Rivera
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
* SpectralAnalyzer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.analysis;

import imr.util.iArray;
import imr.sound.audio.window.Windowing;

/**
* The <code>SpectralAnalyzer</code> class analyzes a floating point vector
* and returns a <code>Spectrum</code> object.
* The spectrum keeps the result of the analyzis.
* <p>
* Windowing is necessary to perform spectral analysis.
* <p>
* You can choose among the following window types:
* <ul>
* <li>BlackmanHarris92Window</li>
* <li>GaussianWindow</li>
* <li>HammingWindow</li>
* <li>TriangularWindow</li>
* </ul>
* <p>
* to apply windowing to the input signal.
* <p>
* Default: apply window = true
*
* @see imr.sound.audio.analysis.Spectrum
* @see imr.sound.audio.window.Windowing
* @see imr.sound.audio.window.WindowType
*
* @author Ismael Mosquera Rivera.
*/
public final class SpectralAnalyzer extends Windowing
{

/**
* Constructor.
* Makes a new instance for a <code>SpectralAnalyzer</code> object
* <p>
* default sample rate = 44100 Hz.
*
*/
public SpectralAnalyzer()
{
	this(44100.0f);
	}

/**
* Constructor.
* <p>
* Makes a new instance of an <code>SpectralAnalyzer</code> object.
* <p>
* @param sampleRate Sample rate.
*
*/
public SpectralAnalyzer(float sampleRate)
{
	super();
_sampleRate = sampleRate;
}

/**
* This method performs the spectral analysis.
* @param v Floating point vector to be analyzed.
*
* @see imr.sound.audio.analysis.Spectrum
*
* @return A <code>Spectrum</code> object with the analyzed information.
*
*/
public Spectrum analyze(float[] v)
{
	float[] z = (float[])iArray.clone(v);
	if(_applyWindow) applyWindow(z);
Spectrum spec = new Spectrum(_sampleRate);
spec.compute(z);
return spec;
}

/**
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
	float max = w[half_size];
	if(max > 1.0f)
	{
// normalize and apply window
for(int i = 0; i < size; i++)
{
	w[i] /= max;
	x[i] *= w[i];
}
	}
	else
	{
	// just apply windowing
for(int i = 0; i < size; i++) x[i] *= w[i];
}
}


private float _sampleRate;

}

// END
