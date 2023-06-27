/*
 * Copyright (c) 2021-2022 Ismael Mosquera Rivera
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
* MIDISysExEvent.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.midi;

/**
* A <code>MIDISysExEvent</code> extends <code>MIDIEvent</code>
* so it has, a <code>MIDIMessage</code> and a time stamp in tiks.
* In addition, it has a byte array with additional data.
* @see MIDIEvent
* @see MIDIMessage
* @author Ismael Mosquera Rivera
*/
public final class MIDISysExEvent extends MIDIEvent
{

/**
* Makes a new instance of a <code>MIDISysExEvent</code> object.
* @param m a <code>MIDIMessage</code> object.
* @param t a time stamp in ticks.
* @param dataSize the length of the byte array with additional data.
*/
	public MIDISysExEvent(MIDIMessage m,long t,int dataSize)
	{
	super(m,t);
	data = new byte[dataSize];
	}

/**
* The byte array to store the additional data.
*/
	public byte[] data;
}

// END
