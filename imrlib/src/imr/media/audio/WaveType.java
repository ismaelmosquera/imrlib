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
* WaveType.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

/**
* The <code>WaveType</code> class is a utility class with just 4 public constant fields.
* Its 4 public static final int members are self explanatory.
*
* @see imr.media.audio.Modulator
* @see imr.media.audio.AmplitudeModulator
* @see imr.media.audio.FrequencyModulator
* @see imr.media.audio.RingModulator
*
* @author Ismael Mosquera Rivera
*
*/
public class WaveType
{

/**
* Constant to set SawWave
*
*/
public static final int wSaw = 0;

/**
* Constant to set SineWave
*
*/
public static final int wSine = 1;

/**
* Constant to set SquareWave
*
*/
public static final int wSquare = 2;

/**
* Constant to set TriangularWave
*
*/
public static final int wTriangular =3;

}

// END
