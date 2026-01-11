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
* RandomNoise.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.synthesis;

import imr.math.RandomNumberGenerator;

/**
* The <code>RandomNoise</code> class generates random noise signals.
*
* @author Ismael Mosquera Rivera
*
*/
public class RandomNoise
{

/**
* Constructor.
* Makes a new instance object of this class.
* The default amplitude value is set to 1.0f
*
*/
public RandomNoise()
{
_amplitude = 1.0f;
}

/**
* Gets the current amplitude value from this object.
*
* @return amplitude value
*/
public float getAmplitude()
{
return _amplitude;
}

/**
* Sets the amplitude value for this object.
* The amplitude value must be in the [0..1] range.
* @param amp Amplitude value
*
*/
public void setAmplitude(float amp)
{
assert (amp >= 0.0f && amp <= 1.0f): "Bad amplitude value: the value must be in the range [0..1]";
_amplitude = amp;
}

/**
* Reads a random noise signal.
* Actually, what this method does is to fill the frame passed
* as parameter with the random noise signal information.
* @param frame Byte data frame to be filled
*
* @return number of bytes read
*
*/
public int read(byte[] frame)
{
if(frame == null || frame.length == 0) return -1;
for(int i = 0; i < frame.length; i++)
{
frame[i] = (byte)(_amplitude * (float)RandomNumberGenerator.generate(MIN, MAX));
}
return frame.length;
}


private float _amplitude;
private static final int MIN = -127;
private static final int MAX = 127;
}

// END
