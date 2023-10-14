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
* Carrier.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

/**
* The <code>Carrier</code> class implements a synthesizer to generate sinusoids at any frequency.
* The difference with the <code>Wave</code> or <code>Oscillator</code> classes is the incorporation of an <code>Envelope</code> object.
* So, this class generates sinusoidals waves when an envelope  can be applied.
* We coded this class just for clarify the functionallity offered by the <code>AMInstrument</code> class.
*
* @author Ismael Mosquera Rivera
*/
public class Carrier extends Synthesizer
{
/**
* Constructor.
* Makes a new instance object of this class.
* Actually, what this constructor does is calling the default constructor of its super class.
*/
public Carrier()
{
super();
}

/**
* Constructor.
* Makes a new instance object of this class.
* @param amp Amplitude.
* @param freq Frequency.
* @param sr Sample rate.
*
*/
public Carrier(float amp, float freq, float sr)
{
super(amp, freq, sr);
}


/**
* This method must be implemented by any class inherited from  the <code>Synthesizer</code> abstract class.
*
* The implementation of this method determines the kind of wave generated.
* In this case, it is a <code>SineWave</code>
*
* @param amp Amplitude.
* @param freq Frequency.
* @param sr Sample rate.
*
*/
protected void setWave(float amp, float freq, float sr)
{
_wave = new SineWave(amp, freq, sr);
}

}

// END
