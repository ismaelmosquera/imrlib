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
* MusicalSynthesizer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

/**
* The <code>MusicalSynthesizer</code> class encapsulates an <code>Envelope</code> object and a <code>MusicalNote</code> object.
* So, this class generates objects with capability to dispatch enveloped musical notes.
* A <code>MusicalNote</code> object produces four-harmonic sinusoidal sounds.
*
* @see imr.media.audio.Synthesizer
* @see imr.media.audio.Envelope
* @see imr.media.audio.MusicalNote
*
* @author Ismael Mosquera Rivera
*
*/
public class MusicalSynthesizer extends Synthesizer
{

/**
* Constructor.
* Makes a new instance of a <code>MusicalSynthesizer</code> object.
* This constructor calls the one of its superclass with default parameters.
*
* @see imr.media.audio.Synthesizer
*/
public MusicalSynthesizer()
{
super();
}

/**
* Constructor.
* Makes a new instance of a <code>MusicalSynthesizer</code> object.
* This constructor calls the one of its superclass with parameters.
*
* @param amp Amplitude
* @param freq Frequency
* @param sr Sample rate
*
* @see imr.media.audio.Synthesizer
*/
public MusicalSynthesizer(float amp, float freq, float sr)
{
super(amp, freq, sr);
}

/**
* Sets odd and even harmonics to be synthesized.
* That is, if F0 is the fundamental frequency then, all harmonics mean:
* F0 + nF1 + nF2 + nF3
* when n = 2, 3, 4.
*
* @see imr.media.audio.MusicalNote
*
*/
public void allHarmonics()
{
((MusicalNote)_wave).allHarmonics();
}

/**
* Sets only even harmonics to be synthesized.
* That is, if F0 is the fundamental frequency then, even harmonics mean:
* F0 + 2nF1 + 2nF2 + 2nF3
* when n = 1, 2, 3.
*
* @see imr.media.audio.MusicalNote
*
*/
public void evenHarmonics()
{
((MusicalNote)_wave).evenHarmonics();
}

/**
* Sets only odd harmonics to be synthesized.
* That is, if F0 is the fundamental frequency then, odd harmonics mean:
* F0 + (2n+1)F1 + (2n+1)F2 + (2n+1)F3
* when n = 1, 2, 3.
*
* @see imr.media.audio.MusicalNote
*
*/
public void oddHarmonics()
{
((MusicalNote)_wave).oddHarmonics();
}

/**
* Sets the harmonic amplitudes n = 1, 2, 3.
* @param a1 Amplitude for the first harmonic.
* @param a2 Amplitude for the second harmonic.
* @param a3 Amplitude for the third harmonic.
*
* @see imr.media.audio.MusicalNote
*
*/
public void setHarmonicAmplitudes(float a1, float a2, float a3)
{
((MusicalNote)_wave).setHarmonicAmplitudes(a1, a2, a3);
}


/**
* Sets the wave type to be generated.
* In this case, it is a <code>MusicalNote</code> object (4-harmonic sinusoidal wave).
*
* @param amp Amplitude
* @param freq Frequency
* @param sr Sample rate
*
* @see imr.media.audio.Synthesizer
*/
protected void setWave(float amp, float freq, float sr)
{
_wave = new MusicalNote(amp, freq, sr);
}

}

// END
