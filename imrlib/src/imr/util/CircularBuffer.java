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
* CircularBuffer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.util;

/**
* The <code>CircularBuffer</code> implements a buffer linking its tail to its head.
* <p>
* A circular buffer is useful to have a memory area where you need a structure suitable to store and serve data
* continously just using a fix amount of memory.
* <p>
*
* @author Ismael Mosquera Rivera
*
*/
@SuppressWarnings("unchecked")
public final class CircularBuffer<T>
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>CircularBuffer</code> object.
* @param capacity Number of elements which can be stored in this buffer.
*
*/
public CircularBuffer(int capacity)
{
this.capacity = capacity;
nElements = 0;
pRead = 0;
pWrite = 0;
pSet = 0;
buffer = new Object[this.capacity];
}

/**
* Reads a single element from this buffer.
* <p>
*
* @return the read element or <code>null</code> if the buffer is empty.
*
*/
public T read()
{
if(nElements == 0) return null;
if(pRead == capacity) pRead = 0;
nElements--;
return (T)buffer[pRead++];
}

/**
* Gets n elements from this buffer.
* <p>
* @param output array where to store the requested number of elements to read.
* <p>
*
* @return number of elements actually read or 0 if the buffer is empty.
*
*/
public int read(T[] output)
{
	if(nElements == 0) return 0;
int elementsToRead = output.length;
if(elementsToRead > nElements) elementsToRead = nElements;
for(int i= 0;i < elementsToRead;i++)
{
	if(pRead == capacity) pRead = 0;
	output[i] = (T)buffer[pRead++];
	nElements--;
}
return elementsToRead;
}

/**
* Writes a single element to this buffer.
* <p>
* @param elem
* Element to write.
* <p>
* If the buffer is full, this method returns, and the element will not be written to the buffer.
*
* /
public void write(T elem)
{
if(nElements == capacity) return;
if(pWrite == capacity) pWrite = 0;
buffer[pWrite++] = elem;
nElements++;
}

/**
* Writes n elements to this buffer.
* <p>
* @param input array with the elements to write.
* <p>
* This method will do the best effort to write all the elements in the input parameter till this buffer gets full.
* <p>
*
* @return number of elements actually written or 0 if the buffer is full.
*
*/
public int write(T[] input)
{
	if(nElements == capacity) return 0;
	int elementsToWrite = input.length;
	int available = capacity-nElements;
	if(elementsToWrite > available) elementsToWrite = available;
	for(int i = 0;i < elementsToWrite;i++)
	{
		if(pWrite == capacity) pWrite = 0;
		buffer[pWrite++] = input[i];
		nElements++;
	}
	return elementsToWrite;
}


/**
* Picks an element from this buffer.
* <p>
* After picking, the content of the buffer is not changed.
* <p>
* @param index.
* Index in the buffer where to pick the desired element.
* <p>
* @return requested element, or null if the index is out of range.
*/
public T pick(int index)
{
	if(index < 0 || index > capacity-1) return null;
	return (T)buffer[index];
}

/**
* Sets an element in the buffer in the current position of the pSet internal pointer.
* <p>
* The pSet internal pointer starts to 0 and increments its position by one for each call.
* <p>
* @param elem
* Element to set at the current pSet pointer position.
*
*/
public void set(T elem)
{
if(pSet == capacity) pSet = 0;
buffer[pSet++] = elem;
}

/**
* Sets an element to the buffer at the index position.
* <p>
* @param elem
* Element to set.
* <p>
* @param index
* Buffer index where to set the element.
*
*/
public void set(T elem, int index)
{
if(index < 0 || index > capacity-1) return;
buffer[index] = elem;
}

/**
* Evaluates if this buffer is empty.
* <p>
*
* @return true if the buffer is empty or false otherwise.
*
*/
public synchronized boolean isEmpty()
{
	return (nElements == 0);
}

/**
* Evaluates if this buffer is full.
* <p>
*
* @return true if the buffer is full or false otherwise.
*
*/
public synchronized boolean isFull()
{
return (nElements == capacity);
}

/**
* Gets the capacity ( number of elements which can be hold in this buffer ).
* <p>
*
* @return capacity of this buffer.
*
*/
public int getCapacity()
{
return capacity;
}

/**
* Gets the number of elements currently stored in this buffer.
* <p>
*
* @return number of elements stored in this buffer.
*
*/
public int getNumberOfElements()
{
return nElements;
}

/**
* Gets the available space ( number of elements which can be added to this buffer ).
* <p>
*
* @return availabel space in this buffer.
*
*/
public int getAvailableSpace()
{
return (capacity-nElements);
}

/**
* Resets this buffer.
* <p>
* That is, gets this buffer back to its original state.
*
*/
public void reset()
{
pRead =0;
pWrite = 0;
pSet = 0;
nElements = 0;
}


private int capacity;
private int nElements;
private int pRead;
private int pWrite;
private int pSet;
private Object[] buffer;

}

// END
