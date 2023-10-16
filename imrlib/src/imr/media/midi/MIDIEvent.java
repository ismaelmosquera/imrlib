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
* MIDIEvent.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.midi;

/**
* A <code>MIDIEvent</code> has a <code>MIDIMessage</code> and a time stamp expressed in ticks.
* @see MIDIMessage
* @author Ismael Mosquera Rivera
*/
public class MIDIEvent
{

/**
* Makes a new instance of a <code>MIDIEvent</code> object
* @param m a MIDI message for this MIDIEvent.
* @param t a time stamp in ticks for this MIDIEvent.
*/
public MIDIEvent(MIDIMessage m,long t)
{
msg=m;
ticks=t;
}

/**
* Gets a concrete byte from the MIDIMessage in this MIDIEvent.
* @param i an index between 0 and 2.
* A MIDIMesage has 3 bytes as much.
* @return the byte requested.
* @see MIDIMessage
*/
public final byte get(int i)
{
return msg.get(i);
}

/**
* Sets a concrete byte for the MIDIMessage in this MIDIEvent.
* @param i an index between 0 and 2.
* A MIDIMessage has 3 bytes as much.
* @param b the byte to set for that index.
* @see MIDIMessage
*/
public final void set(int i,byte b)
{
msg.set(i,b);
}

/**
* Gets the time stamp in ticks for this MIDIEvent.
* @return t time stamp.
*/
public final long getTicks()
{
return ticks;
}

/**
* Sets the time stamp in ticks for this MIDIEvent
* @param t time stamp to set.
*/
public final void setTicks(long t)
{
ticks=t;
}

private MIDIMessage msg;
private long ticks;
}

// END
