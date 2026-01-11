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
* Spectrum.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.analysis;

import imr.util.iArray;
import imr.math.ComplexNumber;
import imr.sound.audio.FrameFactory;

/**
* The <code>Spectrum</code> keeps data resulting from a spectral analysis.
* Gets the FFT and its magnitude and phase spectrum.
* <p>
* In addition, it also computes the magnitude spectrum expressed in dB.
* <p>
* @see imr.sound.audio.analysis.SpectralAnalyzer
*
* @author Ismael Mosquera Rivera.
*
*/
public final class Spectrum
{

/**
* Constructor.
* Makes a new instance of a <code>Spectrum</code> object.
* <p>
* default sample rate = 44100 Hz.
*
*/
public Spectrum()
{
	this(44100.0f);
}

/**
* Constructor.
* Makes a new instance of a <code>Spectrum</code> object.
* <p>
* @param sampleRate SampleRate.
*
*/
public Spectrum(float sampleRate)
{
_spectralRange = (float)(Math.floor((double)sampleRate / 2.0));
_fft = null;
_magnitudeSpectrum = null;
}

/**
* This method computes the FFT and its magnitude an phase spectrum.
* @param v Floating point vector signal.
*
* @see imr.sound.audio.analysis.Fourier
*
*/
public void compute(float[] v)
{
_fft = Fourier.fft(v);
update();
}

/**
* Gets the computed FFT.
*
* @see imr.math.ComplexNumber
*
* @return complex number array with the computed FFT information data.
*
*/
public ComplexNumber[] getFFT()
{
return _fft;
}

/**
* Gets the magnitude spectrum.
*
* @return magnitude spectrum ( linear scale ).
*
*/
public float[] getMagnitudeSpectrum()
{
return (float[])iArray.clone(_magnitudeSpectrum);
}

/**
* Gets the phase spectrum.
*
* @return phase spectrum.
*
*/
public float[] getPhaseSpectrum()
{
	float[] phaseSpec = new float[_magnitudeSpectrum.length];
	int len = phaseSpec.length;
	for(int i = 0; i < len; i++) phaseSpec[i] = _fft[i].argument();
	return phaseSpec;
}

/**
* Gets the amplitude spectrum expressed in dB.
* <p>
*
* @return spectrum expressed in dB ( logarithmic scale )
*/
public float[] getMagnitude2dBSpectrum()
{
	float[] dBSpec = new float[_magnitudeSpectrum.length];
	int len = dBSpec.length;
	for(int i = 0; i < len; i++) dBSpec[i] = ( _magnitudeSpectrum[i] != 0.0f) ? (float)(20.0 * Math.log10((double)_magnitudeSpectrum[i])) : -200.0f;
	return dBSpec;
}

/**
* Computes the energy of the audio signal in terms of frequency.
* <p>
* @return computed energy.
*
*/
public float[] getEnergy()
{
int len = _magnitudeSpectrum.length;
float[] _energy = new float[len];
for(int i = 0; i < len; i++)
{
	_energy[i] = _magnitudeSpectrum[i] * _magnitudeSpectrum[i];
}
return _energy;
}

/**
* Gets the spectral range for this spectrum.
* <p>
* @return spectral range.
*
*/
public float getSpectralRange()
{
return _spectralRange;
}

/**
* Gets the index of the spectrum vector respect to the frequency value passed as parameter.
* <p>
* @param freq Frequency value.
* <p>
* @return the index relative to the frequency value passed as parameter
*
*/
public int getIndexFromFrequency(float freq)
{
return (int)(Math.round((double)freq * (double)_magnitudeSpectrum.length / (double)_spectralRange));
}

/**
* Gets the size of this specturm.
* <p>
* @return size ( number of values ) for this specturm.
*
*/
public int size()
{
return (_magnitudeSpectrum == null) ? 0 : _magnitudeSpectrum.length;
}


// Spectral arithmetic operations

/**
* Spectraal addition.
* @param spec Spectrum to be added.
* <p>
* @return Spectral addition.
*
*/
public Spectrum add(Spectrum spec)
{
	if(_fft == null || spec.getFFT() == null) return null;
assert (_fft.length == spec.getFFT().length): "Bad parameter: length of both spectrums must be equal.";
int n = _fft.length-1;
for(int i = 0; i < n; i++)
{
_fft[i] = _fft[i].add(spec.getFFT()[i]);
}
update();
return this;
}

/**
* Spectraal subtraction.
* @param spec Spectrum to be subtracted.
* <p>
* @return Spectral subtraction.
*
*/
public Spectrum sub(Spectrum spec)
{
	if(_fft == null || spec.getFFT() == null) return null;
assert (_fft.length == spec.getFFT().length): "Bad parameter: length of both spectrums must be equal.";
int n = _fft.length-1;
for(int i = 0; i < n; i++)
{
_fft[i] = _fft[i].sub(spec.getFFT()[i]);
}
update();
return this;
}

/**
* Spectraal product.
* @param spec Spectrum to be multiplied.
* <p>
* @return Spectral product.
*
*/
public Spectrum mul(Spectrum spec)
{
	if(_fft == null || spec.getFFT() == null) return null;
assert (_fft.length == spec.getFFT().length): "Bad parameter: length of both spectrums must be equal.";
	int n = _fft.length-1;
	for(int i = 0; i < n; i++)
	{
	_fft[i] = _fft[i].mul(spec.getFFT()[i]);
	}
	update();
	return this;
}

/**
* Spectraal division.
* @param spec Spectrum to be divided.
* <p>
* @return Spectral division.
*
*/
public Spectrum div(Spectrum spec)
{
	if(_fft == null || spec.getFFT() == null) return null;
assert (_fft.length == spec.getFFT().length): "Bad parameter: length of both spectrums must be equal.";
	int n = _fft.length-1;
	for(int i = 0; i < n; i++)
	{
	_fft[i] = _fft[i].div(spec.getFFT()[i]);
	}
	update();
	return this;
}


/*
* This private method just computes and normalizes the linear magnitude spectrum.
*/
private void update()
{
int len = _fft.length - 1;
int zp = (int)(_fft[len].getImag());
int size = len - zp;
_magnitudeSpectrum = new float[size];
for(int i = 0; i < size; i++)
{
	_magnitudeSpectrum[i] = _fft[i].magnitude();
}
_magnitudeSpectrum = FrameFactory.normalizeFrame(_magnitudeSpectrum);
}


private ComplexNumber[] _fft;
private float[] _magnitudeSpectrum;
private float _spectralRange;
}

// END
