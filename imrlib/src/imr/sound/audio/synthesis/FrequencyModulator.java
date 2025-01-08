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
* FrequencyModulator.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.synthesis;

/**
* The <code>FrequencyModulator</code> class performs fm synthesis.
* It subclasses the abstract <code>Modulator</code> class.
* That class modulates a pure sinusoidal which acts as a carrier.
* You have the following kind of signals to modulate such a sinusoid:
* <ul>
* <li>Saw</li>
* <li>Sine</li>
* <li>Square</li>
* <li>Triangular</li>
* </ul>
* So, you can choose different kind of signals from the modulator.
*
* @see imr.sound.audio.synthesis.Modulator
* @see imr.sound.audio.synthesis.Envelope
* @see imr.sound.audio.synthesis.Oscillator
* @see imr.sound.audio.synthesis.WaveType
*
* @author Ismael Mosquera Rivera
*
*/
public class FrequencyModulator extends Modulator
{

/**
* Constructor.
* Makes a new instance object of this class.
* This is the default constructor.
* Actually, it calls the superclass constructor.
*/
public FrequencyModulator()
{
super();
}

/**
* constructor.
* Makes a new instance for an object of this class.
*
* @param amp Amplitude.
* @param freq Frequency.
* @param sr Sample rate.
*
*/
public FrequencyModulator(float amp, float freq, float sr)
{
	super(amp, freq, sr);
}

/**
* This method performs the synthesis.
* @param frame A byte data frame
*
*/
public void synth(byte[] frame)
{
	if(frame == null || frame.length == 0) return;
float[] _m_ = new float[frame.length];
_modulator.getFrequencyModulator(_m_);
_carrier.setModulator(_m_);
_carrier.read(frame);
if(_applyCEnv)
{
	_cenv.setDuration((float)getCEnvDuration(frame.length));
_cenv.apply(frame);
}
}

}

// END
