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
* Fourier.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera
*/

package imr.sound.audio.analysis;

import imr.util.ComplexNumber;

/**
* The <code>Fourier</code> class has static methods to perform FFT and IFFT.
* A Fourier analysis is useful to get the sinusoidal components from a periodic signal.
*
* @see imr.util.ComplexNumber
*
* @author Ismael Mosquera Rivera
*
*/
public final class Fourier
{

/**
* This method computes the FFT of the signal passed as parameter.
* @param v Floating point vector signal.
*
* @return <code>ComplexNumber</code> array with the FFT information data.
*
*/
public static ComplexNumber[] fft(float[] v)
{
	ComplexNumber[] c = ComplexNumber.getComplexNumberArray(v);
	int last_pos = c.length-1;
	_zp = c[last_pos].getImag();
ComplexNumber[] z = fft(c);
z[last_pos] = new ComplexNumber(0.0f, _zp);
return z;
}

/**
* This method computes the IFFT of the <code>ComplexNumber</code> array passed as parameter.
* It is supposed that the complex number array contains a FFT.
* That is, if we get a complex number array resulting to compute the FFT from a signal,
* if we take that array and compute its IFFT, we should get the original signal.
* @param c Complex number array with FFT information data.
*
* @return a floating point vector signal.
*
*/
public static float[] ifft(ComplexNumber[] c)
{
int len = c.length;
int zp = (int)(c[len-1].getImag());
if(len % 2 != 0) len--;
ComplexNumber[] complex = new ComplexNumber[len];
for(int i = 0; i < len; i++) complex[i] = c[i].conjugated();
complex = fft(complex);
for(int i = 0; i < complex.length; i++) complex[i] = complex[i].conjugated();
for(int i = 0; i < len; i++) complex[i] = complex[i].scale(1.0f/(float)len);
int size = (c.length-(zp+1));
float[] out = new float[size];
for(int i = 0; i < size; i++) out[i] = complex[i].getReal();
return out;
}

/*
* Actually, this private method computes the FFT.
*/
private static ComplexNumber[] fft(ComplexNumber[] complex)
{
if(complex.length == 1) return new ComplexNumber[] {complex[0]};
ComplexNumber z = new ComplexNumber();
int len = complex.length;
if(len % 2 != 0) len--;
ComplexNumber[] tmp = new ComplexNumber[len/2];
int n = tmp.length;
for(int i = 0; i < n; i++)
{
tmp[i] = complex[2*i];
}
ComplexNumber[] even = fft(tmp);
for(int i = 0; i < n; i++)
{
	tmp[i] = complex[2*i+1];
}
ComplexNumber[] odd = fft(tmp);
ComplexNumber[] out = new ComplexNumber[complex.length];
for(int i = 0; i < n; i++)
{
double ith = -2.0 * (double)i * Math.PI / (double)len;
z.setReal((float)Math.cos(ith));
z.setImag((float)Math.sin(ith));
out[i] = even[i].add(z.mul(odd[i]));
out[i+n] = even[i].sub(z.mul(odd[i]));
}
return out;
}


/*
* Private constructor, so that this class cannot be instantiated.
*/
private Fourier() {}

private static float _zp;
}

// END
