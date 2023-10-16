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
* SquareWave.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

/**
* The <code>SquareWave</code> class generates square waves, as you have already guessed.
* Basically, it is an oscillator generating square shaped waves instead of sinusoidal ones.
*
* @see imr.media.audio.Modulator
* @see imr.media.audio.Wave
*
* @author Ismael Mosquera Rivera
*/
public class SquareWave extends Wave
{

/**
* Constructor.
* Makes a new instance of a <code>SquareWave</code> object.
* This constructor calls the one of its superclass with default parameters.
*
*/
public SquareWave()
{
	super();
}

/**
* Constructor.
* Makes a new instance of a <code>SquareWave</code> object.
* This constructor calls the one of its superclass with parameters.
*
* @param amp Amplitude
* @param freq Frequency
* @param sr Sample rate
*
*/
public SquareWave(float amp, float freq, float sr)
{
	super(amp, freq, sr);
}


/**
* Builds the concrete needed wave.
* In this case, a square wave.
*
* @see imr.media.audio.Wave
*/
protected void buildWave()
{
int nsamples = (int)(_sampleRate / _frequency);
if(nsamples % 2 != 0) nsamples -= 1;
_wave = new byte[nsamples];
int kn = nsamples / 2;
for(int i = 0; i < kn; i++) _wave[i] = (byte)(_amplitude * 127.0f);
for(int i = kn; i < nsamples; i++) _wave[i] = (byte)(_amplitude * -127.0f);

_mustUpdate = false;
}

}

// END

