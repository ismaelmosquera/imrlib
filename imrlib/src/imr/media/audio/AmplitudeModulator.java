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
* AmplitudeModulator.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

/**
* The <code>AmplitudeModulator</code> class performs am synthesis.
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
* @see imr.media.audio.Modulator
* @see imr.media.audio.Envelope
* @see imr.media.audio.Oscillator
* @see imr.media.audio.WaveType
*
* @author Ismael Mosquera Rivera
*
*/
public class AmplitudeModulator extends Modulator
{

/**
* Constructor.
* Makes a new instance object of this class.
* This is the default constructor.
* Actually, it calls the superclass constructor.
*/
public AmplitudeModulator()
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
public AmplitudeModulator(float amp, float freq, float sr)
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
_carrier.read(frame);
if(_applyCEnv)
{
	_cenv.setDuration((float)getCEnvDuration(frame.length));
_cenv.apply(frame);
}
_modulator.applyAmplitudeModulation(frame);
}

}

// END
