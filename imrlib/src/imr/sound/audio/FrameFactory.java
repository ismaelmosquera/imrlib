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

package imr.sound.audio;

/**
* The <code>FrameFactory</code> class is useful to generate data packages.
* This class builds byte frames with the desired size according to its parameters.
* In the other hand, implements useful static methods related to manage frames.
*
* @author Ismael Mosquera rivera.
*/
public final class FrameFactory
{

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
* Builds and gets a byte[] frame from a floating point vector previously normalized.
* @param frame Floating point vector with range [-1 .. 1].
*
* @return byte[] frame.
*
*/
public static byte[] getFrame(float[] frame)
{
byte[] out = new byte[frame.length];
for(int i = 0; i < frame.length; i++) out[i] = (byte)(frame[i] * 127.0f);
return out;
}

/**
* Gets a float frame in the range [-1 .. 1] from a byte frame.
* @param frame Byte frame to be mapped to float in the range [-1 .. 1].
* <p>
*
* @return a floating point vector in the range [-1 .. 1]
*
*/
public static float[] getFrame(byte[] frame)
{
	int len = frame.length;
	float[] out = new float[len];
	for(int i = 0; i < len; i++) out[i] = ((float)frame[i] / 127.0f);
	return out;
}

/**
* Gets the max value from a byte vector as a floating point value.
* @param frame Byte vector.
*
* @return max value as a float.
*
*/
public static float getMax(byte[] frame)
{
float max = 0.0f;
for(int i = 0; i < frame.length; i++)
{
if((float)Math.abs((double)frame[i]) > max) max = (float)Math.abs((double)frame[i]);
}
return max;
}

/**
* Gets the max value from a float vector as a floating point value.
* @param frame Float vector.
*
* @return max value as a float.
*
*/
public static float getMax(float[] frame)
{
float max = 0.0f;
for(int i = 0; i < frame.length; i++)
{
if((float)Math.abs((double)frame[i]) > max) max = (float)Math.abs((double)frame[i]);
}
return max;
}

/**
* Gets a normalized floating point vector from a byte array.
* @param frame Byte array.
*
* @return normalized floating point vector.
*
*/
public static float[] normalizeFrame(byte[] frame)
{
float max = getMax(frame);
if(max == 0.0f)
{
	float[] zeros = new float[frame.length];
	for(int i = 0; i < zeros.length; i++) zeros[i] = 0.0f;
return zeros;
}
float[] out = new float[frame.length];
for(int i = 0; i < frame.length; i++) out[i] = (float)frame[i] / max;
return out;
}

/**
* Gets a normalized floating point vector from a float array.
* @param frame Float array.
*
* @return normalized floating point vector.
*
*/
public static float[] normalizeFrame(float[] frame)
{
float max = getMax(frame);
if(max == 0.0f)
{
	float[] zeros = new float[frame.length];
	for(int i = 0; i < zeros.length; i++) zeros[i] = 0.0f;
	return zeros;
}
float[] out = new float[frame.length];
for(int i = 0; i < frame.length; i++) out[i] = frame[i] / max;
return out;
}

/**
* Flips a floating point vector.
* That is, reverses the vector.
* @param frame A floating point vector to flip
*
* @return flipped vector
*
*/
public static float[] flipFrame(float[] frame)
{
	int n = frame.length;
float[] out = new float[frame.length];
for(int i = 0; i < n; i++) out[i] = frame[n-(i+1)];
return out;
}


/*
* Private constructor, so that this class cannot be instantiated.
*/
private FrameFactory() {}
}

// END
