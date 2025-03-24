/*
 * Copyright (c) 2024 Ismael Mosquera Rivera
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
* FrameShifter.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.analysis;

import imr.util.iArray;

/**
* The <code>FrameShifter</code> class was thought as an utility class for spectral analysis.
* <p>
* Nevertheless, it could be used in other kind of task.
* <p>
* It shifts n positions from the end to the beginning of a frame, like as a barrel shifter.
* <p>
* So, it can be useful to manage hop size in spectral analysis.
*
* @author Ismael Mosquera Rivera
*
*/
public final class FrameShifter
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>FrameShifter</code> object.
* <p>
* default frame size = 4096
*
*/
public FrameShifter()
{
this(4096);
}

/**
* Constructor.
* <p>
* Makes a new instance for a <code>FrameShifter</code> object.
* @param size Frame size.
*
*/
public FrameShifter(int size)
{
_shiftSize = 0;
_frameSize = size;
_frame = new byte[_frameSize];
}

/**
* Gets the current frame size of this object.
* <p>
*
* @return Frame size.
*
*/
public int getFrameSize()
{
return _frameSize;
}

/**
* Sets the frame size for this object.
* @param size New frame size.
*
*/
public void setFrameSize(int size)
{
_frameSize = size;
_frame = (byte[])iArray.resize(_frame, _frameSize);
}

/**
* Gets the current shifting size of this object.
* <p>
*
* @return Shift size.
*
*/
public int getShiftSize()
{
return _shiftSize;
}

/**
* Sets the shift size for this object.
* @param size New shift size.
*
*/
public void setShiftSize(int size)
{
_shiftSize = size;
}

/**
* Adds data to the shifter.
* @param data A byte array of samples.
*
*/
public void add(byte[] data)
{
	int k = 0;
for(int i = _shiftSize; i < _frameSize; i++) _frame[i] = data[k++];
}

/**
* Gets the current data frame.
* <p>
*
* @return Current frame.
*
*/
public byte[] getFrame()
{
byte[] outFrame = (byte[])iArray.clone(_frame);
shift();
return outFrame;
}


/*
* This method performs shifting.
*
*/
private void shift()
{
int k = (_frameSize - _shiftSize);
for(int i = 0; i < _shiftSize; i++) _frame[i] = _frame[k++];
}


private int _frameSize;
private int _shiftSize;

private byte[] _frame;
}

// END
