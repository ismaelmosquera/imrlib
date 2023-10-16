/*
 * Copyright (c) 202 3 Ismael Mosquera Rivera
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
* MusicalNote.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

/**
* The <code>MusicalNote</code> class generates four-harmonic musical notes.
* Objects of this class can be useful, for example, to combine with other objects to create sophisticated components.
* A <code>MusicalInstrument</code> uses an object of this class and an envelope to perform its task.
*
* @see imr.media.audio.MusicalInstrument
* @see imr.media.audio.Wave
*
* @author Ismael Mosquera Rivera
*/
public class MusicalNote extends Wave
{

/**
* Constructor.
* Makes a new instance of a <code>MusicalNote</code> object.
* This constructor calls the one of its superclass with default parameters.
*
*/
public MusicalNote()
{
	super();
}

/**
* Constructor.
* Makes a new instance of a <code>MusicalNote</code> object.
* This constructor calls the one of it superclass with parameters.
*
* @param amp Amplitude
* @param freq Frequency
* @param sr Sample rate
*
*/
public MusicalNote(float amp, float freq, float sr)
{
	super(amp, freq, sr);
	allHarmonics();
	setHarmonicAmplitudes(_amplitude/4.0f, _amplitude/8.0f, _amplitude/16.0f);
}

/**
* Sets odd and even harmonics to be synthesized.
* That is, if F0 is the fundamental frequency then, all harmonics mean:
* F0 + nF1 + nF2 + nF3
* when n = 2, 3, 4.
*
* @see imr.media.audio.MusicalInstrument
*
*/
public void allHarmonics()
{
_fh[0] = 2.0f;
_fh[1] = 3.0f;
_fh[2] = 4.0f;
_mustUpdate = true;
}

/**
* Sets only even harmonics to be synthesized.
* That is, if F0 is the fundamental frequency then, even harmonics mean:
* F0 + 2nF1 + 2nF2 + 2nF3
* when n = 1, 2, 3.
*
* @see imr.media.audio.MusicalInstrument
*
*/
public void evenHarmonics()
{
_fh[0] = 2.0f;
_fh[1] = 4.0f;
_fh[2] = 6.0f;
_mustUpdate = true;
}

/**
* Sets only odd harmonics to be synthesized.
* That is, if F0 is the fundamental frequency then, odd harmonics mean:
* F0 + (2n+1)F1 + (2n+1)F2 + (2n+1)F3
* when n = 1, 2, 3.
*
* @see imr.media.audio.MusicalInstrument
*
*/
public void oddHarmonics()
{
_fh[0] = 3.0f;
_fh[1] = 5.0f;
_fh[2] = 7.0f;
_mustUpdate = true;
}

/**
* Sets the harmonic amplitudes n = 1, 2, 3.
* @param ah1 Amplitude for the first harmonic.
* @param ah2 Amplitude for the second harmonic.
* @param ah3 Amplitude for the third harmonic.
*
* @see imr.media.audio.MusicalInstrument
*
*/
public void setHarmonicAmplitudes(float ah1, float ah2, float ah3)
{
_ah[0] = ah1;
_ah[1] = ah2;
_ah[2] = ah3;
_mustUpdate = true;
}


/**
* Builds the concrete needed wave.
*
* @see imr.media.audio.Wave
*/
protected void buildWave()
{
	int nsamples = (int)(_sampleRate / _frequency);
float phase = 0.0f;
float inc = 2.0f * (float)Math.PI * _frequency / _sampleRate;
float[] f0 = new float[nsamples+1];
for(int i = 0; i < f0.length; i++)
{
f0[i] = _amplitude * (float)Math.cos((double)phase);
phase += inc;
if(phase > 2.0f * Math.PI) phase -= 2.0f * Math.PI;
}
float[] f1 = new float[f0.length];
phase = 0.0f;
inc = 2.0f * (float)Math.PI * (_frequency*_fh[0]) / _sampleRate;
float amp = _ah[0];
for(int i = 0; i < f1.length; i++)
{
f1[i] = amp * (float)Math.cos((double)phase);
phase += inc;
if(phase > 2.0f * Math.PI) phase -= 2.0f * Math.PI;
}
float[] f2 = new float[f0.length];
phase = 0.0f;
inc = 2.0f * (float)Math.PI * (_frequency*_fh[1]) / _sampleRate;
amp = _ah[1];
for(int i = 0; i < f2.length; i++)
{
f2[i] = amp * (float)Math.cos((double)phase);
phase += inc;
if(phase > 2.0f * Math.PI) phase -= 2.0f * Math.PI;
}
float[] f3 = new float[f0.length];
phase = 0.0f;
inc = 2.0f * (float)Math.PI * (_frequency*_fh[2]) / _sampleRate;
amp = _ah[2];
for(int i = 0; i < f3.length; i++)
{
f3[i] = amp * (float)Math.cos((double)phase);
phase += inc;
if(phase > 2.0f * Math.PI) phase -= 2.0f * Math.PI;
}

float[] sum = new float[f0.length];
for(int i = 0; i < sum.length; i++)
{
	sum[i] = f0[i]+f1[i]+f2[i]+f3[i];
}
float max = getMax(sum);
_wave = new byte[sum.length];
for(int i = 0; i < _wave.length; i++)
{
	_wave[i] = (byte)((sum[i]/max) * 127.0f);
}
_mustUpdate = false;
}

private float getMax(float[] x)
{
	if(x.length == 0) return 1.0f;
float max = Math.abs(x[0]);
for(int i = 1; i < x.length; i++)
{
	if(Math.abs(x[i]) > max) max = Math.abs(x[i]);
}
return max;
}

{
_ah = new float[3];
_fh = new float[3];
}

private float[] _ah;
private float[] _fh;
}

// END
