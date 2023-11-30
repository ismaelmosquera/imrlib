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
* TriangularWave.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

 /**
 * The <code>TriangularWave</code> class generates triangular waves, as you have already guessed.
 * Basically, it is an oscillator generating triangular shaped waves instead of sinusoidal ones.
 *
 * @see imr.media.audio.Modulator
 * @see imr.media.audio.Wave
 *
 * @author Ismael Mosquera Rivera
 */
public class TriangularWave extends Wave
{

/**
* Constructor.
* Makes a new instance of a <code>TriangularWave</code> object.
* This constructor calls the one of its superclass with default parameters.
*
*/
public TriangularWave()
{
	super();
}

/**
* Constructor.
* Makes a new instance of a <code>TriangularWave</code> object.
* This constructor calls the one of its superclass with parameters.
*
* @param amp Amplitude
* @param freq Frequency
* @param sr Sample rate
*
*/
public TriangularWave(float amp, float freq, float sr)
{
	super(amp, freq, sr);
}


/**
* Builds the concrete needed wave.
* In this case, a triangular wave.
*
* @see imr.media.audio.Wave
*/
protected void buildWave()
{
int nsamples = (int)(_sampleRate / _frequency);
int k = (int)(nsamples / 4);
float inc = 1.0f / (float)k;
float value = 0.0f;
float[] tmp = new float[k+1];
tmp[k] = 1.0f;
for(int i = 0; i < tmp.length-1; i++)
{
tmp[i] = value;
value += inc;
}
byte[] up = new byte[tmp.length];
for(int i = 0; i < up.length; i++) up[i] = (byte)(_amplitude * tmp[i] * 127.0f);
byte[] down = new byte[up.length];
for(int i = 0, j = up.length-1; i < down.length; i++, j--) down[i] = up[j];

_wave = new byte[up.length * 4];
int i = 0;
for(int j = 0; j < up.length; j++) _wave[i++] = up[j];
for(int j = 0; j < down.length; j++) _wave[i++] = down[j];
for(int j = 0; j < up.length; j++) _wave[i++] = (byte)(-up[j]);
for(int j = 0; j < down.length; j++) _wave[i++] = (byte)(-down[j]);

_mustUpdate = false;
}

}

// END

