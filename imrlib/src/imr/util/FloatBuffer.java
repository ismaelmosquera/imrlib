/*
 * Copyright (c) 2025 Ismael Mosquera Rivera
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
* FloatBuffer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.util;

/**
* The <code>FloatBuffer</code> class implements a buffer of float ( atomic data type ) which can grow dynamically.
* <p>
* You can add float arrays of arbitrary size to the buffer and retrieve the content of the buffer when you wish.
* <p>
* If you reset the buffer, it goes back to its initial state.
*
* @author Ismael Mosquera Rivera
*
*/
public final class FloatBuffer
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>FloatBuffer</code> object.
* <p>
* The initial state is an empty buffer.
*/
public FloatBuffer()
{
_buffer = null;
_currentIndex = 0;
_size = 0;
}

/**
* Adds data to the buffer.
* <p>
* @param data Data to be added.
*
*/
public void add(float[] data)
{
if(data == null) return;
_size += data.length;
if(_buffer == null)
{
_buffer = new float[_size];
}
else
{
_buffer = (float[])iArray.resize(_buffer, _size);
}
int k = 0;
for(int i = _currentIndex; i < _size; i++)
{
_buffer[i] = data[k++];
}
_currentIndex = _size;
}

/**
* Evaluates for empty buffer.
* <p>
* @return true if the buffer is empty or false otherwise.
*
*/
public boolean isEmpty()
{
return (_size == 0);
}

/**
* Resets this float buffer to its initial state ( an empty buffer ).
*/
public void reset()
{
_buffer = null;
_currentIndex = 0;
_size = 0;
}

/**
* Gets the current size ( number of elements ) of this float buffer.
* <p>
* @return size of this buffer.
*
*/
public int size()
{
return _size;
}

/**
* Returns the contents of this buffer as a float array.
* <p>
* @return buffer contents as a float array.
*
*/
public float[] toFloatArray()
{
return _buffer;
}


private float[] _buffer;
private int _currentIndex;
private int _size;

}

// END
