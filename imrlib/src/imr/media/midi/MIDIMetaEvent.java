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
* MIDIMetaEvent.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.midi;

/**
* A <code>MIDIMetaEvent</code> extends <code>MIDIEvent</code>
* So it has, a <code>MIDIMessage</code> and a time stamp in ticks.
* In addition a <code>MIDIMetaEvent</code> has a byte array with additional data.
* @see MIDIEvent
* @see MIDIMessage
* @author Ismael Mosquera Rivera
*/
public final class  MIDIMetaEvent extends MIDIEvent
{

/**
* Makes a new instance of a <code>MIDIMetaEvent</code> object.
* @param m a <code>MIDIMessage</code> object.
* @param t a time stamp in ticks.
* @param dataSize the length of the byte array.
*/
public MIDIMetaEvent(MIDIMessage m,long t,int dataSize)
{
super(m,t);
data = new byte[dataSize];
}

/**
* A byte array with additional data.
*/
public byte[] data;
}

// END
