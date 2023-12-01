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
* FraneFactory.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

/**
* The <code>FrameFactory</code> class is useful to generate data packages.
* This class builds byte frames with the desired size according to its parameters.
*
* @author Ismael Mosquera rivera.
*/
public class FrameFactory
{

	/**
	* Gets a byte frame which size is determined by its parameters.
	*
	* @param sr Sample rate
	* @param ms Duration expressed in ms ( milliseconds )
	*
	* @return byte[] Frame
	*/
public static byte[] getFrame(int sr, int ms)
{
boolean condition = (sr == 11025 || sr == 22050 || sr == 44100);
assert condition: "bad value for sample rate; allowed values: 11025, 22050 and 44100";
assert (ms > 0): "bad duration: ms must be greater than zero.";
int frame_size = (int)((ms * (sr*2)) / 1000);
frame_size -= (frame_size % 4);
byte[] frame = new byte[frame_size];
for(int i = 0; i < frame.length; i++) frame[i] = (byte)0x00;
return frame;
}

/**
* Gets a clone of the byte[] frame passed as parameter.
* @param frame A byte[] data frame
*
* @return A clone of the frame passed as parameter
*/
public static byte[] cloneFrame(byte[] frame)
{
	byte[] out_frame = new byte[frame.length];
	for(int i = 0; i < out_frame.length; i++) out_frame[i] = (byte)frame[i];
	return out_frame;
}
}

// END
