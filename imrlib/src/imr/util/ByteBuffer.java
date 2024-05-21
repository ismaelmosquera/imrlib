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
* ByteBuffer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.util;

/**
* The <code>ByteBuffer</code> class implements a buffer of bytes which can grow dynamically.
* <p>
* You can add byte arrays of arbitrary size to the buffer and retrieve the content of the buffer when you wish.
* If you reset the buffer, it goes back to its initial state.
*
* @author Ismael Mosquera Rivera
*
*/
public final class ByteBuffer
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>ByteBuffer</code> object.
*
*/
public ByteBuffer()
{
_count = 0;
_buffer = new byte[INITIAL_SIZE];
}

/**
* Adds data to the buffer.
* <p>
* If the buffer has not enough capacity available, it grows dynamically.
* @param data byte array to add to the buffer.
*
*/
public void add(byte[] data)
{
	_buffer = (byte[])iArray.resize(_buffer, _count+data.length);
int k = 0;
for(int i = _count; i < _buffer.length; i++) _buffer[i] = data[k++];
_count += data.length;
}

/**
* Gets the contents of the buffer as a byte aray.
* <p>
* @return byte array with the buffer content.
*
*/
public byte[] toByteArray()
{
return _buffer;
}

/**
* Resets the buffer to its initial state.
*
*/
public void reset()
{
_count = 0;
_buffer = new byte[INITIAL_SIZE];
}

/**
* Gets the number of bytes currently in the buffer.
* <p>
* @return buffer size
*
*/
public int size()
{
return _buffer.length;
}


private int _count;
private byte[] _buffer;

private static final int INITIAL_SIZE = 64;
}

// END

