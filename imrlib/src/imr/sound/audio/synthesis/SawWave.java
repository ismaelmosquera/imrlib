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
* SawWave.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.synthesis;

/**
* The <code>SawWave</code> class generates saw waves, as you have already guessed.
* Basically, it is an oscillator generating saw shaped waves instead of sinusoidal ones.
*
* @see imr.sound.audio.synthesis.Modulator
* @see imr.sound.audio.synthesis.Wave
*
* @author Ismael Mosquera Rivera
*/
public class SawWave extends Wave
{

/**
* Constructor.
* Makes a new instance of a <code>SawWave</code> object.
* This constructor calls the one of its superclass with default parameters.
*
*/
public SawWave()
{
	super();
}

/**
* Constructor.
* Makes a new instance of a <code>SawWave</code> object.
* This constructor calls the one of its superclass with parameters.
*
* @param amp Amplitude
* @param freq Frequency
* @param sr Sample rate
*
*/
public SawWave(float amp, float freq, float sr)
{
	super(amp, freq, sr);
}


/**
* Builds the concrete needed wave.
* In this case, a saw wave.
*
* @see imr.sound.audio.synthesis.Wave
*/
protected void buildWave()
{
int nsamples = (int)(_sampleRate / _frequency);
float inc = 2.0f / (float)nsamples;
float value = -1.0f;
float[] tmp = new float[nsamples+1];
tmp[tmp.length-1] = 1.0f;
for(int i = 0; i < tmp.length-1; i++)
{
tmp[i] = value;
value += inc;
}
_wave = new byte[tmp.length];
for(int i = 0; i < _wave.length; i++)
{
	_wave[i] = (byte)(_amplitude * tmp[i] * 127.0f);
}
_mustUpdate = false;
}

}

// END

