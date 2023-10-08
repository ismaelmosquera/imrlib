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
* AMInstrument.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

/**
* The <code>AMInstrument</code> class implements a AM Synthesizer.
* This class encapsulates a <code>Carrier</code> object, and also a <code>Modulator</code> object.
* You can retrieve both objects to set its parameters according to your needs.
* Its synth method will get a synthesized signal according to the length of the byte data passed as parameter.
*
* @see imr.media.audio.Carrier
* @see imr.media.audio.Modulator
*
* @author Ismael Mosquera Rivera
*/
public class AMInstrument
{
/**
* Constructor.
* Makes a new instance object of this class.
* This constructor calls the one with default parameters.
*/
public AMInstrument()
{
this(1.0f, 440.0f, 44100.0f);
}

/**
* Constructor.
* Makes a new instance object of this class.
*
* @param amp Amplitude.
* @param freq Frequency.
* @param sr Sample rate.
*
*/
public AMInstrument(float amp, float freq, float sr)
{
_carrier = new Carrier(amp, freq, sr);
_modulator = new Modulator(amp, freq*2.0f, sr);
}

/**
* This method produces a synthesized signal of the length according to the byte array passed as parameter.
*
* @param data Byte array to be filled with the synthesized signal.
*/
public void synth(byte[] data)
{
_carrier.synth(data);
_modulator.modulate(data);
}

/**
* Gets the <code>Carrier</code> object from this <code>AMInstrument</code> object.
* This is useful to set parameters to the carrier signal.
*
* @return The <code>Carrier</code> object encapsulated by this am synthesizer.
*/
public Carrier getCarrier()
{
return _carrier;
}

/**
* Gets the <code>Modulator</code> object from this <code>AMInstrument</code> object.
* This is useful to set parameters to the modulator signal.
*
* @return The <code>Modulator</code> object encapsulated by this am synthesizer.
*/
public Modulator getModulator()
{
return _modulator;
}


private Carrier _carrier;
private Modulator _modulator;

}

// END
