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
* Convolution.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.filter;

import imr.util.ComplexNumber;
import imr.sound.audio.analysis.Fourier;


/**
* The <code>Convolution </code> class implements fast convolution in time domain by multiplying the spectrums of x[n] and h[n] in frequency domain.
* <p>
* Where x[n] is the signal to convolve with h[n] impulse response.
* This class uses a FFT implementation in order to increase the computational efficiency of the algorithm.
* <p>
* @author Ismael Mosquera Rivera
*
*/
public final class Convolution
{


/**
* Static method to perform convolution.
* <p>
* @param x incoming signal
* @param h impulsional response of the filter.
* <p>
* @return convolved ( filtered ) signal.
*
*/
public static float[] convolve(float[] x, float[] h)
{
	assert (x.length == h.length): "Bad parameters: x.length must be equal to h.length";

/*
* Since the product of 2 signals in time domain is the convolution
* of their spectrums in frequency domain,
* the product of the spectrums of 2 signals in frequency domain
* is their convolution in time domain.
*/

ComplexNumber[] spec_x = Fourier.fft(x);
if(_update)
{
H = Fourier.fft(h);
_update = false;
}
ComplexNumber[] z = new ComplexNumber[spec_x.length];
int size = spec_x.length-1;
for(int i = 0; i < size; i++) z[i] = spec_x[i].mul(H[i]);
z[size] = new ComplexNumber(0.0f, spec_x[size].getImag());

return Fourier.ifft(z);
}

/**
* This method tells whether the spectra of the impulse response ( h[] ) must be computed or not.
*
*/
public static void update()
{
_update = true;
}


private static boolean _update = true;
private static ComplexNumber[] H = null;


// private constructor so that this class cannot be instantiated
private Convolution()
{}

}

// END
