/*
 * Copyright (c) 2023 Ismael Mosquera Rivera
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
* SineWave.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

/**
* The <code>SineWave</code> class generates sinusoidal waves.
* Basically, it acts like an oscillator generating sinusoids.
*
* @see imr.media.audio.Wave
*
* @author Ismael Mosquera Rivera
*/
public class SineWave extends Wave
{

/**
* Constructor.
* Makes a new instance of a <code>SineWave</code> object.
* This constructor calls the one from its superclass with default parameters.
*
*/
public SineWave()
{
	super();
}

/**
* Constructor.
* Makes a new instance of a <code>SineWave</code> object.
* This constructor calls the one from its superclass with parameters.
*
* @param amp Amplitude
* @param freq Frequency
* @param sr Sample rate
*
*/
public SineWave(float amp, float freq, float sr)
{
	super(amp, freq, sr);
}


/**
* Builds the concrete needed wave.
* In this case, it is a sinusoidal.
*
* @see imr.media.audio.Wave
*/
protected void buildWave()
{
	int nsamples = (int)(_sampleRate / _frequency);
float inc = 2.0f * (float)Math.PI * _frequency / _sampleRate;
_wave = new byte[nsamples+1];
float phase = 0.0f;
for(int i = 0; i < _wave.length; i++)
{
_wave[i] = (byte)(_amplitude * (float)Math.cos((double)phase) * 127.0f);
phase += inc;
if(phase > 2.0f * Math.PI) phase -= 2.0f * Math.PI;
}
_mustUpdate = false;
}

}

// END
