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
* SpectralSynthesizer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.synthesis;

import imr.sound.audio.analysis.Fourier;
import imr.sound.audio.analysis.Spectrum;
import imr.sound.audio.window.Windowing;

/**
* The <code>SpectralSynthesizer</code> class synthesizes a spectrum into a floating point vector.
* <p>
* Windowing is necessary to perform spectral synthesis.
* <p>
* You can choose among the following window types:
* <ul>
* <li>BlackmanHarris92Window</li>
* <li>GaussianWindow</li>
* <li>HammingWindow</li>
* <li>TriangularWindow</li>
* </ul>
* <p>
* to apply windowing to the output signal.
* <p>
* Default: apply window = true
*
* @see imr.sound.audio.analysis.Spectrum
* @see imr.sound.audio.window.Windowing
* @see imr.sound.audio.window.WindowType
*
* @author Ismael Mosquera Rivera.
*/
public final class SpectralSynthesizer extends Windowing
{

/**
* Constructor.
* Makes a new instance for a <code>SpectralSynthesizer</code> object
*
*/
public SpectralSynthesizer()
{
	super();
	}

/**
* This method performs the spectral synthesis.
* @param spec Spectrum to be synthesize.
*
* @see imr.sound.audio.analysis.Spectrum
*
* @return A floating point vector result of the synthesis.
*
*/
public float[] synthesize(Spectrum spec)
{
	float[] x = Fourier.ifft(spec.getFFT());
	if(_applyWindow) applyWindow(x);
return x;
}

/**
* This method applies windowing to the floating point signal passed as parameter.
* <p>
* @param x Floating point vector to be windowing.
* <p>
* The window to be applied must be the same type of the one used in analysis time.
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
// normalize window
for(int i = 0; i < size; i++) w[i] /= max;
	}
	// invert window
	_window.invert(w);
	// just apply windowing
for(int i = 0; i < size; i++) x[i] *= w[i];
}

}

// END
